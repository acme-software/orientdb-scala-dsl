package ch.acmesoftware.orientDbScalaDsl

import ch.acmesoftware.orientDbScalaDsl.VertexDsl._
import com.tinkerpop.blueprints.Element

/** DSL extension for the `Element` class
 *
 *  See methods for detailed API and examples...
 *
 *  @param v The original Java instance to wrap
 */
class VertexDsl(v: Element) {

  /** Adds a property to existing vertex
   *
   *  ==Example==
   *  {{{
   *  existing.dsl withProperty "name" -> "ACME Software Solutions" and "year" -> 2017
   *  }}}
   *
   *  @param prop The property to set
   */
  def withProperty(prop: Property): VertexDsl = {
    v.setProperty(prop._1, prop._2)
    v.dsl
  }

  /** Alias for [[VertexDsl.withProperty]] */
  def and(prop: Property): VertexDsl = withProperty(prop)
}

object VertexDsl {
  type Property = (String, Any)
}
