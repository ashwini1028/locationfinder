package module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import utils.ExcelUtils;

public class JobRun {
	
	public static void main(String[] args) throws  IOException {
		
		ExcelUtils excelUtils = new ExcelUtils();
	//	Map<Integer, String> postalCodeMap = excelUtils.getAllPostalCode("Locations");
		ArrayList<Logic> jobs = new ArrayList<Logic>();
		
		//for(int i = 2; i <= postalCodeMap.size(); i++) {
			
		//	Logic logic = new Logic(postalCodeMap.get(i));
			
		//	jobs.add(logic);
			
		
			ThreadPoolExecutor svc = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
			for(Logic lg : jobs) {
		  //     	svc.submit(lg);
		       }
			svc.shutdown();
	}

 }

