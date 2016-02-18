package co.lastslice.dan

import breeze.linalg.{DenseMatrix => BDM, _}
import breeze.linalg._
import breeze.math._
import breeze.numerics._

object DanModel {

  /**
    * def validate(data, fold, params, deep, f=relu):
    * import ipdb; ipdb.set_trace()
    * correct = 0.
    * total = 0.

    * for sent, label in data:

    * if len(sent) == 0:
    * continue

    * av = average(params[-1][:, sent], axis=1)

    * # forward prop
    * acts = zeros((deep, dh))
    * for i in range(0, deep):
    * start = i * 2
    * prev = av if i == 0 else acts[i - 1]
    * acts[i] = f(params[start].dot(prev) + params[start + 1])

    * Ws = params[deep * 2]
    * bs = params[deep * 2 + 1]
    * if deep == 0:
    * pred = softmax(Ws.dot(av) + bs).ravel()

    * else:
    * pred = softmax(Ws.dot(acts[-1]) + bs).ravel()

    * if argmax(pred) == label:
    * correct += 1

    * total += 1

    * print 'accuracy on ', fold, correct, total, str(correct / total), '\n'
    * return correct / total
    */

  //TODO: change to Spark LabeledPoint
  case class DataPoint(sent: List[BDM], label: Int)

  def validate(data: List[DataPoint], params: List[BDM], deep: Int = 3): Double = {
    var correct = 0.0
    var total = 0.0

    data filter (_.sent.nonEmpty) foreach { case DataPoint(sent, label) =>
      val av = params.reverse.head

    }
    ???
  }

  def feedForward(dataPoint: DataPoint, params: List[BDM], dh: Int = 300, deep: Int = 3): BDM[Double] = ???
}
