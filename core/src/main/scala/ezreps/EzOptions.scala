package ezreps

import ast._
/**
 * Options for status reports
 *
 * IncludeFields has a higher priority than excludeFields and noNoise.
 *
 *
 * @author douven
 */
final case class EzOptions(noNoise: Boolean, excludeNotAvailable: Boolean, includeFields: Set[String], excludeFields: Set[String], args: Map[String, Option[String]])

object EzOptions {
  def apply(noNoise: Boolean, excludeNotAvailable: Boolean, includeFields: Set[String], excludeFields: Set[String]): EzOptions =
    EzOptions(noNoise, excludeNotAvailable, includeFields, excludeFields, Map.empty)

  def apply(noNoise: Boolean, excludeNotAvailable: Boolean, excludeFields: Set[String]): EzOptions =
    EzOptions(noNoise, excludeNotAvailable, Set.empty, excludeFields, Map.empty)

  def apply(noNoise: Boolean, excludeNotAvailable: Boolean): EzOptions =
    EzOptions(noNoise, excludeNotAvailable, Set.empty, Set.empty, Map.empty)

  def apply(noNoise: Boolean): EzOptions =
    EzOptions(noNoise, false, Set.empty, Set.empty, Map.empty)

  def apply(): EzOptions =
    EzOptions(false, true, Set.empty, Set.empty, Map.empty)

  private val noNoiseFields = Set(
    "report-created-on-utc",
    "report-created-on",
    "currently-i-am",
    "date-of-birth",
    "date-of-birth-utc",
    "configuration",
    "config",
    "actor-path",
    "age")

  val everything = EzOptions(false, false, Set.empty, Set.empty, Map.empty)
  val noNoise = EzOptions(true, false, Set.empty, noNoiseFields, Map.empty)

  def makeWithDefaults(noNoise: Boolean, excludeNotAvailable: Boolean): EzOptions = {
    val a = if (excludeNotAvailable) everything.setExculdeNotAvailable else everything
    if (noNoise) a.setNoNoiseExcludeDefaults else a
  }

  implicit class EzOptionsOpts(val self: EzOptions) extends AnyVal {
    def setExculdeNotAvailable: EzOptions = EzOptions(self.noNoise, true, self.includeFields, self.excludeFields, self.args)
    def setNoNoise: EzOptions = EzOptions(true, self.excludeNotAvailable, self.includeFields, self.excludeFields, self.args)
    def setNoNoiseExcludeDefaults: EzOptions = EzOptions(true, self.excludeNotAvailable, self.includeFields, self.excludeFields.union(noNoiseFields), self.args)
    def withArgs(args: (String, Option[String])*) = self.copy(args = self.args ++ args)

    def isIncluded(fieldName: String): Boolean =
      if (self.includeFields(fieldName))
        true
      else
        !self.excludeFields(fieldName)

    def filter(report: EzReport): EzReport =
      EzReportValue(report.fields.filter { field ⇒ isIncluded(field.label) && (!self.excludeNotAvailable || field.value != ast.EzNotAvailable) }.map {
        case ast.EzField(label, subReport: ast.EzReportValue) ⇒ ast.EzField(label, filter(subReport))
        case x ⇒ x
      })
  }
}