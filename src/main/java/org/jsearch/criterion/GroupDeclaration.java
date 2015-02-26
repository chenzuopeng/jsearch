package org.jsearch.criterion;

import java.util.ArrayList;
import java.util.List;

import org.jsearch.LogicalOperateType;

/**
 * 
 * 
 * @author dylan.chen Jul 3, 2009
 * 
 */
public class GroupDeclaration {

	private Class<?> beanClass;

	private String groupId;

	private LogicalOperateType operate;

	private ArrayList<CriterionDeclaration> criterions = new ArrayList<CriterionDeclaration>();

	public Class<?> getBeanClass() {
		return this.beanClass;
	}

	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public LogicalOperateType getOperate() {
		return this.operate;
	}

	public void setOperate(LogicalOperateType operate) {
		this.operate = operate;
	}

	public List<CriterionDeclaration> getCriterions() {
		return this.criterions;
	}

	public void add(CriterionDeclaration criterionDeclaration) {
		this.criterions.add(criterionDeclaration);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GroupDeclaration [beanClass=");
		builder.append(this.beanClass);
		builder.append(", criterions=");
		builder.append(this.criterions);
		builder.append(", groupId=");
		builder.append(this.groupId);
		builder.append(", operate=");
		builder.append(this.operate);
		builder.append("]");
		return builder.toString();
	}

}
