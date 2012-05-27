package com.jdbc.ladder.test;


import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import com.jdbc.ladder.dao.AbstractJLadderDao;


public class CommonDao extends AbstractJLadderDao<Template>
{
	private DataSource ds;
	
	@Override
	public void setDataSource(DataSource dataSource)
	{
		System.out.println("Autowired Called....");
		System.out.println(((BasicDataSource)dataSource).getDriverClassName() + " : " + ((BasicDataSource)dataSource).getUrl());
		ds = dataSource;
	}
	
	@Override
	public DataSource getDataSource()
	{
		return ds;
	}
}
