package example.com.remotecontrol.multiscreen.mdnsdevice;

public class Device {
	private String name ;
	private String ip ;
	private String mac ;
	private String port;
	private String devtype;
	private boolean isMdns ;

	public Device(String name, String ip, String mac){
		this.name = name;
		this.ip = ip;
		this.mac = mac;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public boolean isMdns() {
		return isMdns;
	}
	public void setMdns(boolean isMdns) {
		this.isMdns = isMdns;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
    public String getDevType(){
        return this.devtype;
    }
    
    public void setDevType(String devtype){
        this.devtype = devtype;
    }

	@Override
	public String toString() {
		
		String s = "name = "+ name 
				+ " && ip = "+ ip 
				+ " && mac = " + mac
				+ " && port = " + port
				+ " && isMdns = " + isMdns	;
		return s;
	}

}
