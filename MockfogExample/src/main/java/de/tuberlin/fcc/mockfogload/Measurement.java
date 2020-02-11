package de.tuberlin.fcc.mockfogload;

public class Measurement {
    String type;
    String source;
    double value;
    long time;

    public Measurement(String type, double value, long time, String source) {
        this.type = type;
        this.value = value;
        this.time = time;
        this.source = source;
    }
}
