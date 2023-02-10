package com.sdet.lmsApi.utilities;

public enum ApiResources {
	
	

	/**
	 *  resources for program & Batch
	 */
	AddProgram("saveprogram"),
	GetProgramById("programs"),
	GetAllProgram("allPrograms"),
	DeleteProgramByName("deletebyprogname"),
	DeleteProgramById("deletebyprogid"),
	UpdateProgramById("putprogram"),
	UpdateProgramByName("program"),
	
	
	AddBatch("batches"),
	GetBatchById("batches/batchId"),
	GetAllBatches("batches"),
	GetBatchesByName("batches/batchName"),
	GetBatchesByProgramId("batches/program"),
	DeleteBatchById("batches"),
	UpdateBatchById("batches/");
	
	
	
	private String source;
	
	ApiResources(String resource){
		this.source = resource;
	}
	
	public String getResource() {
		return source;
	}
}