package appserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadUser extends Thread {
	private Socket connessione;
	// stream per gestire il flusso in output
	private OutputStream out;
	private PrintWriter sOUT = null;
	
	private InputStreamReader in;
	private BufferedReader sIN;	
	private String numero;
	private SharedData sData= null;
	private DBclass db;
	private String numTel;
	
	public ThreadUser(Socket conn,SharedData sData) {
		this.connessione = conn;
		this.numero = numero;
		this.sData = sData;
		this.db = new DBclass();
		this.numTel="";
		
		try {
			// connessioni output del socket
			out = connessione.getOutputStream();
			sOUT = new PrintWriter(out);
			// connessioni input del socket
			in = new InputStreamReader(connessione.getInputStream());
			sIN = new BufferedReader(in);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	private void laterSetup(){

		try {
			//ricevo numero di telefono
			numTel = sIN.readLine();
			this.numero = numTel;
			sOUT.println("connected");
			sOUT.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sOUT.println("failed");
			sOUT.flush();
		}
		
		//controllo se ci sono messaggi nel DB Buffer
	}
	
	
	public void run() {
		laterSetup();
		//controllo se gia registrato, altrimenti registro
		//while true > 
		//ascolto messaggi
		//cerco thread destinatario nella lista
		//se Vivo > richiamo funzione invio messaggio
		//se Morto > salvo messaggio nel DB buffer
		//fine while
		
	}
}
