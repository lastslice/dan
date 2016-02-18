import org.apache.spark.mllib.linalg.{Vector, Vectors}

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

  def unrollParams(r: Array[Double], d: Int = 300, dh: Int = 300, lenVoc: Int = 19539, deep: Int = 3, labels: Int = 5, wv: Boolean = true): List[Vector] = {
    val matSize = dh * dh
    var ind = 0
    val params = ArrayBuffer.empty[Array[Double]]

    if (deep > 0) {
      params.append(r.slice(ind, ind + matSize))
      ind += matSize
      params.append(r.slice(ind, ind + dh))
      ind += dh
    }
    params.append(r.slice(ind, ind + labels * dh))
    ind += dh * labels
    params.append(r.slice(ind, ind + labels))
    ind += labels

    if (wv) {
      params.append(r.slice(ind, ind + lenVoc * d))
    }

    params.toList.map(Vectors.dense)
  }
}