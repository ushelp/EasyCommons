package cn.easyproject.easycomms.objetctutils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * EasyCommons 项目下的 Obejct 对象操作工具类 <br/>
 * EasyObjectFilter：对象过滤。将对象中的特殊字符(<,>,...)全部过滤掉，转为转义符；或者将指定字符进行自定义转换。 <br/>
 * 适合场景：需要对对象中的字符串进行过滤转换字符实体时，例如，文件上传时提交的数据封装到对象后，对对象中用户提交的的字符串的进行转义。 <br/>
 * 
 * @author easyproject.cn
 * 
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class EasyObjectFilter {
	/**
	 * ObjectFilter时需要从对象中过滤的字符列表
	 */
	public static final Map<String, String> DEFAULT_OBJECT_FILTER_SPECIAL_CHARACTER = new HashMap<String, String>();

	/*
	 * 初始化ObjectFitler过滤列表
	 */
	static {
		DEFAULT_OBJECT_FILTER_SPECIAL_CHARACTER.put("<", "&lt;");
		DEFAULT_OBJECT_FILTER_SPECIAL_CHARACTER.put(">", "&gt;");
	}

	/**
	 * 过滤对象中所有属性的特殊字符，使用默认特殊字符替换Map（OBJECT_FILTER_SPECIAL_CHARACTER）
	 * 
	 * @param o 对象
	 */
	public static void filter(Object o) {
		filter(o, DEFAULT_OBJECT_FILTER_SPECIAL_CHARACTER);
	}
	/**
	 * 过滤对象中所有属性的特殊字符，使用默认特殊字符替换Map（OBJECT_FILTER_SPECIAL_CHARACTER）
	 * 
	 * @param collection 对象集合
	 */
	public static void filter(Collection collection) {
		for(Object o:collection){
			filter(o);
		}
	}
	/**
	 * 过滤对象中所有属性的特殊字符，使用默认特殊字符替换Map（OBJECT_FILTER_SPECIAL_CHARACTER）
	 * 
	 * @param array 对象数组
	 */
	public static void filter(Object[] array) {
		for(Object o:array){
			filter(o);
		}
	}

	/**
	 * 过滤对象中所有属性的特殊字符，使用指定替换Map
	 * 
	 * @param o 对象
	 * @param specialCharacterMap 自定义的特殊字符替换Map
	 */
	public static void filter(Object o, Map specialCharacterMap) {
		Class c = o.getClass();
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields) {
			Class type = f.getType();
			// 如果是字符串或字符
			if (type.getSimpleName().equals("String")) {
				replaceValue(o, specialCharacterMap, c, f, type);
			}
		}
	}
	/**
	 * 过滤对象中所有属性的特殊字符，使用指定替换Map
	 * 
	 * @param collection 对象集合
	 * @param specialCharacterMap
	 *            自定义的特殊字符替换Map
	 */
	public static void filter(Collection collection, Map specialCharacterMap) {
		for(Object o:collection){
			filter(o, specialCharacterMap);
		}
	}
	/**
	 * 过滤对象中所有属性的特殊字符，使用指定替换Map
	 * 
	 * @param array 对象数组
	 * @param specialCharacterMap
	 *            自定义的特殊字符替换Map
	 */
	public static void filter(Object[] array, Map specialCharacterMap) {
		for(Object o:array){
			filter(o, specialCharacterMap);
		}
	}

	/**
	 * 使用指定的特殊字符替换Map过滤对象中所有属性的特殊字符，指定不过滤字段
	 * 
	 * @param o 对象
	 * @param specialCharacterMap 自定义的特殊字符替换Map
	 * @param doNotField 不过滤字段数组
	 */
	public static void filter(Object o, Map specialCharacterMap,
			String[] doNotField) {
		Class c = o.getClass();
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields) {
			Class type = f.getType();
			// 如果是字符串或字符
			if (type.getSimpleName().equals("String")
					&& notFilterField(f.getName(), doNotField)) {
				replaceValue(o, specialCharacterMap, c, f, type);
			}
		}
	}
	/**
	 * 使用指定的特殊字符替换Map过滤对象中所有属性的特殊字符，指定不过滤字段
	 * 
	 * @param collection 对象集合
	 * @param specialCharacterMap  自定义的特殊字符替换Map
	 * @param doNotField 不过滤字段数组
	 */
	public static void filter(Collection collection, Map specialCharacterMap,
			String[] doNotField) {
		for(Object o:collection){
			filter(o, specialCharacterMap, doNotField);
		}
	}
	/**
	 * 使用指定的特殊字符替换Map过滤对象中所有属性的特殊字符，指定不过滤字段
	 * 
	 * @param array 对象数组
	 * @param specialCharacterMap
	 *            自定义的特殊字符替换Map
	 * @param doNotField
	 *            不过滤字段数组
	 */
	public static void filter(Object[] array, Map specialCharacterMap,
			String[] doNotField) {
		for(Object o:array){
			filter(o, specialCharacterMap, doNotField);
		}
	}

	/**
	 * EasyObjectFilter 内部工具方法，替换特殊字符
	 * @param o
	 * @param specialCharacterMap
	 * @param c
	 * @param f
	 * @param type
	 */
	private static void replaceValue(Object o, Map specialCharacterMap, Class c,
			Field f, Class type) {
		String fieldMethod = getFieldMethod(f.getName());
		String getMethodName = "get" + fieldMethod; // setter&&getter
		String setMethodName = "set" + fieldMethod;
		try {
			Method getMethod = c.getMethod(getMethodName);
			if (getMethod != null) {
				Object getValue = getMethod.invoke(o);
				// getValue
				if (getValue != null && !getValue.equals("")) {
					// filter SetValue
					c.getMethod(setMethodName, type).invoke(
							o,
							replaceSpecialCharacter(
									getValue.toString(),
									specialCharacterMap));
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用默认特殊字符替换Map(OBJECT_FILTER_SPECIAL_CHARACTER)过滤对象中所有属性的特殊字符，指定不过滤字段
	 * 
	 * @param o 对象
	 * @param doNotField 不过滤字段数组
	 */
	public static void filter(Object o, String[] doNotField) {
		filter(o, DEFAULT_OBJECT_FILTER_SPECIAL_CHARACTER, doNotField);
	}
	/**
	 * 使用默认特殊字符替换Map(OBJECT_FILTER_SPECIAL_CHARACTER)过滤对象中所有属性的特殊字符，指定不过滤字段
	 * 
	 * @param collection 对象集合
	 * @param doNotField 不过滤字段数组
	 */
	public static void filter(Collection collection, String[] doNotField) {
		for(Object o:collection){
			filter(o, DEFAULT_OBJECT_FILTER_SPECIAL_CHARACTER, doNotField);
		}
	}
	/**
	 * 使用默认特殊字符替换Map(OBJECT_FILTER_SPECIAL_CHARACTER)过滤对象中所有属性的特殊字符，指定不过滤字段
	 * 
	 * @param array 对象数组
	 * @param doNotField  不过滤字段数组
	 */
	public static void filter(Object[] array, String[] doNotField) {
		for(Object o:array){
			filter(o, DEFAULT_OBJECT_FILTER_SPECIAL_CHARACTER, doNotField);
		}
	}

	/**
	 * 替换特殊字符，使用指定替换Map
	 * 
	 * @param value
	 * @param specialCharacterMap
	 *            特殊字符替换Map
	 * @return 替换后的字符串
	 */
	private static String replaceSpecialCharacter(String value,
			Map specialCharacterMap) {
		for (Entry<String, String> entry : DEFAULT_OBJECT_FILTER_SPECIAL_CHARACTER
				.entrySet()) {
			value = value.replaceAll(entry.getKey(), entry.getValue());
		}
		return value;
	}

	/**
	 * 属性首字母大写
	 * 
	 * @param field
	 *            字段
	 * @return 转换后首字母大写
	 */
	private static String getFieldMethod(String field) {
		return field.replaceFirst(field.substring(0, 1), field.substring(0, 1)
				.toUpperCase());
	}

	/**
	 * 检测字段是否是不进行过滤的字段
	 * 
	 * @param fieldName
	 * @param doNotField
	 * @return 是否不进行过滤
	 */
	private static boolean notFilterField(String fieldName, String[] doNotField) {
		boolean flag = true;
		for (String nf : doNotField) {
			if (nf.equalsIgnoreCase(fieldName)) {
				flag = false;
			}
		}
		return flag;
	}
}
