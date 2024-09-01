package com.codingtroops.foodies.model

import com.google.gson.annotations.SerializedName


data class WeatherForecast (

  @SerializedName("request"  ) var request  : Request?  = Request(),
  @SerializedName("location" ) var location : Location? = Location(),
  @SerializedName("current"  ) var current  : Current?  = Current()

)