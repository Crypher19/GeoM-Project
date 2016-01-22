package com.geomclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
//import java.util.StringTokenizer;

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
			
		
			if ("A".equals(dc.getScelta())) {
				msgDaInviare = "A;" + dc.getUtente().getUser() + ";";
				msgDaInviare += dc.getUtente().getPass();
			}
			else if ("R".equals(dc.getScelta())) {
				msgDaInviare = "R;" + dc.getUtente().getUser() + ";";
				msgDaInviare += dc.getUtente().getPass()+ ";";
				msgDaInviare += dc.getUtente().getName()+ ";";
				msgDaInviare += dc.getUtente().getSurn()+ ";";
				msgDaInviare += dc.getUtente().getMail();
			}
			

			// invio il messaggio
			sOUT.println(msgDaInviare);
			sOUT.flush();		
		
			if ("y".equals(sIN.readLine())) {
				dc.setAutenticato(true);
			}
			else {
				dc.setAutenticato(false);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//Log.e("OpenJM", "IOException", e);
			e.printStackTrace();
		}
		
		try {
			sIN.close();
			sOUT.close();
			connessione.close();
			System.out.println("Connessione chiusa.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//Log.e("OpenJM", "IOException", e);
			e.printStackTrace();
		}
		
	}
}
