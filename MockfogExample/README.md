# Sensor Monitor

This applications purpose is to read sensors, detect if a measurement violates a threshold and forward the data.

It can:
- Receive measurements from multiple heterogeneous sensors via HTTP or UDP.
- Check the read measurements against configured thresholds and write a notification to stdout if a threshold is violated.
- Forward the measurements to an InfluxDB database. Said database can act as a backend for a Grafana dashboard. This way the measurements can be visualized and monitored.  
- Send and receive measurements from and to other application nodes. This makes it possible to construct arbitrary topologies of application nodes.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

- [Maven](https://maven.apache.org/)
- [JDK (Tested with Oracle JDK 8)](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)

### Building

Build an executable JAR containing all the dependencies with maven:
```
mvn clean package
```

### Configuration

The behaviour of an individual application node is defined by a config file. Here is an example:

```
sources:
  - type: http
    port: 4567
  - type: udp
    port: 4567
measurementTypes:
  - name: heartRate
    lowerThreshold: 60
    upperThreshold: 100
sinks:
  - type: udp
    port: 4567
  - type: influxDB
    address: http://12.34.567.890
    port: 8086
    user: thisisnotarealusername
    password: thisisnotarealpassword
``` 

The `sources` section may contain an arbitrary number of sources to receive measurements from. The measurements are encoded with JSON and are expected to have the same fields as the following example:
```
{
  "source":"peter",
  "time":1581438814,
  "value":61.44476,
  "type":"heartRate"
}
```
The supported values for `type` are
- `udp` - there is expected to be one measurement per datagram.
- `http` - one measurement per POST request.

The `type` field of a received measurement will be checked against the configured types in `measurementTypes` section of the config file. The `value` will be checked against the configured `lowerThreshold` and `upperThreshold` for the matching type. If a threshold is violated, a corresponding message will be printed to stdout.

The measurements will be forwarded to all endpoints defined in the `sinks`. The format of the outgoing measurements is the same as for incoming ones. A sink can be of any type which is also supported in the `sources` section.

Additionally a sink can be of the type `influxDB`. In this case a `user` and `password` for the corresponding influxDB server need to be provided.   

## Deployment

Copy ```Heartrate-1.0-jar-with-dependencies.jar``` to where it should be executed.

Run it using
```
java -jar Heartrate-1.0-jar-with-dependencies.jar [config]
```

The optional parameter should contain either
- the path of the config file,
- or the name of an environment variable.
- If no parameter is supplied, the config file is assumed to be located at ``./config.yaml``.

If an environment variable name is specified, the corresponding environment variable must contain either
- the path of the config file,
- or the content of the config file.
