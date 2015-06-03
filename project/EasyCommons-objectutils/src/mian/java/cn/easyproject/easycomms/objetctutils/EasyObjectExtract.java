package cn.easyproject.easycomms.objetctutils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EasyCommons 项目下的 Obejct 对象操作工具类 <br/>
 * EasyObjectExtract：对象抽取。 从对象中使用字段表达式(FieldExpression)抽取指定属性以 key-value
 * 存入Map集合。 <br/>
 * 适合场景：JSON输出时，从对象中抽取指定输出属性和值。 <br/>
 * 
 * FieldExpression 语法： <br/>
 * 指定属性： property <br/>
 * 指定属性的属性：property.property <br/>
 * 指定集合中每一个对象： {collection} <br/>
 * 指定数组中每一个对象： [array] <br/>
 * 指定集合中每一个对象的属性：{collection}.property <br/>
 * 指定数组中每一个对象的属性：[array].property <br/>
 * 
 * @author easyproject.cn
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class EasyObjectExtract {

	/**
	 * 从对象中抽取指定属性以 key-value 存入Map集合
	 * 
	 * @param objCollection 对象集合
	 * @param fieldExpressionAndOutNameMap 字段表达式和输出名的映射列表
	 * @param fields 要抽取的字段列表，区分大小
	 * @return 以List返回所有抽取的Map集合
	 */
	public static List<Map> extract(Collection objCollection, Map fieldExpressionAndOutNameMap, String... fields) {
		List<Map> list = new ArrayList<Map>();
		for (Object o : objCollection) {
			list.add(extract(o,fieldExpressionAndOutNameMap, fields));
		}
		return list;
	}

	/**
	 * 从对象中抽取指定属性以 key-value 存入Map集合
	 * 
	 * @param objCollection
	 *            对象集合
	 * @param fields
	 *            要抽取的字段列表，区分大小
	 * @return 以List返回所有抽取的Map集合
	 */
	public static List<Map> extract(Collection objCollection, String... fields) {
		List<Map> list = new ArrayList<Map>();
		for (Object o : objCollection) {
			list.add(extract(o, fields));
		}
		return list;
	}

	/**
	 * 从对象中抽取指定属性以 key-value 存入Map集合
	 * 
	 * @param o 对象
	 * @param fieldExpressionAndOutNameMap 字段表达式和输出名的映射列表
	 * @param fieldExpressions 要抽取的字段表达式列表，区分大小写
	 * @return 抽取的Map集合
	 */
	public static Map extract(Object o,
			Map<String, String> fieldExpressionAndOutNameMap,
			String... fieldExpressions) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (fieldExpressionAndOutNameMap != null) {
			for (String name : fieldExpressions) {
				map.put(getKey(fieldExpressionAndOutNameMap, name),
						extractFieldExpressionsValue(o, name));
			}
		} else {
			for (String name : fieldExpressions) {
				map.put(name, extractFieldExpressionsValue(o, name));
			}
		}

		return map;
	}

	/**
	 * 从对象中抽取指定属性以 key-value 存入Map集合
	 * 
	 * @param o
	 *            对象
	 * @param fieldExpressions
	 *            要抽取的字段表达式列表，区分大小写
	 * @return 抽取的Map集合
	 */
	public static Map extract(Object o, String... fieldExpressions) {
		return extract(o, null, fieldExpressions);
	}

	/**
	 * 从对象中抽取指定属性以 key-value 存入Map集合
	 * 
	 * @param array 对象数组
	 * @param fieldExpressionAndOutNameMap 字段表达式和输出名的映射列表
	 * @param fields 要抽取的字段列表，区分大小
	 * @return 以List返回所有抽取的Map集合
	 */
	public static List<Map> extract(Object[] array, Map fieldExpressionAndOutNameMap, String... fields) {
		List<Map> list = new ArrayList<Map>();
		for (Object o : array) {
			list.add(extract(o,fieldExpressionAndOutNameMap, fields));
		}
		return list;
	}
	/**
	 * 从对象中抽取指定属性以 key-value 存入Map集合
	 * 
	 * @param objArray
	 *            对象数组
	 * @param fields
	 *            要抽取的字段列表，区分大小
	 * @return 以List返回所有抽取的Map集合
	 */
	public static List<Map> extract(Object[] objArray, String... fields) {
		List<Map> list = new ArrayList<Map>();
		for (Object o : objArray) {
			list.add(extract(o, fields));
		}
		return list;
	}
	/**
	 * ExtractObject 内部使用，提取指定字段表达式的值
	 * 
	 * @param o 对象
	 * @param fieldExpression 字段表达式
	 * @return 从字段表达式抽取到的值
	 */
	private static Object extractFieldExpressionsValue(Object o,
			String fieldExpression) {
		Class c = o.getClass();
		Object value = null;
		fieldExpression = fieldExpression.trim();

		if (fieldExpression.indexOf(".") != -1) {

			String parentFields = fieldExpression.substring(0,
					fieldExpression.indexOf("."));
			String sonFields = fieldExpression.substring(fieldExpression
					.indexOf(".") + 1);

			if ((parentFields.startsWith("{") && parentFields.endsWith("}"))
					|| (parentFields.startsWith("[") && parentFields
							.endsWith("]"))) {
				// 集合属性
				if (parentFields.startsWith("{")) {

					parentFields = parentFields.replace("{", "")
							.replace("}", "").trim();
					List<Object> list = new ArrayList<Object>();
					Field field = getField(c, parentFields);
					field.setAccessible(true);
					try {
						Collection collection = (Collection) field.get(o);
						for (Object pObj : collection) {
							if (pObj != null) {
								list.add(extractFieldExpressionsValue(pObj,
										sonFields));
							}
						}

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}

					value = list;

				} else { // 数组属性
					parentFields = parentFields.replace("[", "")
							.replace("]", "").trim();
					List<Object> list = new ArrayList<Object>();
					Field field = getField(c, parentFields);
					field.setAccessible(true);
					try {
						Object[] array = (Object[]) field.get(o);
						for (Object pObj : array) {
							if (pObj != null) {
								list.add(extractFieldExpressionsValue(pObj,
										sonFields));
							}
						}

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}

					value = list;
				}

			} else {

				Field field = getField(c, parentFields);
				if (field != null) {
					field.setAccessible(true);
					try {
						Object pObj = field.get(o);
						if (pObj != null) {
							value = extractFieldExpressionsValue(pObj,
									sonFields);
						}

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}

		} else {

			// 集合中的属性
			if ((fieldExpression.startsWith("{") && fieldExpression
					.endsWith("}"))
					|| fieldExpression.startsWith("[")
					&& fieldExpression.endsWith("]")) {

				if (fieldExpression.startsWith("{")) {
					fieldExpression = fieldExpression.replace("{", "")
							.replace("}", "").trim();
					Field field = getField(c, fieldExpression);
					if (field != null) {
						field.setAccessible(true);
						// 循环设置为null
						try {
							Collection collection = (Collection) field.get(o);
							List list = new ArrayList();
							for (Object obj : collection) {
								list.add(obj);
							}
							value = list;
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}

				} else {
					fieldExpression = fieldExpression.replace("[", "")
							.replace("]", "").trim();
					Field field = getField(c, fieldExpression);
					if (field != null) {
						field.setAccessible(true);
						// 循环设置为null
						try {
							Object[] array = (Object[]) field.get(o);
							List list = new ArrayList();
							for (Object obj : array) {
								list.add(obj);
							}
							value = list;
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			} else {

				Field field = getField(c, fieldExpression);

				if (field != null) {
					field.setAccessible(true);
					try {
						value = field.get(o);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}

				}
			}
		}
		return value;
	}

	/**
	 * 内部使用，获得指定字段
	 * 
	 * @param c 类
	 * @param name 字段名
	 * @return 获得的字段对象
	 */
	private static Field getField(Class c, String name) {
		Field field = null;
		try {
			field = (c.getDeclaredField(name) == null) ? c.getField(name) : c
					.getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return field;
	}

	/**
	 * EasyObjectExtract内部使用，获得要输出的key
	 * 
	 * @param fieldExpressionAndOutNameMap
	 *            字段表达式和输出名的map映射列表
	 * @param name
	 *            字段表达式名
	 * @return 获得输出名
	 */
	private static String getKey(
			Map<String, String> fieldExpressionAndOutNameMap, String name) {
		return (fieldExpressionAndOutNameMap.get(name) == null) ? name
				: fieldExpressionAndOutNameMap.get(name);
	}
}
