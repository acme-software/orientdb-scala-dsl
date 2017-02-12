package ch.acmesoftware.orientDbScalaDsl

import com.tinkerpop.blueprints.{ Direction, Vertex }
import scala.collection.JavaConverters._

/** DSL extension for the `Vertex` class
 *
 *  See methods for detailed API and examples...
 *
 *  @param underlying The original Java instance to wrap
 */
class VertexDsl(val underlying: Vertex) extends ElementDsl[Vertex] {

  def in(label: String): Iterable[EdgeDsl] = edges(label, Direction.IN)

  def out(label: String): Iterable[EdgeDsl] = edges(label, Direction.OUT)

  private def edges(l: String, d: Direction) = underlying.getEdges(d, l).asScala.map(_.dsl)
}

