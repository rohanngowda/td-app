package com.td.tdapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.jetty.util.log.Log;
import org.springframework.util.StringUtils;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.internal.Lists;

public class Args {
	private static String template = "select %s from %s";
	
	
	@Parameter
	public List<String> parameters = Lists.newArrayList();

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	@Parameter(names={"--database", "-d"}, required = true)
	private String database;

	@Parameter(names={"--table", "-t"}, required = true)
	private String table;

	@Parameter(names={"--columns", "-c"}, required = true)
	private List<String> columns = new ArrayList<String>();
	
	@Parameter(names={"--min", "-m"}, validateWith = NumberValidator.class)
	private String min;

	@Parameter(names={"--max", "-M"}, validateWith = NumberValidator.class)
	private String max;
	
	@Parameter(names={"--limt", "-l"}, validateWith = NumberValidator.class)
	private String limit;
	
	public static class NumberValidator implements IParameterValidator {
		public void validate(String name, String value) throws ParameterException {
			if(!StringUtils.isEmpty(value)) {
				try {
					Integer.valueOf(value);
				}catch(Exception e) {
					throw new ParameterException("Parmameter " + name + " should contain only digits");
				}
			}
		}
	}
	
	
}