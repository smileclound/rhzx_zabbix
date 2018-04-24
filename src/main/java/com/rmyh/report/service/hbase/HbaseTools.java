package com.rmyh.report.service.hbase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.log4j.Logger;

import com.rmyh.report.bean.AlertBean;
import com.rmyh.report.bean.ItemBean;
import com.rmyh.report.bean.TriggerBean;
import com.rmyh.report.bean.XNDataBean;
//import com.rmyh.report.bean.XNDataBeanInterface;
import com.rmyh.report.controller.General;
import com.rmyh.report.dao.HbaseConnection;
import com.rmyh.report.dao.HbaseConnectionFactory;

public class HbaseTools implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(HbaseTools.class);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");

	// Iterator<Tuple2<String, DataBean>> it

	@SuppressWarnings("deprecation")
	public void putsXNDataBean(List<XNDataBean> dataBeans, String tableName, long datePre, long dateNex) {
		if (dataBeans == null) {
			dataBeans = new ArrayList<XNDataBean>();
		}
		if (dataBeans != null && tableName != null && !tableName.trim().equals("")) {

			HTable table = null;
			List<Put> puts = null;
			HbaseConnection hbCon = null;
			ExecutorService pool = null;

			try {
				hbCon = HbaseConnectionFactory.getHbaseConnection();
				pool = Executors.newFixedThreadPool(50);
				puts = new ArrayList<Put>();
				// System.out.println(tableName+""+hbCon+""+pool);
				table = (HTable) hbCon.getConnection().getTable(TableName.valueOf(tableName), pool);

				table.setWriteBufferSize(104857600);

				table.setAutoFlush(false);
				if (dataBeans.size() != 0) {
					for (XNDataBean dataBean : dataBeans) {
						puts.add(putDataColumns(dataBean, tableName));
					}
				}
				table.put(puts);
				table.flushCommits();
			} catch (Exception e) {
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
				logger.error(sdf.format(date) + " HbaseTools.putsDataBean, ERROR: ", e);
			} finally {
				// 閿熸埅闂唻鎷�
				if (table != null) {
					try {
						table.close();
					} catch (IOException e) {
						Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
						logger.error(sdf.format(date) + " HbaseTools.putsDataBean, ERROR: ", e);
					}
				}

				puts.clear();
				puts = null;
				logger.info(new Date()+""+"Successfully insert performance data;");
				writeDateLog(datePre, dateNex, "performance data", dataBeans.size());
				dataBeans.clear();
				dataBeans = null;
				pool.shutdown();
				HbaseConnectionFactory.releaseHbaseConnection(hbCon);

				// try {
				// hbCon.getConnection().close();
				// System.out.println("hbcon close succ!");
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}
		}

	}

	public void writeDateLog(long datePre, long dateNex, String str, int size) {
		FileWriter fwriterlog = null;
		FileWriter fwriterdate = null;
		String property = System.getProperty("user.dir");
		File filelog = new File(property + "/dateFns.log");
		File filedate = new File(property + "/datePre.properties");
		try {
			fwriterlog = new FileWriter(filelog, true);
			fwriterdate = new FileWriter(filedate);
			String contentlog = "\n" + "|finished insert " + size + " " + str + " between " + datePre + " and "
					+ dateNex;
			String contentdate = "datePre=" + String.valueOf(dateNex);
			fwriterlog.write(contentlog);
			fwriterdate.write(contentdate);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				fwriterlog.flush();
				fwriterdate.flush();
				fwriterlog.close();
				fwriterdate.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void writeDateLog(String dateNow, String str, int size) {
		FileWriter fwriterlog = null;
		// FileWriter fwriterdate = null;
		String property = System.getProperty("user.dir");
		File filelog = new File(property + "/dateFns.log");
		// File filedate = new File(property+"/datePre.properties");
		try {
			fwriterlog = new FileWriter(filelog, true);
			// fwriterdate = new FileWriter(filedate);
			String contentlog = "\n" + "|finished insert " + size + " " + str + " at " + dateNow;
			// String contentdate = "itemDatePre="+String.valueOf(dateNex);
			fwriterlog.write(contentlog);
			// fwriterdate.write(contentdate);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				fwriterlog.flush(); // fwriterdate.flush();
				fwriterlog.close(); // fwriterdate.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void putsItemDataBean(List<ItemBean> itemBeans, String tableName, String insertItemClock) {
		if (itemBeans == null) {
			itemBeans = new ArrayList<ItemBean>();
		}
		if (tableName != null && !tableName.trim().equals("")) {

			HTable table = null;
			List<Put> puts = null;
			HbaseConnection hbCon = null;
			ExecutorService pool = null;

			try {
				hbCon = HbaseConnectionFactory.getHbaseConnection();
				pool = Executors.newFixedThreadPool(50);
				puts = new ArrayList<Put>();
				table = (HTable) hbCon.getConnection().getTable(TableName.valueOf(tableName), pool);

				table.setWriteBufferSize(104857600);

				table.setAutoFlush(false);
				if (itemBeans.size() != 0) {
					for (ItemBean itemBean : itemBeans) {
						puts.add(putDataColumns(itemBean, tableName, insertItemClock));
					}
				}
				table.put(puts);
				table.flushCommits();
			} catch (Exception e) {
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
				logger.error(sdf.format(date) + " HbaseTools.putsDataBean, ERROR: ", e);
			} finally {
				// 閿熸埅闂唻鎷�
				if (table != null) {
					try {
						table.close();
					} catch (IOException e) {
						Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
						logger.error(sdf.format(date) + " HbaseTools.putsDataBean, ERROR: ", e);
					}
				}

				puts.clear();
				puts = null;
				logger.info(new Date()+""+"Successfully insert items;");
				writeDateLog(insertItemClock, "items", itemBeans.size());
				itemBeans.clear();
				itemBeans = null;
				pool.shutdown();
				HbaseConnectionFactory.releaseHbaseConnection(hbCon);

				// try {
				// hbCon.getConnection().close();
				// System.out.println("hbcon close succ!");
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}
		}

	}

	@SuppressWarnings("deprecation")
	public void putsTriggerDataBean(List<TriggerBean> triggerBeans, String tableName, String insertTriggerClock) {
		if (triggerBeans == null) {
			triggerBeans = new ArrayList<TriggerBean>();
		}
		if (tableName != null && !tableName.trim().equals("")) {

			HTable table = null;
			List<Put> puts = null;
			HbaseConnection hbCon = null;
			ExecutorService pool = null;

			try {
				hbCon = HbaseConnectionFactory.getHbaseConnection();
				pool = Executors.newFixedThreadPool(50);
				puts = new ArrayList<Put>();
				table = (HTable) hbCon.getConnection().getTable(TableName.valueOf(tableName), pool);

				table.setWriteBufferSize(104857600);

				table.setAutoFlush(false);
				if (triggerBeans.size() != 0) {
					for (TriggerBean triggerBean : triggerBeans) {
						puts.add(putDataColumns(triggerBean, tableName, insertTriggerClock));
					}
				}
				table.put(puts);
				table.flushCommits();
			} catch (Exception e) {
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
				logger.error(sdf.format(date) + " HbaseTools.putsDataBean, ERROR: ", e);
			} finally {
				// 閿熸埅闂唻鎷�
				if (table != null) {
					try {
						table.close();
					} catch (IOException e) {
						Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
						logger.error(sdf.format(date) + " HbaseTools.putsDataBean, ERROR: ", e);
					}
				}

				puts.clear();
				puts = null;
				logger.info(new Date()+""+"Successfully insert triggers;");
				writeDateLog(insertTriggerClock, "triggers", triggerBeans.size());
				triggerBeans.clear();
				triggerBeans = null;
				pool.shutdown();
				HbaseConnectionFactory.releaseHbaseConnection(hbCon);

				// try {
				// hbCon.getConnection().close();
				// System.out.println("hbcon close succ!");
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}
		}

	}

	public XNDataBean getValueByTransDetails(String tableName, String key) {
		// TODO
		HTable table = null;
		HbaseConnection hbCon = null;
		Class<? extends XNDataBean> beanClass = null;
		Field[] fields = null;
		XNDataBean tb = new XNDataBean();
		beanClass = (Class<? extends XNDataBean>) tb.getClass();
		fields = beanClass.getFields();
		String rowKey = key;
		Get get = null;
		ExecutorService pool = Executors.newFixedThreadPool(50);
		try {
			hbCon = HbaseConnectionFactory.getHbaseConnection();
			if (tableName != null && !tableName.equals("")) {
				table = (HTable) hbCon.getConnection().getTable(TableName.valueOf(tableName.trim()), pool);
				get = new Get(Bytes.toBytes(rowKey));
				get.setCacheBlocks(true);
				Result result = table.get(get);
				Cell[] rawCells = result.rawCells();
				if (rawCells != null) {
					for (Cell cell : rawCells) {
						for (Field field : fields) {
							if (field.getName().equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
								try {
									Class<?> type = field.getType();
									if (type.equals(int.class)) {
										String string = Bytes.toString(CellUtil.cloneValue(cell));
										int num = Integer.parseInt(string);
										field.set(tb, num);
									}
									if (type.equals(String.class)) {
										String string = Bytes.toString(CellUtil.cloneValue(cell));
										field.set(tb, string);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
					if (tb.getKey().equals(key)) {
						return tb;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 閿熸埅鎲嬫嫹閿熸枻鎷烽敓鎺ョ鎷�
				pool.shutdown();
				// 閿熸埅闂唻鎷�
				if (table != null) {
					table.close();
				}
				// 閿熼叺鍑ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
				HbaseConnectionFactory.releaseHbaseConnection(hbCon);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Put putDataColumns(XNDataBean dataBean, String tableName) {
		// 閿熸枻鎷烽敓鏂ゆ嫹TransMonResultBean閿熸枻鎷�

		Class<? extends XNDataBean> beanClass = null;
		Field[] fields = null;
		// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓琛楄鎷�
		Put put = null;
		// 閿熸枻鎷烽敓鐭尅鎷烽敓鏂ゆ嫹閿熸枻鎷风紭閿熸枻鎷烽敓鏂ゆ嫹閿燂拷

		try {

			if (dataBean instanceof XNDataBean) {
				XNDataBean tb = (XNDataBean) dataBean;
				beanClass = (Class<? extends XNDataBean>) tb.getClass();
				fields = beanClass.getFields();
				String familyName = "";
				String fieldName = "";
				String value = "";

				if (!tb.getKey().equals("")) {
					put = new Put(Bytes.toBytes(tb.getKey()));
					put.setTTL(Long.parseLong("8640000000"));
					for (Field field : fields) {
						fieldName = field.getName();
						familyName = (String) XNDataBean.map.get(fieldName);
						if (familyName != null && familyName != "" && field.get(tb) != null) {
							value = new String(String.valueOf(field.get(tb)));
							if (value != null && !value.trim().equals("")) {
								put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(fieldName),
										Bytes.toBytes(value));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
			logger.error(sdf.format(date) + " HbaseTools.putDataColumns, ERROR: ", e);
		}
		return put;

	}

	public Put putDataColumns(ItemBean db, String tableName, String insertItemClock) {
		// 閿熸枻鎷烽敓鏂ゆ嫹TransMonResultBean閿熸枻鎷�

		Class<? extends ItemBean> beanClass = null;
		Field[] fields = null;
		// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓琛楄鎷�
		Put put = null;
		// 閿熸枻鎷烽敓鐭尅鎷烽敓鏂ゆ嫹閿熸枻鎷风紭閿熸枻鎷烽敓鏂ゆ嫹閿燂拷

		try {

			if (db instanceof ItemBean) {
				ItemBean tb = (ItemBean) db;
				beanClass = (Class<? extends ItemBean>) tb.getClass();
				fields = beanClass.getFields();
				new Date();
				String familyName = "";
				String fieldName = "";
				String value = "";

				if (!tb.getKey().equals("")) {
					put = new Put(Bytes.toBytes("T" + insertItemClock + tb.getKey()));
					put.setTTL(Long.parseLong("8640000000"));
					for (Field field : fields) {
						fieldName = field.getName();
						familyName = (String) ItemBean.map.get(fieldName);
						if (familyName != null && familyName != "" && field.get(tb) != null) {
							value = new String(String.valueOf(field.get(tb)));
							if (value != null && !value.trim().equals("")) {
								put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(fieldName),
										Bytes.toBytes(value));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
			logger.error(sdf.format(date) + " HbaseTools.putDataColumns, ERROR: ", e);
		}
		return put;

	}

	public Put putDataColumns(TriggerBean db, String tableName, String insertItemClock) {
		// 閿熸枻鎷烽敓鏂ゆ嫹TransMonResultBean閿熸枻鎷�

		Class<? extends TriggerBean> beanClass = null;
		Field[] fields = null;
		// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓琛楄鎷�
		Put put = null;
		// 閿熸枻鎷烽敓鐭尅鎷烽敓鏂ゆ嫹閿熸枻鎷风紭閿熸枻鎷烽敓鏂ゆ嫹閿燂拷

		try {

			if (db instanceof TriggerBean) {
				TriggerBean tb = (TriggerBean) db;
				beanClass = (Class<? extends TriggerBean>) tb.getClass();
				fields = beanClass.getFields();
				new Date();
				String familyName = "";
				String fieldName = "";
				String value = "";

				if (!tb.getKey().equals("")) {
					put = new Put(Bytes.toBytes("T" + insertItemClock + tb.getKey()));
					put.setTTL(Long.parseLong("8640000000"));
					for (Field field : fields) {
						fieldName = field.getName();
						familyName = (String) TriggerBean.map.get(fieldName);
						if (familyName != null && familyName != "" && field.get(tb) != null) {
							value = new String(String.valueOf(field.get(tb)));
							if (value != null && !value.trim().equals("")) {
								put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(fieldName),
										Bytes.toBytes(value));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
			logger.error(sdf.format(date) + " HbaseTools.putDataColumns, ERROR: ", e);
		}
		return put;

	}

	public Put putDataColumns(AlertBean db, String tableName) {
		// 閿熸枻鎷烽敓鏂ゆ嫹TransMonResultBean閿熸枻鎷�

		Class<? extends AlertBean> beanClass = null;
		Field[] fields = null;
		// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓琛楄鎷�
		Put put = null;
		// 閿熸枻鎷烽敓鐭尅鎷烽敓鏂ゆ嫹閿熸枻鎷风紭閿熸枻鎷烽敓鏂ゆ嫹閿燂拷

		try {

			if (db instanceof AlertBean) {
				AlertBean tb = (AlertBean) db;
				beanClass = (Class<? extends AlertBean>) tb.getClass();
				fields = beanClass.getFields();
				String familyName = "";
				String fieldName = "";
				String value = "";

				if (!tb.getKey().equals("")) {
					put = new Put(Bytes.toBytes(tb.getKey()));
					put.setTTL(Long.parseLong("8640000000"));
					for (Field field : fields) {
						fieldName = field.getName();
						familyName = (String) AlertBean.map.get(fieldName);
						if (familyName != null && familyName != "" && field.get(tb) != null) {
							value = new String(String.valueOf(field.get(tb)));
							if (value != null && !value.trim().equals("")) {
								put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(fieldName),
										Bytes.toBytes(value));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
			logger.error(sdf.format(date) + " HbaseTools.putDataColumns, ERROR: ", e);
		}
		return put;

	}

	public List<XNDataBean> getValueByTimeWId(String tableName, long startTime, long stopTime, int itemId)
			throws java.text.ParseException {
		// String tableName, String startTime, String stopTime, Integer itemId,
		// TODO
		List<XNDataBean> transBeanList = new ArrayList<XNDataBean>();
		Class<? extends XNDataBean> beanClass = null;
		Field[] fields = null;
		HTable table = null;
		ResultScanner scanner = null;
		SingleColumnValueFilter scanItemid = null;

		// SimpleDateFormat sdfM = new SimpleDateFormat("yyyyMMddHHmm");
		String startTimeS = sdf.format(new Date(startTime));
		String stopTimeS = sdf.format(new Date(stopTime));

		// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓浠嬶紝閿熸枻鎷烽敓鐭櫢鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷峰師閿熸枻鎷�
		FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
		ExecutorService pool = Executors.newFixedThreadPool(50);
		HbaseConnection hbCon = HbaseConnectionFactory.getHbaseConnection();
		// 涓洪敓鏂ゆ嫹椤甸敓鏂ゆ嫹閿熸枻鎷烽敓渚ュ嚖鎷疯閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋噲閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
		try {
			// 閿熸帴鎲嬫嫹閿熸枻鎷烽敓楗衡槄鎷烽敓绱緽ASE閿熸枻鎷烽敓鏂ゆ嫹閿燂拷
			table = (HTable) hbCon.getConnection().getTable(TableName.valueOf(tableName.trim()), pool);
			// 閿熸枻鎷峰彇閿熸枻鎷疯爴绁蜂簾閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�

			Scan scan = new Scan();

			scan.setCaching(10000);
			// 閿熷彨璁规嫹閿熸枻鎷烽敓缁炵》鎷烽敓杞匡拷"閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷峰叏閿熸枻鎷风墶閿熸彮浼欐嫹閿岄敓缁炵磩can

			scan.setStartRow(Bytes.toBytes(startTimeS));

			scan.setStopRow(Bytes.toBytes(stopTimeS));

			// 閿熸枻鎷烽敓鏂ゆ嫹鍊奸敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熷壙鍑ゆ嫹閿熺禈ppName閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹

			scanItemid = new SingleColumnValueFilter(Bytes.toBytes("basic_info"), Bytes.toBytes("appName"),
					CompareFilter.CompareOp.EQUAL, Bytes.toBytes(itemId));
			scanItemid.setFilterIfMissing(true);
			filterList.addFilter(scanItemid);

			// 閿熸枻鎷烽敓鏂ゆ嫹鍊奸敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熷壙鍑ゆ嫹閿熺但ransType閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹

			scan.setFilter(filterList);
			scanner = table.getScanner(scan);
			// 閿熸枻鎷烽敓鏂ゆ嫹鎵敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熻锛屾枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹鎷ラ敓绲st閿熸枻鎷�
			// GO:
			for (Result result : scanner) {
				XNDataBean tb = new XNDataBean();
				beanClass = (Class<? extends XNDataBean>) tb.getClass();
				fields = beanClass.getFields();
				// 閿熷彨鏂嚖鎷烽敓鎴鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓绐栴垽鎷烽敓鏂ゆ嫹閿熸彮顏庢嫹閿熸枻鎷烽敓鏂ゆ嫹姘愰敓鏂ゆ嫹閿熸嵎纰夋嫹閿熸枻鎷烽敓鎻紮鎷烽敓鏂ゆ嫹鏆敓鏂ゆ嫹閿熸彮浼欐嫹閿熺粸鎲嬫嫹閿熸枻鎷烽敓鏂ゆ嫹scan

				for (Cell cell : result.rawCells()) {
					for (Field field : fields) {
						try {
							if (field.getName().equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
								// 閿熸枻鎷峰彇閿熸枻鎷烽敓鏂ゆ嫹閿熻璁规嫹閿熸枻鎷烽敓鏂ゆ嫹
								Class<?> type = field.getType();

								// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷穕ong閿熸枻鎷烽敓閰甸潻鎷峰�奸敓鏂ゆ嫹Bean
								if (type.equals(long.class)) {
									String longValue = Bytes.toString(CellUtil.cloneValue(cell));
									long l = Long.parseLong(longValue);
									field.set(tb, l);
								}
								if (type.equals(String.class)) {
									String string = Bytes.toString(CellUtil.cloneValue(cell));
									field.set(tb, string);
								}
								// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷穌ate閿熸枻鎷烽敓閰甸潻鎷峰�奸敓鏂ゆ嫹Bean
								if (type.equals(java.util.Date.class)) {
									String dateValue = Bytes.toString(CellUtil.cloneValue(cell));
									SimpleDateFormat sdfT = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy",
											Locale.US);
									Date dateTime = sdfT.parse(dateValue);
									field.set(tb, dateTime);
								}
								// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷稴tring閿熸枻鎷烽敓閰甸潻鎷峰�奸敓鏂ゆ嫹Bean
								if (type.equals(java.lang.String.class)) {
									field.set(tb, Bytes.toString(CellUtil.cloneValue(cell)));
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				transBeanList.add(tb);

			}

			// 閿熸枻鎷烽敓鎴鎷疯閿熸枻鎷烽敓锟�
			return transBeanList;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null)
				scanner.close();
			try {
				if (table != null) {
					// 閿熸埅闂唻鎷�
					table.close();
				}
				// 閿熸埅鎲嬫嫹閿熸枻鎷烽敓鎺ョ鎷�
				pool.shutdown();
				// 閿熼叺鍑ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
				HbaseConnectionFactory.releaseHbaseConnection(hbCon);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 閿熸枻鎷疯閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹涔滈敓锟�
		return null;
	}

	public XNDataBean getValueByTime(String tableName, long startTime, long stopTime, int itemId)
			throws java.text.ParseException {
		// String tableName, String startTime, String stopTime, Integer itemId,
		// TODO
//		List<XNDataBean> transBeanList = new ArrayList<XNDataBean>();
		Class<? extends XNDataBean> beanClass = null;
		Field[] fields = null;
		HTable table = null;
		ResultScanner scanner = null;
		// SingleColumnValueFilter scanItemid = null;
		// SimpleDateFormat sdfM = new SimpleDateFormat("yyyyMMddHHmm");
		// String startTimeS = sdf.format(new Date(startTime));
		// String stopTimeS = sdf.format(new Date(stopTime));
		// FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
		ExecutorService pool = Executors.newFixedThreadPool(50);
		HbaseConnection hbCon = HbaseConnectionFactory.getHbaseConnection();
		try {
			table = (HTable) hbCon.getConnection().getTable(TableName.valueOf(tableName.trim()), pool);
			Scan scan = new Scan();
			scan.setCaching(10000);
			// scan.setStartRow(Bytes.toBytes(startTime));
			// long endTime = stopTime+1;
			// scan.setStopRow(Bytes.toBytes(endTime+1));
			String startRowS = "I" + itemId + "T" + startTime;
			String stopRowS = "I" + itemId + "T" + stopTime;
			scan.setStartRow(Bytes.toBytes(startRowS));
			scan.setStopRow(Bytes.toBytes(stopRowS));
			// scanItemid = new SingleColumnValueFilter(Bytes.toBytes("basic_info"),
			// Bytes.toBytes("appName"),
			// CompareFilter.CompareOp.EQUAL, Bytes.toBytes(itemId));
			// scanItemid.setFilterIfMissing(true);
			// filterList.addFilter(scanItemid);
			// scan.setFilter(filterList);
			scanner = table.getScanner(scan);
			XNDataBean resultXNBean = new XNDataBean();
			resultXNBean.setItemId(itemId);
			float listvaluesum = 0;
			int listnum = 0;
			for (Result result : scanner) { // GO:
				XNDataBean tb = new XNDataBean();
				beanClass = (Class<? extends XNDataBean>) tb.getClass();
				fields = beanClass.getFields();
				for (Cell cell : result.rawCells()) {
					for (Field field : fields) {
						try {
							if (field.getName().equals(Bytes.toString(CellUtil.cloneQualifier(cell)))) {
								Class<?> type = field.getType();
								if (type.equals(long.class)) {
									String longValue = Bytes.toString(CellUtil.cloneValue(cell));
									long l = Long.parseLong(longValue);
									field.set(tb, l);
								}
								if (type.equals(String.class)) {
									String string = Bytes.toString(CellUtil.cloneValue(cell));
									field.set(tb, string);
								}
								if (type.equals(int.class)) {
									String longValue = Bytes.toString(CellUtil.cloneValue(cell));
									int num = Integer.parseInt(longValue);
									field.set(tb, num);
								}
								if (type.equals(java.util.Date.class)) {
									String dateValue = Bytes.toString(CellUtil.cloneValue(cell));
									SimpleDateFormat sdfT = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy",
											Locale.US);
									Date dateTime = sdfT.parse(dateValue);
									field.set(tb, dateTime);
								}
								if (type.equals(java.lang.String.class)) {
									field.set(tb, Bytes.toString(CellUtil.cloneValue(cell)));
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				listvaluesum += Float.parseFloat(tb.getValue());
				listnum++;
				if (listnum == 1) {
					resultXNBean.setValue_max(tb.getValue());
					resultXNBean.setValue_min(tb.getValue());
				}
				if (General.isNum(tb.getValue())) {
					if (Float.parseFloat(tb.getValue()) > Float.parseFloat(resultXNBean.getValue_max())) {
						resultXNBean.setValue_max(tb.getValue());
					}
				}
				if (General.isNum(tb.getValue())) {
					if (Float.parseFloat(tb.getValue()) < Float.parseFloat(resultXNBean.getValue_min())) {
						resultXNBean.setValue_min(tb.getValue());
					}
				}

				// transBeanList.add(tb);
			}
			if (listnum != 0) {
				resultXNBean.setValue_avg(String.valueOf(listvaluesum / listnum));
			}
			// 閿熸枻鎷烽敓鎴鎷疯閿熸枻鎷烽敓锟�
			return resultXNBean;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null)
				scanner.close();
			try {
				if (table != null) {
					// 閿熸埅闂唻鎷�
					table.close();
				}
				// 閿熸埅鎲嬫嫹閿熸枻鎷烽敓鎺ョ鎷�
				pool.shutdown();
				// 閿熼叺鍑ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
				HbaseConnectionFactory.releaseHbaseConnection(hbCon);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 閿熸枻鎷疯閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹涔滈敓锟�
		return null;
	}

	@SuppressWarnings("deprecation")
	public void putsAlertDataBean(List<AlertBean> dataBeans, String tableName, long datePre, long dateNex) {
		if (dataBeans == null) {
			dataBeans = new ArrayList<AlertBean>();
		}
		if (dataBeans != null && tableName != null && !tableName.trim().equals("")) {

			HTable table = null;
			List<Put> puts = null;
			HbaseConnection hbCon = null;
			ExecutorService pool = null;

			try {
				hbCon = HbaseConnectionFactory.getHbaseConnection();
				pool = Executors.newFixedThreadPool(50);
				puts = new ArrayList<Put>();
				// System.out.println(tableName+""+hbCon+""+pool);
				table = (HTable) hbCon.getConnection().getTable(TableName.valueOf(tableName), pool);

				table.setWriteBufferSize(104857600);

				table.setAutoFlush(false);
				if (dataBeans.size() != 0) {
					for (AlertBean dataBean : dataBeans) {
						puts.add(putDataColumns(dataBean, tableName));
					}
				}
				table.put(puts);
				table.flushCommits();
			} catch (Exception e) {
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
				logger.error(sdf.format(date) + " HbaseTools.putsDataBean, ERROR: ", e);
			} finally {
				// 閿熸埅闂唻鎷�
				if (table != null) {
					try {
						table.close();
					} catch (IOException e) {
						Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
						logger.error(sdf.format(date) + " HbaseTools.putsDataBean, ERROR: ", e);
					}
				}

				puts.clear();
				puts = null;
				logger.info(new Date()+""+"Successfully insert alert data;");
				writeDateLog(datePre, dateNex, "alert data", dataBeans.size());
				dataBeans.clear();
				dataBeans = null;
				pool.shutdown();
				HbaseConnectionFactory.releaseHbaseConnection(hbCon);

				// try {
				// hbCon.getConnection().close();
				// System.out.println("hbcon close succ!");
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}
		}

	}

}