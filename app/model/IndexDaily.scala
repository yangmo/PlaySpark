package model
import java.util.Date
import org.apache.spark.sql.SaveMode
import util.{SparkUtil, Constants, FileUtil}

/**
 * Created by moyang on 15/11/29.
 */
case class IndexDaily(index_code: String, date: String, open: Double, close: Double,low: Double, high: Double
                 ,volume: Long, money: Double, change: Double) {


  override def toString: String = {
    Array(index_code, date, open, close, low, high, volume, money, change)
      .reduce(_ + "\t" + _).toString
  }
}


object IndexDaily {
  def lineToRecord(line: String): IndexDaily = {
    val array = line.split(",")
    new IndexDaily(array(0), array(1), array(2).toDouble, array(3).toDouble
      , array(4).toDouble, array(5).toDouble, array(6).toDouble.toLong, array(7).toDouble
      , if(array.length >= 9)array(8).toDouble else 0.0)
  }

  def isValid(line: String): Boolean = {
    ! line.contains("date")
  }

  def loadIndex(path: String): Unit = {
    val content = FileUtil.read(path)
    val records = content.split("\n").filter(isValid).map(lineToRecord)
    import SparkUtil.sqlContext.implicits._

    SparkUtil.sc.parallelize(records).toDF()
      .write.mode(SaveMode.Append).save(Constants.DATA_BASE +"index/" + records.take(1)(0).index_code + ".parquet")
  }

  def main(args: Array[String]): Unit = {
    println(SparkUtil.sqlContext.read.parquet(Constants.DATA_BASE   + "index/sz399006.parquet").collect().size)
  }
}