package module;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.opencsv.CSVWriter;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import utils.ExcelUtils;
import utils.RestUtils;

public class Logic {

	RestUtils restutils;
	ExcelUtils excelUtils;
	private Workbook workbook;
	private WritableWorkbook writableWorkbook;
	private String sheetName = "sheet1";
	private String filePath = "C:\\Users\\708610\\Desktop\\TestData1.xls";

	Map<String, ArrayList<String>> addressMap = null;

	/*
	 * String postalCode;
	 * 
	 * Logic(String postalCode){
	 * 
	 * this.postalCode = postalCode; }
	 */

	/*
	 * public void run() {
	 * 
	 * RestUtils restutils = new RestUtils(); addressMap = new HashMap<String,
	 * ArrayList<String>>();
	 * 
	 * ArrayList<String> addressArray = restutils.getAddressSeperately(postalCode);
	 * 
	 * System.out.println(postalCode+"="+addressArray);
	 * 
	 * addressMap.put(postalCode, addressArray);
	 * 
	 * 
	 * }
	 */

	public static void main(String[] args) throws BiffException, IOException, RowsExceededException, WriteException {

		Logic logic = new Logic();
		logic.csvWriter();

	}

	public void storeInAddressMap() throws BiffException, IOException, RowsExceededException, WriteException {
		excelUtils = new ExcelUtils();
		restutils = new RestUtils();
		addressMap = new HashMap<String, ArrayList<String>>();

		File inp = new File(filePath);
		workbook = Workbook.getWorkbook(inp);
		writableWorkbook = Workbook.createWorkbook(inp, workbook);
		WritableSheet excelSheet;

		excelSheet = writableWorkbook.getSheet("Sheet1");

		Map<Integer, String> postalCodeMap = excelUtils.getAllPostalCode("Sheet1");

		for (int i = 2; i <= postalCodeMap.size(); i++) {

			ArrayList<String> addressArray = restutils.getAddressSeperately(postalCodeMap.get(i));

			System.out.println(postalCodeMap.get(i) + "=" + addressArray);

			Label label = new Label(2, i, addressArray.get(0));

			excelSheet.addCell(label);
			// addressMap.put(postalCodeMap.get(i), addressArray);

		}
		if (workbook != null) {
			workbook.close();
		}
		writableWorkbook.write();
		writableWorkbook.close();

	}

	public void csvWriter() throws IOException, BiffException {
		excelUtils = new ExcelUtils();
		restutils = new RestUtils();
		Map<Integer, String> postalCodeMap = excelUtils.getAllPostalCode("Sheet1");

		FileWriter fw = new FileWriter(
				"C:\\Users\\708610\\Documents\\Macys\\locationfinder\\src\\test\\resources\\TestData.csv");

		fw.append("PostalCode,AddressLine,State,Country,Country_Code");

		for (int i = 2; i <= postalCodeMap.size() + 1; i++) {

			try {
				ArrayList<String> addressArray = restutils.getAddressSeperately(postalCodeMap.get(i));

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
				fw.flush();

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
				fw.flush();

			}

		}

		fw.close();

	}

}
