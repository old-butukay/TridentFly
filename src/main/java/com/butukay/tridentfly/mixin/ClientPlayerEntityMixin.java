package com.butukay.tridentfly.mixin;

import com.butukay.tridentfly.TridentFly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;



@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    @Final
    @Shadow
    protected MinecraftClient client;

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(at = {@At("HEAD")}, method = {"sendChatMessage"}, cancellable = true)
    public void onChatMessage(final String message, final CallbackInfo ci) {
        if (TridentFly.getConfig().isEnabledCommand()) {
            if (message.equals(TridentFly.getConfig().getCommandName())) {
                TridentFly.toggleTridentFly(client);
                ci.cancel();
            }
        }
    }
}
