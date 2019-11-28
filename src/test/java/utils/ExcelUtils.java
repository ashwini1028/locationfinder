package utils;

import java.io.IOException;

import java.io.File;
import java.util.Iterator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.common.Logger;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelUtils {
	protected Logger logger = Logger.getLogger(ExcelUtils.class);
	public  boolean recdFound = false;

	//  String Region =
	// FileConfig.getInstance().getStringConfigValue("region");
	//  String WorkBookName =
	// FileConfig.getInstance().getStringConfigValue("WorkBookName");
	//  String filePath = ".\\datamodel\\" + Region + "\\" + WorkBookName +
	// ".xls";

	//  String sheetName =
	// FileConfig.getInstance().getStringConfigValue("sheetname");

	private Workbook workbook;
	private WritableWorkbook writableWorkbook;
	private String sheetName = "sheet1";
	Sheet sheet, tcSheet;
	Sheet testDataSheet;
	Sheet testStepSheet;
	Cell cell;
	int tot_Rows;
	int tot_Cols;
	int tot_testData_rows;
	int tot_test_steps_cols;
	String testID;
	private String filePath = "C:\\Users\\708610\\Documents\\Macys\\locationfinder\\postalcodes.xls";



	public Map<String, String> getTestDatas(String testId, String sheetNameValue)
			throws BiffException, IOException {
		recdFound = false;
		int rownum = 0;

		filePath = "C:\\Users\\708610\\Desktop\\TestData.xls";

		Map<String, Integer> testDataColumns = new HashMap<String, Integer>();
		Map<String, String> testData = new HashMap<String, String>();

		Workbook work = Workbook.getWorkbook(new File(filePath));
		for (String sheetName : work.getSheetNames()) {

			if (sheetName.startsWith(sheetNameValue) || sheetName.startsWith("ScenarioList")) {
				int noOfRows = work.getSheet(sheetName).getRows();
				int noOfColumns = 0;
				for (int i = 0; i < work.getSheet(sheetName).getColumns(); i++) {

					if (!work.getSheet(sheetName).getCell(i, 0).getContents().isEmpty()) {
						noOfColumns++;
					}
				}

				search: for (int j = 0; j < noOfRows; j++) {
					for (int k = 0; k < noOfColumns; k++) {
						String cell = work.getSheet(sheetName).getCell(k, j).getContents();
						if (cell.equalsIgnoreCase(testId)) {
							rownum = j;
							recdFound = true;
							break search;
						}
					}
				}

				if (recdFound == true) {
					for (int i = 1; i <= noOfColumns; i++) {
						testDataColumns.put(work.getSheet(sheetName).getCell(i - 1, 0).getContents(), i - 1);

						for (Object key : testDataColumns.keySet()) {
							try {
								if(!work.getSheet(sheetName).getCell(i - 1, rownum).getContents().isEmpty()) {
								testData.put(work.getSheet(sheetName).getCell(i - 1, 0).getContents(),
										work.getSheet(sheetName).getCell(i - 1, rownum).getContents());
								System.out.println(testData);
								}

							} catch (NullPointerException ne) {
								testData.put(key.toString(), "");
							}
						}
					}

				}
			}

			recdFound = false;
		}
		return testData;
	}

	public Map<Integer, String> getAllPostalCode(String sheetNameValue) throws BiffException, IOException {

		Workbook work = Workbook.getWorkbook(new File(filePath));
		Map<Integer, String> testData = new HashMap<Integer, String>();
		for (String sheetName : work.getSheetNames()) {

			if (sheetName.startsWith(sheetNameValue)) {
				

				for (int i = 1; i < work.getSheet(sheetName).getRows(); i++) {
					if(!work.getSheet(sheetName).getCell(0, i).getContents().isEmpty()) {
						
					testData.put(i, work.getSheet(sheetName).getCell(0, i).getContents());
					
					}

				}
				
			}
			work.close();
		}

		return testData;

	}

	public void writeToExcel(int col, int row ,String data) throws IOException, BiffException, RowsExceededException, WriteException  {
		WritableSheet excelSheet;
		
		File inp = new File(filePath);
		workbook = Workbook.getWorkbook(inp);
        writableWorkbook = Workbook.createWorkbook(inp, workbook);
        
        excelSheet = writableWorkbook.getSheet("Locations");
        Label label = new Label(col, row, data);
        excelSheet.addCell(label);
        if(workbook != null) {
        	workbook.close();
        }
        writableWorkbook.write();
        writableWorkbook.close();
    }
        
		
		
		
		
	}
