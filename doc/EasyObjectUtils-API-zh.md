# EasyCommons-EasyObjectUtils

---------------
[EasyCommons](readme-zh.md "EasyCommons")  项目下的 Obejct 对象操作工具类。


## 1. Javadoc
[EasyObjectUtils Javadoc](../javadoc/easycommons-objectutils/index.html "EasyObjectUtils Javadoc")

## 2. EasyObject FieldExpression（字段表达式）

EasyObjectUtils的工具类中使用到了**EasyObject FieldExpression（字段表达式）语言**来进行属性定位。

语法：
```
 指定属性： property 
 指定属性的属性：property.property
 指定集合中每一个对象： {collection}
 指定数组中每一个对象： [array] 
 指定集合中每一个对象的属性：{collection}.property 
 指定数组中每一个对象的属性：[array].property 

 别名定义(仅适用于EasyObjectExtract)：FieldExpression#Alias
```

## 3. API 简介
EasyObjectUtils 包括如下组件：
 
1. **EasyObjectExtract**：对象抽取。 从对象中使用字段表达式(`FieldExpression`)抽取指定属性以 **key-value** 存入Map集合。 
**适合场景**：JSON输出时，从对象中抽取指定输出属性和值。 
```JAVA
 /**
 * 两种properties文件修改方式（代替store方法）：
 * - merge：将Properties对象合并到指定文件（增，改，删）
 * - modify：将Properties对象修改到指定文件（增，改，不包括删除原文件中具有的参数）
 * 
 * @param object 要抽取数据的对象
 * @param collection 要抽取数据的对象集合
 * @param array 要抽取数据的对象数组
 * @param fieldExpressionAndOutNameMap 字段表达式和输出别名的映射列表，可选，默认字段表达式名作为输出名
 * @param fieldExpressions 要抽取的字段表达式列表，不定参，区分大小写；支持#号分隔的别名定义
 * @return 抽取的Map集合结果
 */
Map extract(object [, fieldExpressionAndOutNameMap], fieldExpressions)
List<Map> extract(collection [, fieldExpressionAndOutNameMap], fieldExpressions)
List<Map> extract(array [, fieldExpressionAndOutNameMap], fieldExpressions)
```

 **demo:**
```JAVA
List<Map> list = EasyObjectExtract.extract(getData(), 
				"userId", "name", "status", "{sysRoles}.name#roleNames",
				"{sysRoles}.roleId#roleIds");
```

2. **EasyObjectFilter**：对象过滤。将JavaBean对象属性中的特殊字符(<,>,...)全部过滤掉，转为转义符；或者自定义字符转换映射。 
**适合场景**：将JavaBean对象属性中的字符串包含的特殊字符进行过滤转换为字符实体；或将对象字符串属性中包含的字符全部替换为指定字符。例如，在Struts2中文件上传时提交的数据封装到对象后，对对象中可能包含的用户提交的的特殊字符串的进行转义。
```JAVA
 /**
 * 过滤对象中字符串属性中包含的特殊字符，默认替换<, >为字符实体&lt;, &gt
 * 
 * @param object 要过滤内容的对象
 * @param collection 要过滤内容的对象集合
 * @param array 要过滤内容的对象数组
 * @param specialCharacterMap 自定义的过滤映射列表（key为要过滤的字符，value为过滤后的字符）
 * @param doNotFieldArray 不进行过滤的字段名数组
 */
filter(object [, specialCharacterMap] [, doNotFieldArray]);
filter(collection [, specialCharacterMap] [, doNotFieldArray]);
filter(array [, specialCharacterMap] [, doNotFieldArray]);
```
 **demo:**
```JAVA
Map replaceMap=new HashMap();
replaceMap.put("drug","*");
replaceMap.put("fuck","F***");
EasyObjectExtract.filter(news,replaceMap);
```

3. **EasyObjectSetNull**：对象属性置空。使用字段表达式(`FieldExpression`)将对象中指定属性设置为null。 
 **适合场景**：将Hibernate加载的对象中有些延迟无法加载的属性设置为空 ，防止在序列化属性时出现no session异常。
```JAVA
 /**
 * 将FieldExpression指定的字段值设置为null
 * 
 * @param object 要过setNUll的对象
 * @param collection 要要过setNUll的对象的对象集合
 * @param array 要要过setNUll的对象的对象数组
 * @param fieldExpressions 段表达式数组列表，不定参
 */
setNull(Object, fieldExpressions)
setNull(Collection, fieldExpressions)
setNull(Object[], fieldExpressions)
```
 **demo:**
```JAVA
EasyObjectSetNull.setNull(users, "password","{roles}.rights");
```

## 3. Maven
```XML
<!-- EasyObjectUtils -->
<dependency>
	<groupId>cn.easyproject</groupId>
	<artifactId>easycommons-object</artifactId>
	<version>1.7.4-RELEASE</version>
</dependency>
```

## 结束

[留言评论](http://www.easyproject.cn/easycommons/zh-cn/index.jsp#about '留言评论')

如果您有更好意见，建议或想法，请联系我。


联系、反馈、定制、培训 Email：<inthinkcolor@gmail.com>

<p>
<strong>支付宝钱包扫一扫捐助：</strong>
</p>
<p>

<img alt="支付宝钱包扫一扫捐助" src="http://www.easyproject.cn/images/s.png"  title="支付宝钱包扫一扫捐助"  height="256" width="256"></img>


[http://www.easyproject.cn](http://www.easyproject.cn "EasyProject Home")