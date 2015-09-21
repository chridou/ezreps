package ezreps.util

import ezreps.ast

trait EzFuns {
  def toAST[T](what: T)(implicit converter: EzValueConverter[T]): ast.EzValue = converter.convert(what)

  implicit def toField[T: EzValueConverter](v: (String, T)): ast.EzField = ast.EzField(v._1, toAST(v._2))

}