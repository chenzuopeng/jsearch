package org.jsearch;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jsearch.OperateType.Amount;
import org.jsearch.criterion.CriterionDeclaration;
import org.jsearch.criterion.GroupDeclaration;
import org.jsearch.utils.Assert;
import org.jsearch.utils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *
 * @author dylan.chen 2010-9-23 下午01:38:05
 * 
 */
public class JSearch {
	
	private static final String SEPARATOR="_";
	
	private static Logger logger = LoggerFactory.getLogger(JSearch.class);

	private static AnnotationResolver annotationResolver=new AnnotationResolver();
	
	private static TemplateParser templateParser=new TemplateParser();
	
	private static CriterionTranslator criterionTranslator=new CriterionTranslator();
	
	private static Map<String,String> expressionCache=Collections.synchronizedMap(new HashMap<String, String>());
	
	private String template;
	
	private Object bean;
	
	private Map<String,Object> paramValues;
	
	public static JSearch getInstance(String template, Object bean){
		return new JSearch(template,bean);
	}

	private JSearch(String template, Object bean) {
		super();
		Assert.notNull(template);
		Assert.notNull(bean);
		this.template = template;
		this.bean = bean;
	}

	public String getExpression(){
		Map<String, GroupDeclaration> groups=annotationResolver.resolve(this.bean.getClass());
		BeanObject beanObject=new BeanObject();
		StringBuilder key=new StringBuilder(this.template.trim().replaceAll("\\s+",SEPARATOR)+"::"+StringUtils.substringAfterLast(bean.getClass().getName(),"."));
		getKeyAndBeanObject(groups, key, beanObject);
		this.paramValues=beanObject.getParamValues();
		String expression=expressionCache.get(key.toString());
		if(expression==null){
			logger.debug("could not find expression from template=\"{}\" for key=\"{}\" in cache",this.template,key);
			Map<String,String> groupToString=criterionTranslator.translate(groups, beanObject);
			expression=templateParser.parse(this.template, groupToString);
			expressionCache.put(key.toString(),expression);
		}else{
			logger.debug("find expression from template=\"{}\" for key=\"{}\" from cache",this.template,key);
		}
		logger.debug("expression:{}",expression);
		return expression;
	}
	
	private void getKeyAndBeanObject(Map<String, GroupDeclaration> groups,final StringBuilder key,final BeanObject beanObject){
		Collection<GroupDeclaration> groupDeclarations=groups.values();
		for (GroupDeclaration groupDeclaration : groupDeclarations) {
			List<CriterionDeclaration> criterionDeclarations=groupDeclaration.getCriterions();
			for (CriterionDeclaration criterionDeclaration : criterionDeclarations) {
				Object value=extractFieldValue(this.bean,criterionDeclaration);
				String fieldName=criterionDeclaration.getField().getName();
				if(BeanUtils.isNotEmptyValueOfField(value)){
					logger.debug("extract value {} of name=\"{}\" of Field with Criterion",value,fieldName);
					Amount amountOfRightOperand=criterionDeclaration.getOperate().getAmountOfRightOperand();
					if(Amount.MULTI==amountOfRightOperand){
						buildKey(key, fieldName,Integer.toString(ArrayUtils.getLength(value)));
					}else{
						buildKey(key, fieldName);
					}
					beanObject.add(criterionDeclaration, value);
				}else{
					logger.debug("value of name=\"{}\" of Field with Criterion is empty", fieldName);
				}
			}
		}
	}
	
	private Object extractFieldValue(Object bean,CriterionDeclaration criterionDeclaration){
		Object value=BeanUtils.getPropertyValue(this.bean, criterionDeclaration.getField());
		if(value==null){
			return value;
		}
		OperateType operateType=criterionDeclaration.getOperate();
		if(!operateType.validateRightOperand(value)){
			throw new RuntimeException(String.format("invalid value %s of param that extracted from name=\"%s\" of Field for Operate[%s]",ToStringBuilder.reflectionToString(value),criterionDeclaration.getField().getName(),operateType));
		}
		if(OperateType.LIKE == operateType || OperateType.NOT_LIKE == operateType){
			String likeExp=criterionDeclaration.getExpression();
			String valueToString=(String)value;
			if(StringUtils.isNotBlank(likeExp)&&likeExp.toUpperCase().contains(Constants.PLACEHOLDER_LIKE)){
				value=likeExp.toUpperCase().replaceAll(Pattern.quote(Constants.PLACEHOLDER_LIKE),valueToString);
			}
		}
		return value;
	}
	
	private void buildKey(StringBuilder key,String fieldName,String... suffix){
		key.append(SEPARATOR);
		key.append(fieldName);
		if(suffix.length>0){
			key.append(SEPARATOR);
			key.append(suffix[0]);
		}
	}

	public Map<String,Object> getParamValues() {
		logger.debug("paramValues:{}",paramValues);
		return paramValues;
	}
	
	//TODO CZP JSEARCH 当缓存命中时,当前不会验证查询字段值有效性
	//TODO CZP JSEARCH 完整的规范的单元测试
	//TODO CZP JSEARCH 重构
}
