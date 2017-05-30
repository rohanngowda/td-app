package com.td.tdapp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

public class QueryBuilder {

	private List<String> columns = new ArrayList<String>();

	private List<String> tables = new ArrayList<String>();

	private List<String> wheres = new ArrayList<String>();

	private String limit;
	
	public QueryBuilder() {

	}

	public QueryBuilder(String table) {
		tables.add(table);
	}

	private void appendList(StringBuilder sql, List<String> list, String init,
			String sep) {
		boolean first = true;
		for (String s : list) {
			if (first) {
				sql.append(init);
			} else {
				sql.append(sep);
			}
			sql.append(s);
			first = false;
		}
	}

	public QueryBuilder column(String name) {
		columns.add(name);
		return this;
	}

	public QueryBuilder from(String table) {
		tables.add(table);
		return this;
	}

	@Override
	public String toString() {

		StringBuilder sql = new StringBuilder("select ");

		if (columns.size() == 0) {
			sql.append("*");
		} else {
			appendList(sql, columns, "", ", ");
		}
		
		appendList(sql, tables, " from ", ", ");
		appendList(sql, wheres, " where ", " and ");
		if(!StringUtils.isEmpty(limit)) {
			sql.append(" limit " + limit);
		}
		return sql.toString();
	}

	public QueryBuilder where(String expr) {
		wheres.add(expr);
		return this;
	}
	
	public QueryBuilder limit(String expr) {
		this.limit = expr;
		return this;
	}
}
