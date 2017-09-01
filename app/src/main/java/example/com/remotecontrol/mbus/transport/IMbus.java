package example.com.remotecontrol.mbus.transport;

import example.com.remotecontrol.mbus.message.AbstractMbusCommand;

public abstract interface IMbus {
    public abstract void connect()
            throws Exception;

    public abstract void send(AbstractMbusCommand paramAbstractMbusCommand)
            throws Exception;

    public abstract void send(AbstractMbusCommand paramAbstractMbusCommand, String paramString)
            throws Exception;

    public abstract void send(AbstractMbusCommand paramAbstractMbusCommand, String paramString, int paramInt)
            throws Exception;

    public abstract byte[] receive()
            throws Exception;

    public abstract void disconnect()
            throws Exception;
}
