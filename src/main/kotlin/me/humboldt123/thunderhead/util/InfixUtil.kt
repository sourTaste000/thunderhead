package me.humboldt123.thunderhead.util

object InfixUtil {
    operator fun String.times(factor: Int) = this.repeat(factor)
}