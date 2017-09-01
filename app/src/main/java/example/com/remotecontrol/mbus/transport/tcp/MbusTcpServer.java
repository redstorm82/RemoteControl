package example.com.remotecontrol.mbus.transport.tcp;

import java.net.ServerSocket;

import example.com.remotecontrol.mbus.message.AbstractMbusCommand;
import example.com.remotecontrol.mbus.transport.IMbus;

public class MbusTcpServer
        implements IMbus {
    private int localPort = 0;
    private String localHost;
    private int backlog = 0;
    private static final int BUFFER_SIZE = 1024;
    private static final int MAX_SIZE = 1024;
    private ServerSocket serverSocket;

    public void connect()
            throws Exception {
    }

    public void send(AbstractMbusCommand paramAbstractMbusCommand)
            throws Exception {
    }

    public void send(AbstractMbusCommand paramAbstractMbusCommand, String paramString)
            throws Exception {
    }

    public void send(AbstractMbusCommand paramAbstractMbusCommand, String paramString, int paramInt)
            throws Exception {
    }

    public byte[] receive()
            throws Exception {
        return null;
    }

    public void disconnect()
            throws Exception {
    }
}

