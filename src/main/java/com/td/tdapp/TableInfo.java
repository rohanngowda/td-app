package com.td.tdapp;

import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableInfo {
	
	private List<String> columns;
	private List<List<String>> rows;
	private static final Logger log = LoggerFactory.getLogger(TdAppApplication.class);
	
	public List<String> getColumns() {
		return columns;
	}
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	public List<List<String>> getRows() {
		return rows;
	}
	public void setRows(List<List<String>> rows) {
		this.rows = rows;
	}
	
	/*
	 * To Display the Rows and columns using this method.
	 * Instead CSV writter to directly write in spread sheet
	 * 
	 */
	public void printTable(){
		System.out.println("----------------------------------------------------------");
		for(String s: this.getColumns())
			System.out.format("%s\t\t\t",s);
		System.out.println("----------------------------------------------------------");
		for(List<String> strList: this.getRows()){
			for(String s: strList)
				System.out.format("%s\t\t",s);
		System.out.println();	
		}	
		System.out.format("\n----------------------------------------------------------\n");
	}
	
}
