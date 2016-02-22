package co.lastslice.dan

import breeze.linalg.{ DenseMatrix => BDM }

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object Main {
  def main(args: Array[String]) = {
    println("Loading model..")
    val bufferedSource = Source.fromFile("../python/models/dan.csv")
    val r = bufferedSource.getLines.map(_.toDouble)
    val params = unrollParams(r.toArray)

    println("Loading data points")
    val bufferedDataSource = Source.fromFile("../python/models/data-points.csv")
    val d = bufferedDataSource.getLines().map(x => x.split(",").reverse.toList) map {
      case head :: tail => tail.map(_.toDouble) -> head.toInt
    }

    val dataPoints = d.map {
      case (sent, label) => DanModel.DataPoint(new BDM(sent.size, 1, sent.toArray), label)
    }.toList

    DanModel.validate(dataPoints, params, "test")

    () // must return unit :(
  }

  def unrollParams(r: Array[Double], d: Int = 300, dh: Int = 300, lenVoc: Int = 19539, deep: Int = 3, labels: Int = 5, wv: Boolean = true): List[BDM[Double]] = {
    val matSize = dh * dh
    var ind = 0
    val params = ArrayBuffer.empty[BDM[Double]]

    if (deep > 0) {
      params.append(new BDM(dh, d, r.slice(ind, ind + d * dh)))
      ind += d * dh
      params.append(new BDM(dh, 1, r.slice(ind, ind + dh)))
      ind += dh

      for (i <- 1 until deep) {
        params.append(new BDM(dh, dh, r.slice(ind, ind + matSize)))
        ind += matSize
        params.append(new BDM(dh, 1, r.slice(ind, ind + dh)))
        ind += dh
      }
    }
    params.append(new BDM(labels, dh, r.slice(ind, ind + labels * dh)))
    ind += dh * labels
    params.append(new BDM(labels, 1, r.slice(ind, ind + labels)))
    ind += labels

    if (wv) {
      params.append(new BDM(d, lenVoc, r.slice(ind, ind + lenVoc * d)))
    }

    params.toList
  }

}
