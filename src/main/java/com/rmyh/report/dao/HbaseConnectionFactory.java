package com.rmyh.report.dao;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.log4j.Logger;



public class HbaseConnectionFactory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(HbaseConnectionFactory.class);
	
	private static Configuration _Configuration = HBaseConfiguration.create();
	private static int connectionMaxNum = 5;
	private static int connectionNum = 2;
	private static Vector<HbaseConnection> connections = new Vector<HbaseConnection>();
	private static Vector<HbaseConnection> usedConnections = new Vector<HbaseConnection>();
	
	public static int getConnectionMaxNum() {
		return connectionMaxNum;
	}

	public static void setConnectionMaxNum(int connectionMaxNum) {
		HbaseConnectionFactory.connectionMaxNum = connectionMaxNum;
	}

	public static int getCurrentConnectionNum() {
		return usedConnections.size();
	}
	
	public static int getConnectionNum() {
		return connectionNum;
	}

	public static void setConnectionNum(int connectionNum) {
		HbaseConnectionFactory.connectionNum = connectionNum;
	}

	public static void init(String url, String port) {
		
		_Configuration.set("hbase.zookeeper.property.clientPort", port);
		_Configuration.set("hbase.zookeeper.quorum", url);
		
		
		
		try {
			
			for (int i = 0; i < connectionNum; i++){
				HbaseConnection con = new HbaseConnection();
				con.setConnection(ConnectionFactory.createConnection(_Configuration));
				con.setConnectionId(new Integer(i).toString().trim());
				connections.add(con);
			}
			
		} catch (Exception e) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
			logger.error(sdf.format(date) + " HbaseConnectionFactory.init, ERROR: ", e);
		}
		
		logger.info(" HbaseConnectionFactory.initHbasePool, SUCC! ");
		
	}
	
	public synchronized static final HbaseConnection getHbaseConnection() {
			
			HbaseConnection con = null;
			if (connections.size() > 0) {
				//ÓÐ¿ÉÓÃµÄconnection
				con = (HbaseConnection) connections.firstElement();
				connections.removeElementAt(0);
				if (con.getConnection() == null || con.getConnection().isClosed()) {
					con = getHbaseConnection();
					return con;
				} else {
					usedConnections.add(con);
					//System.out.println("connection used : " + usedConnections.size());
					return con;
				}
			} else {
				//ÎÞ¿ÉÓÃµÄconnection
				int currentConnNum = connections.size() + usedConnections.size();
				
				try {
									
					if (currentConnNum < connectionMaxNum) {
						con = new HbaseConnection();
						con.setConnection(ConnectionFactory.createConnection(_Configuration));
						con.setConnectionId(new Integer(currentConnNum).toString().trim());
						usedConnections.add(con);
						return con;
					} else {
						Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
						logger.error(sdf.format(date) + " HbaseConnectionFactory.getHbaseConnection, ERROR: Hbase connection pool is full! Currect connection: " + currentConnNum + "Max connection: " + HbaseConnectionFactory.connectionMaxNum);  
					}
				} catch (Exception e) {
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
					logger.error(sdf.format(date) + " HbaseConnectionFactory.getHbaseConnection, ERROR: " , e);  
				} 
			}
			
			return null;
			
		} 
     
		public synchronized static final void releaseHbaseConnection(HbaseConnection con) {
			if ((con !=null) && (!connections.contains(con))){
				usedConnections.remove(con);
				connections.add(con);
			}
		} 
		
		public static final  void closeConnections(String jobName) {
			Enumeration<HbaseConnection> conns=connections.elements();
			HbaseConnection hbCon = null;
			Connection con = null;
			try{
				while (conns.hasMoreElements()) {
					hbCon = (HbaseConnection)conns.nextElement();
					if (hbCon != null) {
						con = hbCon.getConnection();
					}
					if (con != null && (!con.isClosed())) {
						con.close();
					}
				}
			}catch(Exception e){
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
				logger.error(sdf.format(date) + " HbaseConnectionFactory.closeConnections, ERROR: ", e);  
			}
			
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
			logger.info(sdf.format(date) + " JOB:" + jobName + " HbaseConnectionFactory.closeHbasePool, SUCC!");
			
		}     

}
