package com.mummyhead.model

import spray.json._

/**
  * Created by chris on 12/29/15.
  */


case class FaaModel(
                     airportCode: String,
                     name: String,
                     phone: String
                   )

object FaaModelProtocol extends DefaultJsonProtocol {
  implicit val format = jsonFormat3(FaaModel)
}