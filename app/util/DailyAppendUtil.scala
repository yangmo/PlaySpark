package util

import java.io.File

import model._

/**
 * Created by moyang on 15/12/3.
 */
object DailyAppendUtil {

  def appendDailyIndex(data: String): Unit = {
    FileUtil.read(data)
      .split("\n").tail
      .foreach(
        x => {
          val index = IndexDaily.lineToRecord(x)
          index.append()
        }
      )
  }

  def appendDailyStock(data: String): Unit = {
    println(data)
    FileUtil.read(data)
      .split("\n").tail
      .foreach(
        x => {
          val index = StockDaily.lineToRecord(x)
          index.append
        }
      )
  }

  def appendDaily(root: String): Unit = {
    //appendIndexStock(root + "/" + new File(root).list().filter(x => x.endsWith("index data.csv")))(0))

    appendDailyStock(root + "/" + new File(root).list().filter(x => x.endsWith("data.csv") && !x.contains("index"))(0))
  }

  def main(args: Array[String]): Unit = {
    val root = "/Users/moyang/Downloads/data/"
    new File(root).list().filter(!_.contains("full")).sorted.foreach(x =>{ appendDaily(root + x)})
    //appendDaily("/Users/moyang/Downloads/data/trading-data-push-20151116")
  }
}
