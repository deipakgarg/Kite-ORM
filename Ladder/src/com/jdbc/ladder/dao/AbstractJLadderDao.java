package com.jdbc.ladder.dao;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.jdbc.ladder.annotation.Column;
import com.jdbc.ladder.annotation.Keys;
import com.jdbc.ladder.annotation.PrimaryKey;
import com.jdbc.ladder.annotation.Table;
import com.jdbc.ladder.annotation.UniqueKey;
import com.jdbc.ladder.util.ReflectionUtils;


/**
 * Every dao class needs to extends this abstract class.
 * Need to implementation of setDataSource & getDataSource Method.
 * 
 * @see com.jdbc.ladder.dao.JLadderDao
 * @author Deepak Garg
 * @param <T> any dto Class
 */
@Transactional
public abstract class AbstractJLadderDao<T> implements JLadderDao<T>
{
	protected SimpleJdbcTemplate simpleJdbcTemplate;
	
	/**
	 * @param dataSource
	 */
	@Autowired
	public abstract void setDataSource(DataSource dataSource);
	
	/**
	 *  
	 */
	@Autowired
	public abstract DataSource getDataSource();
	
	private void setJdbcTemplate()
	{
		if (simpleJdbcTemplate == null)
		{
			simpleJdbcTemplate = new SimpleJdbcTemplate(getDataSource());
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdbc.ladder.dao.JDao#insert(java.lang.Object)
	 */
	public int insert(Object o) throws DataAccessException
	{
		setJdbcTemplate();
		StringBuilder qry = new StringBuilder();
		StringBuilder strFieldColumn = new StringBuilder();
		StringBuilder strValueColumn = new StringBuilder();
		List<Object> val = new ArrayList<Object>();
		qry.append("insert into ");
		Class<?> c = o.getClass();
		final String strTableName = c.getAnnotation(Table.class).name();
		final PrimaryKey objPk = c.getAnnotation(PrimaryKey.class);
		final String strPrimaryKeyName = objPk.key();
		Field objPkField = null;
		try
		{
			objPkField = c.getDeclaredField(strPrimaryKeyName);
			final String strPkColumnName = objPkField.getAnnotation(Column.class).name();
			qry.append(strTableName);
			Annotation[] annotations;
			Field[] fields = c.getDeclaredFields();
			Column clmn;
			boolean blnFlag = false;
			for (Field f : fields)
			{
				f.setAccessible(true);
				annotations = f.getAnnotations();
				for (Annotation a : annotations)
				{
					if (a instanceof Column)
					{
						clmn = (Column)a;
						if (!clmn.name().equalsIgnoreCase(strPkColumnName) || !objPk.autoIncrement())
						{
							if (blnFlag)
							{
								strFieldColumn.append(",");
								strValueColumn.append(",");
							}
							blnFlag = true;
							strFieldColumn.append(clmn.name());
							strValueColumn.append("?");
							val.add(f.get(o));
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		qry.append("(" + strFieldColumn.toString() + ") values (" + strValueColumn.toString() + ")");
		
		System.out.println(qry.toString());
		for (Object o1 : val)
		{
			System.out.println(o1);
		}
		System.out.println(simpleJdbcTemplate.toString());
		return simpleJdbcTemplate.update(qry.toString(), val.toArray());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdbc.ladder.dao.JLadderDao#update(java.lang.Object)
	 */
	public int update(Object o) throws DataAccessException
	{
		setJdbcTemplate();
		StringBuilder qry = new StringBuilder();
		StringBuilder strFieldColumn = new StringBuilder();
		StringBuilder strWherePkClause = new StringBuilder();
		StringBuilder strWhereUkClause = new StringBuilder();
		List<Object> val = new ArrayList<Object>();
		List<Object> valUk = new ArrayList<Object>();
		qry.append("update ");
		Class<?> c = o.getClass();
		final String strTableName = c.getAnnotation(Table.class).name();
		qry.append(strTableName);
		final PrimaryKey objPk = c.getAnnotation(PrimaryKey.class);
		final String strPrimaryKeyName = objPk.key();
		Field objPkField = null;
		try
		{
			objPkField = c.getDeclaredField(strPrimaryKeyName);
			final String strPkColumnName = objPkField.getAnnotation(Column.class).name();
			Annotation[] annotations;
			Field[] fields = c.getDeclaredFields();
			Column clmn;
			Object pk = null;
			boolean blnFlag = false;
			boolean blnPkFlag = false;
			boolean blnUkFlag = false;
			for (Field f : fields)
			{
				f.setAccessible(true);
				annotations = f.getAnnotations();
				for (Annotation a : annotations)
				{
					if (a instanceof Column)
					{
						clmn = (Column)a;
						if (blnFlag)
						{
							strFieldColumn.append(",");
						}
						else
						{
							strFieldColumn.append(" set ");
						}
						blnFlag = true;
						if (clmn.name().equalsIgnoreCase(strPkColumnName))
						{
							strWherePkClause.append(clmn.name() + "=?");
							pk = f.get(o);
							blnPkFlag = true;
						}
						strFieldColumn.append(clmn.name() + "=?");
						val.add(f.get(o));
					}
				}
			}
			if (blnPkFlag)
			{
				qry.append(strFieldColumn.toString() + " Where " + strWherePkClause.toString());
				val.add(pk);
			}
			else
			{
				Keys keys = c.getAnnotation(Keys.class);
				UniqueKey[] uniqueKeys = keys.uniqueKey();
				String[] strFieldsName;
				Object objFieldValue;
				for (UniqueKey uniqueKey : uniqueKeys)
				{
					strFieldsName = uniqueKey.keys();
					blnUkFlag = false;
					strWhereUkClause.setLength(0);
					valUk = new ArrayList<Object>();
					int j = 0;
					for (; j < strFieldsName.length; j++)
					{
						objFieldValue = ReflectionUtils.getFieldValue(o, strFieldsName[j]);
						if (null != objFieldValue)
						{
							if (blnUkFlag)
							{
								strWhereUkClause.append(" and ");
							}
							blnUkFlag = true;
							strWhereUkClause.append(uniqueKey.keys()[j] + "=?");
							valUk.add(objFieldValue);
						}
					}
					if (j == strFieldsName.length)
					{
						break;
					}
				}
				qry.append(strFieldColumn.toString() + " Where " + strWhereUkClause.toString());
				val.addAll(valUk);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println(qry.toString());
		return simpleJdbcTemplate.update(qry.toString(), val.toArray());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdbc.ladder.dao.JLadderDao#remove(java.lang.Object)
	 */
	public int remove(Object o) throws DataAccessException
	{
		setJdbcTemplate();
		StringBuilder qry = new StringBuilder();
		StringBuilder strWherePkClause = new StringBuilder();
		StringBuilder strWhereUkClause = new StringBuilder();
		List<Object> val = new ArrayList<Object>();
		List<Object> valUk = new ArrayList<Object>();
		qry.append("delete from ");
		Class<?> c = o.getClass();
		qry.append(c.getAnnotation(Table.class).name());
		final PrimaryKey objPk = c.getAnnotation(PrimaryKey.class);
		final String strPrimaryKeyName = objPk.key();
		Field objPkField = null;
		boolean blnUkFlag = false;
		Object pk = null;
		try
		{
			objPkField = c.getDeclaredField(strPrimaryKeyName);
			final String strPkColumnName = objPkField.getAnnotation(Column.class).name();
			if (null != strPkColumnName && !strPkColumnName.equals(""))
			{
				strWherePkClause.append(strPkColumnName + "=?");
				pk = ReflectionUtils.getFieldValue(o, strPrimaryKeyName);
				qry.append(" Where " + strWherePkClause.toString());
				val.add(pk);
			}
			else
			{
				Keys keys = c.getAnnotation(Keys.class);
				UniqueKey[] uniqueKeys = keys.uniqueKey();
				String[] strFieldsName;
				Object objFieldValue;
				for (UniqueKey uniqueKey : uniqueKeys)
				{
					strFieldsName = uniqueKey.keys();
					blnUkFlag = false;
					strWhereUkClause.setLength(0);
					valUk = new ArrayList<Object>();
					int j = 0;
					for (; j < strFieldsName.length; j++)
					{
						objFieldValue = ReflectionUtils.getFieldValue(o, strFieldsName[j]);
						if (null != objFieldValue)
						{
							if (blnUkFlag)
							{
								strWhereUkClause.append(" and ");
							}
							blnUkFlag = true;
							strWhereUkClause.append(uniqueKey.keys()[j] + "=?");
							valUk.add(objFieldValue);
						}
					}
					if (j == strFieldsName.length)
					{
						break;
					}
				}
				qry.append(" Where " + strWhereUkClause.toString());
				val.addAll(valUk);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println(qry.toString());
		return simpleJdbcTemplate.update(qry.toString(), val.toArray());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdbc.ladder.dao.JLadderDao#read(java.lang.Object)
	 */
	@SuppressWarnings({"unchecked"})
	public T read(Object o) throws DataAccessException
	{
		setJdbcTemplate();
		T object = null;
		try
		{
			StringBuilder qry = new StringBuilder();
			StringBuilder strFieldColumn = new StringBuilder();
			StringBuilder strWherePkClause = new StringBuilder();
			StringBuilder strWhereUkClause = new StringBuilder();
			List<Object> val = new ArrayList<Object>();
			List<Object> valUk = new ArrayList<Object>();
			List<Object> colName = new ArrayList<Object>();
			List<Object> colType = new ArrayList<Object>();
			Class<?> c = o.getClass();
			final PrimaryKey objPk = c.getAnnotation(PrimaryKey.class);
			final String strPrimaryKeyName = objPk.key();
			Field objPkField = null;
			boolean blnFlag = false;
			boolean blnPkFlag = false;
			boolean blnUkFlag = false;
			Object pk = null;
			objPkField = c.getDeclaredField(strPrimaryKeyName);
			final String strPkColumnName = objPkField.getAnnotation(Column.class).name();
			Annotation[] annotations = c.getAnnotations();
			Field[] fields = c.getDeclaredFields();
			Column clmn;
			for (Field f : fields)
			{
				f.setAccessible(true);
				annotations = f.getAnnotations();
				for (Annotation a : annotations)
				{
					if (a instanceof Column)
					{
						clmn = (Column)a;
						if (blnFlag)
						{
							strFieldColumn.append(",");
						}
						blnFlag = true;
						strFieldColumn.append(clmn.name());
						colName.add(f.getName());
						colType.add(f.getType());
						System.out.println("Name: " + f.getName() + " Type: " + f.getType());
						if (clmn.name().equalsIgnoreCase(strPkColumnName))
						{
							strWherePkClause.append(clmn.name() + "=?");
							pk = f.get(o);
							blnPkFlag = true;
						}
					}
				}
			}
			if (blnPkFlag)
			{
				qry.append("select " + strFieldColumn.toString() + " from " + c.getAnnotation(Table.class).name() + " where " + strWherePkClause.toString());
				val.add(pk);
			}
			else
			{
				Keys keys = c.getAnnotation(Keys.class);
				UniqueKey[] uniqueKeys = keys.uniqueKey();
				String[] strFieldsName;
				Object objFieldValue;
				for (UniqueKey uniqueKey : uniqueKeys)
				{
					strFieldsName = uniqueKey.keys();
					blnUkFlag = false;
					strWhereUkClause.setLength(0);
					valUk = new ArrayList<Object>();
					int j = 0;
					for (; j < strFieldsName.length; j++)
					{
						objFieldValue = ReflectionUtils.getFieldValue(o, strFieldsName[j]);
						if (null != objFieldValue)
						{
							if (blnUkFlag)
							{
								strWhereUkClause.append(" and ");
							}
							blnUkFlag = true;
							strWhereUkClause.append(uniqueKey.keys()[j] + "=?");
							valUk.add(objFieldValue);
						}
					}
					if (j == strFieldsName.length)
					{
						break;
					}
				}
				qry.append("select " + strFieldColumn.toString() + " from " + c.getAnnotation(Table.class).name() + " where " + strWhereUkClause.toString());
				val.addAll(valUk);
			}
			System.out.println(qry.toString() + " : " + pk);
			final List<Map<String, Object>> objList = simpleJdbcTemplate.queryForList(qry.toString(), val.toArray());
			if (null != objList && !objList.isEmpty())
			{
				Map<String, Object> objTemp = objList.get(0);
				Map<String, Object> objMap = new HashMap<String, Object>();
				int index = 0;
				for (Object ok : objTemp.keySet())
				{
					if (null != objTemp.get(ok))
					{
						objMap.put(colName.get(index) + "", objTemp.get(ok));
					}
					index++;
				}
				
				object = (T)c.newInstance();
				BeanUtils.copyProperties(object, objMap);
				System.out.println(object);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return object;
	}
}