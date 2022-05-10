package pl.vertty.nomenhc.handlers;

import com.google.common.collect.Sets;
import pl.vertty.nomenhc.objects.User;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

public class UserManager {

    @Getter
    private static final Set<User> accounts = Sets.newConcurrentHashSet();

    protected void add(User account){
        accounts.add(account);
    }

    protected void remove(User account){
        accounts.remove(account);
    }

    public static User get(UUID uuid){
        return accounts.stream().filter(account -> account.getUuid().equals(uuid)).findFirst().orElse(null);
    }
    public static User get(String name){
        return accounts.stream().filter(account -> account.getName().equals(name)).findFirst().orElse(null);
    }

}
