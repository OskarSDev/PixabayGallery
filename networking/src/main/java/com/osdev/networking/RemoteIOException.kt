package com.osdev.networking

import java.io.IOException

class RemoteIOException(
    val code: Int,
    override val message: String
) : IOException(message)