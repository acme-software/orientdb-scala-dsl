package ch.acmesoftware.orientDbScalaDsl

import ch.acmesoftware.orientDbScalaDsl.VertexDsl._
import ch.acmesoftware.orientDbScalaDsl.OrientVertexTypeDsl.PropertyDefinition
import com.tinkerpop.blueprints.Element

class VertexDsl(v: Element) {

  def withProperty(prop: Property): VertexDsl = {
    v.setProperty(prop._1, prop._2)
    v.dsl
  }

  def withProperty(propertyDefinition: PropertyDefinition, value: Any): VertexDsl = {
    v.setProperty(propertyDefinition._1, value)
    v.dsl
  }

  def and(prop: Property): VertexDsl = withProperty(prop)

  def and(propertyDefinition: PropertyDefinition, value: Any): VertexDsl = withProperty(propertyDefinition, value)
}

object VertexDsl {
  type Property = (String, Any)
}
