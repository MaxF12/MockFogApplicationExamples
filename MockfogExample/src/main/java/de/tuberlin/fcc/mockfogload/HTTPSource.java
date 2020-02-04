package de.tuberlin.fcc.mockfogload;

import spark.Spark;

import static spark.Spark.post;
import static spark.Spark.stop;

public class HTTPSource extends Source {
    HTTPSource(int port) {
        Spark.port(port);
        post("/", (request, response) -> {
            try {
                String body = request.body();
                Source.handleBody(body);

            } catch (Exception e) {
                response.status(400);
                return e;
            }

            response.status(200);
            return "OK";
        });
    }

    @Override
    void close() {
        stop();
    }
}
