package utils;

import io.restassured.response.Response;
import static io.restassured.RestAssured.get;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RestUtils {
	 String formedURLLatAndLon;
	 String formedURLAddress;
	 String resLatAndLon =null;
	 String resAddress =null;
	 Map<String, String> latAndLon;
	 ArrayList<String> addressArray;
	
	private String URLLatAndLon = "https://nominatim.openstreetmap.org/search/?format=xml&addressdetails=#ADDRESSDETAILS&limit=#LIMIT&country_codes=us&postalcode=#POSTALCODE";
	private String URLAddress = "https://nominatim.openstreetmap.org/reverse?format=xml&lat=#LAT&lon=#LON&zoom=18&addressdetails=1";
	
	
	
	public Map<String, String> getLatitudeAndLongitude(String postalCode) {
		
		latAndLon = new HashMap<String, String>();
		formedURLLatAndLon = URLLatAndLon.replace("#ADDRESSDETAILS", "1").replace("#LIMIT", "1").replace("#POSTALCODE", postalCode);
		Response response = get(formedURLLatAndLon);
		resLatAndLon = response.asString();
		if(resLatAndLon.contains("lat")||resLatAndLon.contains("lon")) {
			
		try {
			latAndLon.put(postalCode+"Lattitude",resLatAndLon.split("lat='")[1].split("'")[0]);
			latAndLon.put(postalCode+"Longitude",resLatAndLon.split("lon='")[1].split("'")[0]);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		}
		//
		return latAndLon;
		
	}
	
	public String getFullAddressXML(String postalCode) {
		
		getLatitudeAndLongitude(postalCode);
		if(latAndLon.size()>=1) {
		try {
			formedURLAddress = URLAddress.replace("#LAT", latAndLon.get(postalCode+"Lattitude")).replace("#LON", latAndLon.get(postalCode+"Longitude"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	//	System.out.println(formedURLAddress);
		Response response = get(formedURLAddress);
		resAddress = response.asString();
		}
		//System.out.println(resAddress);
		
	return resAddress;
		
	}
	
	
	public ArrayList<String> getAddressSeperately(String postalCode) {
		
		addressArray = new ArrayList<String>();
		
		String fullAddressXML = getFullAddressXML(postalCode);
		
		if(fullAddressXML.length()>=50) {

		try {
			String country = fullAddressXML.split("<country>")[1].split("</country>")[0];
			String state;
			try {
				state = fullAddressXML.split("<state>")[1].split("</state>")[0];
			} catch (Exception e) {
				
				try {
					state = fullAddressXML.split("<state_district>")[1].split("</state_district>")[0];
				} catch (Exception e1) {
					
					state = fullAddressXML.split("<county>")[1].split("</county>")[0];
					
				}
			}
			String country_code = fullAddressXML.split("<country_code>")[1].split("</country_code>")[0];		
			String fullAddressLine = fullAddressXML.split("\">")[1].split("</result>")[0];
			String addressLine = fullAddressLine.split(", "+state)[0];

			addressArray.add(addressLine);
			addressArray.add(state);
			addressArray.add(country);
			addressArray.add(country_code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
}
		return addressArray;
	
	
}

}
