package ch.acmesoftware.orientDbScalaDsl

import com.tinkerpop.blueprints.impls.orient.{OrientBaseGraph, OrientGraph}

/** DSL extension for the `OrientBaseGraph` class
  *
  * See methods for detailed API and examples...
  *
  * @param g The original Java instance to wrap
  */
class OrientGraphDsl(g: OrientBaseGraph) {

  /** Creates a new vertex type (sometimes refered to as "class)
    *
    * ==Example==
    * {{{
    * g.dsl createVertexType "SomeLabel"
    *
    * // ...with properties
    * g.dsl createVertexType "Person" withProperty "name" -> STRING
    * g.dsl createVertexType "User" withProperty "name" -> STRING and "active" -> BOOLEAN
    *
    * // ...and indexes
    * g.dsl createVertexType "City" withProperty "name" -> STRING and "zip" -> INTEGER unique "name" unique "zip"
    *
    * // edit existing vertex type
    * g.getVertexType("Person").dsl withProperty "age" -> INTEGER
    * }}}
    *
    * @param label The lable of the vertex type to create
    * @return An [[OrientVertexTypeDsl]] instance
    */
  def createVertexType(label: String): OrientVertexTypeDsl = g.createVertexType(label).dsl

  /** Adds a vertex to graph
    *
    * ==Example==
    * {{{
    * // add vertex
    * g.dsl addVertex "Person" withProperty "name" -> "Frank"
    * g.dsl addVertex "Customer" withProperty "name" -> "ACME" and "active" -> true
    * }}}
    *
    * @param label
    * @return
    */
  def addVertex(label: String): VertexDsl = g.addVertex("class:" + label, Nil: _*).dsl

  /** Creates a by-label filter query
    *
    * ==Example==
    * {{{
    * g.findVertices("Person").get
    *
    * g.findVertices("Person")
    * .filter("name" -> "Frank")
    * .filter("age" -> 28)
    * .filter("active" -> true)
    * .get
    *
    * g.findVertices("Person")
    * .filter("name" -> "Frank", "age" -> 28, "active" -> true)
    * .get
    * }}}
    *
    * @param label The label to filter by
    * @return A by-label filter wuery
    */
  def findVertices(label: String) = new VerticlesFilterQuery(label)

  class VerticlesFilterQuery(label: String, fieldFilters: Seq[(String, AnyRef)] = Nil) {

    def filter(filter: (String, AnyRef)) = new VerticlesFilterQuery(label, fieldFilters :+ filter)

    def filter(filters: Seq[(String, AnyRef)]) = new VerticlesFilterQuery(label, fieldFilters ++ filters)

    def get() = g.getVertices(label, fieldFilters.toMap.keys.toArray, fieldFilters.toMap.values.toArray)
  }
}
