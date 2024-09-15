package org.gtlcore.gtlcore.common.data;

import com.gregtechceu.gtceu.api.data.chemical.Element;
import com.gregtechceu.gtceu.common.data.GTElements;

public class GTLElements {

    public static Element SPACETIME;
    public static Element INFINITY;

    public static void init() {
        SPACETIME = GTElements.createAndRegister(0, 0, -1, null, "spacetime", "§7熔炼为流体的时空", false);
        INFINITY = GTElements.createAndRegister(100000, 100000, -1, null, "infinity", "∞", false);
    }
}
