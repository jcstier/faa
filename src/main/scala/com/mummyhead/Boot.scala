package com.mummyhead

import akka.actor.{ActorSystem, Props}
import akka.event.Logging
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import com.mummyhead.httpservice.FaaServiceActor
import com.mummyhead.loader.FaaDataLoader
import spray.can.Http

import scala.concurrent.duration._

object Boot extends App {

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")

  val log = Logging(system, getClass)
  log.info("Faa Service app is starting!!!")

  log.info("Loading FAA data")

  // create and start our service actor
  val service = system.actorOf(Props[FaaServiceActor], "faa-service")

  implicit val timeout = Timeout(5.seconds)
  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)
}
