package util


import com.google.inject.Inject
import play.api.{Application, Plugin}
/**
 * Created by moyang on 15/12/4.
 */
class SparkPlugin extends Plugin {

  @Inject
  def SparkPlugin(app: Application) = {

  }

  override def onStart(): Unit = {
    SparkUtil.getSC()
  }

  override def onStop(): Unit = {
    SparkUtil.stop()
  }

  override def enabled = true;
}
