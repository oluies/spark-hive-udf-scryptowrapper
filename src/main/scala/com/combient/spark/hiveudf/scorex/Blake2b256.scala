package com.combient.spark.hiveudf.scorex

import org.apache.hadoop.hive.ql.exec.UDF
import scorex.crypto.hash.CryptographicHash.Digest
import scorex.crypto.hash._

/** This UDF takes a SQL Timestamp and converts it to a string, using a
  * Java `SimpleDateFormat` string to dictate the format.
  */


class Blake2b256 extends UDF {

  def evaluate(s: String): String = {
    if ((s == null)) {
      ""
    }
    else {
      try {
        var d: Digest = Blake2b256(s)

        d.array.map("%02X" format _).mkString
      }
      catch {
        // Bad format possibly. Return Timestmap.toString. (We could return
        // an error message, as well, but this is fine for now.)
        case e: Exception =>
          System.err.println("Input $s")
          throw new Exception("UDF" + this.getClass.getName )
      }
    }
  }
}
