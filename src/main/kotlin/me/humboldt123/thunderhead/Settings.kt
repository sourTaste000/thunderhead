package me.humboldt123.thunderhead

import java.io.File

object Settings {
    val TOKEN = File("token.txt").readText(Charsets.UTF_8)
    val PREFIX = "."
}