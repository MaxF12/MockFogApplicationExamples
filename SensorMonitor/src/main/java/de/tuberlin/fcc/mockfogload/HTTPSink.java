package de.tuberlin.fcc.mockfogload;

import com.google.gson.Gson;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HTTPSink extends Sink {
	HttpClient client;
	URI uri;
	public HTTPSink(String address, int port) {
		this.client = HttpClient.newBuilder()
				.version(HttpClient.Version.HTTP_2)
				.build();

		try {
			uri = new URIBuilder(address).setPort(port).build();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	void close() {
	}

	@Override
	void send(Measurement msg) {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://openjdk.java.net/"))
				.timeout(Duration.ofMinutes(1))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(msg)))
				.build();
		try {
			client.send(request, HttpResponse.BodyHandlers.discarding());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException ignored) {

		}
	}
}
