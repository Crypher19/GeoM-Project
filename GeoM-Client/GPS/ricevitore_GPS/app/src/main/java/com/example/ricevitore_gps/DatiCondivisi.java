package com.example.ricevitore_gps;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class DatiCondivisi {

	private double lon;
	private double lat;
	private GoogleMap gMap;
	private TextView tView;
	private Geocoder gc;

	public DatiCondivisi(GoogleMap gMap, TextView t,Geocoder geoCod, double lat ,double lon)
	{
		this.lon = lon;
		this.lat = lat;
		gc = geoCod;
		this.gMap = gMap;
		tView = t;
	}

	public TextView gettView() {
		return tView;
	}

	public void settView(TextView tView) {
		this.tView = tView;
	}

	public GoogleMap getgMap() {
		return gMap;
	}

	public void setgMap(GoogleMap gMap) {
		this.gMap = gMap;
	}
	
	public double getLongitudine()
	{
		return lon;
	}
	
	public double getLatitudine()
	{
		return lat;
	}

	public void updateMap(double latitudine,double longitudine)
	{
		lat = latitudine;
		lon = longitudine;
		LatLng pos = new LatLng(lat, lon);
		gMap.addMarker(new MarkerOptions().position(pos).title("Marker"));
		gMap.moveCamera(CameraUpdateFactory.newLatLng(pos));

		updateAddress(); //aggiorno la textView che contiene l'indirizzo


	}

	private void updateAddress()
	{
		String addressString = "No address found";

		if (lat != 0 && lon != 0) {
			// Update the position overlay.
			double latitude = lat;
			double longitude = lon;


			if (!Geocoder.isPresent())
				addressString = "No geocoder available";
			else {
				try {
					List<Address> addresses = gc.getFromLocation(latitude, longitude, 1);
					StringBuilder sb = new StringBuilder();
					if (addresses.size() > 0) {
						Address address = addresses.get(0);

						for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
							sb.append(address.getAddressLine(i)).append("\n");

						sb.append(address.getLocality()).append("\n");
						sb.append(address.getPostalCode()).append("\n");
						sb.append(address.getCountryName());
					}
					addressString = sb.toString();
				} catch (IOException e) {
					Log.d("WHEREAMI", "IO Exception", e);
				}
			}
		}

		tView.setText("Indirizzo:\n" + "\n" + addressString);
	}
}
