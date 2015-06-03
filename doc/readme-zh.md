# EasyCommons 项目简介

---------------


EasyCommons是一个针对Java开发领域提供通用开发组件的项目。旨在为Java项目开发程序领域并不完善的一些方面，提供一些组件级的解决方案。

目前包括以下子项目：

- **EasyImageUtils**  图片处理组件
- **EasyPropertiesUitls**  Properties 文件操作组件
- **EasyObjectUtils** Obejct 对象操作组件


[官方主页](http://www.easyproject.cn/easycommons/zh-cn/index.jsp '官方主页')

##  1. EasyImageUtils：

EasyCommons 项目下的图片处理工具类。 包括如下组件：
1. **EasyImageCompressionUtils**：图片大小压缩改变工具类，支持网络图片，等比，最大宽高等多种模式。 
**适合场景**：上传图片时压缩到不同大小。 

2. **EasyImageSrcUtils**：图片地址提取工具类。将字符串内容中的图片路径提取出来。 
**适合场景**：在发表新闻内容时，自动提取内容中所有的图片，或提取第一张图片作为新闻封面。 <br/>

3. **EasyImageWaterMarkUtils**：图片水印工具类。支持为图片添加图片水印或文字水印。 
**适合场景**：上传图片时添加水印。 <br/>

### [EasyImageUtils API](EasyImageUtils-API-zh.md "EasyImageUtils API")


##  2. EasyPropertiesUitls：

> Java提供的 java.util.Properties 不仅仅在设计上存在问题，并且功能异常简陋，一般除了读，在写上不具备实用性（会导致文件原格式和注释丢失）。在维护或写properties文件时必须自行实现写方案。

EasyCommons 项目下的 Properties 文件操作工具类。包括如下组件：

1. **EasyProperties**： 用来代替java.util.Properties的properties文件类， 扩展了java.util.Properties，提供了内容合并（merge: 增, 删, 改）和修改（modify: 增,改）功能，修改属性时不影响原格式。
**适合场景**：任何需要对properties文件进行读写操作的场景，不影响文件原格式。 

2. **EasyPropertiesUtils**： 直接修改Properties文件的工具类，提供原格式写功能，包括保留注释，支持内容合并（merge: 增, 删, 改）和修改（modify: 增,改）。
**适合场景**：对properties文件进行修改操作，又不影响原格式的情况。 

### [EasyPropertiesUitls API](EasyPropertiesUitls-API-zh.md "EasyPropertiesUitls API")


##  3. EasyObjectUtils：

EasyCommons 项目下的 Obejct 对象操作工具类。

- **EasyObject FieldExpression（字段表达式）**语法：
```
 指定属性： property 
 指定属性的属性：property.property
 指定集合中每一个对象： {collection}
 指定数组中每一个对象： [array] 
 指定集合中每一个对象的属性：{collection}.property 
 指定数组中每一个对象的属性：[array].property 
```

- 包括如下组件：
 
1. **EasyObjectExtract**：对象抽取。 从对象中使用字段表达式(`FieldExpression`)抽取指定属性以 **key-value** 存入Map集合。 
**适合场景**：JSON输出时，从对象中抽取指定输出属性和值。 

2. **EasyObjectFilter**：对象过滤。将对象中的特殊字符(<,>,...)全部过滤掉，转为转义符；或者自定义字符转换映射。 
**适合场景**：将对象中的字符串包含的特殊字符进行过滤转换为字符实体；或将对象字符串属性中包含的字符全部替换为指定字符。例如，在Struts2中文件上传时提交的数据封装到对象后，对对象中可能包含的用户提交的的特殊字符串的进行转义。

3. **EasyObjectSetNull**：对象属性置空。使用字段表达式(`FieldExpression`)将对象中指定属性设置为null。 
 **适合场景**：将Hibernate加载的对象中有些延迟无法加载的属性设置为空 ，防止在序列化属性时出现no session异常。
 
### [EasyObjectUtils API](EasyObjectUtils-API-zh.md "EasyObjectUtils API")



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