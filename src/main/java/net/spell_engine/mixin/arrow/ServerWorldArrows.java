package net.spell_engine.mixin.arrow;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.server.world.ServerWorld;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.arrow.ArrowExtension;
import net.spell_engine.internals.casting.SpellCasterEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerWorld.class)
public class ServerWorldArrows {
    @Inject(method = "spawnEntity", at = @At("HEAD"))
    public void asd(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof PersistentProjectileEntity arrow) {
            if (arrow.getOwner() instanceof SpellCasterEntity caster) {
                var process = caster.getSpellCastProcess();
                if (process != null) {
                    ((ArrowExtension)arrow).applyArrowPerks(new SpellInfo(process.spell(), process.id()));
                }
            }
        }
    }
}
