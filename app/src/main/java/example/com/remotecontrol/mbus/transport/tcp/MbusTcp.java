package example.com.remotecontrol.mbus.transport.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import example.com.remotecontrol.mbus.message.AbstractMbusCommand;
import example.com.remotecontrol.mbus.transport.IMbus;
import example.com.remotecontrol.util.OPENLOG;

public class MbusTcp
        implements IMbus {
    private static final String TAG = "MbusTcp";
    private String remoteHost;
    private int remotePort;
    private int localPort = 0;
    private String localHost;
    private int backlog = 0;
    private static final int BUFFER_SIZE = 1024;
    private static final int MAX_SIZE = 1024;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private SocketAddress localAddress;
    private SocketAddress remoteAddress;
    private int timeout = 60000;
    private Socket receiveSocket;
    private OutputStream outputStream = null;
    private InputStream inputStream = null;

    public MbusTcp() {
    }

    public MbusTcp(int paramInt) {
        this.localPort = paramInt;
    }

    public MbusTcp(String paramString, int paramInt) {
        this.remoteHost = paramString;
        this.remotePort = paramInt;
    }

    public MbusTcp(String paramString1, int paramInt1, String paramString2, int paramInt2) {
        this.remoteHost = paramString1;
        this.remotePort = paramInt1;
        this.localHost = paramString2;
        this.localPort = paramInt2;
    }

    public void connect()
            throws Exception {
        createServerSocket();
        createSocket();
    }

    public void send(AbstractMbusCommand paramAbstractMbusCommand)
            throws Exception {
        byte[] arrayOfByte = paramAbstractMbusCommand.toBytes();
        if (arrayOfByte.length <= 1024) {
            if (null == this.clientSocket)
                createSocket();
            this.outputStream = this.clientSocket.getOutputStream();
            this.outputStream.write(arrayOfByte);
            this.outputStream.flush();
        } else {
            OPENLOG.I("MbusTcp Easybus--->send message too long,length >1024");
        }
    }

    public void send(AbstractMbusCommand paramAbstractMbusCommand, String paramString)
            throws Exception {
        if (null == paramString) {
            OPENLOG.I("MbusTcp Easybus---> remoteIp is null, return .");
            return;
        }
        if (!paramString.equals(this.remoteHost)) {
            this.remoteHost = paramString;
            createSocket();
        }
        send(paramAbstractMbusCommand);
    }

    public void send(AbstractMbusCommand paramAbstractMbusCommand, String paramString, int paramInt)
            throws Exception {
        if (null == paramString) {
            OPENLOG.I("EasybusTcpEasybus---> remoteIp is null, return .");
            return;
        }
        if ((!paramString.equals(this.remoteHost)) && (paramInt != this.remotePort)) {
            this.remoteHost = paramString;
            this.remotePort = paramInt;
            createSocket();
        }
        send(paramAbstractMbusCommand);
    }

    public byte[] receive()
            throws Exception {
        byte[] arrayOfByte1 = null;
        if (null == this.receiveSocket)
            this.receiveSocket = this.serverSocket.accept();
        this.inputStream = this.receiveSocket.getInputStream();
        byte[] arrayOfByte2 = new byte[1024];
        int i = this.inputStream.read(arrayOfByte2);
        if (-1 == i) {
            arrayOfByte1 = new byte[0];
        } else {
            arrayOfByte1 = new byte[i];
            System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, i);
        }
        return arrayOfByte1;
    }

    public void disconnect()
            throws Exception {
        if (null != this.receiveSocket) {
            this.receiveSocket.close();
            this.receiveSocket = null;
        }
        if (null != this.serverSocket) {
            this.serverSocket.close();
            this.serverSocket = null;
        }
        if (null == this.clientSocket)
            return;
        this.clientSocket.close();
        this.clientSocket = null;
    }

    private void createSocket()
            throws SocketException, IOException, SocketTimeoutException, Exception {
        this.clientSocket = new Socket();
        this.clientSocket.setReuseAddress(true);
        if ((null != this.localHost) && (this.localPort >= 0)) {
            this.localAddress = new InetSocketAddress(this.localHost, this.localPort);
            this.clientSocket.bind(this.localAddress);
        }
        if ((null != this.remoteHost) && (this.remotePort >= 0)) {
            this.remoteAddress = new InetSocketAddress(this.remoteHost, this.remotePort);
            this.clientSocket.connect(this.remoteAddress, this.timeout);
        }
        if (this.clientSocket.getTcpNoDelay())
            return;
        this.clientSocket.setTcpNoDelay(true);
    }

    private void createServerSocket()
            throws SocketException, IOException, Exception {
        this.serverSocket = new ServerSocket();
        this.serverSocket.setReuseAddress(true);
        this.serverSocket.bind(new InetSocketAddress(this.localPort), this.backlog);
    }

    public String getRemoteHost() {
        return this.remoteHost;
    }

    public void setRemoteHost(String paramString) {
        this.remoteHost = paramString;
    }

    public int getRemotePort() {
        return this.remotePort;
    }

    public void setRemotePort(int paramInt) {
        this.remotePort = paramInt;
    }

    public int getLocalPort() {
        return this.localPort;
    }

    public void setLocalPort(int paramInt) {
        this.localPort = paramInt;
    }

    public String getLocalHost() {
        return this.localHost;
    }

    public void setLocalHost(String paramString) {
        this.localHost = paramString;
    }

    public int getBacklog() {
        return this.backlog;
    }

    public void setBacklog(int paramInt) {
        this.backlog = paramInt;
    }

    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }

    public void setServerSocket(ServerSocket paramServerSocket) {
        this.serverSocket = paramServerSocket;
    }

    public Socket getClientSocket() {
        return this.clientSocket;
    }

    public void setClientSocket(Socket paramSocket) {
        this.clientSocket = paramSocket;
    }

    public SocketAddress getLocalAddress() {
        return this.localAddress;
    }

    public void setLocalAddress(SocketAddress paramSocketAddress) {
        this.localAddress = paramSocketAddress;
    }

    public SocketAddress getRemoteAddress() {
        return this.remoteAddress;
    }

    public void setRemoteAddress(SocketAddress paramSocketAddress) {
        this.remoteAddress = paramSocketAddress;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int paramInt) {
        this.timeout = paramInt;
    }

    public Socket getReceiveSocket() {
        return this.receiveSocket;
    }

    public void setReceiveSocket(Socket paramSocket) {
        this.receiveSocket = paramSocket;
    }
}
