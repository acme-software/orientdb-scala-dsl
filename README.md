OrientDB Scala DSL
==================

**It's a Scala DSL for OrientDb...**

> Disclaimer: 
> Neiher this code, documentation nor any part of this repository is officially connected/related to 
[orientdb.com](http://orientdb.com/) or one of their services. This is an open source project wrapping the OrientDb Java
driver to provide better compatibility to Scala.
> For any questions regarding the underlying Java driver or professional services, please refer to 
[OrientDB Offical](http://orientdb.com/)

Usage
-----

SBT dependency:

```scala
libraryDependencies += "ch.acmesoftware" %% "orientdb-scala-dsl" % "VERSION"
```

The library provides a `.dsl()` method on different Orient-Objects. Just import the wrappers:

```scala
import ch.acmesoftware.orientDbScalaDsl._
```

*For now, the lib is unpublished, so you have to check it out localy using git and run:*

```shell
sbt publishLocal
```

Examples
--------

### Vertex Type

```scala
// things needed from java driver
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
import com.orientechnologies.orient.core.metadata.schema.OType._

// scala dsl import
import ch.acmesoftware.orientDbScalaDsl._

val g = new OrientGraphFactory("memory:orientDbScalaDslTest").getNoTx

// create new vertex type
g.dsl createVertexType "SomeLabel"

// ...with properties
g.dsl createVertexType "Person" withProperty "name" -> STRING
g.dsl createVertexType "User" withProperty "name" -> STRING and "active" -> BOOLEAN

// ...and indexes
g.dsl createVertexType "City" withProperty "name" -> STRING and "zip" -> INTEGER unique "name" unique "zip"

// edit existing vertex type
g.getVertexType("Person").dsl withProperty "age" -> INTEGER

```

Contribution
------------

Please use the GitHub Issue tracker to file bugs or place pull-requests. Any commitment is very welcome.

Authors
------------

* Frank Neff
* Marco Wüthrich

License
-------

This project is licensed under MIT. Please refere to [LICENSE](LICENSE) file...
