package de.tuberlin.fcc.mockfogload;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Collection;

import static de.tuberlin.fcc.mockfogload.Main.config;
import static spark.Spark.post;

public abstract class Source {
    private static Collection<Source> sources = new ArrayList<>();
    public static void create(Config.SourceConfig config) {
        Source s;
        switch (config.type){
            case "http":
                s = new HTTPSource(config.port);
                break;
            case "udp":
                s = new UDPSource(config.port);
                break;
            default:throw new IllegalArgumentException();
        }

        sources.add(s);

    }

    abstract void close();

    static void closeAll(){
        sources.forEach(Source::close);
    }

    static void handleBody(String body) {
        JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();

        for (Config.MeasurementType type:config.measurementTypes){
            String receivedType = jsonObject.get("type").getAsString();

            if (!receivedType.equals(type.name)){
                continue;
            }

            double value = jsonObject.get("value").getAsDouble();

            if (value < type.lowerThreshold){
                System.out.println("Measurement for "+type.name+" violated lower threshold: "+value +" < "+type.lowerThreshold);
            }

            if (value > type.upperThreshold){
                System.out.println("Measurement for "+type.name+" violated upper threshold: "+value +" > "+type.upperThreshold);
            }

            Sink.sendToSinks(new Measurement(
                    receivedType,
                    value,
                    jsonObject.get("time").getAsInt(),
                    jsonObject.get("source").getAsString()));
        }


    }

}

