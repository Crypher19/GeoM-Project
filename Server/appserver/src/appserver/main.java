package appserver;

import java.net.*;
import java.io.*;

public class main {
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
				
				ServerSocket sSocket;
				Socket connessione;
				int porta = 4444;

				sharedData sData = new sharedData();
				String numero = "";
						
				try {
					sSocket = new ServerSocket(porta);
					System.out.println("In attesa di connessioni...");

					while (true) {
						connessione = sSocket.accept();
						System.out.println("Arrivato");
						
						threadUser t = new threadUser(connessione,sData);
						sData.addUser(t);
						t.start();
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
	}
