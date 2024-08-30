package org.gtlcore.gtlcore.common.data.machines;

import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.api.sound.SoundEntry;

import static com.gregtechceu.gtceu.common.registry.GTRegistration.REGISTRATE;


public class GTLSoundEntries {

    static {
        GTRegistries.SOUNDS.unfreeze();
    }

    public static final SoundEntry DTPF = REGISTRATE.sound("dtpf").build();
    public static final SoundEntry FUSIONLOOP = REGISTRATE.sound("fusionloop").build();

    public static void init() {}
}
