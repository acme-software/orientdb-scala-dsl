OrientDB Scala DSL
==================

**It's a Scala DSL for OrientDb...** 

[![Build Status](https://travis-ci.org/acme-software/orientdb-scala-dsl.svg?branch=master)](https://travis-ci.org/acme-software/orientdb-scala-dsl)
[![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/acme-software/orientdb-scala-dsl/master/LICENSE)
![Scala 2.11](https://img.shields.io/badge/scala-2.11-lightgrey.svg)
![Scala 2.12](https://img.shields.io/badge/scala-2.12-lightgrey.svg)

*Disclaimer:*
*Neiher this code, documentation nor any part of this repository is officially connected/related to 
[orientdb.com](http://orientdb.com/) or one of their services. This is an open source project wrapping the OrientDb Java
driver to provide better compatibility to Scala.*
*For any questions regarding the underlying Java driver or professional services, please refer to 
[OrientDB Offical](http://orientdb.com/)*

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

### Vertex

```scala
// things needed from java driver
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
import com.orientechnologies.orient.core.metadata.schema.OType._

// java converters for collections
import scala.collection.JavaConverters._

// scala dsl import
import ch.acmesoftware.orientDbScalaDsl._

val g = new OrientGraphFactory("memory:orientDbScalaDslTest").getNoTx

// add vertex
g.dsl addVertex "Person" withProperty "name" -> "Frank"
g.dsl addVertex "Customer" withProperty "name" -> "ACME" and "active" -> true

// find & filter
g.dsl findVertices "City" single()
g.dsl findVertices "City" filter "name" -> "Zurich" filter  "zip" -> 8000 single() // Option[VertexDsl]
g.dsl findVertices "City" filter "name" -> "Zurich" list() // Iterable[VertexDsl]
g.dsl findVertices "City" filter "name" -> "Zurich" list() take 3 // Iterable[VertexDsl] (first 3)

// edit existing
val existing = g.dsl findVertices "Customer" single()
existing foreach(_ withProperty "name" -> "ACME Software Solutions" and "year" -> 2017)
```

Get Involved
------------

Please use the GitHub issue tracker to file bugs or place pull-requests. Any commitment is highly appreciated.

### License

This project is licensed under MIT. Please refere to [LICENSE](LICENSE) file...

### Contributors

* [Frank Neff](https://github.com/frne)
* [Marco Wüthrich](https://github.com/marcow93)

*Supported by [ACME Software Solutions GmbH](https://github.com/acme-software)*
