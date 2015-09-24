package ezreps
package util

import ezreps.ast

object NumericBinOps {
  def floatOp[T: EzValueConverter](a: ast.EzValue, b: ast.EzValue)(op: (Double, Double) ⇒ T): ast.EzValue = {
    (a, b) match {
      case (ast.EzError(_), _)                 ⇒ ast.EzError("No operation when the first operator is an error.")
      case (_, ast.EzError(_))                 ⇒ ast.EzError("No operation when the second operator is an error.")
      case (ast.EzNotAvailable, _)             ⇒ ast.EzNotAvailable
      case (_, ast.EzNotAvailable)             ⇒ ast.EzNotAvailable
      case (ast.EzFloat(a), ast.EzFloat(b))     ⇒ toAST(op(a, b))
      case (ast.EzInteger(a), ast.EzFloat(b))   ⇒ toAST(op(a.toDouble, b))
      case (ast.EzFloat(a), ast.EzInteger(b))   ⇒ toAST(op(a, b.toDouble))
      case (ast.EzInteger(a), ast.EzInteger(b)) ⇒ toAST(op(a.toDouble, b.toDouble))
      case _                                  ⇒ ast.EzError("Incompatible types.")
    }
  }

  def intOp[T: EzValueConverter](a: ast.EzValue, b: ast.EzValue)(op: (Long, Long) ⇒ T): ast.EzValue = {
    (a, b) match {
      case (ast.EzError(_), _)                 ⇒ ast.EzError("No operation when the first operator is an error.")
      case (_, ast.EzError(_))                 ⇒ ast.EzError("No operation when the second operator is an error.")
      case (ast.EzNotAvailable, _)             ⇒ ast.EzNotAvailable
      case (_, ast.EzNotAvailable)             ⇒ ast.EzNotAvailable
      case (ast.EzFloat(a), ast.EzFloat(b))     ⇒ toAST(op(a.toLong, b.toLong))
      case (ast.EzInteger(a), ast.EzFloat(b))   ⇒ toAST(op(a, b.toLong))
      case (ast.EzFloat(a), ast.EzInteger(b))   ⇒ toAST(op(a.toLong, b))
      case (ast.EzInteger(a), ast.EzInteger(b)) ⇒ toAST(op(a, b))
      case _                                  ⇒ ast.EzError("Incompatible types.")
    }
  }
}