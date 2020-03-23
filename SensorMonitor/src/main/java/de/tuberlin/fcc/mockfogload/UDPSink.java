package de.tuberlin.fcc.mockfogload;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.*;

class UDPSink extends Sink  {
    private DatagramSocket socket;

    {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    private InetAddress address;
    private int port;

    UDPSink(String address, int port) {
        try {
            this.address = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        this.port = port;
    }

    @Override
    void send(Measurement m) {
        String msg = new Gson().toJson(m);

        byte[] bytes = msg.getBytes();
        try {
            socket.send(new DatagramPacket(bytes, bytes.length, address, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    void close() {
        socket.close();
    }
}
