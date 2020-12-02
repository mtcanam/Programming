package module6;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.fill(11);
		pg.ellipse(x, y, 5, 5);
		
		
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		String top = removeQuotes(getName()) + " - " + removeQuotes(getCode());
		String bottom = removeQuotes(getCity()) + " - " + removeQuotes(getCountry());
		
		int maxLen = Math.max(top.length(), bottom.length());
		
		float w = maxLen * 7;
		float h = 30;
		float topX = x-w/2;
		float topY = y-1.5f*h;
		// show rectangle with title
		pg.fill(pg.color(255,255,255));
		pg.rect(topX,topY,w,h);
		pg.fill(pg.color(0,0,0));
		pg.text(top, topX+1, topY+12);
		pg.text(bottom, topX+1, topY+24);
		// show routes
		
		
	}
	
	//Get methods (name, city, country, code, altitude)
	public String getName() {
		return (String) getProperty("name");
	}
	
	public String getCity() {
		return (String)getProperty("city");
	}
	
	public String getCountry() {
		return (String) getProperty("country");
	}
	
	public String getCode() {
		return (String) getProperty("code");
	}
	
	public String getAltitude() {
		return (String) getProperty("altitude");
	}

	
	private String removeQuotes(String orig) {
		int l = orig.length();
		if(l > 1) {
			return orig.substring(1,l-1);
		}else {
			return orig;
		}
	}
	
}
