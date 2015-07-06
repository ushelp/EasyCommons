# EasyCommons-EasyObjectUtils

---------------
[EasyCommons](readme-zh.md "EasyCommons")  Obejct manipulation tools under the project.

## 1. Javadoc
[EasyObjectUtils Javadoc](../javadoc/easycommons-objectutils/index.html "EasyObjectUtils Javadoc")

## 2. EasyObject FieldExpression

EasyObjectUtils the tools used to ** EasyObject FieldExpression (field expression) language ** for property positioning.

Syntax:
```
Specified attributes: property
Specify the attribute that: property.property
Each object in the specified collection: {collection}
Each object in the specified array: [array] Attribute specifies for each object in the collection: {collection} .property
Attribute specifies for each object in the array: [array] .property

Alias definitions (only for EasyObjectExtract): FieldExpression#Alias
```

## 3. API summary
EasyObjectUtils It includes the following components:
 
1. **EasyObjectExtract**:Extraction of the object. Using field expression from the object (`FieldExpression`) to extract the specified property deposited ** key-value ** Map collection.
** Suit the scene **: JSON output, specify the output attributes and values extracted from the object.
```JAVA
 /**
  * Two ways to modify the properties file (instead of store method):
  * - Merge: The Properties object into the specified file (add, change, delete)
  * - Modify: Modify the Properties object to the specified file (add, change, delete the original file does not include having a parameter)
  *
  *@param object the object data to be extracted
  *@param collection object data set to be extracted
  *@param array array object data to be extracted
  *@param fieldExpressionAndOutNameMap Map list field expression and output alias, optional default field expression name as the output name
  *@param fieldExpressions A list of the fields to be extracted expression, variable parameters, is case-sensitive; support hash character alias definition
  *@return Map collection extracted results
 */
Map extract(object [, fieldExpressionAndOutNameMap], fieldExpressions)
List<Map> extract(collection [, fieldExpressionAndOutNameMap], fieldExpressions)
List<Map> extract(array [, fieldExpressionAndOutNameMap], fieldExpressions)
```
** demo:**
```JAVA
List<Map> list = EasyObjectExtract.extract(getData(), 
				"userId", "name", "status", "{sysRoles}.name#roleNames",
				"{sysRoles}.roleId#roleIds");
```

2. **EasyObjectFilter**:Object filtering. The object of special characters (<,>, ...) to filter out, into the escape character; or custom character transformation maps.
** Suit the scene** : the special character object contains a string to filter into character entity; or a character string property objects included replacing all the specified character. For example, the data in the file upload Struts2 package submitted to the object, a special string to the user object may contain submitted escaped.
```JAVA
 /**
 * Special character filtering object attribute contains the string, the default Replace <,> is the character entity & lt ;, & gt
  *
  *@param object Object to filter content
  *@param collection set of objects you want to filter content
  *@param array object array to filter content
  *@param specialCharacterMap Filter mapping listparam specialCharacterMap custom (key for the character you want to filter, value character filtered)
  *@param DoNotFieldArray does not filter the array of field names
 */
filter(object [, specialCharacterMap] [, doNotFieldArray])；
filter(collection [, specialCharacterMap] [, doNotFieldArray])；
filter(array [, specialCharacterMap] [, doNotFieldArray])；
```

 **demo:**
```JAVA
Map replaceMap=new HashMap();
replaceMap.put("drug","*");
replaceMap.put("fuck","F***");
EasyObjectExtract.filter(news,replaceMap);
```

3. **EasyObjectSetNull**:Object Properties empty. Using field expressions (`FieldExpression`) specified property set to null object.
  ** Suit the scene** : Hibernate object loaded some delay attribute set can not be loaded is empty, to prevent abnormal when no session serialized property.
```JAVA
 /**
 * The field is set to the value specified for the null FieldExpression
 *
 *@param Object setNUll of the object to be over
 *@param Collection of objects you want to lead a collection of objects setNUll
 *@param Array array object to object to lead setNUll
 * Expression array list fieldExpressions segmentparam, uncertain parameters
*/
setNull(Object, fieldExpressions)
setNull(Collection, fieldExpressions)
setNull(Object[], fieldExpressions)
```
 **demo:**
```JAVA
EasyObjectSetNull.setNull(users, "password","{roles}.rights");
```

## End

[Comments](http://www.easyproject.cn/easycommons/en/index.jsp#about 'Comments')

If you have more comments, suggestions or ideas, please contact me.

Email:<inthinkcolor@gmail.com>

[http://www.easyproject.cn](http://www.easyproject.cn "EasyProject Home")



[http://www.easyproject.cn](http://www.easyproject.cn "EasyProject Home")