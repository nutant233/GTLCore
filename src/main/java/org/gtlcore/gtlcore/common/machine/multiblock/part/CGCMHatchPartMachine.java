package org.gtlcore.gtlcore.common.machine.multiblock.part;

import org.gtlcore.gtlcore.api.machine.part.IGravityPartMachine;

import com.gregtechceu.gtceu.api.gui.widget.IntInputWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.ICleanroomProvider;

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
public class CGCMHatchPartMachine extends CCMHatchPartMachine implements IGravityPartMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            CGCMHatchPartMachine.class, ACMHatchPartMachine.MANAGED_FIELD_HOLDER);

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Persisted
    private int currentGravity;

    public CGCMHatchPartMachine(IMachineBlockEntity blockEntity, ICleanroomProvider cleanroomTypes) {
        super(blockEntity, cleanroomTypes);
    }

    @Override
    public Widget createUIWidget() {
        Widget widget = super.createUIWidget();
        if (widget instanceof WidgetGroup group) {
            group.addWidget(new IntInputWidget(10, 35, 80, 10, this::getCurrentGravity, this::setCurrentGravity).setMin(0).setMax(100));
            return group;
        }
        return widget;
    }

    public void setCurrentGravity(int gravity) {
        this.currentGravity = Mth.clamp(gravity, 0, 100);
    }
}
