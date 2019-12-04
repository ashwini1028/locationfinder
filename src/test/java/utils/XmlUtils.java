package utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlUtils {

	DocumentBuilder dBuilder;
	InputSource is;
	Document doc;
	public static Map<String, String> latAndLon = null;
	ArrayList<String> addressArray;

	public Map<String, String> fetchByCountry(String xml, String postalCode, String countryCode)
			throws ParserConfigurationException, SAXException, IOException {
		
		latAndLon = new HashMap<String, String>();

		dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		is = new InputSource();
		is.setCharacterStream(new StringReader(xml.replace("&", "&amp;")));
		doc = dBuilder.parse(is);
		doc.getDocumentElement().normalize();
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("place");
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				if (eElement.getElementsByTagName("country_code").item(0).getTextContent()
						.equalsIgnoreCase(countryCode)) {

					try {
						latAndLon.put(postalCode + "Lattitude", eElement.getAttribute("lat"));
						latAndLon.put(postalCode + "Longitude", eElement.getAttribute("lon"));
					} catch (Exception e) {

						e.printStackTrace();
					}

				}

			}

		}
		return latAndLon;
	}

	public ArrayList<String> fetchByLatnLon(String xml, String postalCode) throws ParserConfigurationException, SAXException, IOException {

		addressArray = new ArrayList<String>();

		dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		is = new InputSource();
		is.setCharacterStream(new StringReader(xml.replace("&", "&amp;")));
		doc = dBuilder.parse(is);
		doc.getDocumentElement().normalize();
		NodeList resultTag = doc.getElementsByTagName("result");
		NodeList addressPartsTag = doc.getElementsByTagName("addressparts");

		String result = resultTag.item(0).getTextContent();

		Node addressParts = addressPartsTag.item(0);
		if (addressParts.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) addressParts;

			String country = eElement.getElementsByTagName("country").item(0).getTextContent();
			String state;
			try {
				state = eElement.getElementsByTagName("state").item(0).getTextContent();
			} catch (Exception e) {
				e.printStackTrace();
				try {
					state = eElement.getElementsByTagName("state_district").item(0).getTextContent();
				} catch (Exception e1) {
					state = eElement.getElementsByTagName("county").item(0).getTextContent();
				}
			}

				String country_code = eElement.getElementsByTagName("country_code").item(0).getTextContent();
				String fullAddressLine = result.split(", " + state)[0];

				addressArray.add(fullAddressLine);
				addressArray.add(state);
				addressArray.add(country);
				addressArray.add(country_code);
				addressArray.add(latAndLon.get(postalCode + "Lattitude"));
				addressArray.add(latAndLon.get(postalCode + "Longitude"));
			

		}
		return addressArray;

	}

}
