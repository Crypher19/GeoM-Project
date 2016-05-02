package com.example.trasmettitore_gps;

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

	private String recived;

	private Double lat,lng;

	private XMLWriter writer;

	public Connessione(double latitudine,double longitudine){
		this.server = "192.168.1.102"; //cambiare a seconda dell'indirizzo del server
		this.port = 3333;

		writer = new XMLWriter();

		recived = null;
		
		lat = latitudine;
		lng = longitudine;
	}

	public Connessione(String server, int port, double latitudine,double longitudine){
		this.server = server;
		this.port = port;

		writer = new XMLWriter();

		recived = null;

		lat = latitudine;
		lng = longitudine;

	}

	public void run()
	{
	
		try {
			//connesione al server

			connect();

			Coordinate c = new Coordinate();
			c.setLat(lat);
			c.setLng(lng);

			sOUT.println(recived = writer.toXMLMessage(c));
			sOUT.flush();

			//chiusura canali
			close();

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
