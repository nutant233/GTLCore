package org.gtlcore.gtlcore.common.block;

import org.gtlcore.gtlcore.api.machine.multiblock.GTLCleanroomType;

import com.gregtechceu.gtceu.api.block.IFilterType;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public enum CleanroomFilterType implements IFilterType {

    FILTER_CASING_LAW("law_filter_casing", GTLCleanroomType.LAW_CLEANROOM);

    private final String name;
    @Getter
    private final com.gregtechceu.gtceu.api.machine.multiblock.CleanroomType cleanroomType;

    CleanroomFilterType(String name, com.gregtechceu.gtceu.api.machine.multiblock.CleanroomType cleanroomType) {
        this.name = name;
        this.cleanroomType = cleanroomType;
    }

    @NotNull
    @Override
    public String getSerializedName() {
        return this.name;
    }

    @NotNull
    @Override
    public String toString() {
        return getSerializedName();
    }
}
