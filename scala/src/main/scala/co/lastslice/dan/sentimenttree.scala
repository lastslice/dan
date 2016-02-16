package co.lastslice.dan

sealed trait Node
case class Leaf(label: Int, word: String) extends Node

class Tree {

}

object WordMap {
  def parse(lines: Seq[String]) = {
    
  }
}