package org.gtlcore.gtlcore.data;

import org.gtlcore.gtlcore.data.lang.LangHandler;

import com.tterrag.registrate.providers.ProviderType;

import static org.gtlcore.gtlcore.api.registries.GTLRegistration.REGISTRATE;

public class GTLDatagen {

    public static void init() {
        REGISTRATE.addDataGenerator(ProviderType.LANG, LangHandler::init);
    }
}
