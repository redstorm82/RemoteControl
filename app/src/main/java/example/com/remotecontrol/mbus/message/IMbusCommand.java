package example.com.remotecontrol.mbus.message;

public abstract interface IMbusCommand extends ICommand {
    public abstract byte[] toBytes()
            throws Exception;
}
