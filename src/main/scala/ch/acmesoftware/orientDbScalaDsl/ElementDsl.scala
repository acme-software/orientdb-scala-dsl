package ch.acmesoftware.orientDbScalaDsl

import ch.acmesoftware.orientDbScalaDsl.ElementDsl.Property
import com.tinkerpop.blueprints.Element

trait ElementDsl[U <: Element] {

  val underlying: U

  /** Adds a property to existing edge
   *
   *  ==Example==
   *  {{{
   *    existing.dsl withProperty "name" -> "ACME Software Solutions" and "year" -> 2017
   *    existing.dsl withProperty "name" -> Some("foo") // will be persisted
   *    existing.dsl withProperty "name" -> None // nothing will happen
   *    existing.dsl withProperty "name" -> null // nothing will happen
   *  }}}
   *
   *  @param prop The property to set
   */
  def withProperty(prop: Property): ElementDsl[U] = {
    prop._2 match {
      case Some(value) => underlying.setProperty(prop._1, value)
      case None        => // do nothing, option is empty
      case null        => // do nothing, value is null
      case _           => underlying.setProperty(prop._1, prop._2)
    }
    this
  }

  /** Alias for [[VertexDsl.withProperty]] */
  def and(prop: Property): ElementDsl[U] = withProperty(prop)

  /** Gets a property of a vertex
   *
   *  ==Example==
   *  {{{
   *   existing.dsl.property[String]("name") // Option[String]
   *  }}}
   *
   *  @param name The filed name of the property
   *  @tparam T Property type
   */
  def property[T](name: String): Option[T] = Option(underlying.getProperty[T](name))

  /** Gets a mandatory property of a vertex
   *
   *  Returns a property if it exists or not. This method can be used to retrieve properties baked in the schema, so
   *  it must be present. The result is not wrapped in an `Option[T]`, so if the property does not exist on the database
   *  result will be null.
   *
   *  ==Example==
   *  {{{
   *   existing.dsl.mandatoryProperty[String]("name") // String
   *  }}}
   *
   *  @param name The filed name of the property
   *  @tparam T Property type
   */
  def mandatoryProperty[T](name: String): T = underlying.getProperty[T](name)
}

object ElementDsl {
  type Property = (String, Any)
}
