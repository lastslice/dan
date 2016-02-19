package co.lastslice.dan

import scala.collection.mutable.ArrayBuffer
import breeze.linalg.{DenseMatrix => BDM, _}
import breeze.linalg._
import breeze.math._
import breeze.numerics._
import breeze.stats._
import MathUtil._

object DanModel {

  //TODO: change to Spark LabeledPoint
  case class DataPoint(sent: BDM[Double], label: Int)

  def validate(data: List[DataPoint], params: List[BDM[Double]], fold: String, dh: Int = 300, deep: Int = 3): Double = {
    var correct = 0.0
    var total = 0.0

    data filter (_.sent.size != 0) foreach {
      case d @ DataPoint(sent, label) =>
        val pred = feedForward(d, params, dh, deep)
        total += 1
        if (argmax(pred)._1 == d.label) {
          correct += 1
        }
    }
    val accuracy = correct / total
    println(s"accuracy on $fold: correct: $correct, total: $total, accuracy: $accuracy")
    accuracy
  }

  def feedForward(dataPoint: DataPoint, params: List[BDM[Double]], dh: Int = 300, deep: Int = 3): BDM[Double] = {
    //FIXME: the next line should be equivalent to this in python: av = average(params[-1][:, sent], axis=1)
    val av: BDM[Double] = params.reverse.head(::, 1).toDenseMatrix.t

    val acts = ArrayBuffer.empty[BDM[Double]]

    for (i <- 0 until deep-1) {
      val start = i * 2
      val prev: BDM[Double] = if (i == 0) av else acts(i-1)
      val h = params(start) * prev + params(start + 1)
      acts.append(relu(h))
    }

    val ws = params(deep * 2)
    val bs = params(deep * 2 + 1)

    if(deep == 0) {
      MathUtil.softmax((ws * av) + bs)
    } else {
      MathUtil.softmax((ws * acts.reverse.head) + bs)
    }
  }

}
