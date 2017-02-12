package ch.acmesoftware.orientDbScalaDsl

import com.tinkerpop.blueprints.impls.orient.OrientBaseGraph

import scala.collection.JavaConverters._

/** DSL extension for the `OrientBaseGraph` class
 *
 *  See methods for detailed API and examples...
 *
 *  @param underlying The original Java instance to wrap
 */
class GraphDsl(val underlying: OrientBaseGraph) {

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
   *  @return An [[VertexTypeDsl]] instance
   */
  def createVertexType(label: String): VertexTypeDsl = underlying.createVertexType(label).dsl

  /** Retrieves existing vertex type if present
   *
   *  ==Example==
   *  {{{
   *  g.dsl getVertexType "Existing" // Option[VertexTypeDsl]
   *  }}}
   *
   *  @param label
   *  @return
   */
  def getVertexType(label: String): Option[VertexTypeDsl] = Option(underlying.getVertexType(label)).map(_.dsl)

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
  def addVertex(label: String): VertexDsl = underlying.addVertex("class:" + label, Nil: _*).dsl

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
   *  @return A by-label filter query
   */
  def findVertices(label: String) = new VerticlesFilterQuery(label)

  /** Add an edge to graph
   *
   *  ==EXAMPLE==
   *  {{{
   *   val company = g addVertex "Company"
   *   val employee = g addVertex "Employee"
   *
   *   // create edge: Employee --(WorksFor)--> Company
   *   val e1 = g.dsl addEdge "WorksFor" -> (employee -> company)
   *  }}}
   *
   *  @param e The edge definition which is a nested tuple containing label and (in / out) vertices
   *  @return The EdgeDsl of the just created edge
   */
  def addEdge(e: (Label, (FromVertex, ToVertex))) = underlying.addEdge(null, e._2._1.underlying, e._2._2.underlying, e._1).dsl

  /** Commits the transaction */
  def commit() = underlying.commit()

  class VerticlesFilterQuery(label: String, fieldFilters: Seq[(String, Any)] = Nil) {

    def filter(filter: (String, Any)) = new VerticlesFilterQuery(label, fieldFilters :+ filter)

    def filter(filters: Seq[(String, Any)]) = new VerticlesFilterQuery(label, fieldFilters ++ filters)

    def single(): Option[VertexDsl] = list().take(1).lastOption

    def list(): Iterable[VertexDsl] = underlying.getVertices(
      label,
      fieldFilters.toMap.keys.toArray,
      fieldFilters.toMap.values.map(_.asInstanceOf[AnyRef]).toArray
    ).asScala.map(_.dsl)
  }

  type Label = String
  type FromVertex = VertexDsl
  type ToVertex = VertexDsl
}
