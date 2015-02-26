package org.jsearch.utils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 使用反射对一个Bean进行操作的工具类
 * 
 * @author dylan.chen Dec 18, 2008
 * 
 */
public class BeanUtils {

	private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

	/**
	 * 获取Class 对象所表示的类或接口所声明的所有字段，包括公共、保护、默认（包）访问和私有字段。
	 * 
	 * @description:获取Class 对象所表示的类或接口所声明的所有字段，包括公共、保护、默认（包）访问和私有字段。
	 * 
	 * @param beanClass
	 * 
	 * @return Field[]
	 */
	@SuppressWarnings({"rawtypes" })
	public static Field[] getDeclaredFields(Class beanClass) {
		if (beanClass == null) {
			return null;
		}
		Set<Field> beanPropertys = new LinkedHashSet<Field>();
		getDeclaredFields(beanClass, beanPropertys);
		return beanPropertys.toArray(new Field[0]);
	}

	/**
	 * 获取Class 对象所表示的类或接口所声明的所有字段，包括公共、保护、默认（包）访问和私有字段。
	 * 
	 * @description:获取Class 对象所表示的类或接口所声明的所有字段，包括公共、保护、默认（包）访问和私有字段。
	 * 
	 * @param beanClass
	 * @param beanPropertys
	 * 
	 * @return void
	 */
	@SuppressWarnings({"rawtypes" })
	private static void getDeclaredFields(final Class beanClass, final Set<Field> beanPropertys) {
		Field[] fields = beanClass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			beanPropertys.add(fields[i]);
		}
		Class superClass = beanClass.getSuperclass();
		if (superClass != null && !Object.class.equals(superClass)) {
			getDeclaredFields(superClass, beanPropertys);
		}
	}

	/**
	 * 获取指定对象的指定属性值
	 * 
	 * @description:获取指定对象的指定属性值
	 * 
	 * @param obj
	 * @param field
	 * 
	 * @return Object
	 */
	public static Object getPropertyValue(Object obj, Field field) {
		Object result = null;
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			logger.warn("unable to get value of field[name=" + field.getName() + "] on class " + obj.getClass());
		}
		field.setAccessible(accessible);
		return result;
	}

	/**
	 * 获取带有指定注释的属性
	 * 
	 * @param clazz 
	 *            Class对象
	 * @param annotationClass
	 *            注释
	 * @return Field[]
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Field[] getFieldsByAnnotation(Class clazz, Class annotationClass) {
		Field[] fields = getDeclaredFields(clazz);
		List<Field> fieldWithCriterions = new ArrayList<Field>();
		for (Field field : fields) {
			if (field.isAnnotationPresent(annotationClass)) {
				fieldWithCriterions.add(field);
			}
		}
		return fieldWithCriterions.toArray(new Field[0]);
	}

	/**
	 * 属性值是否为空,如果值为String,则此字符串必须为非空白字符
	 * 
	 * @param value
	 *            属性的值
	 * @return boolean
	 * */
	@SuppressWarnings({"rawtypes" })
	public static boolean isNotEmptyValueOfField(Object value) {
		if (value != null) {
			if (value instanceof String) {// String
				return StringUtils.isNotBlank(value.toString());
			}else if (value.getClass().isArray()) {// 数组
				return ArrayUtils.getLength(value) > 0;
			} else if (value instanceof Collection) {// Set,List等实现了Collection接口的类
				return !((Collection) value).isEmpty();
			} else if (value instanceof Map) {// Map
				return !((Map) value).isEmpty();
			} else {// 基本类型与其他
				return true;
			}
		}
		return false;
	}
}
