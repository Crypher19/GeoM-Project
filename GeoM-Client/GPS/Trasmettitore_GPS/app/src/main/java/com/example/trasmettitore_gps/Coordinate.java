package com.example.trasmettitore_gps;

public class Coordinate {
	private double lng;
	private double lat;
	
	public Coordinate()
	{
		lng = 0;
		lat = 0;
	}
	
	public void setLng(double lng)
	{
		this.lng = lng;
	}
	
	public double getLng()
	{
		return lng;
	}
	
	public void setLat(double lat)
	{
		this.lat = lat;
	}
	
	public double getLat()
	{
		return lat;
	}
}
