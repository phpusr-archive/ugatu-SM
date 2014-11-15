package org.phpusr.ugatu.sm.lab.two

import scala.collection.mutable.ListBuffer
import scala.util.Random

/**
 * @author Sergey Doronin
 *         Date: 15.11.2014
 *         Time: PM 4:25
 */

/**
 * ЛР 2 по СМ
 * Расчет высоты дамбы,
 * чтобы в течение 100 лет с вер-ю 0.95
 * не было наводнения
 */
object LabTwo {

  /** Кол-во лет для расчета */
  private val Years = 100
  /** Вероятность что не будет наводнения */
  private val Probability = 0.95
  /** Вариант */
  private val Variant = 2
  /** Кол-во испытаний */
  private val TrialCount = 1000

  /** TODO */
  def action() = {
    /** Список максимальных высоты из 1000 испытаний */
    val trialList = for (i <- 1 to TrialCount) yield getHmax
    /** Макс. значение высоты */
    val trialMax = trialList.max
    /** Мин. значение высоты */
    val trialMin = trialList.min
    /** Приращение для построения диаграммы */
    val increment = (trialMax - trialMin) / Years
    println(s"Max: ${formatFloat(trialMax)}")
    println(s"Min: ${formatFloat(trialMin)}")
    println(s"Inc: ${formatFloat(increment)}")
    println()

    /** Список интервалов для построения диаграммы */
    val intervalList = new ListBuffer[Interval]
    var value = trialMin
    for (i <- 1 to Years) {
      value += increment
      intervalList += Interval(value, value + increment)
    }

    /** Распределение значений высот по интервалам */
    intervalList.foreach(_.addBelongValues(trialList))
    intervalList.foreach(println)

    intervalList
  }

  /** Возвращает макс. значение высоты из 100 сгенерированных */
  private def getHmax = {
    val lambda = Variant / 10.0 //TODO д.б. умножение

    (1 to Years).map { e =>
      -1.0 / lambda * Math.log(Random.nextFloat())
    }.max.toFloat
  }

  /** Формат вывода вещ. числа */
  def formatFloat(value: Float) = value formatted "%1.3f"

  /** Тестирование */
  def main(args: Array[String]) {
    action()
  }

}

/** Интервал для создания диаграммы */
case class Interval(start: Float, end: Float) {

  /** Спиок значений принадлежащих интервалу */
  private val valueList = new ListBuffer[Float]

  /** Конечное значение в виде строки для диаграммы */
  def endAsString = LabTwo.formatFloat(end)

  /** Добавляет только те значения, которые принадлежат интервалу */
  def addBelongValues(list: Seq[Float]) {
    list.foreach { value =>
      if (value >= start && value <= end) valueList += value
    }
  }

  /** Кол-во входящих значений */
  def valueCount = valueList.size

  override def toString = s"Interval(${LabTwo.formatFloat(start)} = ${LabTwo.formatFloat(end)})[$valueCount]"
}
