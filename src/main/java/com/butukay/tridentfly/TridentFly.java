package com.butukay.tridentfly;

import com.butukay.tridentfly.config.TridentFlyConfig;
import com.butukay.tridentfly.config.TridentFlyConfigUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.TranslatableText;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class TridentFly implements ModInitializer {
    @Override
    public void onInitialize() {

        TridentFlyConfigUtil.loadConfig();

        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.trident-fly.toggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "key.category.butukay-tweaks"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                toggleTridentFly(client);
            }
        });
    }

    public static void toggleTridentFly(MinecraftClient client) {
        getConfig().toggleEnabled();

        client.player.sendMessage(new TranslatableText("key.trident-fly." + (getConfig().isEnabled() ? "enabled" : "disabled")), getConfig().isActionBar());
    }

    public static TridentFlyConfig getConfig(){
        return TridentFlyConfigUtil.getConfig();
    }
}
