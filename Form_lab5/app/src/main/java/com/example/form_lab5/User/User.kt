package com.example.form_lab5.model

import java.io.Serializable

data class User(
    val fullName: String,
    val email: String,
    val password: String,
    val phone: String
) : Serializable