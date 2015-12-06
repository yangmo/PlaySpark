package util
import org.apache.spark._
import org.apache.spark.sql.{SaveMode, SQLContext}
import model._
import scala.collection.mutable.ListBuffer
/**
 * Created by moyang on 15/11/29.
 */
object SparkUtil {
  val sparkConf = new SparkConf().setAppName("EagleWood").setMaster("local")
  val INDEX_CODES = Array("sh000001", "sh000300", "sz399001", "sz399005", "sz399006")
  val sc = {
    System.setProperty("spark.master", "spark://myhost:7077")
    new SparkContext(sparkConf)
  }
  val sqlContext = new org.apache.spark.sql.SQLContext(sc)
  import SparkUtil.sqlContext.implicits._

  def getSC(): SparkContext = synchronized {
    return sc
  }

  def stop(): Unit = {
    sc.stop()
  }

  def getSqlContext(): SQLContext = {
    return sqlContext
  }

  def appendIndex(indexDaily: IndexDaily): Unit = {
    val records = Array(indexDaily)
    SparkUtil.sc.parallelize(records).toDF()
      .write.mode(SaveMode.Append).save(Constants.DATA_BASE + Constants.INDEX + indexDaily.index_code + ".parquet")

  }

  def appendStock(stockDaily: StockDaily): Unit = {
    val records = Array(stockDaily)
    SparkUtil.sc.parallelize(records).toDF()
      .write.mode(SaveMode.Append).save(Constants.DATA_BASE + Constants.STOCK + stockDaily.index_code.takeRight(6) + ".parquet")

  }

  def load(id: String, start: String, end: String): Array[_ <: model.Daily] = {
    load(id).filter(_.date >= start).filter(_.date <= end)

  }

  def load(id: String): Array[_ <: model.Daily] = {
    if(INDEX_CODES.contains(id)) {
      return loadIndex(id)
    }
    return loadStock(id)
  }

  def loadStock(stockId: String): Array[StockDaily]= {
    sqlContext.read.parquet(Constants.DATA_BASE + "stock/" + stockId + ".parquet").collect()
      .map(x=>StockDaily.lineToRecord(x.mkString(","))).sortBy(_.date)
  }


  def loadIndex(indexId: String): Array[IndexDaily]= {
    sqlContext.read.parquet(Constants.DATA_BASE + Constants.INDEX + indexId + ".parquet").collect()
      .map(x=>IndexDaily.lineToRecord(x.mkString(","))).sortBy(_.date)
  }


  def main(args: Array[String]): Unit = {
    load("sh000001", "2014-01-01", "2014-12-31").foreach(println)
  }
}
