package org.phpusr.ugatu.sm.lab.one

import scala.util.Random

/**
 * @author Sergey Doronin
 *         Date: 03.11.2014
 *         Time: PM 3:18
 */

/**
 * Статистическое моделирование
 *
 * ЛР1 - Проверка эталонных СВ
 *
 * + Сгенерировать 1000 СВ в промежутке [0, 1]
 * + Проверить гипотезу о равенстве мат. ожидания, д.б. 0.5
 * + Дисперсия д.б. 1/12 у СВ
 * + Тест цифр
 * + Тест групп
 */
object LabOne extends App {

  /** Кол-во генерируемых СВ */
  val n = 1000

  /** Кол-во знаков после запятой */
  val Accuracy = 2

  /** Частота появления СВ */
  val FreqSV = 0.1

  // Генерирование n СВ
  val array = for (i <- 1 to n) yield Random.nextFloat()
  println(s"Size: ${array.size}")

  calcDx()
  signTest()
  groupTest()

  /** Подсчет математического ожидания */
  def calcMx() = {
    val mx = array.sum / n
    println(s"\nM(X) = $mx, must be: 0.5")
    mx
  }

  /** Подсчет дисперсии */
  def calcDx() {
    val mx = calcMx()
    val dx = array.map( x => Math.pow(x - mx, 2).abs).sum / n
    println(s"\nD(X) = $dx, must be: ${1.toFloat/12}")
  }

  /** Тест цифр */
  def signTest() {
    /** Хи(квадрарт) из таблицы k=7 a=0.95 */
    val Hi2Table = 2.17

    // Преобразование массива вещ. чисел в строку символов, которые стоят после запятой
    val signs = array.map(_.formatted(s"%1.${Accuracy}f").substring(2)).mkString

    println("\nSign test:")
    val sum = ('0' to '9').map { i =>
      val count = signs.count(_ == i)
      println(s"$i - $count")
      count.toDouble / n
    }.map(e => Math.pow(e-0.1, 2)).sum
    println(s"Sum: $sum, must be: $Hi2Table")
  }

  /** Тест групп */
  def groupTest() {
    /** Хи(квадрарт) из таблицы k=30 a=0.95 */
    val Hi2Table = 18.5

    // Преобразование массива вещ. чисел в массив двузначлных целых чисел
    val groups = array.map(_.formatted(s"%1.${Accuracy}f").substring(2).toInt)

    println("\nGroup test:")
    val sum = (0 to 99).map { i =>
      val count = groups.count(_ == i)
      println(s"$i - $count")
      count.toDouble / n
    }.map(e => Math.pow(e-0.1, 2)).sum
    println(s"Sum: $sum, must be: $Hi2Table")
  }

  /** Вывод всех СВ */
  def printSV() {
    println(s"Print $n SV")
    println(array.map(_.formatted(s"%1.${Accuracy}f")).mkString("; "))
  }


}
