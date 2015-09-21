

package object ezreps {
  type EzReport = ast.EzReportValue

  type EzFields = Vector[ast.EzField]


  object Implicits extends util.EzValueIdentityConverters with util.EzValueConverters with util.EzValueOptionConverters

  def toAST[T](what: T)(implicit converter: util.EzValueConverter[T]): ast.EzValue = converter.convert(what)


  implicit def toField[T: util.EzValueConverter](v: (String, T)): ast.EzField = ast.EzField(v._1, toAST(v._2))


  implicit class StatusReportManipulationOps(val self: ast.EzReportValue) extends AnyVal {
    def add(field: ast.EzField): ast.EzReportValue =
      ast.EzReportValue(self.fields :+ field)

    def addMany(fields: ast.EzField*): ast.EzReportValue =
      self ~~ fields

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
      
    def removeNotAvailable: ast.EzReportValue =
      ast.EzReportValue(self.fields.filter {
        case ast.EzField(_, ast.EzNotAvailable) ⇒ false
        case _                                ⇒ true

      })
  }

  implicit class RValueQueryOps(val self: ast.EzValue) extends AnyVal {
    def /(label: String): ast.EzValue =
      self match {
        case r: ast.EzReportValue ⇒ r.get(label)
        case _              ⇒ ast.EzNotAvailable
      }
  }

  implicit class StatusReportQueryOps(val self: ast.EzReportValue) extends AnyVal {
    def contains(label: String): Boolean = get(label) != ast.EzNotAvailable

    def get(label: String): ast.EzValue = self.fields.find { _.label == label }.map(_.value) getOrElse ast.EzNotAvailable

    @scala.annotation.tailrec
    def getByPath(path: List[String]): ast.EzValue =
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
  
}