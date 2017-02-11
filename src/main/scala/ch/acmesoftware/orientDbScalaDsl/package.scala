package ch.acmesoftware

import com.orientechnologies.orient.core.metadata.schema.OType
import com.tinkerpop.blueprints.{ Element, Vertex }
import com.tinkerpop.blueprints.impls.orient.{ OrientGraph, OrientGraphNoTx, OrientVertex, OrientVertexType }

package object orientDbScalaDsl {

  implicit class OrientVertexTypeWrapper(vt: OrientVertexType) {
    def dsl = new OrientVertexTypeDsl(vt)
  }

  implicit class VertexWrapper(v: Element) {
    def dsl = new VertexDsl(v)
  }

  implicit class OrientGraphWrapper(g: OrientGraph) {
    def dsl = new OrientGraphDsl(g)
  }

  implicit class OrientGraphNoTxWrapper(g: OrientGraphNoTx) {
    def dsl = new OrientGraphDsl(g)
  }
}
