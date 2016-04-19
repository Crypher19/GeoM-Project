package com.example.ricevitore_gps;

public class DatiCondivisi {

	private double longitudine;
	private double latitudine;
	
	public DatiCondivisi()
	{
		longitudine = 0;
		latitudine = 0;
	}
	
	public void setLongitudine(double longitudine)
	{
		this.longitudine = longitudine;
	}
	
	public void setLatitudine(double latitudine)
	{
		this.latitudine = latitudine;
	}
	
	public double getLongitudine()
	{
		return longitudine;
	}
	
	public double getLatitudine()
	{
		return latitudine;
	}
	
}
