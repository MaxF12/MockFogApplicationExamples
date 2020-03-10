package de.tuberlin.fcc.mockfogload;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;

public abstract class Main {
	public static Config config;

	private static boolean isYamlFilename(String s){
		String[] x = s.split("\\.");
		return x.length == 2 && x[1].equals(".yaml");
	}

	private static String ensureFile(String[] args) throws IOException {
		final String FALLBACK = "config.yaml";;

		if (args.length == 0) {
			return FALLBACK;
		}
		String arg = args[0];

		if (isYamlFilename(arg)) {
			return arg;
		}
		arg = System.getenv().get(arg);

		if (arg == null){
			return FALLBACK;
		}

		if (isYamlFilename(arg)) {
			return arg;
		}
		File file = File.createTempFile("config", "yaml");
		new FileOutputStream(file).write(arg.getBytes());
		file.deleteOnExit();
		return file.getAbsolutePath();
	}

	private static void loadConfig(String[] args) throws IOException {
		String filename = ensureFile(args);

		Yaml yaml = new Yaml(new Constructor(de.tuberlin.fcc.mockfogload.Config.class));
		InputStream inputStream = new FileInputStream(new File(filename));
		config =  yaml.load(inputStream);
	}

	public static void main(String[] args) throws IOException {
		loadConfig(args);

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