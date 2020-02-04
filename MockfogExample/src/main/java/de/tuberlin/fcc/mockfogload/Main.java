package de.tuberlin.fcc.mockfogload;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import static spark.Spark.post;

public abstract class Main {
	public static Config config;

	public static void main(String[] args) throws Exception {
		Yaml yaml = new Yaml(new Constructor(Config.class));
		InputStream inputStream = new FileInputStream(new File("config.yaml"));
		config = (Config) yaml.load(inputStream);

		for (Config.SourceConfig sourceConfig:config.sources){
			Source.create(sourceConfig);
		}

		for (Config.SinkConfig sinkConfig:config.sinks){
			Sink.create(sinkConfig);
		}

		Runtime.getRuntime().addShutdownHook(new Thread(()->{
			Source.closeAll();
			Sink.closeAll();
		}));
	}
}