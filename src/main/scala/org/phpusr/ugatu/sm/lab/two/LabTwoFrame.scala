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
//TODO поменять заголовки
//TODO сделать 95%
//TODO убрать все лишнее
//TODO настроить размер
object LabTwoFrame extends JFXApp {

  /** Интервалы со значениями в них */
  private val intervals = LabTwo.action()

  stage = new JFXApp.PrimaryStage {
    title = "Advanced Bar Chart Example"
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

        val series = new XYChart.Series[String, Number] {
          name = "Data"
          data = intervals.map { e =>
            XYChart.Data[String, Number](e.endAsString, e.valueCount)
          }
        }

        // Диаграмма
        new BarChart(xAxis, yAxis) {
          barGap = 0
          categoryGap = 3
          title = "Advanced Bar Chart"
          data() ++= Seq(series)
        }
      }
    }
  }
}
