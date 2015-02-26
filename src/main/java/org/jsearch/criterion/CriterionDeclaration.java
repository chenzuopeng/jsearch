package org.jsearch.criterion;

import java.lang.reflect.Field;

import org.jsearch.OperateType;

/**
 * 
 * 
 * @author dylan.chen Jul 3, 2009
 * 
 */
public class CriterionDeclaration {

	private Class<?> beanClass;

	private Field field;

	private String columnName;

	private String groupId;

	private OperateType operate;

	private String expression;

	public Class<?> getBeanClass() {
		return this.beanClass;
	}

	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	public Field getField() {
		return this.field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public OperateType getOperate() {
		return this.operate;
	}

	public void setOperate(OperateType operate) {
		this.operate = operate;
	}

	public String getExpression() {
		return this.expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CriterionDeclaration [beanClass=");
		builder.append(this.beanClass);
		builder.append(", columnName=");
		builder.append(this.columnName);
		builder.append(", expression=");
		builder.append(this.expression);
		builder.append(", field=");
		builder.append(this.field);
		builder.append(", groupId=");
		builder.append(this.groupId);
		builder.append(", operate=");
		builder.append(this.operate);
		builder.append("]");
		return builder.toString();
	}

}
