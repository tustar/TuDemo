package com.tustar.demo.data.remote

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    val status: String,
    val query: String,
    val places: List<Place>
)

data class Place(
    val id: String,
    val name: String,
    @SerializedName("formatted_address")
    val address: String,
    val location: Location,
    @SerializedName("place_id")
    val placeId: String
)

data class Location(
    val lat: Double,
    val lng: Double
)