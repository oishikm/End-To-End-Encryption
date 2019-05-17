/*
	Network Microproject (6th Semester, CS692)
	End-To-End Encryption Version 1.0

	Client
	Creates a client and connects it to a port of Server on localhost.
	Clients communicate through the Server.
	Client 1 <-> Server <-> Client 2
	But as the data is end-to-end encrypted, Server cannot understand the data.
	
	Command line arguements : client number, port number
	Example : $/> java Client 1 3030

	Oishik M | 18 May 2019
*/

import java.io.*;
import java.net.*;

public class Client {
	
	public static String cinbuf, coutbuf, pinbuf, poutbuf;
	public static BufferedReader bin;
	public static DataInputStream dis;
	public static DataOutputStream dos;
	public static Socket s;
	public static Encrypt enc;

	public static void init(int portNumber) throws IOException {
		cinbuf = "";
		coutbuf = "";
		pinbuf = "";
		poutbuf = "";
		s = new Socket("127.0.0.1", portNumber);
		bin = new BufferedReader(new InputStreamReader(System.in));
		dis = new DataInputStream(s.getInputStream());
		dos = new DataOutputStream(s.getOutputStream());
		enc = new Encrypt();
	}

	public static class GetInput extends Thread {
		public void run() {
			try {
				while(pinbuf.equals("END") != true) {	
					cinbuf = dis.readUTF();
					pinbuf = enc.decrypt(String.valueOf(cinbuf));
					System.out.println("\n(Incoming message) : " + pinbuf);
				}
			} catch(Exception e) {System.out.println(e);}
		}
	}

	public static class SendOutput extends Thread {
		public void run() {
			try {	
				while(poutbuf.equals("END") != true) {
					poutbuf = bin.readLine();
					coutbuf = enc.encrypt(poutbuf);
					dos.writeUTF(coutbuf);
				}
			} catch(Exception e) {System.out.println(e);}
		} 
	}

	public static void execute(int clientNumber, int portNumber) throws IOException {
		System.out.println("\n(CLIENT " + clientNumber + ")\n\nEnter message : \n");
		init(portNumber);
		
		GetInput gi = new GetInput();
		SendOutput so = new SendOutput();
		
		so.start();
		gi.start();
	}

	public static void main(String[] args) throws IOException {
		int clientNumber, portNumber;
		if(args.length < 2) {
			System.err.println("\nMissing arguements.");
		}
		else
		{
			clientNumber = Integer.parseInt(args[0]);
			portNumber = Integer.parseInt(args[1]);
			execute(clientNumber, portNumber);
		}
	}
}
