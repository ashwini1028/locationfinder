package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import jxl.read.biff.BiffException;

public class CsvUtils {

	public void csvWriter() throws IOException, BiffException {
		ExcelUtils excelUtils = new ExcelUtils();
		Map<Integer, String> postalCodeMap = excelUtils.getAllPostalCode("Sheet1");

		FileWriter fw = new FileWriter("C:\\Users\\708610\\Documents\\Macys\\locationfinder\\src\\test\\resources\\TestData.csv");

		fw.append("name1,name2,name3,name4");

		for (int i = 0; i < 1000; i++) {

			fw.append("\n");
			fw.append(postalCodeMap.get(0));
			fw.append(",");
			fw.append("\"" + postalCodeMap.get(1) + "\"");
			fw.append(",");
			fw.append(postalCodeMap.get(2));
			fw.append(",");
			fw.append(postalCodeMap.get(3));

		}

		fw.flush();
		fw.close();

	}

}
