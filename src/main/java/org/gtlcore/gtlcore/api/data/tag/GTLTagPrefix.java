package org.gtlcore.gtlcore.api.data.tag;

import org.gtlcore.gtlcore.api.data.chemical.material.info.GTLMaterialFlags;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconType;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;

@SuppressWarnings("unused")
public class GTLTagPrefix {

    public static void init() {}

    private static final MaterialIconType nanoswarmIcon = new MaterialIconType("nanoswarm");

    public static final TagPrefix nanoswarm = new TagPrefix("nanoswarm")
            .idPattern("%s_nanoswarm")
            .defaultTagPath("nanoswarms/%s")
            .unformattedTagPath("nanoswarms")
            .langValue(" %s nanoswarm")
            .materialAmount(GTValues.M)
            .materialIconType(nanoswarmIcon)
            .unificationEnabled(true)
            .generateItem(true)
            .generationCondition(mat -> mat.hasFlag(GTLMaterialFlags.GENERATE_NANOSWARM));

    public static final TagPrefix contaminableManoswarm = new TagPrefix("contaminable_nanoswarm")
            .idPattern("contaminable_%s_nanoswarm")
            .defaultTagPath("contaminable_nanoswarm/%s")
            .unformattedTagPath("contaminable_nanoswarm")
            .langValue(" %s contaminable_nanoswarm")
            .materialAmount(GTValues.M)
            .materialIconType(nanoswarmIcon)
            .unificationEnabled(true)
            .generateItem(true)
            .generationCondition(mat -> mat.hasFlag(GTLMaterialFlags.GENERATE_NANOSWARM));

    public static final TagPrefix milled = new TagPrefix("milled")
            .idPattern("milled_%s")
            .defaultTagPath("milled/%s")
            .unformattedTagPath("milled")
            .langValue("Milled %s")
            .materialAmount(GTValues.M)
            .materialIconType(new MaterialIconType("milled"))
            .unificationEnabled(true)
            .generateItem(true)
            .generationCondition(mat -> mat.hasFlag(GTLMaterialFlags.GENERATE_MILLED));
}
