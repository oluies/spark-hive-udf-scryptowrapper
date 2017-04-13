name := """spark-hive-udf-scryptowrapper"""
version := "0.0.1"
organization := "com.combient"

scalaVersion := "2.11.8"
scalacOptions ++= Seq("-unchecked", "-feature", "-deprecation")
// https://mvnrepository.com/artifact/org.bouncycastle/bcprov-jdk15on

libraryDependencies ++= Seq(
  "org.apache.hive" % "hive-exec" % "1.2.1" % "provided",
  "org.apache.hadoop" % "hadoop-core" % "1.2.1" % "provided",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.consensusresearch" %% "scrypto" % "1.1.0"
)

// Without this repo, you might get a failure trying to resolve transitive
// dependency org.pentaho:pentaho-aggdesigner-algorithm:5.1.5-jhyde
resolvers += "conjars" at "http://conjars.org/repo"

resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"


addCommandAlias("jar", ";test;package")
