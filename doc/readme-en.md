# EasyCommons Project Description

---------------

EasyCommons is a development component to provide a common field for Java development projects. Java is designed to develop programs in the field of some aspects of the project is not perfect, some component-level solutions.

Currently consists of the following components:

- **EasyImageUtils**  Image processing components
- **EasyPropertiesUitls**  Properties file operations components
- **EasyObjectUtils** Obejct object manipulation components


[Official Homepage](http://www.easyproject.cn/easycommons/en/index.jsp 'Official Homepage')

##  1. EasyImageUtils:

Image processing tools under EasyCommons project. Comprising the following components:

1. **EasyImageCompressionUtils**: change the image size compression tools, support network pictures, geometric, higher maximum wide variety of modes.
**Suit the scene** : compression to upload pictures of different sizes.

2. **EasyImageSrcUtils**: Picture address extraction tools. The string contents of the image path extracted.
**Suit the scene** : at the time of publication of news content, the content automatically extract all images, or extract the first image as a news cover.

3. **EasyImageWaterMarkUtils**: image watermark tools. Support for the picture add a picture watermark or text watermark.
**Suit the scene** : add a watermark to upload pictures.
### [EasyImageUtils API](EasyImageUtils-API-en.md "EasyImageUtils API")


##  2. EasyPropertiesUitls:

> Java.util.Properties Java provides not only the existence of problems in the design and function of abnormal primitive, usually in addition to reading and writing not practical in the (original file format and can lead to loss of the comment). When maintenance or write properties file must be self-fulfilling to write programs.

Properties file with projects under EasyCommons tools. Comprising the following components:

1. ** EasyProperties **: java.util.Properties properties file used in place of class, extends java.util.Properties, provides content merge (merge: add, delete, change) and modifications (modify: add, change ) function, modify the property does not affect the original format.
** Suit the scene** : properties require any scene file read and write operations, without affecting the original file format.

2. ** EasyPropertiesUtils **: Properties file directly modify the tools to provide the original format write functions, including a comment, support is incorporated (merge: add, delete, change) and modifications (modify: add, change).
**Suit the scene ** : properties files to modify the operation, without prejudice to the original format.

### [EasyPropertiesUitls API](EasyPropertiesUitls-API-en.md "EasyPropertiesUitls API")


##  3. EasyObjectUtils:

Obejct manipulation tools under EasyCommons project.

- **EasyObject FieldExpression ** Syntax:
```
 Specified attributes: property
 Specify the attribute that: property.property
 Each object in the specified collection: {collection}
 Each object in the specified array: [array]
 Attribute specifies for each object in the collection: {collection} .property
 Attribute specifies for each object in the array: [array] .property
```

- Comprising the following components:
 
1. ** EasyObjectExtract **: extraction of the object. Using field expression from the object (`FieldExpression`) to extract the specified property deposited ** key-value ** Map collection.
** Suit the scene **: JSON output, specify the output attributes and values extracted from the object.

2. ** EasyObjectFilter **: JavaBean Object property filtering. The object of special characters (<,>, ...) to filter out, into the escape character; or custom character transformation maps.
** Suit the scene** : the special character JavaBean Object property contains a string to filter into character entity; or a character string property objects included replacing all the specified character. For example, the data in the file upload Struts2 package submitted to the object, a special string to the user object may contain submitted escaped.

3. ** EasyObjectSetNull **: Object Properties empty. Using field expressions (`FieldExpression`) specified property set to null object.
  ** Suit the scene** : Hibernate object loaded some delay attribute set can not be loaded is empty, to prevent abnormal when no session serialized property.
 
### [EasyObjectUtils API](EasyObjectUtils-API-en.md "EasyObjectUtils API")




## End

[Comments](http://www.easyproject.cn/easycommons/en/index.jsp#about 'Comments')

If you have more comments, suggestions or ideas, please contact me.

Email:<inthinkcolor@gmail.com>

[http://www.easyproject.cn](http://www.easyproject.cn "EasyProject Home")
