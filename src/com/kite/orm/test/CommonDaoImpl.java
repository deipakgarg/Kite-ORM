package com.kite.orm.test;


import org.springframework.stereotype.Repository;

import com.kite.orm.dao.AbstractKiteDao;


@Repository("CommonDao")
public class CommonDaoImpl extends AbstractKiteDao<Employee> implements CommonDao
{
	public CommonDaoImpl()
	{
		System.out.println("Constructor of CommonDaoImpl");
	}
}
