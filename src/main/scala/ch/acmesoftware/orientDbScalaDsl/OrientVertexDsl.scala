package ch.acmesoftware.orientDbScalaDsl

import com.orientechnologies.orient.core.metadata.schema.OType
import com.tinkerpop.blueprints.impls.orient.OrientVertex

class OrientVertexDsl(v: OrientVertex) {




  def withProperty(fieldDefinition: (String, OType), value: Any): OrientVertex = {
    v.setProperty(fieldDefinition._1, value)
    v
  }
}
