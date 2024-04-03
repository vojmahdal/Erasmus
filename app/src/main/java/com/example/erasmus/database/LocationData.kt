package com.example.erasmus.database

data class LocationData(
    var locationName: String? = null,
    var locationCountry: String? = null,
    var locationCity: String? = null,
    var locationGps: String? = null,
    var createdBy: String? = null
)
