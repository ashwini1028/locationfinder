package utils;

import io.restassured.response.Response;
import static io.restassured.RestAssured.get;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class RestUtils {
	String formedURLLatAndLon;
	String formedURLAddress;
	String resLatAndLon = null;
	String resAddress = null;
	Map<String, String> latAndLon;
	ArrayList<String> addressArray;
	XmlUtils xmlutils;

	private String URLPostalCode = "https://nominatim.openstreetmap.org/search/?format=xml&addressdetails=#ADDRESSDETAILS&limit=#LIMIT&country_codes=us&postalcode=#POSTALCODE";
	private String URLAddress = "https://nominatim.openstreetmap.org/reverse?format=xml&lat=#LAT&lon=#LON&zoom=18&addressdetails=1";



	public ArrayList<String> fetchAddressByLatNLon(String postalCode)
			throws ParserConfigurationException, SAXException, IOException {
		xmlutils = new XmlUtils();
		formedURLLatAndLon = URLPostalCode.replace("#ADDRESSDETAILS", "1").replace("#LIMIT", "10")
				.replace("#POSTALCODE", postalCode);
		Response response = null;
		try {
			response = get(formedURLLatAndLon);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		resLatAndLon = response.asString();
		latAndLon = xmlutils.fetchByCountry(resLatAndLon, postalCode, "us");

		if (latAndLon.size() >= 1) {
			try {
				formedURLAddress = URLAddress.replace("#LAT", latAndLon.get(postalCode + "Lattitude")).replace("#LON",
						latAndLon.get(postalCode + "Longitude"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			Response response1 = get(formedURLAddress);
			resAddress = response1.asString();
			addressArray = xmlutils.fetchByLatnLon(resAddress,postalCode);

		}
		return addressArray;

	}
}
