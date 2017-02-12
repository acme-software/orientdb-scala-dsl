package ch.acmesoftware.orientDbScalaDsl

import com.orientechnologies.orient.core.metadata.schema.OType._

import scala.collection.JavaConverters._

class VertexTypeDslSpec extends Spec {

  "VertexTypeDsl" should "create vertex type by label" in {
    notTx(g => {
      g.dsl createVertexType "NoProp"
    })

    tx(g => {

      val res = g.getVertexType("NoProp")

      res should not be null
      res.isVertexType should equal(true)
      res.properties().size() should equal(0)
    })
  }

  it should "create vertex type by label with properties" in {
    notTx(g => {
      g.dsl createVertexType "Person" withProperty "name" -> STRING and "active" -> BOOLEAN
    })

    tx(g => {
      val res = g.getVertexType("Person")

      res should not be null
      res.isVertexType should equal(true)
      res.properties().size() should equal(2)
      res.properties().asScala.count(prop => prop.getName.equals("name") && prop.getType.eq(STRING)) should equal(1)
      res.properties().asScala.count(prop => prop.getName.equals("active") && prop.getType.eq(BOOLEAN)) should equal(1)
    })
  }

  it should "create vertex type by label with unique index" in {
    notTx(g => {
      g.dsl createVertexType "City" withProperty "name" -> STRING unique "name"
    })

    tx(g => {
      val res = g.getVertexType("City")

      res should not be null
      res.isVertexType should equal(true)
      res.properties().size() should equal(1)
      res.properties().asScala.count(prop => prop.getName.equals("name") && prop.getType.eq(STRING)) should equal(1)

      res.getClassIndexes.asScala.size should equal(1)
      res.getClassIndexes.asScala.count(index => index.getName.startsWith("name-unique")) should equal(1)
    })
  }
}
