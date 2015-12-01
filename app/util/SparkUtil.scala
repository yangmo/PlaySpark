package util
import org.apache.spark._
import org.apache.spark.sql.SQLContext
import scala.collection.mutable.ListBuffer
/**
 * Created by moyang on 15/11/29.
 */
object SparkUtil {
  var sparkConf = new SparkConf().setAppName("EagleWood").setMaster("local")

  val sc = {
    System.setProperty("spark.master", "spark://myhost:7077")
    new SparkContext(sparkConf)
  }
  val sqlContext = new org.apache.spark.sql.SQLContext(sc)
  import SparkUtil.sqlContext.implicits._

  def getSC(): SparkContext = synchronized {
    return sc
  }

  def getSqlContext(): SQLContext = {
    return sqlContext
  }

}
