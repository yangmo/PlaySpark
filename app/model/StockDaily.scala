package model

import org.apache.spark.sql.SaveMode
import util.{Constants, SparkUtil, FileUtil}

import scala.tools.scalap.scalax.util.StringUtil

/**
 * Created by moyang on 15/12/1.
 */
case class StockDaily (index_code: String, date: String, open: Double, close: Double,low: Double, high: Double
                  ,change: Double, volume: Long, money: Double, traded_market_value: Long,market_value: Long
                  ,turnover: Double, adjust_price: Double, report_type: String, report_date: String, PE_TTM: Double
                  ,PS_TTM: Double, PC_TTM: Double, PB: Double) {
}

object StockDaily {
  def lineToRecord(line: String): StockDaily = {

    val array = line.split(",")
println(line)
    val index_code = array(0)
    val date = array(1)
    val open = array(2).toDouble
    val high = array(3).toDouble
    val low = array(4).toDouble
    val close = array(5).toDouble
    val change = array(6).toDouble
    val volume = array(7).toDouble.toLong
    val money = array(8).toDouble
    val traded_market_value = array(9).toDouble.toLong
    val market_value = array(10).toDouble.toLong
    val turnover =  if(array(11) == "") {0} else array(11).toDouble
    val adjust_price =  if(array(12) == "") {0} else array(12).toDouble
    if (array.length > 18) {
      val report_type = array(13)
      val report_date = array(14)
      val PE_TTM = if(array(15) == "") {0} else array(15).toDouble
      val PS_TTM = if(array(16) == "") {0} else array(16).toDouble
      val PC_TTM =  if(array(17) == "") {0} else array(17).toDouble
      val PB =  if(array(18) == "") {0} else array(18).toDouble
      StockDaily(index_code, date, open, high, low, close, change, volume, money,
        traded_market_value, market_value, turnover, adjust_price, report_type, report_date, PE_TTM, PS_TTM, PC_TTM, PB)

    } else {
      val report_type = "null"
      val report_date = "null"
      val PE_TTM = 0
      val PS_TTM = 0
      val PC_TTM = 0
      val PB = 0
      return StockDaily(index_code.takeRight(6), date, open, high, low, close, change, volume, money, traded_market_value
        , market_value, turnover, adjust_price, report_type, report_date, PE_TTM, PS_TTM, PC_TTM, PB)
    }

  }
  def isValid(line: String): Boolean = {
    ! line.contains("date") && line.contains(",")
  }

  def loadIndex(path: String): Unit = {
    println("loading " + path)
    val content = FileUtil.read(path)
    val records = content.split("\n").filter(isValid).map(lineToRecord(_))
    import SparkUtil.sqlContext.implicits._

    SparkUtil.sc.parallelize(records).toDF()
      .write.mode(SaveMode.Append).save(Constants.DATA_BASE +"stock/"  + records(0).index_code + ".parquet")
  }

  def main(args: Array[String]): Unit = {
    val root = "/Users/moyang/springapp/data/stock data/"
    //FileUtil.getFilesUnder(root).filter(x => {"[0-9]{6}".r.findFirstIn(x)!= None && x.endsWith(".csv")}).foreach(loadIndex)

  }
}