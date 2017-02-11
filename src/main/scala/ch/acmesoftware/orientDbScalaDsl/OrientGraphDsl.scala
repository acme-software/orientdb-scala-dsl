package ch.acmesoftware.orientDbScalaDsl

import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.orient.OrientBaseGraph

import scala.collection.JavaConverters._

/** DSL extension for the `OrientBaseGraph` class
 *
 *  See methods for detailed API and examples...
 *
 *  @param g The original Java instance to wrap
 */
class OrientGraphDsl(g: OrientBaseGraph) {

  /** Creates a new vertex type (sometimes refered to as "class)
   *
   *  ==Example==
   *  {{{
   *  g.dsl createVertexType "SomeLabel"
   *
   *  // ...with properties
   *  g.dsl createVertexType "Person" withProperty "name" -> STRING
   *  g.dsl createVertexType "User" withProperty "name" -> STRING and "active" -> BOOLEAN
   *
   *  // ...and indexes
   *  g.dsl createVertexType "City" withProperty "name" -> STRING and "zip" -> INTEGER unique "name" unique "zip"
   *
   *  // edit existing vertex type
   *  g.getVertexType("Person").dsl withProperty "age" -> INTEGER
   *  }}}
   *
   *  @param label The lable of the vertex type to create
   *  @return An [[OrientVertexTypeDsl]] instance
   */
  def createVertexType(label: String): OrientVertexTypeDsl = g.createVertexType(label).dsl

  /** Adds a vertex to graph
   *
   *  ==Example==
   *  {{{
   *  // add vertex
   *  g.dsl addVertex "Person" withProperty "name" -> "Frank"
   *  g.dsl addVertex "Customer" withProperty "name" -> "ACME" and "active" -> true
   *  }}}
   *
   *  @param label
   *  @return
   */
  def addVertex(label: String): VertexDsl = g.addVertex("class:" + label, Nil: _*).dsl

  /** Creates a by-label filter query
   *
   *  ==Example==
   *  {{{
   *  // single by label
   *  g.dsl findVertices "City" single
   *
   *  // single by label and properties
   *  g.dsl findVertices "City" filter "name" -> "Zurich" filter  "zip" -> 8000 single
   *
   *  // list
   *  g.dsl findVertices "City" filter "name" -> "Zurich" filter  "zip" -> 8000 list
   *  }}}
   *
   *  @param label The label to filter by
   *  @return A by-label filter wuery
   */
  def findVertices(label: String) = new VerticlesFilterQuery(label)

  class VerticlesFilterQuery(label: String, fieldFilters: Seq[(String, Any)] = Nil) {

    def filter(filter: (String, Any)) = new VerticlesFilterQuery(label, fieldFilters :+ filter)

    def filter(filters: Seq[(String, Any)]) = new VerticlesFilterQuery(label, fieldFilters ++ filters)

    def single(): Option[VertexDsl] = list().take(1).lastOption

    def list(): Iterable[VertexDsl] = g.getVertices(
      label,
      fieldFilters.toMap.keys.toArray,
      fieldFilters.toMap.values.map(_.asInstanceOf[AnyRef]).toArray
    ).asScala.map(_.dsl)
  }
}
