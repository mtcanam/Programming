package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;

/** An applet that shows airports (and routes)
 * on a world map.  
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMap extends PApplet {
	
	UnfoldingMap map;
	private List<Marker> airportList;
	List<Marker> routeList;
	private HashMap<Integer, Location> airports;
	private HashMap<Location, Integer> routeMap;
	private Marker lastClicked;
	private Marker lastSelected;
	
	public void setup() {
		// setting up PAppler
		size(800,600, OPENGL);
		
		// setting up map and default events
		map = new UnfoldingMap(this, 50, 50, 750, 550);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();
		airports = new HashMap<Integer, Location>();
		routeMap = new HashMap<Location, Integer>();
		
		// create markers from features
		for(PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature);
	
			m.setRadius(5);
			airportList.add(m);
			
			// put airport in hashmap with OpenFlights unique id for key
			airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
			routeMap.put(feature.getLocation(), Integer.parseInt(feature.getId()));
		}
		
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		for(ShapeFeature route : routes) {
			
			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
			}
			
			SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());
		
			System.out.println(sl.getProperties());
			
			//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
			routeList.add(sl);
		}
		
		
		
		//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		map.addMarkers(routeList);
		
		map.addMarkers(airportList);
		
	}
	
	public void draw() {
		background(0);
		map.draw();
		
	}
	
	@Override
	public void mouseMoved()
	{
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		
		}
		selectMarkerIfHover(airportList);
	}
	
	// If there is a marker selected 
	private void selectMarkerIfHover(List<Marker> markers)
	{
		// Abort if there's already a marker selected
		if (lastSelected != null) {
			return;
		}
		
		for (Marker m : markers) 
		{
			CommonMarker marker = (CommonMarker)m;
			if (marker.isInside(map,  mouseX, mouseY)) {
				lastSelected = marker;
				marker.setSelected(true);
				return;
			}
		}
	}
	
	@Override
	public void mouseClicked()
	{
		if (lastClicked != null) {
			unhideMarkers();
			lastClicked = null;
		}
		else if (lastClicked == null) 
		{
			checkAirportsForClick();
		}
	}
	
	//If airport was clicked, display all routes leading to/from that airport, and hide all other non-related airports
	public void checkAirportsForClick() {
		//Initialize an arraylist to get all relevant airports
		List<Integer> usedList = new ArrayList<Integer>();
		if (lastClicked != null) return;
		// Loop over the earthquake markers to see if one of them is selected
		for (Marker m : airportList) {
			AirportMarker marker = (AirportMarker)m;
			if (!marker.isHidden() && marker.isInside(map, mouseX, mouseY)) {
				lastClicked = marker;
				//Find the id of the airport from the hashmap
				int markerId = routeMap.get(marker.getLocation());
				// Decide which routes connect at this airport
				for (Marker rm : routeList) {
					//Cast the id of the source destination to int for comparison
					int routeSourceId = Integer.parseInt(rm.getProperty("source").toString());
					//Cast the id of the end destination to int for comparison
					int routeDestId = Integer.parseInt(rm.getProperty("destination").toString());
					//If either the source or end destination of the flight is the airport that was clicked, add this airport id to the usedList (ensuring no dupes). Make the route visible.
					if (routeSourceId == markerId || routeDestId == markerId){
						//this means that the route is either to or from the airport
						if(!usedList.contains(routeDestId)) {
							usedList.add(routeDestId);
						}
						if(!usedList.contains(routeSourceId)) {
							usedList.add(routeSourceId);
						}
						rm.setHidden(false);
					}else {
						rm.setHidden(true);
					}
				}
				//Iterate through all airports. If the id showed up in the usedList, keep it visible, otherwise set to hidden
				for (Marker mhide : airportList) {
					int mHideId = routeMap.get(mhide.getLocation());
					if (!usedList.contains(mHideId)) {
						mhide.setHidden(true);
					}
				}
				//For the airports selected that didn't have ids. If there are no matches from the route list, we have a non routed airport selected, so just set to visible.
				if(usedList.size() == 0) {
					marker.setHidden(false);
				}
				return;
			}
		}
	}
	
	// loop over and unhide all airports, hide all routes
		private void unhideMarkers() {
			for(Marker marker : airportList) {
				marker.setHidden(false);
			}
				
			for(Marker marker : routeList) {
				marker.setHidden(true);
			}
		}

}
