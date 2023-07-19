package com.osdev.persistence.exceptions

class MapperException() : RuntimeException() {
    override val message: String
        get() = "Ups... This photo is invalid"
}