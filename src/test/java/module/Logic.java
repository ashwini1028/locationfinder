package module;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import jxl.read.biff.BiffException;
import utils.ExcelUtils;
import utils.RestUtils;
import utils.XmlUtils;

public class Logic extends XmlUtils {

	RestUtils restutils;
	ExcelUtils excelUtils;
	private String filePath = "./src\\test\\resources\\TestData.csv";

	Map<String, ArrayList<String>> addressMap = null;

	public static void main(String[] args) throws BiffException, IOException {

		Logic logic = new Logic();
		logic.csvWriter();

	}

	public void csvWriter() throws BiffException, IOException {
		excelUtils = new ExcelUtils();
		restutils = new RestUtils();
		Map<Integer, String> postalCodeMap = excelUtils.getAllPostalCode("Sheet1");

		FileWriter fw = new FileWriter(filePath);
		String header = "\"PostalCode\",\"AddressLine\",\"State\",\"Country\",\"Country_Code\",\"Lat/Lon\"";
		fw.append(header);

		for (int i = 2; i <= postalCodeMap.size() + 1; i++) {
			try {
				ArrayList<String> addressArray = restutils.fetchAddressByLatNLon(postalCodeMap.get(i));
				if (addressArray.size() >= 1) {

					System.out.println(postalCodeMap.get(i) + "=" + addressArray);

					fw.append("\n");
					fw.append("\"" + postalCodeMap.get(i) + "\"");
					fw.append(",");
					fw.append("\"" + addressArray.get(0) + "\"");
					fw.append(",");
					fw.append("\"" + addressArray.get(1) + "\"");
					fw.append(",");
					fw.append("\"" + addressArray.get(2) + "\"");
					fw.append(",");
					fw.append("\"" + addressArray.get(3) + "\"");
					fw.append(",");
					fw.append("\"" + addressArray.get(4) +","+ addressArray.get(5) + "\"");
					fw.flush();
				}

			} catch (Exception e) {

				fw.append("\n");
				fw.append("\"" + postalCodeMap.get(i) + "\"");
				fw.append(",");
				fw.append("No Record");
				fw.append(",");
				fw.append("No Record");
				fw.append(",");
				fw.append("No Record");
				fw.append(",");
				fw.append("No Record");
				fw.append(",");
				fw.append("No Record");
				fw.flush();
			}

		}

		fw.close();

	}

}
