package ch.acmesoftware.orientDbScalaDsl

import com.tinkerpop.blueprints.impls.orient.{OrientBaseGraph, OrientGraph}

/** DSL extension for the `OrientGraph` class
  *
  * @param g The original OrientGraph Java instance
  */
class OrientGraphDsl(g: OrientBaseGraph) {

  def createVertexType(label: String): OrientVertexTypeDsl = g.createVertexType(label).dsl

  def addVertex(label: String): VertexDsl = g.addVertex("class:" + label, Nil: _*).dsl

  /** Creates a by-label filter query
    *
    * Example:
    * <code>
    * g.findVerticles("Person").get
    *
    * g.findVerticles("Person")
    * .filter("name" -> "Frank")
    * .filter("age" -> 28)
    * .filter("active" -> true)
    * .get
    *
    * g.findVerticles("Person")
    * .filter("name" -> "Frank", "age" -> 28, "active" -> true)
    * .get
    * </code>
    *
    * @param label The label to filter by
    * @return A by-label filter wuery
    */
  def findVerticles(label: String) = new VerticlesFilterQuery(label)

  class VerticlesFilterQuery(label: String, fieldFilters: Seq[(String, AnyRef)] = Nil) {

    def filter(filter: (String, AnyRef)) = new VerticlesFilterQuery(label, fieldFilters :+ filter)

    def filter(filters: Seq[(String, AnyRef)]) = new VerticlesFilterQuery(label, fieldFilters ++ filters)

    def get() = g.getVertices(label, fieldFilters.toMap.keys.toArray, fieldFilters.toMap.values.toArray)
  }
}
