package ch.acmesoftware.orientDbScalaDsl

import java.util.UUID

import com.orientechnologies.orient.core.metadata.schema.OClass.INDEX_TYPE
import com.orientechnologies.orient.core.metadata.schema.OType
import com.tinkerpop.blueprints.impls.orient.OrientVertexType
import OrientVertexTypeDsl._

class OrientVertexTypeDsl(vt: OrientVertexType) {

  def and(d: PropertyDefinition): OrientVertexTypeDsl = withProperty(d)

  def withProperty(d: PropertyDefinition): OrientVertexTypeDsl = {
    vt.createProperty(d._1, d._2)
    vt.dsl
  }

  def unique(fieldName: String): OrientVertexTypeDsl = {
    vt.createIndex(fieldName + "-unique-" + UUID.randomUUID().toString, INDEX_TYPE.UNIQUE, fieldName)
    vt.dsl
  }
}

object OrientVertexTypeDsl {
  type PropertyDefinition = (String, OType)
}
