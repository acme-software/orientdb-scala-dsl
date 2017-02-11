package ch.acmesoftware

import com.tinkerpop.blueprints.Element
import com.tinkerpop.blueprints.impls.orient.{ OrientGraph, OrientGraphNoTx, OrientVertexType }

/** Main DSL entry point
 *
 *  This package provides a set of implicit classes (wrappers) which can be imported
 *
 *  ==Example==
 *  {{{
 *  import ch.acmesoftware.orientDbScalaDsl._
 *  }}}
 *
 *  The above import statement decorates the OrientDB Java API with a fluid Scala DSL
 */
package object orientDbScalaDsl {

  /** Wrapper providing [[OrientVertexTypeDsl]] */
  implicit class OrientVertexTypeWrapper(vt: OrientVertexType) {
    def dsl = new OrientVertexTypeDsl(vt)
  }

  /** Wrapper providing [[VertexDsl]] */
  implicit class VertexWrapper(v: Element) {
    def dsl = new VertexDsl(v)
  }

  /** Wrapper providing [[OrientGraphDsl]] */
  implicit class OrientGraphWrapper(g: OrientGraph) {
    def dsl = new OrientGraphDsl(g)
  }

  /** Wrapper providing [[OrientGraphDsl]] */
  implicit class OrientGraphNoTxWrapper(g: OrientGraphNoTx) {
    def dsl = new OrientGraphDsl(g)
  }
}
