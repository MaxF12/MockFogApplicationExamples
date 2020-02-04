package de.tuberlin.fcc.mockfogload;

import java.util.List;

public class Config {
    String email;
    List<MeasurementType> measurementTypes;
    List<SourceConfig> sources;
    List<SinkConfig> sinks;

    public List<SinkConfig> getSinks() {
        return sinks;
    }

    public void setSinks(List<SinkConfig> sinks) {
        this.sinks = sinks;
    }

    public List<SourceConfig> getSources() {
        return sources;
    }

    public void setSources(List<SourceConfig> sources) {
        this.sources = sources;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<MeasurementType> getMeasurementTypes() {
        return measurementTypes;
    }

    public void setMeasurementTypes(List<MeasurementType> measurementTypes) {
        this.measurementTypes = measurementTypes;
    }

    public static class MeasurementType {
        String name;
        double lowerThreshold;
        double upperThreshold;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getLowerThreshold() {
            return lowerThreshold;
        }

        public void setLowerThreshold(double lowerThreshold) {
            this.lowerThreshold = lowerThreshold;
        }

        public double getUpperThreshold() {
            return upperThreshold;
        }

        public void setUpperThreshold(double upperThreshold) {
            this.upperThreshold = upperThreshold;
        }
    }

    public static class SourceConfig{
        String type;
        int port;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }

    public static class SinkConfig {
        String type;
        String address;
        int port;

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
