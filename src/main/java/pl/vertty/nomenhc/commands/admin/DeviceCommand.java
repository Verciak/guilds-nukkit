package pl.vertty.nomenhc.commands.admin;

import cn.nukkit.Player;
import cn.nukkit.command.ConsoleCommandSender;
import pl.vertty.nomenhc.handlers.DropManager;
import pl.vertty.nomenhc.objects.drop.Drop;
import pl.vertty.nomenhc.commands.PlayerCommand;

public class DeviceCommand extends PlayerCommand {

    public DeviceCommand() {
        super("device", "Przeladowanie device", "device", "cmd.admin.device", new String[] { "device" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        for(Drop drop : DropManager.drops){
            p.sendMessage(drop.getMessage());
        }
//        p.sendMessage("IP: " + p.getAddress());
//        p.sendMessage("ClientSecret: " + p.getClientSecret());
//        p.sendMessage("CapeData: " + p.getLoginChainData().getCapeData());
//        p.sendMessage("DeviceId: " + p.getLoginChainData().getDeviceId());
//        p.sendMessage("DeviceModel: " + p.getLoginChainData().getDeviceModel());
//        p.sendMessage("IdentityPublicKey: " + p.getLoginChainData().getIdentityPublicKey());
//        p.sendMessage("ClientUUID: " + p.getLoginChainData().getClientUUID());
//        p.sendMessage("ClientId: " + p.getLoginChainData().getClientId());
//        p.sendMessage("XUID: " + p.getLoginChainData().getXUID());
//        p.sendMessage("UIProfile: " + p.getLoginChainData().getUIProfile());
//        p.sendMessage("DeviceOS: " + p.getLoginChainData().getDeviceOS());
//        p.sendMessage("CurrentInputMode: " + p.getLoginChainData().getCurrentInputMode());
//        p.sendMessage("DefaultInputMode: " + p.getLoginChainData().getDefaultInputMode());

        return false;
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p0, String[] p1) {
        return false;
    }


}

