package com.example.oompaloompasapp.oompaloompa.domain

data class OompaLoompa(
    val id: String,
    val first_name: String,
    val last_name: String,
    val country: String,
    val age: Int,
    val description: String,
    val image: String,
    val profession: String,
    val quota: String,
    val height: Int,
    val gender: String,
    val email: String,
    val favorite: Favorite
)

data class Favorite(
    val color: String,
    val food: String,
    val random_string: String,
    val song: String
)
