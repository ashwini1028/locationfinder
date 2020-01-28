package module;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import jxl.read.biff.BiffException;
import utils.ExcelUtils;
import utils.RestUtils;
import utils.XmlUtils;

public class ThreadLogic extends XmlUtils  implements Runnable{
	
	RestUtils restutils;
	ExcelUtils excelUtils;
	String postCode;
	FileWriter fw;
	private String file = "TestData.csv";

	
	public ThreadLogic(String postCode){

		this.postCode = postCode;

	}


	@Override
	public void run() {
		restutils = new RestUtils();


		try {
			File f = new File(file);
			fw = new FileWriter(f.getAbsoluteFile(),true);
			String header = "\"PostalCode\",\"AddressLine\",\"State\",\"Country\",\"Country_Code\",\"Lat/Lon\"";
			//fw.append(header);

				try {
					ArrayList<String> addressArray = restutils.fetchAddressByLatNLon(postCode);
					if (addressArray.size() >= 1) {
						
						System.out.println(postCode + "=" + addressArray);
						
						fw.append("\n");
						fw.append("\"" + postCode + "\"");
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
					fw.append("\"" + postCode + "\"");
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

			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



}
