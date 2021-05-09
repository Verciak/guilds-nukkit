package eu.dkcode.guilds.handlers;

import com.google.common.collect.Sets;
import eu.dkcode.guilds.objects.Account;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 07.05.2021
 * @Class: AccountHandler
 **/

public class AccountHandler {

    @Getter
    private static final Set<Account> accounts = Sets.newConcurrentHashSet();

    protected void add(Account account){
        accounts.add(account);
    }

    protected void remove(Account account){
        accounts.remove(account);
    }

    public static Account get(UUID uuid){
        return accounts.stream().filter(account -> account.getUuid().equals(uuid)).findFirst().orElse(null);
    }


}
