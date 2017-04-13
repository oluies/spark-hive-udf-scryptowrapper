# Scrypt as Hive User Defined Functions (UDFs), for use in Apache Spark

based on https://github.com/bmc/spark-hive-udf

## Introduction

This project wraps several [Scrypt](https://github.com/input-output-hk/scrypto) as 
[Hive User Defined Functions][] (UDFs), for use in Apache Spark. It's
intended to be used in  Hive UDF in Scala or Java within [Apache Spark][].

## Why use a Hive UDF?

One especially good use of Hive UDFs is with Python and DataFrames.
Native Spark UDFs written in Python are slow, because they have to be
executed in a Python process, rather than a JVM-based Spark Executor.
For a Spark Executor to run a Python UDF, it must:

* send data from the partition over to a Python process associated with
  the Executor, and
* wait for the Python process to deserialize the data, run the UDF on it,
  reserialize the data, and send it back.

By contrast, a Hive UDF, whether written in Scala or Java, can be executed
in the Executor JVM, _even if the DataFrame logic is in Python_.

There's really only one drawback: a Hive UDF _must_ be invoked via SQL.
You can't call it as a function from the DataFrame API.

## Building

This project builds with [SBT][] assembly

That command will download the dependencies (if they haven't already been
downloaded), compile the code, run the unit tests, and create a jar file
in `target/scala-2.11`.

## Running in Spark

The following Python code demonstrates the UDFs in this package and assumes
that you've packaged the code into `target/scala-2.10/hiveudf_2.10-0.0.1.jar`.
These commands assume Spark local mode, but they should also work fine within
a cluster manager like Spark Standalone or YARN.

You can also use Hive UDFs from Scala, by the way.

First, fire up PySpark:

```
$ pyspark --jars target/scala-2.11/spark-hive-udf-blake2-assembly-0.0.1.jar
```

At the PySpark prompt, enter the following. (If you're using IPython,
`%paste` works best.)

```
sqlContext.sql("CREATE TEMPORARY FUNCTION scorex_blake2b256 AS 'com.combient.spark.hiveudf.scorex.Blake2b256'")

df2 = sqlContext.sql("SELECT scorex_blake2b256('sdfadf')")
```

Then, take a look at the second DataFrame:

```
df2.show(1,False)
+----------------------------------------------------------------+
|scorex_blake2b256(sdfadf)                                       |
+----------------------------------------------------------------+
|A1E2ECF3183C97368C9B4D0764522B9D31D92C13BB03BC2AC56854F1E9FEC0E8|
+----------------------------------------------------------------+

```

## "Why did you write these things in Scala?"

Because, resons

## Reference

[Hive User Defined Functions]: https://cwiki.apache.org/confluence/display/Hive/LanguageManual+UDF
[Apache Spark]: http://spark.apache.org
[SBT]: http://scala-sbt.org


