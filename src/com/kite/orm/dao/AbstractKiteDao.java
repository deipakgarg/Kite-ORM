package com.kite.orm.dao;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.kite.orm.annotation.Column;
import com.kite.orm.annotation.Keys;
import com.kite.orm.annotation.PrimaryKey;
import com.kite.orm.annotation.Table;
import com.kite.orm.annotation.UniqueKey;
import com.kite.orm.util.ReflectionUtils;


/**
 * Every dao class needs to extends this abstract class.
 * Need to implementation of setDataSource & getDataSource Method.
 * 
 * @see com.kite.orm.dao.KiteDao
 * @author Deepak Garg
 * @param <T> any dto Class
 */
@Transactional
public abstract class AbstractKiteDao<T> implements KiteDao<T>
{
	protected SimpleJdbcTemplate simpleJdbcTemplate;
	private Logger log = Logger.getLogger(AbstractKiteDao.class);
	
	@Autowired
	public void setDataSource(DataSource dataSource)
	{
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}
	
	/**
	 * @param c
	 * @return
	 */
	private PrimaryKey getPrimaryKey(Class<?> c)
	{
		return c.getAnnotation(PrimaryKey.class);
	}
	
	private String getColumnName(final String strKey, Object o)
	{
		String strColumnName = "";
		Class<?> c = o.getClass();
		Field[] fields = c.getDeclaredFields();
		Annotation[] annotations;
		Column clmn;
		for (Field f : fields)
		{
			f.setAccessible(true);
			annotations = f.getAnnotations();
			if (f.getName().equalsIgnoreCase(strKey))
			{
				for (Annotation a : annotations)
				{
					if (a instanceof Column)
					{
						clmn = (Column)a;
						strColumnName = clmn.name();
						break;
					}
				}
			}
		}
		return strColumnName;
	}
	
