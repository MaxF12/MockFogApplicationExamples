package de.tuberlin.fcc.mockfogload;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;

import static de.tuberlin.fcc.mockfogload.Main.config;
import static spark.Spark.post;

public abstract class Sink {
    private static Collection<Sink> sinks = new ArrayList<>();

    public static void sendToSinks(Measurement msg){
        for (Sink s:sinks){
            s.send(msg);
        }
    }

    abstract void close();

    static void closeAll(){
        sinks.forEach(Sink::close);
    }

    abstract void send(Measurement msg);

    public static void create(Config.SinkConfig config) {
        Sink s;
        switch (config.type){
            case "udp": s = new UDPSink(config.address, config.port);
            break;
            case "influxDB": s = new InfluxDBSink(config.address, config.port, config.user, config.password);
            break;
            default:throw new IllegalArgumentException(config.type);
        }

        sinks.add(s);
    }

    private static void handleBody(String body) {
        JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();

        for (Config.MeasurementType type:config.measurementTypes){
            JsonElement measurement = jsonObject.get(type.name);
            if (measurement == null){
                continue;
            }

            double value = measurement.getAsDouble();

            if (value < type.lowerThreshold){
                System.out.println("Measurement for "+type.name+" violated lower threshold: "+value +" < "+type.lowerThreshold);
            }

            if (value > type.upperThreshold){
                System.out.println("Measurement for "+type.name+" violated upper threshold: "+value +" > "+type.upperThreshold);
            }
        }
    }
}

