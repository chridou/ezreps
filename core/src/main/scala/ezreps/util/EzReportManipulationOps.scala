package ezreps.util

import ezreps.ast

trait EzReportManipulationOps {
  def self: ast.EzReportValue

  def add(field: ast.EzField): ast.EzReportValue =
    ast.EzReportValue(self.fields :+ field)

  def addMany(fields: ast.EzField*): ast.EzReportValue =
    ast.EzReportValue(self.fields ++ fields)

  def ~(field: ast.EzField): ast.EzReportValue =
    ast.EzReportValue(self.fields :+ field)

  def ~~(fields: Iterable[ast.EzField]): ast.EzReportValue =
    ast.EzReportValue(self.fields ++ fields)

  def withReportName(name: String): ast.EzReportValue =
    ast.EzReportValue(self.fields :+ ast.EzField("report-name", ast.EzString(name)))

  def createdOnUtc(now: java.time.LocalDateTime): ast.EzReportValue =
    ast.EzReportValue(self.fields :+ ast.EzField("report-created-on-utc", ast.EzLocalDateTime(now)))

  def createdOn(now: java.time.ZonedDateTime): ast.EzReportValue =
    ast.EzReportValue(self.fields :+ ast.EzField("report-created-on", ast.EzZonedDateTime(now)))

  def currentlyIAm(doing: String): ast.EzReportValue =
    ast.EzReportValue(self.fields :+ ast.EzField("currently-i-am", ast.EzString(doing)))

  def born(when: java.time.ZonedDateTime): ast.EzReportValue =
    ast.EzReportValue(self.fields :+ ast.EzField("date-of-birth", ast.EzZonedDateTime(when)))

  def bornUtc(when: java.time.LocalDateTime): ast.EzReportValue =
    ast.EzReportValue(self.fields :+ ast.EzField("date-of-birth-utc", ast.EzLocalDateTime(when)))

  def age(duration: java.time.Duration): ast.EzReportValue =
    ast.EzReportValue(self.fields :+ ast.EzField("age", ast.EzDuration(duration)))

  def subReport(label: String, subFields: ast.EzField*): ast.EzReportValue =
    ast.EzReportValue(self.fields :+ ast.EzField(label, ast.EzReportValue(subFields.toVector))) 
    
  def removeNotAvailable: ast.EzReportValue =
    ast.EzReportValue(self.fields.filter {
      case ast.EzField(_, ast.EzNotAvailable) ⇒ false
      case _                                  ⇒ true

    })
}
