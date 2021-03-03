package me.humboldt123.thunderhead.command.commands

import me.humboldt123.thunderhead.command.Command
import me.humboldt123.thunderhead.util.MessageUtil
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.VoiceChannel
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.managers.AudioManager

@Suppress("Unused")
class Music : Command("music", "play music", listOf("[name]"), 1, listOf()) {
    override fun execute(args: List<String>, event: GuildMessageReceivedEvent) {
        val guild: Guild = event.guild
        val voiceChannel: VoiceChannel? = event.member?.voiceState?.channel
        val audioManager: AudioManager = guild.audioManager
        if (args.isEmpty()) {
            event.message.channel.sendMessage(MessageUtil.error("Please provide at least 1 argument!"))
        }
        if (voiceChannel == null) {
            event.message.channel.sendMessage(MessageUtil.error("You are not in a voice channel!"))
        }
        audioManager.openAudioConnection(voiceChannel)
        event.message.channel.sendMessage("Playing music...").queue()
        Thread.sleep(5000)
        audioManager.closeAudioConnection()
    }
}