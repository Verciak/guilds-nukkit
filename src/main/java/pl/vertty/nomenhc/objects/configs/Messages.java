package pl.vertty.nomenhc.objects.configs;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Messages {

    private static Messages instance;

    public String spawn_permission,wiadomosci_actionbarpvp,wiadomosci_actionbarendpvp,wiadomosci_actionbarendpvpchat,wiadomosci_chatlogout,wiadomosci_chatdamager,wiadomosci_chatplayer;
    public List<String> komendy_pvp;
    public String komendy_permission,komendy_error,ANVIL_MESSAGE,BEACON_MESSAGE,BREWING_MESSAGE,CHEST_MESSAGE,DISPENSER_MESSAGE,DROPPER_MESSAGE,ENCHANTING_MESSAGE,ENDER_CHEST_MESSAGE,FURNACE_MESSAGE,HOPPER_MESSAGE,WORKBENCH_MESSAGE,HOPPER_MINECART_MESSAGE,STORAGE_MINECART_MESSAGE,TRAPPED_CHEST_MESSAGE;
    public String ban_window,ban_no_reason,user_have_ban,ban_reason,user_nohave_ban,unban_reason;
    public String guildActionBar_terrain,guildRemoveLife,guildRemoveLifeError,have_guild,next_to_spawn,next_to_border,next_to_guild,create_name_error,create_name_istnieje,create_name_bc,guild_no_have,guild_no_lider,guild_delete_code,guild_delete_code_error,guild_vleader_succes_remove,guild_vleader_limit,guild_vleader_succes,guild_officer_succes_remove,guild_officer_limit,guild_officer_succes,guild_onlyleader_vleader,guild_player_offline,guild_invite_back_request,guild_invite,guild_invite_request,guild_no_exists,guild_invite_nohave,guild_invite_accept_bc,guild_leave_error,guild_leave_succes_bc,guild_player_no,guild_kick_succes_bc,guild_onlyleader_vleader_mistrz,guild_sethome_error,guild_sethome_succes,guild_prolong_error,guild_prolong_error1,guild_prolong_succes, guild_large_error,guild_large_error1,guild_large_succes,guild_ally_error1,guild_ally_error2,guild_ally_remove,guild_ally_succes,guild_ally_invite,guild_ally_invite_members_notify,guild_pvp_ally,guild_pvp,guild;
    public String teleport;
    public String actionbar_append, clear_eq, clear_error;

    public Messages() {
        clear_error = "gracz jest offline kurwa";
        clear_eq = "Ekwipunek zostal wyczyszczony";
        actionbar_append = " §8:: ";
        teleport = "§6Teleportacja §8(§e {TIME}§8)";
        guildActionBar_terrain = "§fZnajdujesz sie na terenie gildii: §8[§{COLOR}{TAG}§8:: §{COLOR}{DISTANCE}§8]";
        guild = "§cPoprawne uzycie:{N}§8» §4/g zaloz [tag] [nazwa]{N}§8» §4/g usun";
        guild_pvp = "§cPVP w gildii zostalo: §{STATUS}";
        guild_pvp_ally = "§cPVP w sojuszu zostalo: §{STATUS}";
        guild_ally_invite_members_notify = "§aTwoja gildia otrzymala zaproszenie do sojuszu od gildii §8[§4{TAG}§8]";
        guild_ally_invite = "§aZaprosiles gildie §8[§2{TAG}§8] §ado sojuszu!";
        guild_ally_succes = "§cGildia §8[§4{TAG}§8] §czawarla sojusz z gildia §8[§4{O-TAG}§8]";
        guild_ally_remove = "§cGildia §8[§4{TAG}§8] §czerwala sojusz z gildia §8[§4{O-TAG}§8]";
        guild_ally_error2 = "§cTwoja gildia osiagnela limit sojusznikow!";
        guild_ally_error1 = "§cMusisz posiadac min. 16 blokow szmaragdow za kazdym razem jak uzywasz komend /g sojusz!";
        guild_large_succes = "§aPowiekszyles gildie do rozmiaru: §l{SIZE} x {SIZE}";
        guild_large_error1 = "§cMusisz posiadac min. {COST} blokow szmaragdow aby powiekszyc gildie!";
        guild_large_error = "§cTwoja gildia jest juz powiekszona na maksymalny rozmiar";
        guild_prolong_succes = "§aGildia zostala przedluzona o 2 dni";
        guild_prolong_error1 = "§cMusisz posiadac min. {COST} blokow szmaragdow aby przedluzyc gildie!";
        guild_prolong_error = "§cTwoja gildia jest juz przedluzona na maksymalny okres czasu!";
        guild_sethome_succes = "§aPoprawnie ustawiono nowy dom gildii!";
        guild_sethome_error = "§cDom gildii mozesz ustawic tylko na terenie twojej gildii!";
        guild_onlyleader_vleader_mistrz = "§cTylko lider, vlider i mistrz moga to zrobic!";
        guild_kick_succes_bc = "§cGracz §4{NICK} §czostal wyrzucony z gildii §8[§4{TAG}§8]";
        guild_player_no = "§cNie ma takiego gracza w gildii!";
        guild_leave_succes_bc = "§cGracz §4{NICK} §copuscil gildie §8[§4{TAG}§8]";
        guild_leave_error = "§cNie mozesz porzucic gildii bedac jej liderem!";
        guild_invite_accept_bc = "§cGracz §4{NAME} §cdolaczyl do gildii §8[§4{TAG}§8]";
        guild_invite_nohave = "§cNie posiadasz zaproszenia do tej gildii!";
        guild_no_exists = "§cTaka gildia nie istnieje!";
        guild_invite_request = "Otrzymales zaproszenie do gildii {TAG}";
        guild_invite = "§cZaprosiles gracza §l{NICK} §cdo gildii!";
        guild_invite_back_request = "§cCofnales zaproszenie gracza §l{NICK} §cdo gildii!";
        guild_player_offline = "§cgracz jest offline";
        guild_onlyleader_vleader = "§cTylko lider i vlider moga to zrobic!";
        guild_officer_succes = "§cGracz §l{NICK} §czostal nowym mistrzem w gildii!";
        guild_officer_limit = "§cOsiagnales juz limit mistrzemow w gildii!";
        guild_officer_succes_remove= "§cGracz §l{NAME} §cnie jest juz mistrzem";
        guild_vleader_succes = "§cGracz §l{NICK} §czostal nowym vliderem w gildii!";
        guild_vleader_limit = "§cOsiagnales juz limit vliderow w gildii!";
        guild_vleader_succes_remove = "§cGracz §l{NICK} §cnie jest juz vliderem";
        guild_delete_code_error = "§cPodany kod jest nieprawidlowy!";
        guild_delete_code = "§cJezeli jestes pewny ze chcesz usunac gildie napisz: §4/g usun {CODE}";
        guild_no_lider = "§cNie jestes liderem gildii!";
        guild_no_have = "§cNie posiadasz gildii!";
        create_name_bc = "§cGildia §8[§4{TAG}§8] §4{G-NAME} §czostala zalozona przez §4{P-NAME}";
        create_name_istnieje = "§cGildia o takim tagu juz istnieje!";
        create_name_error = "§cTag gildii musi posiadac od 2 do 5 znakow,\nNazwa gidlii musi posiadac od 5 do 30 znakow,\nTag oraz nazwa gildii moze zawierac tylko liczby i litery!";
        next_to_guild = "§cjestes zbyt blisko innej gildii!";
        next_to_border = "§cJestes zbyt blisko borderu mapy! (§4Aktualnie: §l{DISTANCE} §8| §4Minimum: §l{ALLOW_DISTANCE}§c)";
        next_to_spawn = "§cJestes zbyt blisko spawna! (§4Aktualnie: §l{DISTANCE} §8| §4Minimum: §l{ALLOW_DISTANCE}§c)";
        have_guild = "§cPosiadasz juz gildie!";
        guildRemoveLifeError = "§cWyglada na to ze ta gildia byla podbita w ostatnim czasie!";
        guildRemoveLife = "§cGildia §8[§4{TAG}§8] §cstracila jedo serce na rzecz gidlii §8[§4{OTAG}§8]";
        
        spawn_permission = "logout.admin";
        wiadomosci_actionbarpvp = "§cAntyLogout §8(§c{TIME}§8)";
        wiadomosci_actionbarendpvp = "§aMozesz sie wylogowac!";
        wiadomosci_actionbarendpvpchat = "§8» §aSkonczyles walczyc, mozesz sie wylogowac!";
        wiadomosci_chatlogout = "§8§l• §cGracz §6{NICK} §cwylogowal sie podczas walki!";
        wiadomosci_chatdamager = "§8§l• §4Zostales zaatakowany nie mozesz wylogowac sie przez {TIME} sekund!";
        wiadomosci_chatplayer = "§8§l• §4Zaatakowales gracza, nie mozesz wylogowac sie przez {TIME} sekund!";
        komendy_pvp = new ArrayList<>();
        komendy_pvp.add("s");
        komendy_pvp.addAll(komendy_pvp);
        komendy_permission = "antylog.bypass";
        komendy_error = "§cTa komenda jest zablokowana podczas walki!";
        ANVIL_MESSAGE = "§8§l• §cNie mozesz otwierac §4ANVIL §cpodczas walki!";
        BEACON_MESSAGE = "§8§l• §cNie mozesz otwierac §4BEACON §cpodczas walki!";
        BREWING_MESSAGE = "§8§l• §cNie mozesz otwierac §4BREWING §cpodczas walki!";
        CHEST_MESSAGE = "§8§l• §cNie mozesz otwierac §4CHEST §cpodczas walki!";
        DISPENSER_MESSAGE = "§8§l• §cNie mozesz otwierac §4DISPENSER §cpodczas walki!";
        DROPPER_MESSAGE = "§8§l• §cNie mozesz otwierac §4DROPPER §cpodczas walki!";
        ENCHANTING_MESSAGE = "§8§l• §cNie mozesz otwierac §4ENCHANTING §cpodczas walki!";
        ENDER_CHEST_MESSAGE = "§8§l• §cNie mozesz otwierac §4ENDER_CHEST §cpodczas walki!";
        FURNACE_MESSAGE = "§8§l• §cNie mozesz otwierac §4FURNACE §cpodczas walki!";
        HOPPER_MESSAGE = "§8§l• §cNie mozesz otwierac §4HOPPER §cpodczas walki!";
        WORKBENCH_MESSAGE = "§8§l• §cNie mozesz otwierac §4WORKBENCH §cpodczas walki!";
        HOPPER_MINECART_MESSAGE = "§8§l• §cNie mozesz otwierac §4HOPPER_MINECART §cpodczas walki!";
        STORAGE_MINECART_MESSAGE = "§8§l• §cNie mozesz otwierac §4STORAGE_MINECART §cpodczas walki!";
        TRAPPED_CHEST_MESSAGE = "§8§l• §cNie mozesz otwierac §4TRAPPED_CHEST §cpodczas walki!";

        ban_window = "§8============={N}§7MASZ BANA CHUJU{N}§7TWOJ NICK: {NAME}{N}§7TWOJ admina: {ADMIN}{N}§7powod: {REASON}{N}§7wygasa: {EXPIRE}{N}§8============={N}";
        ban_no_reason = "Nie ustalony";
        user_have_ban = "typ ma juz bana pajacu";
        ban_reason = "Gracz {NAME} zostal zbanowy przez {ADMIN} za {REASON} wygasa {EXPIRE}";
        user_nohave_ban = "typ nie ma bana pajacu";
        unban_reason = "Gracz {NAME} dostal ub od {ADMIN}";

    }

    public static Messages getInstance() {
        if (Messages.instance == null) {
            Messages.instance = fromDefaults();
        }
        return Messages.instance;
    }

    public static void init(File filePath){
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
    }

    public static void load(final File file) {
        Messages.instance = fromFile(file);
        if (Messages.instance == null) {
            Messages.instance = fromDefaults();
        }
    }

    public static void load(final String file) {
        load(new File(file));
    }

    private static Messages fromDefaults() {
        final Messages config = new Messages();
        return config;
    }

    public void toFile(final String file) {
        this.toFile(new File(file));
    }

    public void toFile(final File file) {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final String jsonconfig = gson.toJson(this);
        try {
            final FileWriter writer = new FileWriter(file);
            writer.write(jsonconfig);
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Messages fromFile(final File configFile) {
        try {
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
            return gson.fromJson(reader, Messages.class);
        }
        catch (FileNotFoundException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}

