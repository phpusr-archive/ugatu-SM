package org.phpusr.ugatu.sm.lab.two

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.chart.{BarChart, CategoryAxis, NumberAxis, XYChart}

/**
 * @author Sergey Doronin
 *         Date: 15.11.2014
 *         Time: PM 5:27
 */

/**
 * Форма с диаграммой для 2-й лабы
 */
//TODO сделать 95%
//TODO убрать все лишнее
object LabTwoFrame extends JFXApp {

  /** Интервалы со значениями в них */
  private val (intervals, suitable, residue) = LabTwo.action()

  stage = new JFXApp.PrimaryStage {
    title = "Lab 2"
    width = 1024
    height = 600

    scene = new Scene {
      root = {

        val xAxis = new CategoryAxis {
          label = "Высота"
          categories = ObservableBuffer(intervals.map(_.endAsString))
          //tickLabelFormatter = NumberAxis.DefaultFormatter(this)
        }

        val yAxis = new NumberAxis {
          label = "Кол-во"
          tickLabelFormatter = NumberAxis.DefaultFormatter(this)
        }

        val seriesList = List(("Подходящая высота", suitable), ("Излишняя высота", residue)).map { interval =>
          new XYChart.Series[String, Number] {
            name = interval._1
            data = interval._2.map { e =>
              XYChart.Data[String, Number](e.endAsString, e.valueCount)
            }
          }
        }

        // Диаграмма
        new BarChart(xAxis, yAxis) {
          barGap = -3
          categoryGap = 0
          title = "Диаграмма высот"
          data() ++= Seq(seriesList(0), seriesList(1))
        }
      }
    }

  }

}
