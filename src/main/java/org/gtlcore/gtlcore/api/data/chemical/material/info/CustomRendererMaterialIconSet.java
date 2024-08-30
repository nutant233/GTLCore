package org.gtlcore.gtlcore.api.data.chemical.material.info;

import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.api.item.component.ICustomRenderer;
import lombok.Getter;
import org.gtlcore.gtlcore.client.renderer.item.StereoscopicItemRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class CustomRendererMaterialIconSet extends com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet {

    private final ICustomRenderer customRenderer;

    public CustomRendererMaterialIconSet(@NotNull String name,
                                         @Nullable com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet parentIconset,
                                         boolean isRootIconset,
                                         ICustomRenderer customRenderer) {
        super(name, parentIconset, isRootIconset);
        this.customRenderer = customRenderer;
    }

    public static final CustomRendererMaterialIconSet CUSTOM_TRANSCENDENT_MENTAL = new CustomRendererMaterialIconSet(
            "transcendent_mental",
            MaterialIconSet.METALLIC,
            false,
            () -> StereoscopicItemRenderer.INSTANCE);
}
