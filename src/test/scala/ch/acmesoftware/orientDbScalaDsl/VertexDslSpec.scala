package ch.acmesoftware.orientDbScalaDsl

import scala.collection.JavaConverters._

class VertexDslSpec extends Spec {

  "OrientVertexDsl" should "create vertex with properties" in {
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
}
