import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class DSocket extends DatagramSocket {
	protected static final int CAP = 80;
	DatagramPacket packet;
	
	public DSocket(int port) throws SocketException {
		super(port);
		// TODO Auto-generated constructor stub
	}
	
	public DSocket() throws SocketException {
		this(5555);
	}
	
	public String receive() {
		byte[] buffer = new byte[CAP];
		try {
			packet = new DatagramPacket(buffer, CAP);
			super.receive(packet);
		} catch(IOException e) {
			System.err.println(e.getMessage());
		}
		return new String(buffer);
	}
	
	public void send(InetAddress hostIP, int port, String msg) {
		try {
			byte[] buffer = msg.getBytes();
			DatagramPacket packet = 
					new DatagramPacket(buffer, buffer.length, hostIP, port);
			super.send(packet);
		} catch(IOException e) {
			System.err.println(e.getMessage());
		}
	}
	public void send(String host, int port, String msg) {
		try {
			InetAddress hostIP = InetAddress.getByName(host);
			send(hostIP, port, msg);
		} catch(IOException e) {
			System.err.println(e.getMessage());
		}
	}
	public void send(int port, String msg) { 
		send("localhost", port, msg); 
	}

	public void sendResponse(String msg) {
		if (packet != null) {
			send(packet.getAddress(), packet.getPort(), msg);
		}
	}

}
