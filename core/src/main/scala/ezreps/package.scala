

package object ezreps extends util.EzFuns {
  type EzReport = ast.EzReportValue
  val EzReport = ast.EzReportValue

  type EzFields = Vector[ast.EzField]

  object Implicits extends util.EzValueIdentityConverters with util.EzValueConverters with util.EzValueOptionConverters

  implicit class EzReportOpsInst(val self: ast.EzReportValue) extends util.EzReportManipulationOps with util.EzReportQueryOps

  implicit class EzValueOpsInst(val self: ast.EzValue) extends util.EzValueOps 
}