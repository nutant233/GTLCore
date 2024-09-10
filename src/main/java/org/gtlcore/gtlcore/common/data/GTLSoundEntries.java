package org.gtlcore.gtlcore.common.data;

import com.gregtechceu.gtceu.api.sound.SoundEntry;

import static com.gregtechceu.gtceu.common.registry.GTRegistration.REGISTRATE;

public class GTLSoundEntries {

    public static final SoundEntry DTPF = REGISTRATE.sound("dtpf").build();
    public static final SoundEntry FUSIONLOOP = REGISTRATE.sound("fusionloop").build();

    public static void init() {}
}
