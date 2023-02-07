// package man in the middle;
import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException
	{
		try {
			int port = 8088;

			// Server Key
			int b = 3;
			int z = 5;

			// Client p, g, and key
			double clientP1, clientG1, clientA1, clientA2, B, C, Bdash, clientP2, clientG2;
			String Bstr, Cstr;

			// Established the Connection
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Waiting for client1 on port " + serverSocket.getLocalPort() + "...");
			Socket server1 = serverSocket.accept();
			System.out.println("Just connected to " + server1.getRemoteSocketAddress());

			// Server's Private Key
			System.out.println("From Server : Private Key = " + b);

			// Accepts the data from client
			DataInputStream in1 = new DataInputStream(server1.getInputStream());

			clientP1 = Integer.parseInt(in1.readUTF()); // to accept p
			System.out.println("From Client1 : P = " + clientP1);
			clientG1 = Integer.parseInt(in1.readUTF()); // to accept g
			System.out.println("From Client1 : G = " + clientG1);
			clientA1 = Double.parseDouble(in1.readUTF()); // to accept A
			System.out.println("From Client1 : Public Key = " + clientA1);

			B = ((Math.pow(clientG1, z)) % clientP1); // calculation of B
			Bstr = Double.toString(B);

			// Sends data to client
			// Value of B
			OutputStream outToclient1 = server1.getOutputStream();
			DataOutputStream out1 = new DataOutputStream(outToclient1);

			out1.writeUTF(Bstr); // Sending B
			
			System.out.println("Waiting for client2 on port " + serverSocket.getLocalPort() + "...");
			Socket server2 = serverSocket.accept();
			System.out.println("Just connected to " + server2.getRemoteSocketAddress());
			DataInputStream in2 = new DataInputStream(server2.getInputStream());
			clientP2 = Integer.parseInt(in2.readUTF()); // to accept p
			System.out.println("From Client2 : P = " + clientP2);
			clientG2 = Integer.parseInt(in1.readUTF()); // to accept g
			System.out.println("From Client2 : G = " + clientG2);
			clientA2 = Double.parseDouble(in2.readUTF()); // to accept A
			System.out.println("From Client2 : Public Key = " + clientA2);

			C = ((Math.pow(clientG2, z)) % clientP2); // calculation of B
			Cstr = Double.toString(C);

			OutputStream outToclient2 = server2.getOutputStream();
			DataOutputStream out2 = new DataOutputStream(outToclient2);

			out2.writeUTF(Cstr);


			Bdash = ((Math.pow(clientA1, b)) % clientP1); // calculation of Bdash

			System.out.println("Secret Key to perform Symmetric Encryption = "
							+ Bdash);
			

			double Cdash = ((Math.pow(clientA2, b)) % clientP2); // calculation of Bdash

			System.out.println("Secret Key to perform Symmetric Encryption = "
							+ Cdash);

			server1.close();
			server2.close();
		}

		catch (SocketTimeoutException s) {
			System.out.println("Socket timed out!");
		}
		catch (IOException e) {
		}
	}
    
}
