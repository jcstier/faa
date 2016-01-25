package com.mummyhead.search

import com.mummyhead.model.FaaModel

import scala.collection.mutable
import scala.collection.mutable.Map

/**
  * Created by chris on 1/19/16.
  */
class FaaDataSearch {

  def bruteForce(searchTerm: String, data: Map[String, FaaModel]):List[FaaModel] = {
    val results = mutable.MutableList[FaaModel]()

    data.foreach {keyVal => {
      if(keyVal._2.name.contains(searchTerm.toUpperCase) || keyVal._2.airportCode.contains(searchTerm.toUpperCase)){
        results += keyVal._2
      }
    }}
    results.toList;
  }

}
