package com.sdet.lmsApi.stepdefinition;

public class ProgramData {
	
	private int programId;
	private String programName;
	
	public ProgramData(int programId, String programName) {
		this.programId = programId;
		this.programName = programName;
	}
	public int getProgramId() {
		return programId;
	}
	public String getProgramName() {
		return programName;
	}
	
	public String toString() {
		return programId + " - " + programName;
	}
}
