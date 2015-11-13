package ezreps

package util

import java.time.{ LocalDateTime, ZonedDateTime }
import ezreps.ast._

trait EzValueConverter[T] {
  def convert(value: T): ezreps.ast.EzValue
}

trait EzValueIdentityConverters {
  implicit val RValueIdentityConverterStringInst: EzValueConverter[ast.EzString] = new EzValueConverter[ast.EzString] {
    def convert(value: ast.EzString): ast.EzValue = value
  }
  implicit val RValueIdentityConverterIntInst: EzValueConverter[ast.EzInteger] = new EzValueConverter[ast.EzInteger] {
    def convert(value: ast.EzInteger): ast.EzValue = value
  }
  implicit val RValueIdentityConverterFloatInst: EzValueConverter[ast.EzFloat] = new EzValueConverter[ast.EzFloat] {
    def convert(value: ast.EzFloat): ast.EzValue = value
  }
  implicit val RValueIdentityConverterBooleanInst: EzValueConverter[ast.EzBool] = new EzValueConverter[ast.EzBool] {
    def convert(value: ast.EzBool): ast.EzValue = value
  }
  implicit val RValueIdentityConverterLocalDateTimeInst: EzValueConverter[ast.EzLocalDateTime] = new EzValueConverter[ast.EzLocalDateTime] {
    def convert(value: ast.EzLocalDateTime): ast.EzValue = value
  }
  implicit val RValueIdentityConverterZonedDateTimeInst: EzValueConverter[ast.EzZonedDateTime] = new EzValueConverter[ast.EzZonedDateTime] {
    def convert(value: ast.EzZonedDateTime): ast.EzValue = value
  }
  implicit val RValueIdentityConverterDurationInst: EzValueConverter[ast.EzDuration] = new EzValueConverter[ast.EzDuration] {
    def convert(value: ast.EzDuration): ast.EzValue = value
  }
  implicit val RValueIdentityConverterErrorInst: EzValueConverter[ast.EzError] = new EzValueConverter[ast.EzError] {
    def convert(value: ast.EzError): ast.EzValue = value
  }

  implicit val RValueIdentityConverterCollectionInst: EzValueConverter[ast.EzCollection] = new EzValueConverter[ast.EzCollection] {
    def convert(value: ast.EzCollection): ast.EzValue = value
  }

  
  implicit val RValueIdentityConverterReportInst: EzValueConverter[ast.EzReportValue] = new EzValueConverter[ast.EzReportValue] {
    def convert(value: ast.EzReportValue): ast.EzValue = value
  }
}

trait EzValueConverters {
  implicit val EzValueConverterStringInst: EzValueConverter[String] = new EzValueConverter[String] {
    def convert(value: String): ast.EzValue = ast.EzString(value)
  }
  implicit val EzValueConverterIntInst: EzValueConverter[Int] = new EzValueConverter[Int] {
    def convert(value: Int): ast.EzValue = ast.EzInteger(value.toLong)
  }
  implicit val EzValueConverterLongInst: EzValueConverter[Long] = new EzValueConverter[Long] {
    def convert(value: Long): ast.EzValue = ast.EzInteger(value)
  }
  implicit val EzValueConverterFloatInst: EzValueConverter[Float] = new EzValueConverter[Float] {
    def convert(value: Float): ast.EzValue = ast.EzFloat(value.toDouble)
  }
  implicit val EzValueConverterDoubleInst: EzValueConverter[Double] = new EzValueConverter[Double] {
    def convert(value: Double): ast.EzValue = ast.EzFloat(value)
  }
  implicit val EzValueConverterBooleanInst: EzValueConverter[Boolean] = new EzValueConverter[Boolean] {
    def convert(value: Boolean): ast.EzValue = ast.EzBool(value)
  }
  implicit val EzValueConverterLocalDateTimeInst: EzValueConverter[LocalDateTime] = new EzValueConverter[LocalDateTime] {
    def convert(value: LocalDateTime): ast.EzValue = ast.EzLocalDateTime(value)
  }
  implicit val EzValueConverterZonedDateTimeInst: EzValueConverter[ZonedDateTime] = new EzValueConverter[ZonedDateTime] {
    def convert(value: ZonedDateTime): ast.EzValue = ast.EzZonedDateTime(value)
  }
  implicit val EzValueConverterConcDurationInst: EzValueConverter[scala.concurrent.duration.FiniteDuration] = new EzValueConverter[scala.concurrent.duration.FiniteDuration] {
    def convert(value: scala.concurrent.duration.FiniteDuration): ast.EzValue = ast.EzDuration(java.time.Duration.of(value.toMicros, java.time.temporal.ChronoUnit.MICROS)  )
  }
  implicit val EzValueConverterDurationInst: EzValueConverter[java.time.Duration] = new EzValueConverter[java.time.Duration] {
    def convert(value: java.time.Duration): ast.EzValue = ast.EzDuration(value)
  }
}

