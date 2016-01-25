package com.geomclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
//import java.util.StringTokenizer;

import android.util.Log;

//import android.util.Log;

public class ThreadSocket extends Thread {
	private Socket connessione;
	// stream per gestire il flusso in output
	private OutputStream out;
	private PrintWriter sOUT;
	
	private InputStreamReader in;
	private BufferedReader sIN;
	
	private DatiCondivisi dc;
	String msgDaInviare;

		
	public ThreadSocket(DatiCondivisi dc) {
		this.dc = dc;
		msgDaInviare = "";
	}
	
	public void run() {
		try {
			String addr = dc.getServerAddress();
			int port = dc.getServerPort();

			connessione = new Socket(addr, port);
			// connessioni output del socket
			out = connessione.getOutputStream();
			sOUT = new PrintWriter(out);
			
			// connessioni input del socket
			in = new InputStreamReader(connessione.getInputStream());
			sIN = new BufferedReader(in);
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("GeoM", "IOException", e);
		}
		
		try {
			sIN.close();
			sOUT.close();
			connessione.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("GeoM", "IOException", e);
		}
		
	}
}
