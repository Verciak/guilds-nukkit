package pl.vertty.nomenhc.entity.projectile;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityPrimedTNT;
import cn.nukkit.event.Event;
import cn.nukkit.event.entity.EntityExplosionPrimeEvent;
import cn.nukkit.level.Explosion;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.entity.QueryThread;

import java.util.Objects;

public class PrimedTNT extends EntityPrimedTNT
{
    public PrimedTNT(final FullChunk chunk, final CompoundTag nbt) {
        super(chunk, nbt);
    }
    
    public PrimedTNT(final FullChunk chunk, final CompoundTag nbt, final Entity source) {
        super(chunk, nbt, source);
    }
    
    public void explode() {
        final EntityExplosionPrimeEvent event = new EntityExplosionPrimeEvent((Entity)this, 4.0);
        this.server.getPluginManager().callEvent((Event)event);
        if (!event.isCancelled()) {
            final Explosion explosion = new Explosion((Position)this, event.getForce(), (Entity)this);
            if (event.isBlockBreaking()) {
                final QueryThread query = GuildPlugin.getQuery();
                final Explosion explosion2 = explosion;
                Objects.requireNonNull(explosion2);
                query.addQueue(explosion2::explodeA);
            }
        }
    }
}
