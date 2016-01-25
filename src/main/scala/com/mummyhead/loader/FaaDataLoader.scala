package com.mummyhead.loader

import com.mummyhead.model.FaaModel
import scala.collection.mutable
import scala.collection.mutable.Map

/**
  * Created by chris on 1/15/16.
  */
class FaaDataLoader {

  def loadFaaData() : Map[String,FaaModel] = {
    val faaData = new mutable.HashMap[String,FaaModel]()

    val bufferedSource = io.Source.fromURL(getClass.getResource("/NfdcFacilities.csv"))
    for( line <- bufferedSource.getLines()){
      val cols = line.split('\t').map(_.trim)
      val model = new FaaModel(cols(2).replace("'",""), cols(11),cols(21))
      faaData.put(model.airportCode, model)
    }
    return faaData
  }

}
