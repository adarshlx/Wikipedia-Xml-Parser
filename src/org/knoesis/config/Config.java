package org.knoesis.config;

public class Config {

	public static String OUTPUT_FILE_NAME;
	public static String INPUT_FILE_NAME;
	
	public static void setConstants(String[] args) {
		INPUT_FILE_NAME = args[0];
		OUTPUT_FILE_NAME = args[1];
	}
	
}
