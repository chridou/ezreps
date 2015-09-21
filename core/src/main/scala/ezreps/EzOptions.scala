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
final case class EzOptions(noNoise: Boolean, excludeNotAvailable: Boolean, includeFields: Set[String], excludeFields: Set[String])

object EzOptions {
  private val noNoiseFields = Set(
    "report-created-on-utc",
    "report-created-on",
    "currently-i-am",
    "date-of-birth",
    "date-of-birth-utc",
    "actor-path",
    "age")

  val everything = EzOptions(false, false, Set.empty, Set.empty)
  val noNoise = EzOptions(true, false, Set.empty, noNoiseFields)

  def makeWithDefaults(noNoise: Boolean, excludeNotAvailable: Boolean): EzOptions = {
    val a = if (excludeNotAvailable) everything.setExculdeNotAvailable else everything
    if (noNoise) a.setNoNoiseExcludeDefaults else a
  }

  implicit class EzOptionsOpts(val self: EzOptions) extends AnyVal {
    def setExculdeNotAvailable: EzOptions = EzOptions(self.noNoise, true, self.includeFields, self.excludeFields)
    def setNoNoise: EzOptions = EzOptions(true, self.excludeNotAvailable, self.includeFields, self.excludeFields)
    def setNoNoiseExcludeDefaults: EzOptions = EzOptions(true, self.excludeNotAvailable, self.includeFields, self.excludeFields.union(noNoiseFields))

    def isIncluded(fieldName: String): Boolean =
      if (self.includeFields(fieldName))
        true
      else
        !self.excludeFields(fieldName)

    def filter(report: EzReport): EzReport =
      EzReportValue(report.fields.filter { field ⇒ isIncluded(field.label) && (!self.excludeNotAvailable || field.value != ast.EzNotAvailable) }.map {
        case ast.EzField(label, subReport: ast.EzReportValue) ⇒ ast.EzField(label, filter(subReport))
        case x                                         ⇒ x
      })
  }
}