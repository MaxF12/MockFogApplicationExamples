package de.tuberlin.fcc.mockfogload;

public class Measurement {
    String type;
    String source;
    double value;
    int time;

    public Measurement(String type, double value, int time, String source) {
        this.type = type;
        this.value = value;
        this.time = time;
        this.source = source;
    }
}
