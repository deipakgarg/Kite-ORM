package com.kite.orm.dao;


import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;


/**
 * The super class of Data Access Objects generated through the kite framework. 
 * This class offers utility routines for quickly invoking queries and manipulating persistence objects.
 * @author Deepak Garg
 *
 * @param <T>
 */
public interface KiteDao<T>
{
	/**
	 * @param DataSource ds
	 */
	public void setDataSource(DataSource ds);
	
	/**
	 * @return DataSource
	 */
	public DataSource getDataSource();

	/**
	 * Persist (Create New Object) the specified object through the kite framework.
	 * 
	 * @param <T> the type for the any Entity to be persisted
	 * @param object of the Entity to be persisted
	 * @return the persisted Entity
	 * @throws DataAccessException
	 */
	public int insert(T object) throws DataAccessException;
	
	/**
	 * Persist (Modify Object) the specified object through the kite framework.
	 * 
	 * @param <T> the type for the any Entity to be persisted
	 * @param object of the Entity to be persisted
	 * @return the persisted Entity
	 * @throws DataAccessException
	 */
	public int update(T object) throws DataAccessException;
	
	/**
	 * Remove the specified Entity through the kite framework
	 * 
	 * @param <T> the type for the Entity to be removed
	 * @param object of the Entity to be removed
	 * @return success flag
	 * @throws DataAccessException
	 */
	public int remove(T object) throws DataAccessException;
	
	/**
	 * Retrieved Single Object based on Primary Key or combination of any Unique key.
	 * 
	 * @param <T> the type for the Entity to be returned
	 * @param object of the Entity to be retrieved
	 * @return the persisted Entity
	 * @throws DataAccessException
	 */
	public T read(T object) throws DataAccessException;
	
	/**
	 * Retrieved list of objects based on criteria passed in sql form.
	 * 
	 * @param object
	 * @param strQry Example: Employee_Id = ? And Employee_Name like ?
	 * @param paramenters Example: 5, Deepak
	 * @return list of persisted Entities
	 * @throws DataAccessException
	 */
	public List<T> read(T object, String strQry, Object... paramenters) throws DataAccessException;
}
