package controllers

import play.api._
import play.api.mvc._
import util._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index(SparkUtil.load("600000").takeRight(200)))

  }

}