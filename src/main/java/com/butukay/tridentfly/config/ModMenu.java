package com.butukay.tridentfly.config;

import com.butukay.tridentfly.TridentFly;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.TranslatableText;

public class ModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(new TranslatableText("title.trident-fly.config"));

            ConfigCategory settings = builder.getOrCreateCategory(new TranslatableText("category.trident-fly.settings"));

            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            settings.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("option.trident-fly.enabled"), TridentFly.getConfig().isEnabled())
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> TridentFly.getConfig().setEnabled(newValue))
                    .build());

            settings.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("option.trident-fly.enable-command"), TridentFly.getConfig().isEnabledCommand())
                    .setDefaultValue(false)
                    .setSaveConsumer(newValue -> TridentFly.getConfig().setEnabledCommand(newValue))
                    .build());

            settings.addEntry(entryBuilder.startStrField(new TranslatableText("option.trident-fly.command-name"), TridentFly.getConfig().getCommandName())
                    .setDefaultValue(".trident")
                    .setSaveConsumer(newValue -> TridentFly.getConfig().setCommandName(newValue))
                    .build());

            settings.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("option.trident-fly.action-bar"), TridentFly.getConfig().isActionBar())
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> TridentFly.getConfig().setActionBar(newValue))
                    .build());

            builder.setSavingRunnable(TridentFlyConfigUtil::saveConfig);

            return builder.build();
        };
    }
}
