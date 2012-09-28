package com.kite.orm.test;


import java.util.Date;

import com.kite.orm.annotation.Column;
import com.kite.orm.annotation.ForeignKey;
import com.kite.orm.annotation.Keys;
import com.kite.orm.annotation.PrimaryKey;
import com.kite.orm.annotation.Table;
import com.kite.orm.annotation.UniqueKey;


@Table(name = "WEBSITE_TEMPLATE")
@PrimaryKey(key = "templateId", autoIncrement = true)
@Keys(
		uniqueKey = {
				@UniqueKey(keys = {"name"}),
				@UniqueKey(keys = {"category", "title"})},
		foreignKey = {
				@ForeignKey(key = "PROJECT_ID", referenceEntity = "PROJECT", referenceColumn = "PROJECT_ID"),
				@ForeignKey(key = "CREATED_BY", referenceEntity = "USER", referenceColumn = "USER_ID"),
				@ForeignKey(key = "UPDATED_BY", referenceEntity = "USER", referenceColumn = "USER_ID")})
public class Template
{
	@Column(name = "TEMPLATE_ID")
	private Integer templateId;
	
	@Column(name = "PROJECT_ID")
	private Integer projectId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "CATEGORY")
	private String category;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate = new Date();
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate = new Date();
	
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
		return createdDate;
	}
	
	/**
	 * @param createdDate
	 *           the createdDate to set
	 */
	public final void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
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
		return updatedDate;
	}
	
	/**
	 * @param updatedDate
	 *           the updatedDate to set
	 */
	public final void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
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
		return ret.toString();
	}
}
