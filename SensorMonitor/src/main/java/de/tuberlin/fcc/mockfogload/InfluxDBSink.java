package de.tuberlin.fcc.mockfogload;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;

class InfluxDBSink extends Sink{
    InfluxDB db;

    public InfluxDBSink(String address, int port, String user, String password) {
        String url = address + ":" + port;
        db = InfluxDBFactory.connect(url, user, password);

        String dbName = "HeartRate";

        db.query(new Query("CREATE DATABASE " + dbName));
        db.setDatabase(dbName);
    }

    @Override
    void send(Measurement msg) {
        db.write(Point.measurement(msg.type)
                .addField("ingestion_time", System.currentTimeMillis()) // Accurate assuming db is hosted on localhost.
                .addField("value", msg.value)
                .addField("generation_time", msg.time)
                .addField("source", msg.source)
                .build());
    }

    @Override
    void close() {
        db.close();
    }


}