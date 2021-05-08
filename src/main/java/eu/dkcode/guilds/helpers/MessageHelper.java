package eu.dkcode.guilds.helpers;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import static eu.dkcode.guilds.helpers.ColorHelper.colored;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 08.05.2021
 * @Class: MessageHelper
 **/

public class MessageHelper {

    public static void sendActionbar(Player p, String message) {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(
                new PacketPlayOutChat(
                        IChatBaseComponent.ChatSerializer.a(
                                "{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}"
                        ), (byte)2
                )
        );
    }

    public static void sendTitle(Player player, String title, String subtitle) {
        if (title == null) title = "";
        if (subtitle == null) subtitle = "";
        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.getHandle().playerConnection.sendPacket(
                new PacketPlayOutTitle(
                        PacketPlayOutTitle.EnumTitleAction.TITLE,
                        IChatBaseComponent.ChatSerializer.a(
                                "{\"text\": \"" + colored(title) + "\"}"
                        )
                )
        );
        craftPlayer.getHandle().playerConnection.sendPacket(
                new PacketPlayOutTitle(
                        PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
                        IChatBaseComponent.ChatSerializer.a(
                                "{\"text\": \"" + colored(subtitle) + "\"}"
                        )
                )
        );
    }

}
