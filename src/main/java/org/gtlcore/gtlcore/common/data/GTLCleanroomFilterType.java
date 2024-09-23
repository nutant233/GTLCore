package org.gtlcore.gtlcore.common.data;

import org.gtlcore.gtlcore.api.machine.multiblock.GTLCleanroomType;

import com.gregtechceu.gtceu.api.block.IFilterType;
import com.gregtechceu.gtceu.api.machine.multiblock.CleanroomType;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public enum GTLCleanroomFilterType implements IFilterType {

    FILTER_CASING_LAW("law_filter_casing", GTLCleanroomType.LAW_CLEANROOM);

    private final String name;
    @Getter
    private final CleanroomType cleanroomType;

    GTLCleanroomFilterType(String name, CleanroomType cleanroomType) {
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
