package com.kite.orm.test;


import java.util.Date;

import com.kite.orm.annotation.Column;
import com.kite.orm.annotation.Keys;
import com.kite.orm.annotation.PrimaryKey;
import com.kite.orm.annotation.Table;
import com.kite.orm.annotation.UniqueKey;


@Table(name = "ORM_EXAMPLE")
@PrimaryKey(key = "templateId", autoIncrement = true)
@Keys(
		uniqueKey = {
				@UniqueKey(keys = {"name"}),
				@UniqueKey(keys = {"projectId", "momentumId"})})
public class KiteOrmExample
{
	@Column(name = "TEMPLATE_ID")
	private Integer templateId;
	
	@Column(name = "PROJECT_ID", nullable = false)
	private Integer projectId;
	
	@Column(name = "PRJ_MOMENTUM_ID", nullable = true)
	private Integer momentumId;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "CATEGORY")
	private String category;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	private String fileContent;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate = new Date();
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate = new Date();
	
	@Column(name = "PAGE_COUNT")
	private Integer pageCount = Integer.valueOf(2);
	
	
	public final Integer getPageCount()
	{
		return pageCount;
	}
	
	public final void setPageCount(Integer pageCount)
	{
		this.pageCount = pageCount;
	}
	
	/**
	 * @return the templateId
	 */
	public final Integer getTemplateId()
	{
		return templateId;
	}
	
	/**
	 * @param templateId
	 *           the templateId to set
	 */
	public final void setTemplateId(Integer templateId)
	{
		this.templateId = templateId;
	}
	
	/**
	 * @return the projectId
	 */
	public final Integer getProjectId()
	{
		return projectId;
	}
	
	/**
	 * @param projectId
	 *           the projectId to set
	 */
	public final void setProjectId(Integer projectId)
	{
		this.projectId = projectId;
	}
	
	/**
	 * @return the momentumId
	 */
	public final Integer getMomentumId()
	{
		return momentumId;
	}
	
	/**
	 * @param momentumId the momentumId to set
	 */
	public final void setMomentumId(Integer momentumId)
	{
		this.momentumId = momentumId;
	}
	
	/**
	 * @return the name
	 */
	public final String getName()
	{
		return name;
	}
	
	/**
	 * @param name
	 *           the name to set
	 */
	public final void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * @return the type
	 */
	public final String getType()
	{
		return type;
	}
	
	/**
	 * @param type
	 *           the type to set
	 */
	public final void setType(String type)
	{
		this.type = type;
	}
	
	/**
	 * @return the category
	 */
	public final String getCategory()
	{
		return category;
	}
	
	/**
	 * @param category
	 *           the category to set
	 */
	public final void setCategory(String category)
	{
		this.category = category;
	}
	
	/**
	 * @return the title
	 */
	public final String getTitle()
	{
		return title;
	}
	
	/**
	 * @param title
	 *           the title to set
	 */
	public final void setTitle(String title)
	{
		this.title = title;
	}
	
	/**
	 * @return the description
	 */
	public final String getDescription()
	{
		return description;
	}
	
	/**
	 * @param description
	 *           the description to set
	 */
	public final void setDescription(String description)
	{
		this.description = description;
	}
	
	/**
	 * @return the fileContent
	 */
	public final String getFileContent()
	{
		return fileContent;
	}
	
	/**
	 * @param fileContent
	 *           the fileContent to set
	 */
	public final void setFileContent(String fileContent)
	{
		this.fileContent = fileContent;
	}
	
	/**
	 * @return the createdBy
	 */
	public final String getCreatedBy()
	{
		return createdBy;
	}
	
	/**
	 * @param createdBy
	 *           the createdBy to set
	 */
	public final void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	
	/**
	 * @return the createdDate
	 */
	public final Date getCreatedDate()
	{
		return (Date)createdDate.clone();
	}
	
	/**
	 * @param createdDate
	 *           the createdDate to set
	 */
	public final void setCreatedDate(Date createdDate)
	{
		this.createdDate = (Date)createdDate.clone();
	}
	
	/**
	 * @return the updatedBy
	 */
	public final String getUpdatedBy()
	{
		return updatedBy;
	}
	
	/**
	 * @param updatedBy
	 *           the updatedBy to set
	 */
	public final void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}
	
	/**
	 * @return the updatedDate
	 */
	public final Date getUpdatedDate()
	{
		return (Date)updatedDate.clone();
	}
	
	/**
	 * @param updatedDate the updatedDate to set
	 */
	public final void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = (Date)updatedDate.clone();
	}
	
	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder();
		ret.append("templateId=" + templateId);
		ret.append(", projectId=" + projectId);
		ret.append(", name=" + name);
		ret.append(", type=" + type);
		ret.append(", category=" + category);
		ret.append(", title=" + title);
		ret.append(", description=" + description);
		ret.append(", createdBy=" + createdBy);
		ret.append(", createdDate=" + createdDate);
		ret.append(", pageCount=" + pageCount);
		return ret.toString();
	}
}
