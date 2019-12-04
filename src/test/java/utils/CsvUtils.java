package utils;

import java.io.FileWriter;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.io.IOException;
import java.util.Map;

public class CsvUtils {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

		String xml = "<reversegeocode timestamp=\"Fri, 29 Nov 19 06:56:40 +0000\" attribution=\"Data © OpenStreetMap contributors, ODbL 1.0. http://www.openstreetmap.org/copyright\" querystring=\"format=xml&lat=31.6837866391859&lon=-82.0357575449561&zoom=100&addressdetails=1\"> <result place_id=\"72158959\" osm_type=\"way\" osm_id=\"9530605\" lat=\"31.6845210607988\" lon=\"-82.0357989208215\" boundingbox=\"31.683548,31.684583,-82.040824,-82.033385\"> Tetlow, Wayne County, Georgia, 31555, United States </result> <addressparts> <hamlet>Tetlow</hamlet> <county>Wayne County</county> <state>Georgia</state> <postcode>31555</postcode> <country>United States</country> <country_code>us</country_code> </addressparts> </reversegeocode>";
		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(xml.replace("&", "&amp;")));
		Document doc = dBuilder.parse(is);
		doc.getDocumentElement().normalize();
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("result");
		
			String nNode = nList.item(0).getTextContent();
			System.out.println(nNode);
		

		}

	}

