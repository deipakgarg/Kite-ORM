package com.kite.orm.test;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.kite.orm.dao.AbstractKiteDao;


public class CommonDao extends AbstractKiteDao<Template> implements ICommonDao
{
	@Autowired
	private DataSource ds;
	
	@Override
	public void setDataSource(DataSource ds)
	{
		simpleJdbcTemplate = new SimpleJdbcTemplate(ds);
	}
	
	@Override
	public DataSource getDataSource()
	{
		return ds;
	}
}
