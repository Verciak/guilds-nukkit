package pl.vertty.nomenhc.listeners;

import java.awt.Color;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.helpers.DelayUtil;
import pl.vertty.nomenhc.objects.configs.DiscordConfig;

public class ChannelListener extends ListenerAdapter {

    public static GuildPlugin plugin = GuildPlugin.getPlugin();
    public void onMessageReceived(MessageReceivedEvent paramMessageReceivedEvent) {
        if (paramMessageReceivedEvent.getAuthor().isBot() || paramMessageReceivedEvent.getMember() == null)
            return;
        DelayUtil delayUtil = new DelayUtil();
        String str = paramMessageReceivedEvent.getMessage().getContentRaw();
        MessageChannel messageChannel = paramMessageReceivedEvent.getChannel();
        User user = paramMessageReceivedEvent.getMember().getUser();
        if (messageChannel.getId().equals(DiscordConfig.getInstance().ChannelID)) {
            paramMessageReceivedEvent.getMessage().delete().queue();
            pl.vertty.nomenhc.objects.User userData = UserManager.get(str);
            if (userData == null) {
                EmbedBuilder embedBuilder1 = new EmbedBuilder();
                embedBuilder1.addField(DiscordConfig.getInstance().NotFound_Title, DiscordConfig.getInstance().NotFound_Description, true);
                embedBuilder1.setColor(Color.getColor(DiscordConfig.getInstance().Discord_Color));
                embedBuilder1.setTimestamp((new Date()).toInstant());
                embedBuilder1.setFooter(user.getName(), user.getAvatarUrl());
                messageChannel.sendMessageEmbeds(embedBuilder1.build()).queue(paramMessage -> {
                    DelayUtil.delay(DiscordConfig.getInstance().Discord_ToDelete, TimeUnit.SECONDS);
                    paramMessage.delete().queue();
                });
                return;
            }
            if (userData.isTaken()) {
                EmbedBuilder embedBuilder1 = new EmbedBuilder();
                embedBuilder1.addField(DiscordConfig.getInstance().Taken_Title, DiscordConfig.getInstance().Taken_Description, true);
                embedBuilder1.setColor(Color.getColor(DiscordConfig.getInstance().Discord_Color));
                embedBuilder1.setTimestamp((new Date()).toInstant());
                embedBuilder1.setFooter(user.getName(), user.getAvatarUrl());
                messageChannel.sendMessageEmbeds(embedBuilder1.build()).queue(paramMessage -> {
                    DelayUtil.delay(DiscordConfig.getInstance().Discord_ToDelete, TimeUnit.SECONDS);
                    paramMessage.delete().queue();
                });
                return;
            }
            if (userData.canTake()) {
                EmbedBuilder embedBuilder1 = new EmbedBuilder();
                embedBuilder1.addField(DiscordConfig.getInstance().ToTake_Title, DiscordConfig.getInstance().ToTake_Description, true);
                embedBuilder1.setColor(Color.getColor(DiscordConfig.getInstance().Discord_Color));
                embedBuilder1.setTimestamp((new Date()).toInstant());
                embedBuilder1.setFooter(user.getName(), user.getAvatarUrl());
                messageChannel.sendMessageEmbeds(embedBuilder1.build()).queue(paramMessage -> {
                    DelayUtil.delay(DiscordConfig.getInstance().Discord_ToDelete, TimeUnit.SECONDS);
                    paramMessage.delete().queue();
                });
                return;
            }
            userData.setTake(true);
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.addField(DiscordConfig.getInstance().Ready_Title, DiscordConfig.getInstance().Ready_Description, true);
            embedBuilder.setColor(Color.getColor(DiscordConfig.getInstance().Discord_Color));
            embedBuilder.setTimestamp((new Date()).toInstant());
            embedBuilder.setFooter(user.getName(), user.getAvatarUrl());
            messageChannel.sendMessageEmbeds(embedBuilder.build()).queue(paramMessage -> {
                DelayUtil.delay(DiscordConfig.getInstance().Discord_ToDelete, TimeUnit.SECONDS);
                paramMessage.delete().queue();
            });
            return;
        }
    }

    public ChannelListener(GuildPlugin paramMain) {
        this.plugin = paramMain;
    }
}

