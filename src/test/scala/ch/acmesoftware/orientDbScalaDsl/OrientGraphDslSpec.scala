package ch.acmesoftware.orientDbScalaDsl

class OrientGraphDslSpec extends Spec {

  "OrientGraphDsl" should "find existing vertices by label" in {
    tx(g => {
      g.dsl addVertex "City"
    })

    tx(g => {
      val res1 = g.dsl findVertices "City" single ()
      res1.isDefined should equal(true)

      val res2 = g.dsl findVertices "City" list ()
      res2.size should equal(1)
    })
  }

  it should "find existing verticews by label and properties" in {
    tx(g => {
      g.dsl addVertex "City" withProperty "name" -> "Zurich" and "zip" -> 8000
    })

    tx(g => {
      val res1 = g.dsl findVertices "City" filter "name" -> "Zurich" filter "zip" -> 8000 list ()
      res1.size should equal(1)

      val res2 = g.dsl findVertices "City" filter "name" -> "Zurich" filter "zip" -> 8000 single ()
      res2.isDefined should equal(true)
    })
  }
}
