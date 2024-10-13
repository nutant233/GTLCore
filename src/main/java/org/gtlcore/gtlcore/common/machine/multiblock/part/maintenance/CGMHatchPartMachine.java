package org.gtlcore.gtlcore.common.machine.multiblock.part.maintenance;

import org.gtlcore.gtlcore.api.machine.part.IGravityPartMachine;

import com.gregtechceu.gtceu.api.gui.widget.IntInputWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.ICleanroomProvider;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;

import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.Mth;

import lombok.Getter;

import javax.annotation.ParametersAreNonnullByDefault;

@Getter
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CGMHatchPartMachine extends CMHatchPartMachine implements IGravityPartMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            CGMHatchPartMachine.class, MultiblockPartMachine.MANAGED_FIELD_HOLDER);

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Persisted
    private int currentGravity;

    public CGMHatchPartMachine(IMachineBlockEntity blockEntity, ICleanroomProvider cleanroomTypes) {
        super(blockEntity, cleanroomTypes);
    }

    @Override
    public Widget createUIWidget() {
        WidgetGroup GravityGroup = new WidgetGroup(0, 0, 100, 20);
        GravityGroup.addWidget(new IntInputWidget(this::getCurrentGravity, this::setCurrentGravity).setMin(0).setMax(100));
        return GravityGroup;
    }

    public void setCurrentGravity(int gravity) {
        this.currentGravity = Mth.clamp(gravity, 0, 100);
    }
}
