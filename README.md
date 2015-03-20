#Lite
Open Source SQLite Framework for Android App Development
## Introduction
As an Android developer, Lite enhance your productivity, it provide all your needs in the persistence layer and helps you focus merely on the business logic and the presentation layer. Using Lite, you will just annotate your entity, and use immediately DAO methods, all that tough work is already done for you. Lite create automatically:
* SQLite database.
* contract class.
* SQL Helper.
* DAO layer.

## Setup
* Download [Lite](https://github.com/marouanMrz/Lite/archive/master.zip), unzip the file, in your terminal run ```mvn package``` on Lite, if the build success you will find the JAR file in the target folder.
* Put the JAR file in the libs folder of your Android project.
* Go to your project properties.
* In the properties for your Android project go to Java Compiler -> Annotation Processing
Check the “Enable Project Specific Settings” and make sure “Enable annotation processing” is checked.
* Set "src" in the Generated source directory text field to have all the genereated source code in the src directory of your app.<br>

![cap1](http://img11.hostingpics.net/pics/316936Cap1.png)
* Go to Factory Path tab under Annotation Processing and add the Lite Jar located in the libs folder of your app.<br>
![cap2](http://img11.hostingpics.net/pics/307173Cap2.png)
* Congratulations, now you are able to use Lite framework.

## Usage
The `LiteEntity` annotation is all what you need to generate source code, use it on all your JavaBean classes that you want to persist in the embedded SQLite database:

```java
@LiteEntity
public class Person {
    private String name;
    // other attributs ...
    
    Person(){
    }
    
    //Getters & Setters
}
```
Amoung the generated source code, you will find the fundemental `LiteDao` class:
* `LiteDao(Context context, Class<?> entityClass)`
  usually you instantiate `LiteDao` class from an activity that represent the context, `entityClass` is the Class object of the persisted JavaBean class.
* `public int insert(Object object)`
  Insert an object with the type specified in constructor via `entityClass` parameter in the constructor.
* `public <T extends Object> List<T> selectByField(String fieldName, String fieldValue)`
  Select a row using a field name and a value.
* `public <T extends Object> List<T> selectAll()`
  Generic method that returns all persisted raws of your JavaBean.


