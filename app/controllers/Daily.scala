package controllers

import controllers.Application._
import play.api._
import play.api.mvc._
import util._

/**
 * Created by moyang on 15/12/6.
 */
object Daily extends Controller{

  def get(id: String) = Action {
    Ok(views.html.daily(SparkUtil.load(id)))
  }

  def getBetween(id: String, start: String, end: String) = Action {
    Ok(views.html.daily(SparkUtil.load(id, start, end)))
  }
}
