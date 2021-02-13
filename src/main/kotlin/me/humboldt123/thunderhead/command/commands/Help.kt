package me.humboldt123.thunderhead.command.commands

import me.humboldt123.thunderhead.Settings
import me.humboldt123.thunderhead.command.Command
import me.humboldt123.thunderhead.command.CommandManager
import me.humboldt123.thunderhead.info.ColorInfo
import me.humboldt123.thunderhead.info.HelpInfo
import me.humboldt123.thunderhead.util.MessageDSL
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

@Suppress("Unused")
class Help : Command("help", "A list of commands.", listOf("[command?]"), 1, listOf("commands")) {
    override fun execute(args: List<String>, event: GuildMessageReceivedEvent) {
        when (args.size) {
            0 -> event.message.channel.sendMessage(getCommandListEmbed(1)).queue()
            else -> {
                val cmd: Int? = args[0].let { it.toIntOrNull() }
                if (cmd == null) {
                    event.message.channel.sendMessage(getCommandEmbed(getCommand(args[0]))).queue()
                } else {
                    event.message.channel.sendMessage(getCommandListEmbed(cmd)).queue()
                }
            }
        }
    }

    fun getCommandEmbed(command: Command?): MessageEmbed {
        return if (command != null)
            EmbedBuilder()
            .setTitle(command.name)
            .setColor(ColorInfo.discord)
            .setDescription(
                "**Description:** ${command.description} " +
                        if (command.syntax.isNotEmpty()) "\n**Use(s):**\n - `${Settings.PREFIX + command.name} ${command.syntax.joinToString("`\n - `${Settings.PREFIX + command.name} ")}`" else ""
            ).build()
        else MessageDSL.error("Not a valid command or alias.")
    }

    fun getCommandListEmbed(page: Int?): MessageEmbed {
        if (HelpInfo.page.size-1 < page ?: 1 || page ?: 1 < 0)
            return MessageDSL.error("Cannot find command at page $page")
        var description = ""
        CommandManager.commands.filter { it.page == page ?: 1 }.forEach {
            description += "`${Settings.PREFIX}${it.name}`: ${it.description}\n"
        }
        return EmbedBuilder()
            .setTitle(
                HelpInfo.page[page
                    ?: 1].emoji + " **__${HelpInfo.page[page ?: 1].name}__ Page ($page/${HelpInfo.page.size - 1})**"
            )
            .setColor(ColorInfo.discord)
            .setDescription(description)
            .build()
    }

    fun getCommand(i: String?): Command? {
        return CommandManager.commands.filter {
            it.name == i || (it.aliases.any { it == i })
        }.getOrNull(0)
    }
}