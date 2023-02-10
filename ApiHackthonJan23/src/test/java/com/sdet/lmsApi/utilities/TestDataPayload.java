package com.sdet.lmsApi.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.sdet.lmsApi.stepdefinition.POJO_UpdateProgram;

public class TestDataPayload {

	public POJO_UpdateProgram createProgramPayload(String name, String desc, String status) {
		
		POJO_UpdateProgram createProgram = new POJO_UpdateProgram();
		 
		 DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS+mm:ss");
		 LocalDateTime now = LocalDateTime.now();
		 
		 createProgram.setProgramName(name);
		 createProgram.setProgramDescription(desc);
		 createProgram.setProgramStatus(status);
		 createProgram.setCreationTime(dateTimeFormatter.format(now));
		 createProgram.setLastModTime(dateTimeFormatter.format(now));
		 
		 return createProgram;
	}
}
