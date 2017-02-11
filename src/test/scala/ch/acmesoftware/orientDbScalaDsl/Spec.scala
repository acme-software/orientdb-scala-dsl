package ch.acmesoftware.orientDbScalaDsl

import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal
import com.tinkerpop.blueprints.impls.orient.{OrientGraph, OrientGraphFactory, OrientGraphNoTx}
import org.scalatest.{FlatSpec, Matchers}

private[orientDbScalaDsl] trait Spec extends FlatSpec with Matchers {

  // Fixing really strange vendor issue: https://github.com/orientechnologies/orientdb/issues/5146
  ODatabaseRecordThreadLocal.INSTANCE

  val graphFactory = new OrientGraphFactory("memory:orientDbScalaDslTest")

  protected def tx[T](f: OrientGraph => T) = {
    val tx = graphFactory.getTx
    try {
      f(tx)
    } finally {
      tx.shutdown()
    }
  }

  protected def notTx[T](f: OrientGraphNoTx => T) = {
    val noTx = graphFactory.getNoTx
    try {
      f(noTx)
    } finally {
      noTx.shutdown()
    }
  }
}
