package co.lastslice.dan

import org.apache.spark.mllib.linalg.DenseMatrix
import org.scalatest._

import scala.io.Source

class MainSpec  extends FlatSpec with Matchers {

  "unrollParams" should "returns the correct sizes" in {
    val bufferedSource = Source.fromFile("../python/models/dan.csv")
    val r = bufferedSource.getLines.map(_.toDouble)

    val params = Main.unrollParams(r.toArray)

    val expected = "[(300, 300), (300, 1), (300, 300), (300, 1), (300, 300), (300, 1), (5, 300), (5, 1), (300, 19539)]"
    val actual = s"[${params.map(x => s"(${x.rows}, ${x.cols})").mkString(", ")}]"

    println(expected)
    println(actual)

    params.size should be(9)
    actual should be(expected)
  }

}
