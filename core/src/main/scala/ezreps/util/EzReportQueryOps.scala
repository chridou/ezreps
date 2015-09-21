package ezreps.util


import ezreps.ast

trait EzReportQueryOps {
  def self: ast.EzReportValue
  def contains(label: String): Boolean = get(label) != ast.EzNotAvailable

  def get(label: String): ast.EzValue = self.fields.find { _.label == label }.map(_.value) getOrElse ast.EzNotAvailable

  @scala.annotation.tailrec
  final def getByPath(path: List[String]): ast.EzValue =
    path match {
      case Nil ⇒
        ast.EzNotAvailable
      case label :: Nil ⇒
        get(label)
      case label :: rest ⇒
        get(label) match {
          case r: ast.EzReportValue ⇒
            getByPath(rest)
          case _ ⇒ ast.EzNotAvailable
        }
    }
}