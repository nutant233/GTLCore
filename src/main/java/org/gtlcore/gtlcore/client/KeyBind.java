package org.gtlcore.gtlcore.client;

import net.minecraft.client.KeyMapping;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;

public class KeyBind {

    public static final KeyMapping flyingspeedKey = new KeyMapping("key.gtlcore.flyingspeed", InputConstants.KEY_X, "key.keybinding.gtlcore");
    public static final KeyMapping nightvisionKey = new KeyMapping("key.gtlcore.nightvision", InputConstants.KEY_Z, "key.keybinding.gtlcore");
    public static final KeyMapping pearlKey = new KeyMapping("key.gtlcore.pearl", InputConstants.KEY_Y, "key.keybinding.gtlcore");
    public static final KeyMapping vajraKey = new KeyMapping("key.gtlcore.vajra", InputConstants.KEY_J, "key.keybinding.gtlcore");

    public static void init() {
        KeyMappingRegistry.register(flyingspeedKey);
        KeyMappingRegistry.register(nightvisionKey);
        KeyMappingRegistry.register(pearlKey);
        KeyMappingRegistry.register(vajraKey);
    }
}
