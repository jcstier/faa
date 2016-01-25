package com.mummyhead.httpservice

import akka.actor.Actor
import akka.event.{Logging, LoggingAdapter}
import com.mummyhead.loader.FaaDataLoader
import com.mummyhead.model.FaaModelProtocol._
import com.mummyhead.search.FaaDataSearch
import spray.http.MediaTypes
import spray.json._
import spray.routing._

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class FaaServiceActor extends Actor with FaaService {
  var log: LoggingAdapter = Logging.getLogger(context.system, classOf[FaaServiceActor])

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(searchRoute)


}


// this trait defines our service behavior independently from the service actor
trait FaaService extends HttpService {

  val faaData = new FaaDataLoader().loadFaaData()
  val search = new FaaDataSearch

  val lookupRoute =
    path("code" / Segment) { (code) =>
      get {
        respondWithMediaType(MediaTypes.`application/json`) {
          complete {
            faaData(code).toJson.toString()
          }
        }
      }
    }

  val searchRoute =
    path("search" / Segment) { (searchTerm) =>
      get {
        respondWithMediaType(MediaTypes.`application/json`) {
          complete {
            search.bruteForce(searchTerm,faaData).toJson.toString()
          }
        }
      }
    }
}

