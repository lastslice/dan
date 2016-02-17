package co.lastslice.dan

import breeze.linalg.{DenseMatrix, _}
import breeze.numerics._

object MathUtil {

  def softmax(w: DenseMatrix[Double]): DenseMatrix[Double] = softmax(w)

  def relu(x: DenseMatrix[Double]): DenseMatrix[Double] = x * drelu(x)

  def drelu(x: DenseMatrix[Double]): DenseMatrix[Double] = (x :> 0.0).map(if (_) 1.0 else 0.0)

  def crossent(label: DenseMatrix[Double], classification: DenseMatrix[Double]) =
    - sum (label * log(classification))

  def dcrossent(label: DenseMatrix[Double], classification: DenseMatrix[Double]) =
    classification - label

}
