package com.tikay.noteappkmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform