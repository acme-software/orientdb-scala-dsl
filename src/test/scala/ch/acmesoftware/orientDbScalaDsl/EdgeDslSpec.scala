package ch.acmesoftware.orientDbScalaDsl

import scala.collection.JavaConverters._

class EdgeDslSpec extends Spec {

  "EdgeDsl" should "create edge with properties" in {
    tx(g => {
      val v1 = g.dsl addVertex "Test1"
      val v2 = g.dsl addVertex "Test2"

      g.dsl addEdge "Likes" -> (v1 -> v2) withProperty "direct" -> true

      g.commit()

      val res = g.getEdgesOfClass("Likes").asScala

      res.size should equal(1)
      res.count(v => v.getProperty[Boolean]("direct").equals(true)) should equal(1)
    })
  }

  it should "edit existing edge" in {
    tx(g => {
      val v1 = g.dsl addVertex "Test3"
      val v2 = g.dsl addVertex "Test4"

      g.dsl addEdge "Knows" -> (v1 -> v2) withProperty "years" -> 3 and "direct" -> true

      g.commit()

      val res = g.getEdgesOfClass("Knows").asScala.last

      res.getProperty[Integer]("years") should equal(3)
      res.getProperty[Boolean]("direct") should equal(true)

      res.dsl withProperty "years" -> 15

      g.commit()

      val res2 = g.getEdgesOfClass("Knows").asScala.last

      res2.getProperty[Integer]("years") should equal(15)
    })
  }

  it should "be able to handle mandatory properties" in {
    tx(g => {
      val v1 = g.dsl addVertex "Test5"
      val v2 = g.dsl addVertex "Test6"

      g.dsl addEdge "MandatoryProp" -> (v1 -> v2) withProperty "name" -> "ACME"

      g.commit()

      val res = v1 out "MandatoryProp"

      res.nonEmpty should equal(true)
      res.last.mandatoryProperty[String]("name") should equal("ACME")
      res.last.mandatoryProperty[String]("doesNotExist") should equal(null)
    })
  }

  it should "be able to handle optional properties" in {
    tx(g => {
      val v1 = g.dsl addVertex "Test7"
      val v2 = g.dsl addVertex "Test8"

      g.dsl addEdge "OptionalProp" -> (v1 -> v2) withProperty "name" -> Some("ACME") and "description" -> None

      g.commit()

      val res = v2 in "OptionalProp"

      res.nonEmpty should equal(true)
      res.last.property("name").isDefined should equal(true)
      res.last.property("description").isDefined should equal(false)
    })
  }

  it should "be able to handle null properties" in {
    tx(g => {
      val v1 = g.dsl addVertex "Test9"
      val v2 = g.dsl addVertex "Test10"

      g.dsl addEdge "NullableProp" -> (v1 -> v2) withProperty "name" -> null

      g.commit()

      val res = v1 out "NullableProp"

      res.nonEmpty should equal(true)
      res.last.property("name").isDefined should equal(false)
    })
  }
}
