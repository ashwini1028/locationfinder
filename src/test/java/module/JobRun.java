package module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import jxl.read.biff.BiffException;
import utils.ExcelUtils;

public class JobRun {
	static ExcelUtils excelUtils;
	
	public static void main(String[] args) throws  IOException, BiffException {
		
		excelUtils = new ExcelUtils();
		Map<Integer, String> postalCodeMap = excelUtils.getAllPostalCode("Sheet1");
		ArrayList<ThreadLogic> jobs = new ArrayList<ThreadLogic>();
		for (int i = 2; i <= postalCodeMap.size() + 1; i++) {
			
			ThreadLogic logic = new ThreadLogic(postalCodeMap.get(i));
			
			jobs.add(logic);
			
		}
			ThreadPoolExecutor svc = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
			for(ThreadLogic lg : jobs) {
		      	svc.submit(lg);
		       }
			svc.shutdown();
	}

 }

