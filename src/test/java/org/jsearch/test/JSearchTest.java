package org.jsearch.test;

import java.util.HashMap;
import java.util.Map;

import org.jsearch.JSearch;
import org.jsearch.LogicalOperateType;
import org.jsearch.OperateType;
import org.jsearch.annotation.Criterion;
import org.jsearch.annotation.Group;
import org.junit.Test;

/**
 *
 *
 * @Copyright: Copyright (c) 2008 FFCS All Rights Reserved 
 * @Company: 北京福富软件有限公司 
 * @author 陈作朋 
 * @version 1.00.00, 2010-9-23 下午02:31:36
 * @history:
 * 
 */
public class JSearchTest {
	
	@Test
	public void test1(){
		long start=System.currentTimeMillis();
		JSearch jsearch=JSearch.getInstance("where ${0} and ${1}", new SearchBean());
		System.out.println(jsearch.getExpression());
		System.out.println(jsearch.getParamValues());
		System.out.println("---------------------------time:"+(System.currentTimeMillis()-start));
	}
	
	@Test
	public void test2(){
		long start=System.currentTimeMillis();
		SearchBean bean=new SearchBean();
		bean.setField2(new String[]{"a","b"});
		JSearch jsearch=JSearch.getInstance("where ${1} and ${1}",bean);
		System.out.println(jsearch.getExpression());
		System.out.println(jsearch.getParamValues());
		System.out.println("---------------------------time:"+(System.currentTimeMillis()-start));
	}
	
	@Test
	public void test22(){
		long start=System.currentTimeMillis();
		SearchBean bean=new SearchBean();
		bean.setField2(new String[]{"a"});
		JSearch jsearch=JSearch.getInstance("where ${1} and ${1}",bean);
		System.out.println(jsearch.getExpression());
		System.out.println(jsearch.getParamValues());
		System.out.println("---------------------------time:"+(System.currentTimeMillis()-start));
	}
	
	@Test
	public void test3(){
		long start=System.currentTimeMillis();
		SearchBean bean=new SearchBean();
		bean.setField1("FASFSFAFSF");
		JSearch jsearch=JSearch.getInstance("where ${1} and ${1}",bean);
		System.out.println(jsearch.getExpression());
		System.out.println(jsearch.getParamValues());
		System.out.println("---------------------------time:"+(System.currentTimeMillis()-start));
	}
	
	@Test
	public void test4(){
		long start=System.currentTimeMillis();
		SearchBean bean=new SearchBean();
		bean.setField5(new Integer[]{1,5});
		JSearch jsearch=JSearch.getInstance("where ${1} and ${1}",bean);
		System.out.println(jsearch.getExpression());
		System.out.println(jsearch.getParamValues());
		System.out.println("---------------------------time:"+(System.currentTimeMillis()-start));
	}
	
	@Test
	public void test5(){
		long start=System.currentTimeMillis();
		SearchBean bean=new SearchBean();
		bean.setField5(new Integer[]{1,2});
		JSearch jsearch=JSearch.getInstance("where ${1} and ${1}",bean);
		System.out.println(jsearch.getExpression());
		System.out.println(jsearch.getParamValues());
		System.out.println("---------------------------time:"+(System.currentTimeMillis()-start));
	}
	
	@Test
	public void test222(){
		long start=System.currentTimeMillis();
		SearchBean bean=new SearchBean();
		bean.setField2(null);
		JSearch jsearch=JSearch.getInstance("where ${1} and ${1} ",bean);
		System.out.println(jsearch.getExpression());
		System.out.println(jsearch.getParamValues());
		System.out.println("---------------------------time:"+(System.currentTimeMillis()-start));
	}
	
	@Test
	public void test22222(){
		long start=System.currentTimeMillis();
		SearchBean bean=new SearchBean();
		bean.setField2(null);
		bean.setField6(10000);
		JSearch jsearch=JSearch.getInstance(" where    ${1}  and     ${1}",bean);
		System.out.println(jsearch.getExpression());
		System.out.println(jsearch.getParamValues());
		System.out.println("---------------------------time:"+(System.currentTimeMillis()-start));
	}
	
	@Test
	public void test23456(){
		long start=System.currentTimeMillis();
		SearchBean bean=new SearchBean();
		bean.setField5(new Integer[]{100,1000});
		JSearch jsearch=JSearch.getInstance("where    ${1}  and     ${1}   ",bean);
		System.out.println(jsearch.getExpression());
		System.out.println(jsearch.getParamValues());
		System.out.println("---------------------------time:"+(System.currentTimeMillis()-start));
	}
	
	@Test
	public void test33(){
		long start=System.currentTimeMillis();
		SearchBean bean=new SearchBean();
		bean.setField3("aaaa");
		JSearch jsearch=JSearch.getInstance("where    ${1}  and     ${1}   ",bean);
		System.out.println(jsearch.getExpression());
		System.out.println(jsearch.getParamValues());
		System.out.println("---------------------------time:"+(System.currentTimeMillis()-start));
	}
	
	@Test
	public void test333(){
		long start=System.currentTimeMillis();
		SearchBean bean=new SearchBean();
		bean.setField3("cccccc");
		JSearch jsearch=JSearch.getInstance("where    ${1}  and     ${1}   ",bean);
		System.out.println(jsearch.getExpression());
		System.out.println(jsearch.getParamValues());
		System.out.println("---------------------------time:"+(System.currentTimeMillis()-start));
	}
}