	/**
	 * @param qry
	 * @param val
	 * @param o
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	private void setUniqueKeyData(StringBuilder qry, List<Object> val, Object o) throws SecurityException, NoSuchFieldException
	{
		boolean blnUkFlag = false;
		Class<?> c = o.getClass();
		Keys keys = c.getAnnotation(Keys.class);
		UniqueKey[] uniqueKeys = keys.uniqueKey();
		StringBuilder strWhereUkClause = new StringBuilder();
		List<Object> valUk = new ArrayList<Object>();
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
				log.debug("Key Name: " + strFieldsName[j]);
				objFieldValue = ReflectionUtils.getFieldValue(o, strFieldsName[j]);
				log.debug("KeyValue: " + objFieldValue);
				if (null != objFieldValue)
				{
					if (blnUkFlag)
					{
						strWhereUkClause.append(" and ");
					}
					blnUkFlag = true;
					strWhereUkClause.append(getColumnName(strFieldsName[j], o) + "=?");
					valUk.add(objFieldValue);
				}
				else
				{
					if (((Column)c.getDeclaredField(strFieldsName[j]).getAnnotation(Column.class)).nullable())
					{
						if (blnUkFlag)
						{
							strWhereUkClause.append(" and ");
						}
						blnUkFlag = true;
						strWhereUkClause.append(getColumnName(strFieldsName[j], o) + " is null");
					}
					else
					{
						log.debug("Nullable false");
					}
				}
			}
			if (j == strFieldsName.length && blnUkFlag)
			{
				break;
			}
		}
		qry.append(" Where " + strWhereUkClause.toString());
		val.addAll(valUk);
		
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kite.orm.dao.KiteDao#insert(java.lang.Object)
	 */
	@Transactional
	public int insert(Object o) throws DataAccessException
	{
		StringBuilder qry = new StringBuilder();
		StringBuilder strFieldColumn = new StringBuilder();
		StringBuilder strValueColumn = new StringBuilder();
		List<Object> val = new ArrayList<Object>();
		qry.append("insert into ");
		Class<?> c = o.getClass();
		final String strTableName = c.getAnnotation(Table.class).name();
		final PrimaryKey objPk = getPrimaryKey(c);
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
		
		log.debug(qry.toString());
		for (Object o1 : val)
		{
			log.debug(o1);
		}
		log.debug(simpleJdbcTemplate.toString());
		simpleJdbcTemplate.update(qry.toString(), val.toArray());
		return simpleJdbcTemplate.queryForInt("select last_insert_id()");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kite.orm.dao.KiteDao#update(java.lang.Object)
	 */
	@Transactional
	public int update(Object o) throws DataAccessException
	{
		StringBuilder qry = new StringBuilder();
		StringBuilder strFieldColumn = new StringBuilder();
		StringBuilder strWherePkClause = new StringBuilder();
		List<Object> val = new ArrayList<Object>();
		qry.append("update ");
		Class<?> c = o.getClass();
		final String strTableName = c.getAnnotation(Table.class).name();
		qry.append(strTableName);
		final PrimaryKey objPk = getPrimaryKey(c);
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
				qry.append(strFieldColumn.toString());
				this.setUniqueKeyData(qry, val, o);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		log.debug(qry.toString());
		return simpleJdbcTemplate.update(qry.toString(), val.toArray());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kite.orm.dao.KiteDao#remove(java.lang.Object)
	 */
	@Transactional
	public int remove(Object o) throws DataAccessException
	{
		StringBuilder qry = new StringBuilder();
		StringBuilder strWherePkClause = new StringBuilder();
		List<Object> val = new ArrayList<Object>();
		qry.append("delete from ");
		Class<?> c = o.getClass();
		qry.append(c.getAnnotation(Table.class).name());
		final PrimaryKey objPk = getPrimaryKey(c);
		final String strPrimaryKeyName = objPk.key();
		Field objPkField = null;
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
				this.setUniqueKeyData(qry, val, o);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		log.debug(qry.toString());
		return simpleJdbcTemplate.update(qry.toString(), val.toArray());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kite.orm.dao.KiteDao#read(java.lang.Object)
	 */
	@SuppressWarnings({"unchecked"})
	@Transactional(readOnly = true)
	public T read(Object o) throws DataAccessException
	{
		T object = null;
		try
		{
			StringBuilder qry = new StringBuilder();
			StringBuilder strFieldColumn = new StringBuilder();
			StringBuilder strWherePkClause = new StringBuilder();
			List<Object> val = new ArrayList<Object>();
			List<Object> colName = new ArrayList<Object>();
			List<Object> colType = new ArrayList<Object>();
			Class<?> c = o.getClass();
			final PrimaryKey objPk = getPrimaryKey(c);
			final String strPrimaryKeyName = objPk.key();
			Field objPkField = null;
			boolean blnFlag = false;
			boolean blnPkFlag = false;
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
						log.debug("Name: " + f.getName() + " Type: " + f.getType());
						if (clmn.name().equalsIgnoreCase(strPkColumnName))
						{
							pk = f.get(o);
							if (pk != null)
							{
								strWherePkClause.append(clmn.name() + "=?");
								blnPkFlag = true;
							}
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
				qry.append("select " + strFieldColumn.toString() + " from " + c.getAnnotation(Table.class).name());
				this.setUniqueKeyData(qry, val, o);
			}
			log.debug(qry.toString() + " : " + val);
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
				log.debug(object);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return object;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kite.orm.dao.KiteDao#read(java.lang.Object, java.lang.String, java.lang.Object[])
	 */
	@Transactional(readOnly = true)
	public List<T> read(Class<T> c, String strQry, Object... parameters) throws DataAccessException
	{
		List<T> list = null;
		try
		{
			StringBuilder qry = new StringBuilder();
			StringBuilder strFieldColumn = new StringBuilder();
			List<Object> colName = new ArrayList<Object>();
			List<Object> colType = new ArrayList<Object>();
			boolean blnFlag = false;
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
					}
				}
			}
			final List<Map<String, Object>> objList;
			if (null != strQry && !strQry.equals(""))
			{
				qry.append("select " + strFieldColumn.toString() + " from " + c.getAnnotation(Table.class).name() + " where " + strQry);
				objList = simpleJdbcTemplate.queryForList(qry.toString(), parameters);
			}
			else
			{
				qry.append("select " + strFieldColumn.toString() + " from " + c.getAnnotation(Table.class).name());
				objList = simpleJdbcTemplate.queryForList(qry.toString());
			}
			if (null != objList && !objList.isEmpty())
			{
				list = new ArrayList<T>();
				Map<String, Object> objTemp;
				Map<String, Object> objMap;
				int index = 0;
				for (Map<String, Object> map : objList)
				{
					objTemp = map;
					objMap = new HashMap<String, Object>();
					index = 0;
					for (Object ok : objTemp.keySet())
					{
						if (null != objTemp.get(ok))
						{
							objMap.put(colName.get(index) + "", objTemp.get(ok));
						}
						index++;
					}
					
					T object = (T)c.newInstance();
					BeanUtils.copyProperties(object, objMap);
					log.debug(object);
					list.add(object);
					
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
}
