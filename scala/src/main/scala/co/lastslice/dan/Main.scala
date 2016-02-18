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

    () // must return unit :(
  }

  /**
    * Python

  def unroll_params(arr, d, dh, len_voc, deep=1, labels=2, wv=True):

    mat_size = dh * dh
    ind = 0

    params = []
    if deep > 0:
        params.append(arr[ind : ind + d * dh].reshape( (dh, d) ))
        ind += d * dh
        params.append(arr[ind : ind + dh].reshape( (dh, ) ))
        ind += dh

        for i in range(1, deep):
            params.append(arr[ind : ind + mat_size].reshape( (dh, dh) ))
            ind += mat_size
            params.append(arr[ind : ind + dh].reshape( (dh, ) ))
            ind += dh

    params.append(arr[ind: ind + labels * dh].reshape( (labels, dh)))
    ind += dh * labels
    params.append(arr[ind: ind + labels].reshape( (labels, )))
    ind += labels
    if wv:
        params.append(arr[ind : ind + len_voc * d].reshape( (d, len_voc)))
    return params

    */

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
