# Entity Comparator

[![Build Status](https://travis-ci.org/carlopantaleo/entity-comparator.svg?branch=master)](https://travis-ci.org/carlopantaleo/entity-comparator)
[![codecov](https://codecov.io/gh/carlopantaleo/entity-comparator/branch/master/graph/badge.svg)](https://codecov.io/gh/carlopantaleo/entity-comparator)
[![jitpack](https://jitpack.io/v/carlopantaleo/entity-comparator.svg)](https://jitpack.io/#carlopantaleo/entity-comparator)



A simple `Comparator` which lets you compare objects by dynamically choosing the properties to compare.

**Please note:** if you are using Java 8 or later, please consider the standard
[`Comparator`](https://docs.oracle.com/javase/9/docs/api/java/util/Comparator.html) class.

## Get it

Add the latest version dependency to your POM:

```xml
<dependency>
    <groupId>com.github.carlopantaleo</groupId>
    <artifactId>entity-comparator</artifactId>
    <version>1.0.1</version>
</dependency>
```

## Example usage

```java
public class Example {
	private String field1;
	private String field2;
	
	// Getters and setters
	// ...
}

Example ex1 = new Example();
ex1.setField1("a");
ex1.setField2("a");

Example ex2 = new Example();
ex2.setField1("a");
ex2.setField2("b");

EntityComparator<Example> ec = new EntityComparator<>(Example.class);
ec.addComparingProperty("field1");
assertEquals(0, ec.compare(ex1, ex2));
ec.addComparingProperty("field2");
assertEquals(-1, ec.compare(ex1, ex2));
```
