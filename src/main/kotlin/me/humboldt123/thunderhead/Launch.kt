package me.humboldt123.thunderhead

object Launch {
    @JvmStatic
    fun main(args: Array<String>) {
        val thunderhead = Instance(Settings.TOKEN)
        thunderhead.start()
    }
}