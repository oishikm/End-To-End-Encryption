/*
	Network Microproject (6th Semester, CS692)
	End-To-End Encryption Version 1.0

	Server
	Sets up 2 ports for clients on localhost.
	Clients communicate through the Server.
	Client 1 <-> Server <-> Client 2
	But as the data is end-to-end encrypted, Server cannot understand the data.
	
	Command line arguements : port numbers for 2 clients
	Example : $/> java Server 3030 3040

	Oishik M | 18 May 2019
*/

import java.io.*;
import java.net.*;

public class Server {
	
	public static String buf1, buf2;
	public static ServerSocket ss1, ss2;
	public static Socket sock1, sock2;
	public static DataInputStream dis1, dis2;
	public static DataOutputStream dos1, dos2;
	
	public static void init(int port1, int port2) throws IOException {
		buf1 = "";
		buf2 = "";
		ss1 = new ServerSocket(port1);
		ss2 = new ServerSocket(port2);
		sock1 = ss1.accept();
		sock2 = ss2.accept();
		dis1 = new DataInputStream(sock1.getInputStream());
		dis2 = new DataInputStream(sock2.getInputStream());
		dos1 = new DataOutputStream(sock1.getOutputStream());
		dos2 = new DataOutputStream(sock2.getOutputStream());
		System.out.println("\nPorts " + port1 + ", " + port2 + " listening to Clients 1, 2.");
	}

	public static class Client1to2 extends Thread {
		public void run() {
			try {
				while(buf1.equals("END") != true) {	
					buf1 = dis1.readUTF();
					System.out.println("\nIncoming message (Client 1) : " + buf1);
					dos2.writeUTF(buf1);
				}
			} catch(Exception e) {System.out.println(e);}
		}
	}

	public static class Client2to1 extends Thread {
		public void run() {
			try {	
				while(buf2.equals("END") != true) {
					buf2 = dis2.readUTF();
					System.out.println("\nIncoming message (Client 2) : " + buf2);
					dos1.writeUTF(buf2);
				}
			} catch(Exception e) {System.out.println(e);}
		} 
	}

	public static void execute(int port1, int port2) throws IOException {
		init(port1, port2);
			
		Client1to2 c1to2 = new Client1to2();
		Client2to1 c2to1 = new Client2to1();

		c1to2.start();
		c2to1.start();
	}
	
	public static void main(String[] args) throws IOException {
		int port1, port2;
		
		if(args.length < 2) {
			System.err.println("\nMissing arguements.");
		}
		
		else {

			port1 = Integer.parseInt(args[0]);
			port2 = Integer.parseInt(args[1]);
			
			execute(port1, port2);
		}
	}
}
