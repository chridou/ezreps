package ezreps

package json4sx


import org.json4s._

/**
 * @author douven
 */
object SerializeJson4s {
 def serializeAst(what: ezreps.ast.EzValue): JValue =
    what match {
      case ezreps.ast.EzString(value)         ⇒ JString(value)
      case ezreps.ast.EzInteger(value)        ⇒ JInt(value)
      case ezreps.ast.EzFloat(value)          ⇒ JDouble(value)
      case ezreps.ast.EzBool(value)           ⇒ JBool(value)
      case ezreps.ast.EzLocalDateTime(value)  ⇒ JString(value.toString)
      case ezreps.ast.EzZonedDateTime(value)  ⇒ JString(value.toString)
      case ezreps.ast.EzDuration(value)       ⇒ JString(value.toString)
      case ezreps.ast.EzError(message)        ⇒ JString(s"ERROR: $message")
      case ezreps.ast.EzNotAvailable          ⇒ JString("N/A")
      case ezreps.ast.EzReportValue(fields) ⇒
        JObject(fields.map(field ⇒ JField(field.label, serializeAst(field.value))): _*)
    } 
}