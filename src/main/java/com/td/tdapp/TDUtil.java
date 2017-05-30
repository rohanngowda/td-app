package com.td.tdapp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.commons.lang.StringEscapeUtils;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.value.ArrayValue;
import org.msgpack.value.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.common.base.Function;
import com.treasuredata.client.ExponentialBackOff;
import com.treasuredata.client.TDClient;
import com.treasuredata.client.model.TDJobRequest;
import com.treasuredata.client.model.TDJobSummary;
import com.treasuredata.client.model.TDResultFormat;

@Component
public class TDUtil {

	private static String RANGE_TEMPLATE = "TD_TIME_RANGE(time, %s, %s) ";

	private String getQuery(Args input) {
		QueryBuilder query = new QueryBuilder().from(input.getTable());
		for(String col : input.getColumns()) {
			query = query.column(col);
		}
		if(!StringUtils.isEmpty(input.getMin()) && !StringUtils.isEmpty(input.getMin())) {
			query.where(String.format(RANGE_TEMPLATE, input.getMin(), input.getMax()));
		}
		if(!StringUtils.isEmpty(input.getLimit())) {
			query.limit(input.getLimit());
		}
		return query.toString();
	}

	@SuppressWarnings("unchecked")
	public TableInfo fetch(Args input) {
		TDClient client = TDClient.newClient();
		TableInfo tableInfo = new TableInfo();
		try {

			String sql = getQuery(input);
			System.out.println("Query is " + sql);
			//	String jobId = client.submit(TDJobRequest.newHiveQuery(input.database, input.getQuery()));
			String jobId = client.submit(TDJobRequest.newHiveQuery(StringEscapeUtils.escapeSql(input.getDatabase()), sql));

			// Wait until the query finishes
			ExponentialBackOff backoff = new ExponentialBackOff();
			TDJobSummary job = client.jobStatus(jobId);
			while(!job.getStatus().isFinished()) {
				Thread.sleep(backoff.nextWaitTimeMillis());
				job = client.jobStatus(jobId);
			}
			List<String> columnName = new ArrayList<String>(); 
			List<List<String>> rowsTable =  new ArrayList<List<String>>();

			for(String c : input.getColumns()) {
				columnName.add(c);	
			}
			tableInfo.setColumns(columnName);
			client.jobResult(jobId, TDResultFormat.MESSAGE_PACK_GZ, new Function() {
				@Override
				public Object apply(Object arg0) {
					// TODO Auto-generated method stub
					try {
						MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(new GZIPInputStream((InputStream)arg0));
						while(unpacker.hasNext()) {
							// Each row of the query result is array type value (e.g., [1, "name", ...])
							ArrayValue array = unpacker.unpackValue().asArrayValue();
							List<String> rowsTableData =  new ArrayList<String>();
							for(Value value : array.list()) {
								rowsTableData.add(value.toString());
							}

							rowsTable.add(rowsTableData);
							//		return null;
						}
					}catch(Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			});
			tableInfo.setRows(rowsTable);
			System.out.println("--------------------------------------------------------------");
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			client.close();
		}

		return tableInfo;
	}

}
