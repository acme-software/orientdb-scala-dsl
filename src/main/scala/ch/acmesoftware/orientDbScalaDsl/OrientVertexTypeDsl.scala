package ch.acmesoftware.orientDbScalaDsl

import java.util.UUID

import com.orientechnologies.orient.core.metadata.schema.OClass.INDEX_TYPE
import com.orientechnologies.orient.core.metadata.schema.OType
import com.tinkerpop.blueprints.impls.orient.OrientVertexType
import OrientVertexTypeDsl._

/** DSL extension for the `OrientVertexType` class
 *
 *  See methods for detailed API and examples...
 *
 *  @param vt The original Java instance to wrap
 */
class OrientVertexTypeDsl(vt: OrientVertexType) {

  /** Alias for [[ch.acmesoftware.orientDbScalaDsl.OrientVertexTypeDsl.withProperty]] */
  def and(d: PropertyDefinition): OrientVertexTypeDsl = withProperty(d)

  /** Adds a property to existing vertex type
   *
   *  ==Example==
   *  {{{
   *  g.getVertexType("Person").dsl withProperty "age" -> INTEGER
   *  }}}
   *
   *  @param d The property definition to add
   *  @return
   */
  def withProperty(d: PropertyDefinition): OrientVertexTypeDsl = {
    vt.createProperty(d._1, d._2)
    vt.dsl
  }

  /** Adds unique index to existing vertex type
   *
   *  ==Example==
   *  {{{
   *  g.getVertexType("Person").dsl unique "name" unique "zip"
   *  }}}
   *
   *  @param fieldName The name of the field which should be unique
   *  @return
   */
  def unique(fieldName: String): OrientVertexTypeDsl = {
    vt.createIndex(fieldName + "-unique-" + UUID.randomUUID().toString, INDEX_TYPE.UNIQUE, fieldName)
    vt.dsl
  }
}

object OrientVertexTypeDsl {
  type PropertyDefinition = (String, OType)
}
