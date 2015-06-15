package de.airport.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.airport.ejb.AirportFacade;
import de.airport.ejb.model.Airline;
import de.airport.ejb.model.Airplane;

@ManagedBean
@SessionScoped
public class AirportFacadeBean {
	private String name;
	private String street;
	private String city;
	//fuer das wetteranzeige
	private String temperatur;
	private String wetterStatus;
	private String windstaerke;
	private String luftfeuchtigkeit;
	private String regenwahrscheinlichkeit;
	
	//aktuelle Uhrzeit und Datum 
	//private String ADateAndTime;
	
	
	@EJB
	private AirportFacade facade;

	/*public String getADateAndTime() {
		setADateAndTime();
		return ADateAndTime;
	}

	public void setADateAndTime() {
		java.util.Date now = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
		this.ADateAndTime = sdf.format(now);
	}*/
	int start = 0;
	int end = 0;
	public void showWeather(){
		
		URL weather;
		try {
			weather = new URL("http://www.webservicex.net/globalweather.asmx/GetWeather?CityName=Stuttgart&CountryName=Germany");
		
        URLConnection yc = weather.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        String inputLine;
        in.readLine();
        in.readLine();
        while ((inputLine = in.readLine()) != null){ 
        if(inputLine.matches("[CurrentWeather]+") ){
        start = inputLine.indexOf("&gt;")+2;
        end = inputLine.indexOf("&lt;",4)-2;
        System.out.print("Start " + start + "Ende "+ end);
        System.out.println(inputLine);
    //    System.out.println(inputLine.substring(start, end));
        	}    
        }
        in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public void createAirline() {
		facade.createAirline(name, street, city);
	}
	
	public List<Airline> getAirlines() {
		return facade.getAirlines();
	}

	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void createAirplane() {
		facade.createAirplane(name);
	}
	
	public List<Airplane> getAirplanes() {
		return facade.getAirplanes();
	}

	
	
	
}
