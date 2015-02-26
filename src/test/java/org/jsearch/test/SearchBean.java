package org.jsearch.test;

import org.jsearch.LogicalOperateType;
import org.jsearch.OperateType;
import org.jsearch.annotation.Criterion;
import org.jsearch.annotation.Group;

@Group(groupId = { "0", "1" }, operate = { LogicalOperateType.OR, LogicalOperateType.OR })
public class SearchBean {

	@Criterion(groupId = "0", columnName = "p.teat", operate = OperateType.LIKE, expression = "${P}%")
	private String field1;

	@Criterion(groupId = "0", operate = OperateType.IN)
	private String[] field2 = { "0", "1", "2" };

	@Criterion(columnName = "p.cloumn", groupId = "1", operate = OperateType.LIKE, expression = "${P}%")
	private String field3;

	@Criterion(groupId = "1", operate = OperateType.BETWEEN)
	private Integer[] field5 = { 1, 2 };

	@Criterion(groupId = "1", operate = OperateType.LT)
	private Integer field6 = 111;

	public String getField1() {
		return this.field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String[] getField2() {
		return this.field2;
	}

	public void setField2(String[] field2) {
		this.field2 = field2;
	}

	public String getField3() {
		return this.field3;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}

	public Integer[] getField5() {
		return this.field5;
	}

	public void setField5(Integer[] field5) {
		this.field5 = field5;
	}

	public Integer getField6() {
		return this.field6;
	}

	public void setField6(Integer field6) {
		this.field6 = field6;
	}

}