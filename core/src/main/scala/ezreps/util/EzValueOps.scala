package ezreps.util

import ezreps.ast

trait EzValueOps {
  def self: ast.EzValue
  def /(label: String): ast.EzValue =
    self match {
      case r: ast.EzReportValue ⇒ r.get(label)
      case _                    ⇒ ast.EzNotAvailable
    }
}