trait EzValueOptionConverters { self: EzValueConverters with EzValueIdentityConverters ⇒
  private def createOptionSomeConverterWrapperInst[T: EzValueConverter]: EzValueConverter[Option[T]] = new EzValueConverter[Option[T]] {
    def convert(value: Option[T]): ast.EzValue =
      value match {
        case Some(t) ⇒ ezreps.toAST(t)
        case None    ⇒ ast.EzNotAvailable
      }

  }

  implicit val OptionRValueIdentityConverterStringInst: EzValueConverter[Option[ast.EzString]] = createOptionSomeConverterWrapperInst[ast.EzString]
  implicit val OptionRValueIdentityConverterIntInst: EzValueConverter[Option[ast.EzInteger]] = createOptionSomeConverterWrapperInst[ast.EzInteger]
  implicit val OptionRValueIdentityConverterFloatInst: EzValueConverter[Option[ast.EzFloat]] = createOptionSomeConverterWrapperInst[ast.EzFloat]
  implicit val OptionRValueIdentityConverterBooleanInst: EzValueConverter[Option[ast.EzBool]] = createOptionSomeConverterWrapperInst[ast.EzBool]
  implicit val OptionRValueIdentityConverterLocalDateTimeInst: EzValueConverter[Option[ast.EzLocalDateTime]] = createOptionSomeConverterWrapperInst[ast.EzLocalDateTime]
  implicit val OptionRValueIdentityConverterZonedDateTimeInst: EzValueConverter[Option[ast.EzZonedDateTime]] = createOptionSomeConverterWrapperInst[ast.EzZonedDateTime]
  implicit val OptionRValueIdentityConverterDurationInst: EzValueConverter[Option[ast.EzDuration]] = createOptionSomeConverterWrapperInst[ast.EzDuration]
  implicit val OptionRValueIdentityConverterErrorInst: EzValueConverter[Option[ast.EzError]] = createOptionSomeConverterWrapperInst[ast.EzError]
  implicit val OptionRValueIdentityConverterReportInst: EzValueConverter[Option[ast.EzReportValue]] = createOptionSomeConverterWrapperInst[ast.EzReportValue]

  implicit val OptionRVAlueConverterStringInst: EzValueConverter[Option[String]] = createOptionSomeConverterWrapperInst[String]
  implicit val OptionRVAlueConverterIntInst: EzValueConverter[Option[Int]] = createOptionSomeConverterWrapperInst[Int]
  implicit val OptionRVAlueConverterLongInst: EzValueConverter[Option[Long]] = createOptionSomeConverterWrapperInst[Long]
  implicit val OptionRVAlueConverterFloatInst: EzValueConverter[Option[Float]] = createOptionSomeConverterWrapperInst[Float]
  implicit val OptionRVAlueConverterDoubleInst: EzValueConverter[Option[Double]] = createOptionSomeConverterWrapperInst[Double]
  implicit val OptionRVAlueConverterrBooleanInst: EzValueConverter[Option[Boolean]] = createOptionSomeConverterWrapperInst[Boolean]
  implicit val OptionRVAlueConverterLocalDateTimeInst: EzValueConverter[Option[LocalDateTime]] = createOptionSomeConverterWrapperInst[LocalDateTime]
  implicit val OptionRVAlueConverterZonedDateTimeInst: EzValueConverter[Option[ZonedDateTime]] = createOptionSomeConverterWrapperInst[ZonedDateTime]
  implicit val OptionRVAlueConverterDurationInst: EzValueConverter[Option[scala.concurrent.duration.FiniteDuration]] = createOptionSomeConverterWrapperInst[scala.concurrent.duration.FiniteDuration]
  implicit val OptionRVAlueConverterJDurationInst: EzValueConverter[Option[java.time.Duration]] = createOptionSomeConverterWrapperInst[java.time.Duration]
}