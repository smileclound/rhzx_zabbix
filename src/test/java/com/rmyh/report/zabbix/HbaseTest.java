package com.rmyh.report.zabbix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.ColumnRangeFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import com.rmyh.report.bean.XNDataBean;
import com.rmyh.report.bean.XNDataBeanInterface;
import com.rmyh.report.dao.HbaseConnectionFactory;
import com.rmyh.report.service.hbase.HbaseTools;

//import redis.clients.jedis.ScanResult;

public class HbaseTest {

	public static final String hbaseTableName = "zabbix1";
	public static final String url = "127.0.0.1";
	static Configuration conf = null;
	private static final String ZKconnect = "127.0.0.1:2181";
	static {
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", ZKconnect);
	}

	public static void main(String[] args) throws Exception {
		HbaseConnectionFactory.init(url,"2181");
		querytest("23726");

	}

	public static void querytest(String itemId) throws Exception {
		Scan scan = new Scan();
		// scan.setStartRow(Bytes.toBytes("user"));
		// scan.setStopRow(Bytes.toBytes("zk002"));
		// scan.setTimeRange(1522159218971l, 1522159218973l);
		FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
		SingleColumnValueFilter filterpitem = new SingleColumnValueFilter(Bytes.toBytes("basic_info"),
				Bytes.toBytes("itemId"), CompareOp.EQUAL, itemId.getBytes());
		filterpitem.setFilterIfMissing(true);
		filterList.addFilter(filterpitem);
		QualifierFilter filterval = new QualifierFilter(CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("value")));
		filterList.addFilter(filterval);
		scan.setFilter(filterList);
		// ResultScanner resultScann1 = getResultScann(hbaseTableName, scan);
		List list = getValueList(hbaseTableName, scan);

	}

	public static ResultScanner getResultScann(String tableName, Scan scan) throws Exception {

		ResultScanner rs = null;
		HTable htable = new HTable(conf, tableName);
		try {
			rs = htable.getScanner(scan);
			for (Result r : rs) {
				for (KeyValue kv : r.list()) {

					// System.out.println(Bytes.toString(kv.getRow()));
					// System.out.println(Bytes.toString(kv.getFamily()));
					System.out.println(Bytes.toString(kv.getQualifier()));
					System.out.println(Bytes.toString(kv.getValue()));
					// System.out.println(kv.getTimestamp());
				}
			}
		} finally {
			rs.close();
		}
		return rs;
	}

	public static List getValueList(String tableName, Scan scan) throws Exception {
		List list = new ArrayList();
		ResultScanner rs = null;
		HTable htable = new HTable(conf, tableName);
		try {
			rs = htable.getScanner(scan);
			OUT: for (Result r : rs) {
				for (KeyValue kv : r.list()) {
					if (isInteger(Bytes.toString(kv.getValue()))) {
						list.add(Integer.parseInt(Bytes.toString(kv.getValue())));
					} else {
						break OUT;
					}
				}
			}
		} finally {
			rs.close();
		}
		System.out.println(list);
		System.out.println("max:" + Collections.max(list));
		System.out.println("min:" + Collections.min(list));
		// System.out.println("avg"+list.);
		
		return list;
	}

	public static int getmax_r(List<Integer> list, int i) {
		return Collections.max(list);
	}

	public static String getmax_r(List<String> list, String str) {
		return "";
	}

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

}
