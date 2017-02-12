package ch.acmesoftware.orientDbScalaDsl

import scala.collection.JavaConverters._

class VertexDslSpec extends Spec {

  "VertexDsl" should "create vertex with properties" in {
    tx(g => {

      g.dsl addVertex "Person" withProperty "name" -> "Frank" and "active" -> true

      g.commit()

      val res = g.getVerticesOfClass("Person").asScala

      res.size should equal(1)
      res.count(v => v.getProperty[String]("name").equals("Frank") && v.getProperty[Boolean]("active")) should equal(1)
    })
  }

  it should "edit existing vertex" in {
    tx(g => {

      g.dsl addVertex "Customer" withProperty "name" -> "ACME" and "active" -> true

      g.commit()

      val res = g.getVerticesOfClass("Customer").asScala.last

      res.getProperty[String]("name") should equal("ACME")
      res.getProperty[Integer]("year") should equal(null)

      res.dsl withProperty "name" -> "ACME Software Solutions" and "year" -> 2017

      g.commit()

      val res2 = g.getVerticesOfClass("Customer").asScala.last

      res2.getProperty[String]("name") should equal("ACME Software Solutions")
      res2.getProperty[Integer]("year") should equal(2017)
    })
  }

  it should "be able to handle mandatory properties" in {
    tx(g => {
      g.dsl addVertex "MandatoryProp" withProperty "name" -> "ACME"

      g.commit()

      val res = g.dsl findVertices "MandatoryProp" single ()

      res.get.mandatoryProperty[String]("name") should equal("ACME")
      res.get.mandatoryProperty[String]("doesNotExist") should equal(null)
    })
  }

  it should "be able to handle optional properties" in {
    tx(g => {
      g.dsl addVertex "OptionalProp" withProperty "name" -> Some("ACME") and "description" -> None

      g.commit()

      val res = g.dsl findVertices "OptionalProp" single ()

      res.isDefined should equal(true)
      res.get.property("name").isDefined should equal(true)
      res.get.property("description").isDefined should equal(false)
    })
  }

  it should "be able to handle null properties" in {
    tx(g => {
      g.dsl addVertex "NullableProp" withProperty "name" -> null

      g.commit()

      val res = g.dsl findVertices "NullableProp" single ()

      res.isDefined should equal(true)
      res.get.property("name").isDefined should equal(false)
    })
  }
}
