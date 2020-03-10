package de.tuberlin.fcc.mockfogload;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

class UDPSource extends Source {
    DatagramSocket socket;
    UDPSource(int port) {
        new Thread(() -> {
            byte[] buf = new byte[65535];
            try {
                socket = new DatagramSocket(port);
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    String body = new String(packet.getData(), 0, packet.getLength());
                    handleBody(body);
                }
            } catch (SocketException ignored) {
                // Socket was closed.
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @Override
    void close() {
        socket.close();
    }
}
