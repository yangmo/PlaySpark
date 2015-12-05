package model

/**
 * Created by moyang on 15/12/4.
 */
trait Daily {

  def getId(): String
  def date(): String
  def adjustedOpen(): Double
  def adjustedClose(): Double
  def adjustedHigh(): Double
  def adjustedLow(): Double
}
