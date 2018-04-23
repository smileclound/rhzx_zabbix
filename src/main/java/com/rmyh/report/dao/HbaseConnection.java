package com.rmyh.report.dao;


import java.io.Serializable;

import org.apache.hadoop.hbase.client.Connection;

public class HbaseConnection implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean used;
	private Connection connection;
	private String connectionId;
	
	public HbaseConnection() {
		super();
		connectionId = "";
		connection = null;
	}

	
	public Connection getConnection() {
		return connection;
	}

	
	public String getConnectionId() {
		return connectionId;
	}

	public void setConnection(Connection newConn) {
		connection = newConn;
	}

	
	public void setConnectionId(String newConnectionId) {
		connectionId = newConnectionId;
	}


	public boolean isUsed() {
		return used;
	}


	public void setUsed(boolean used) {
		this.used = used;
	}

	
}

