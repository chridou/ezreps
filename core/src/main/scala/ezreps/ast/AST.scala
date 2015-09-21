package ezreps.ast

sealed trait EzValue
sealed trait EzBasicValue extends EzValue

final case class EzString(value: String) extends EzBasicValue
final case class EzInteger(value: Long) extends EzBasicValue
final case class EzFloat(value: Double) extends EzBasicValue
final case class EzBool(value: Boolean) extends EzBasicValue
final case class EzLocalDateTime(value: java.time.LocalDateTime) extends EzBasicValue
final case class EzZonedDateTime(value: java.time.ZonedDateTime) extends EzBasicValue
final case class EzDuration(value: java.time.Duration) extends EzBasicValue
final case class EzError(message: String) extends EzBasicValue
case object EzNotAvailable extends EzBasicValue

final case class EzField(label: String, value: EzValue)

final case class EzReportValue(fields: Vector[EzField]) extends EzValue

object EzReportValue {
  val empty: EzReportValue = EzReportValue(Vector.empty)
  def apply(): EzReportValue = empty
}