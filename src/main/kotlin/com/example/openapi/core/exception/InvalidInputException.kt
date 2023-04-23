package com.example.openapi.core.exception

class InvalidInputException(
    message: String = "Invalid Input"
) : RuntimeException(message)