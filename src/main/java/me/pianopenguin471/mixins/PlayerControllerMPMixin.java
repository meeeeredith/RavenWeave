package me.pianopenguin471.mixins;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.weavemc.api.event.EventBus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ravenweave.client.event.AttackEntityEvent;

@Mixin(PlayerControllerMP.class)
public class PlayerControllerMPMixin {
    @Inject(method = "attackEntity", at = @At("HEAD"))
    public void onAttackEntity(EntityPlayer playerIn, Entity targetEntity, CallbackInfo ci) {
        EventBus.postEvent(new AttackEntityEvent(targetEntity, playerIn));
    }
}
