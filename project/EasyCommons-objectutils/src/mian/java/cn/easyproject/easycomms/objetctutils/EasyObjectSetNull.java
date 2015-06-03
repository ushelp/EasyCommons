package cn.easyproject.easycomms.objetctutils;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * EasyCommons 项目下的 Obejct 对象操作工具类 <br/>
 * 
 * EasyObjectSetNull：对象属性置空。使用字段表达式(FieldExpression)将对象中指定属性设置为null。 <br/>
 * 适合场景：将Hibernate加载的对象中有些延迟无法加载的属性设置为空 ，防止在序列化属性时出现no session异常。<br/>
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
@SuppressWarnings({ "rawtypes"})
public class EasyObjectSetNull {

	

	/**
	 * 从集合中将fieldsExpressions指定的属性设置为null
	 * 
	 * @param collection
	 *            集合
	 * @param fieldExpressions 字段表达式
	 */
	public static void setNull(Collection collection,
			String... fieldExpressions) {
		for (Object o : collection) {
			setNull(o, fieldExpressions);
		}
	}

	/**
	 * 从数组中将fieldsExpressions指定的属性设置为null
	 * 
	 * @param array 对象数组
	 * @param fieldExpressions 字段表达式
	 */
	public static void setNull(Object[] array, String... fieldExpressions) {
		for (Object o : array) {
			setNull(o, fieldExpressions);
		}
	}

	/**
	 * 从对象中将fieldsExpressions指定的属性设置为null
	 * 
	 * @param o 对象
	 * @param fieldExpressions 字段表达式
	 */
	public static void setNull(Object o, String... fieldExpressions) {
		Class c = o.getClass();
		for (String name : fieldExpressions) {
			name = name.trim();
			if (name.indexOf(".") != -1) { // 层级元素
				String parentFields = name.substring(0, name.indexOf("."));
				String sonFields = name.substring(name.indexOf(".") + 1);

				if ((parentFields.startsWith("{") && parentFields.endsWith("}"))
						|| (parentFields.startsWith("[") && parentFields
								.endsWith("]"))) {
					if (parentFields.startsWith("{")) {
						parentFields = parentFields.replace("{", "")
								.replace("}", "").trim();
						Field field = getField(c, parentFields);
						field.setAccessible(true);
						try {
							Collection collection = (Collection) field.get(o);
							for (Object pObj : collection) {
								if (pObj != null) {
									setNull(pObj, sonFields);
								}
							}

						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					} else {
						parentFields = parentFields.replace("[", "")
								.replace("]", "").trim();
						Field field = getField(c, parentFields);
						field.setAccessible(true);
						try {
							Object[] array = (Object[]) field.get(o);
							for (Object pObj : array) {
								if (pObj != null) {
									setNull(pObj, sonFields);
								}
							}

						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				} else {
					Field field = getField(c, parentFields);
					if (field != null) {
						field.setAccessible(true);
						try {
							Object pObj = field.get(o);

							if (pObj != null) {
								setNull(pObj, sonFields);
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
				if ((name.startsWith("{") && name.endsWith("}"))
						|| name.startsWith("[") && name.endsWith("]")) {

					if (name.startsWith("{")) {
						name = name.replace("{", "").replace("}", "").trim();

						Field field = getField(c, name);
						if (field != null) {
							// 循环设置为null
							try {
								Collection collection = (Collection) field
										.get(o);
								for (Object obj : collection) {
									setNull(obj, name);
								}
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}

						}

					} else {
						name = name.replace("[", "").replace("]", "").trim();

						Field field = getField(c, name);
						if (field != null) {
							// 循环设置为null
							try {
								Object[] array = (Object[]) field.get(o);
								for (Object obj : array) {
									setNull(obj, name);
								}
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}

						}
					}
				} else {
					// 非集合属性，直接设置为null
					setNullValue(o, name);
				}

			}
		}
	}

	
	/**
	 * SetNullObject 内部使用，获得指定字段
	 * 
	 * @param c
	 * @param name
	 * @return 字段对象
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
	 * SetNullObject 内部使用，设置为null
	 * 
	 * @param o
	 * @param name
	 */
	private static void setNullValue(Object o, String name) {
		Field field = getField(o.getClass(), name);
		if (field != null) {
			field.setAccessible(true);
			try {
				field.set(o, null);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
