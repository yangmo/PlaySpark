package util
import model.StockDaily
import org.apache.spark.sql.Row

/**
 * Created by moyang on 15/12/1.
 */
object DailyStorageUtil {

  def loadStock(stockId: String): Array[StockDaily]= {
    SparkUtil.sqlContext.read.parquet(Constants.DATA_BASE + "stock/" + stockId + ".parquet").collect()
      .map(x=>StockDaily.lineToRecord(x.mkString(",")))
  }

  def main(args: Array[String]): Unit = {
    loadStock("600352").foreach(println)
  }
}
