package example.com.remotecontrol.mbus.transport.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import example.com.remotecontrol.mbus.message.AbstractMbusCommand;
import example.com.remotecontrol.mbus.transport.IMbus;
import example.com.remotecontrol.util.OPENLOG;

public class MbusUdp
        implements IMbus {
    private String remoteHost;
    private int remotePort;
    private int localPort = 0;
    private DatagramSocket datagramSocket;
    private InetAddress remoteIpAddress;
    private DatagramPacket sendDatagramPacket;
    private DatagramPacket receiveDatagramPacket;
    private static final int BUFFER_SIZE = 1024;
    private static final int MAX_SIZE = 1024;
    private static final String TAG = "MbusUdp";

    public MbusUdp() {
    }

    public MbusUdp(String paramString, int paramInt) {
        this.remoteHost = paramString;
        this.remotePort = paramInt;
    }

    public MbusUdp(String paramString, int paramInt1, int paramInt2) {
        this.remoteHost = paramString;
        this.remotePort = paramInt1;
        this.localPort = paramInt2;
    }

    public void connect()
            throws SocketException, UnknownHostException {
        if (null == this.datagramSocket) {
            if (this.localPort == 0)
                this.datagramSocket = new DatagramSocket();
            else
                this.datagramSocket = new DatagramSocket(this.localPort);
            this.datagramSocket.setReuseAddress(true);
        }
        if (null == this.remoteIpAddress)
            this.remoteIpAddress = InetAddress.getByName(this.remoteHost);
        if (null == this.sendDatagramPacket)
            this.sendDatagramPacket = new DatagramPacket(new byte[0], 0, this.remoteIpAddress, this.remotePort);
        if (null != this.receiveDatagramPacket)
            return;
        this.receiveDatagramPacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
    }

    public void connect(int paramInt)
            throws SocketException, UnknownHostException {
        if (null == this.datagramSocket) {
            if (this.localPort == 0)
                this.datagramSocket = new DatagramSocket();
            else
                this.datagramSocket = new DatagramSocket(this.localPort);
            if (paramInt > 0)
                this.datagramSocket.setSoTimeout(paramInt);
            this.datagramSocket.setReuseAddress(true);
        }
        if (null == this.remoteIpAddress)
            this.remoteIpAddress = InetAddress.getByName(this.remoteHost);
        if (null == this.sendDatagramPacket)
            this.sendDatagramPacket = new DatagramPacket(new byte[0], 0, this.remoteIpAddress, this.remotePort);
        if (null != this.receiveDatagramPacket)
            return;
        this.receiveDatagramPacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
    }

    public void send(AbstractMbusCommand paramAbstractMbusCommand)
            throws Exception {
        byte[] arrayOfByte = paramAbstractMbusCommand.toBytes();
        if (arrayOfByte.length <= BUFFER_SIZE) {
            this.sendDatagramPacket.setData(arrayOfByte);
            this.sendDatagramPacket.setLength(arrayOfByte.length);
            if (null == this.datagramSocket) {
                if (this.localPort == 0)
                    this.datagramSocket = new DatagramSocket();
                else
                    this.datagramSocket = new DatagramSocket(this.localPort);
                this.datagramSocket.setReuseAddress(true);
            }
            this.datagramSocket.send(this.sendDatagramPacket);
        } else {
            OPENLOG.I("EasybusUdpEasybus--->send message too long.");
        }
    }

    public void send(AbstractMbusCommand paramAbstractMbusCommand, String paramString)
            throws Exception {
        if (null == paramString) {
            OPENLOG.I("EasybusUdpEasybus---> remoteIp is null, return .");
            return;
        }
        if (!paramString.equals(this.remoteHost)) {
            this.remoteHost = paramString;
            this.remoteIpAddress = InetAddress.getByName(paramString);
            this.sendDatagramPacket = new DatagramPacket(new byte[0], 0, this.remoteIpAddress, this.remotePort);
        }
        send(paramAbstractMbusCommand);
    }

    public void send(AbstractMbusCommand paramAbstractMbusCommand, String paramString, int paramInt)
            throws Exception {
        if (null == paramString) {
            OPENLOG.I("EasybusUdpEasybus---> remoteIp is null, return .");
            return;
        }
        if ((!paramString.equals(this.remoteHost)) && (paramInt != this.remotePort)) {
            this.remoteHost = paramString;
            this.remotePort = paramInt;
            this.remoteIpAddress = InetAddress.getByName(paramString);
            this.sendDatagramPacket = new DatagramPacket(new byte[0], 0, this.remoteIpAddress, paramInt);
        }
        send(paramAbstractMbusCommand);
    }

    public byte[] receive()
            throws Exception {
        this.datagramSocket.receive(this.receiveDatagramPacket);
        return this.receiveDatagramPacket.getData();
    }

    public void disconnect()
            throws Exception {
        if (null == this.datagramSocket)
            return;
        this.datagramSocket.disconnect();
        this.datagramSocket.close();
        this.remoteIpAddress = null;
        this.sendDatagramPacket = null;
        this.receiveDatagramPacket = null;
        this.datagramSocket = null;
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

    public InetAddress getLocalHost() {
        InetAddress localInetAddress = null;
        if (null != this.datagramSocket)
            localInetAddress = this.datagramSocket.getLocalAddress();
        return localInetAddress;
    }

    public int getLocalPort() {
        int i = 0;
        if (null != this.datagramSocket)
            i = this.datagramSocket.getPort();
        return i;
    }
}
