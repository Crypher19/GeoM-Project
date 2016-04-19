package com.example.ricevitore_gps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Connessione extends Thread{
	private Socket connessione;

	private InputStreamReader input;
	private BufferedReader sIN;

	private OutputStream out;
	private PrintWriter sOUT;

	private String server;
	private int port = 3333;

	private XMLReader reader;

	private String recived;

	private DatiCondivisi d;
	public Connessione(DatiCondivisi dati){
		this.server = "192.168.1.102"; //cambiare a seconda dell'indirizzo del server
		this.port = 3333;
		
		reader = new XMLReader();

		recived = null;
		
		d = dati;
	}

	public Connessione(String server, int port,DatiCondivisi dati){
		this.server = server;
		this.port = port;

		reader = new XMLReader();

		recived = null;
		
		d = dati;

	}

	public void run()
	{
	
		try {
			
			//connesione al server
			connect();

			//ricevo esito
			recived = sIN.readLine();

			//chiusura canali
			close();
			
			//estrapolo coordinate dal messaggio ricevuto dal server
			
			d.setLatitudine(Double.parseDouble(reader.getElement(recived, "latitudine")));
			d.setLongitudine(Double.parseDouble(reader.getElement(recived, "longitudine")));

		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	private void connect() throws IOException{
		connessione = new Socket(server, port);
		input = new InputStreamReader(connessione.getInputStream());
		sIN = new BufferedReader(input);

		out = connessione.getOutputStream();
		sOUT = new PrintWriter(out);
	}

	private void close() throws IOException{
		sIN.close();
		sOUT.close();
		connessione.close();
	}
}
