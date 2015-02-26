package org.jsearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.jsearch.OperateType.Amount;
import org.jsearch.criterion.CriterionDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author dylan.chen Jul 3, 2009
 * 
 */
public class BeanObject{
	
	    private static Logger logger = LoggerFactory.getLogger(BeanObject.class);
	
		private Map<String,List<CriterionDeclaration>> groups=new HashMap<String, List<CriterionDeclaration>>();
		
		private Map<String,Object> paramValues=new HashMap<String,Object>();
		
		private Map<String,List<String>> paramsOfColumn=new HashMap<String, List<String>>();
		
		public void add(CriterionDeclaration criterionDeclaration,Object value){
			addParamValue(criterionDeclaration, value);
			addGroup(criterionDeclaration);
		}
		
		private void addParamValue(CriterionDeclaration criterionDeclaration,Object value){
			Amount amountOfOperand=criterionDeclaration.getOperate().getAmountOfRightOperand();
//			String columnName=criterionDeclaration.getColumnName();
			String fieldName=criterionDeclaration.getField().getName();
			List<String> params=new ArrayList<String>();
			if(Amount.ONE==amountOfOperand){
//				String paramName=getParamName(columnName);
				String paramName=getParamName(fieldName);
				paramValues.put(paramName,value);
				params.add(paramName);
				logger.debug("extract value=\"{}\" of name=\"{}\" of Param ",value,paramName);
			}else if(Amount.TWO==amountOfOperand||Amount.MULTI==amountOfOperand){
				int amountOfValue=ArrayUtils.getLength(value);
				for(int i=0;i<amountOfValue;i++){
//					String paramName=getParamName(columnName,Integer.toString(i));
					String paramName=getParamName(fieldName,Integer.toString(i));
					paramValues.put(paramName,((Object[])value)[i]);
					params.add(paramName);
					logger.debug("extract value=\"{}\" of name=\"{}\" of Param ",value,paramName);
				}
			}
			paramsOfColumn.put(fieldName, params);
		}
		
		private void addGroup(CriterionDeclaration criterionDeclaration){
			String groupId=criterionDeclaration.getGroupId();
			if(!groups.containsKey(groupId)){
				groups.put(groupId, new ArrayList<CriterionDeclaration>());
			}
			groups.get(groupId).add(criterionDeclaration);
			logger.debug("add CriterionDeclaration [{}] to groupId=\"{}\" of Group",criterionDeclaration,groupId);
		}
		
		private String getParamName(String input,String... suffix) {
			final String separator="_";
//			String paramName=input().replaceAll("\\.", separator);
			String paramName=input.toUpperCase();
			if(suffix.length>0){
				paramName=paramName+separator+suffix[0];
			}
			return paramName;
		}

		public Map<String, List<CriterionDeclaration>> getGroups() {
			return groups;
		}

		public Map<String, List<String>> getParamsOfColumn() {
			return paramsOfColumn;
		}

		public void setParamsOfColumn(Map<String, List<String>> paramsOfColumn) {
			this.paramsOfColumn = paramsOfColumn;
		}

		public Map<String, Object> getParamValues() {
			return paramValues;
		}

		public void setParamValues(Map<String, Object> paramValues) {
			this.paramValues = paramValues;
		}

	}