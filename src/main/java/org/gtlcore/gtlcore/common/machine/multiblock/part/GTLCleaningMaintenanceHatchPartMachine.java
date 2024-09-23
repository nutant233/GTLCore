package org.gtlcore.gtlcore.common.machine.multiblock.part;

import org.gtlcore.gtlcore.api.machine.multiblock.GTLCleanroomType;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.ICleanroomReceiver;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.ICleanroomProvider;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.multiblock.CleanroomType;
import com.gregtechceu.gtceu.api.machine.multiblock.DummyCleanroom;
import com.gregtechceu.gtceu.common.machine.multiblock.part.AutoMaintenanceHatchPartMachine;

import net.minecraft.MethodsReturnNonnullByDefault;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GTLCleaningMaintenanceHatchPartMachine extends AutoMaintenanceHatchPartMachine {

    protected static final Set<CleanroomType> CLEANROOM = new ObjectOpenHashSet<>();
    protected static final Set<CleanroomType> STERILE_CLEANROOM = new ObjectOpenHashSet<>();
    protected static final Set<CleanroomType> LAW_CLEANROOM = new ObjectOpenHashSet<>();

    static {
        CLEANROOM.add(CleanroomType.CLEANROOM);
        STERILE_CLEANROOM.addAll(CLEANROOM);
        STERILE_CLEANROOM.add(CleanroomType.STERILE_CLEANROOM);
        LAW_CLEANROOM.addAll(STERILE_CLEANROOM);
        LAW_CLEANROOM.add(GTLCleanroomType.LAW_CLEANROOM);
    }

    // must come after the static block
    public static final ICleanroomProvider DUMMY_CLEANROOM = DummyCleanroom.createForTypes(CLEANROOM);
    public static final ICleanroomProvider STERILE_DUMMY_CLEANROOM = DummyCleanroom.createForTypes(STERILE_CLEANROOM);
    public static final ICleanroomProvider LAW_DUMMY_CLEANROOM = DummyCleanroom.createForTypes(LAW_CLEANROOM);

    ICleanroomProvider cleanroomTypes;

    public GTLCleaningMaintenanceHatchPartMachine(IMachineBlockEntity metaTileEntityId,
                                                  ICleanroomProvider cleanroomTypes) {
        super(metaTileEntityId);
        this.cleanroomTypes = cleanroomTypes;
    }

    public static GTLCleaningMaintenanceHatchPartMachine Cleaning(IMachineBlockEntity metaTileEntityId) {
        return new GTLCleaningMaintenanceHatchPartMachine(metaTileEntityId, DUMMY_CLEANROOM);
    }

    public static GTLCleaningMaintenanceHatchPartMachine SterileCleaning(IMachineBlockEntity metaTileEntityId) {
        return new GTLCleaningMaintenanceHatchPartMachine(metaTileEntityId, STERILE_DUMMY_CLEANROOM);
    }

    public static GTLCleaningMaintenanceHatchPartMachine LawCleaning(IMachineBlockEntity metaTileEntityId) {
        return new GTLCleaningMaintenanceHatchPartMachine(metaTileEntityId, LAW_DUMMY_CLEANROOM);
    }

    @Override
    public void addedToController(IMultiController controller) {
        super.addedToController(controller);
        if (controller instanceof ICleanroomReceiver receiver) {
            receiver.setCleanroom(cleanroomTypes);
        }
    }

    @Override
    public void removedFromController(IMultiController controller) {
        super.removedFromController(controller);
        if (controller instanceof ICleanroomReceiver receiver && receiver.getCleanroom() == cleanroomTypes) {
            receiver.setCleanroom(null);
        }
    }

    @Override
    public int getTier() {
        if (this.cleanroomTypes == STERILE_DUMMY_CLEANROOM) return GTValues.ZPM;
        if (this.cleanroomTypes == LAW_DUMMY_CLEANROOM) return GTValues.UEV;
        return GTValues.HV;
    }

    /**
     * Add an {@link CleanroomType} that is provided to multiblocks with this hatch
     *
     * @param type the type to add
     */
    @SuppressWarnings("unused")
    public static void addCleanroomType(@NotNull CleanroomType type) {
        CLEANROOM.add(type);
    }

    /**
     * @return the {@link CleanroomType}s this hatch provides to multiblocks
     */
    @SuppressWarnings("unused")
    public static ImmutableSet<CleanroomType> getCleanroomTypes(ICleanroomProvider p) {
        return ImmutableSet.copyOf(p.getTypes());
    }
}
