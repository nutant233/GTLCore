package org.gtlcore.gtlcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.machine.ConditionalSubscriptionHandler;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IExplosionMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import com.mojang.datafixers.util.Pair;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.gtlcore.gtlcore.api.pattern.util.IValueContainer;
import org.gtlcore.gtlcore.utils.MachineIO;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FissionReactorMachine extends WorkableElectricMultiblockMachine implements IExplosionMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            FissionReactorMachine.class, WorkableMultiblockMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    private BlockPos centrePos = null;
    @Persisted
    private int heat = 298;
    @Persisted
    private int damaged = 0;
    @Persisted
    private int parallel = 0;
    @Persisted
    private int fuel = 0, cooler = 0, heatAdjacent = 1, coolerAdjacent = 0;

    protected ConditionalSubscriptionHandler HeatSubs;

    public FissionReactorMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
        this.HeatSubs = new ConditionalSubscriptionHandler(this, this::HeatUpdate, this::isFormed);
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    public static int adjacent(Level level, BlockPos pos, String id) {
        int a = 0;
        for (int i = -1; i < 1; i += 2) {
            for (int j = -1; j < 1; j += 2) {
                for (int k = -1; k < 1; k += 2) {
                    if (Objects.equals(level.kjs$getBlock(pos.offset(i, j, k)).getId(), id)) {
                        a++;
                    }
                }
            }
        }
        return a;
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        BlockPos pos = getPos();
        Level level = getLevel();
        BlockPos[] coordinates = new BlockPos[] { pos.offset(4, 0, 0),
                pos.offset(-4, 0, 0),
                pos.offset(0, 0, 4),
                pos.offset(0, 0, -4) };
        for (BlockPos blockPos : coordinates) {
            if (Objects.equals(level.kjs$getBlock(blockPos).getId(), "kubejs:fission_reactor_casing")) {
                centrePos = blockPos.offset(0, 4, 0);
                for (int i = -3; i < 4; i++) {
                    for (int j = 0; j < 8; j++) {
                        for (int k = -3; k < 4; k++) {
                            BlockPos assemblyPos = blockPos.offset(i, j, k);
                            if (Objects.equals(level.kjs$getBlock(assemblyPos).getId(), "gtlcore:fission_fuel_assembly")) {
                                this.heatAdjacent += adjacent(level, assemblyPos, "gtlcore:fission_fuel_assembly");
                            }
                            if (Objects.equals(level.kjs$getBlock(assemblyPos).getId(), "gtlcore:cooler")) {
                                this.coolerAdjacent += adjacent(level, assemblyPos, "gtlcore:cooler");
                            }
                        }
                    }
                }
            }
        }
        IValueContainer<?> FuelContainer = getMultiblockState().getMatchContext()
                .getOrCreate("FuelAssemblyValue", IValueContainer::noop);
        if (FuelContainer.getValue() instanceof Integer Fuel) {
            this.fuel = Fuel;
        }
        IValueContainer<?> CoolerContainer = getMultiblockState().getMatchContext()
                .getOrCreate("CoolerValue", IValueContainer::noop);
        if (CoolerContainer.getValue() instanceof Integer Cooler) {
            this.cooler = Cooler;
        }
        HeatSubs.initialize(getLevel());
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        fuel = 0;
        cooler = 0;
        heatAdjacent = 0;
        coolerAdjacent = 0;
    }

    @Override
    public void afterWorking() {
        parallel = 0;
    }

    @Override
    public void doExplosion(BlockPos pos, float explosionPower) {
        var machine = this.self();
        var level = machine.getLevel();
        level.removeBlock(machine.getPos(), false);
        level.explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                explosionPower, Level.ExplosionInteraction.BLOCK);
    }

    protected void HeatUpdate() {
        if (getOffsetTimer() % 20 == 0) {
            if (heat > 1500) {
                if (damaged > 99) {
                    doExplosion(centrePos, fuel);
                } else {
                    damaged += Math.max(1, heatAdjacent / 6);
                }
            }
            if (!getRecipeLogic().isWorking()) {
                if (heat > 298) {
                    heat--;
                } else if (damaged > 0) {
                    damaged--;
                }
            }
        }
    }

    public static GTRecipe recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe) {
        if (machine instanceof FissionReactorMachine fissionReactorMachine) {
            Pair<GTRecipe, Integer> result = GTRecipeModifiers.accurateParallel(machine, recipe,
                    fissionReactorMachine.fuel, false);
            GTRecipe recipe1 = result.getFirst();
            fissionReactorMachine.parallel = result.getSecond();
            return recipe1;
        }
        return null;
    }

    private boolean inputWater(double amount) {
        boolean value = MachineIO.inputFluid(this, GTMaterials.DistilledWater.getFluid((long) (amount * 800)));
        double steamMultiplier = heat > 373 ? 160 : 160 / Math.pow(1.4, 373 - heat);
        if (value) MachineIO.outputFluid(this, GTMaterials.Steam.getFluid((long) (amount * 800 * steamMultiplier)));
        return value;
    }

    private boolean inputSodiumPotassium(double amount) {
        boolean value = MachineIO.inputFluid(this, GTMaterials.SodiumPotassium.getFluid((long) (amount * 20)));
        if (heat > 825) {
            if (value) MachineIO.outputFluid(this, GTMaterials.get("supercritical_sodium_potassium").getFluid((long) (amount * 20)));
        } else if (value) MachineIO.outputFluid(this, GTMaterials.get("hot_sodium_potassium").getFluid((long) (amount * 20)));
        return value;
    }

    @Override
    public void onWaiting() {
        super.onWaiting();
        getRecipeLogic().resetRecipeLogic();
    }

    @Override
    public boolean onWorking() {
        boolean value = super.onWorking();
        if (getOffsetTimer() % 20 == 0) {
            GTRecipe recipe = getRecipeLogic().getLastRecipe();
            int h = recipe.data.getInt("FRheat");
            double required = (double) (h * parallel * heat) / 1500;
            double surplus = ((cooler - ((double) coolerAdjacent / 3)) * 8) - required;
            if (surplus >= 0) {
                if (inputWater(required)) {
                    while (surplus >= required && getProgress() < getMaxProgress()) {
                        if (inputWater(required)) {
                            surplus = surplus - required;
                            getRecipeLogic().setProgress(getProgress() + 20);
                        } else {
                            break;
                        }
                    }
                    if (heat > 298 && surplus >= required && inputWater(required)) {
                        heat--;
                    }
                    return value;
                } else if (inputSodiumPotassium(required)) {
                    while (surplus >= required && getProgress() < getMaxProgress()) {
                        if (inputSodiumPotassium(required)) {
                            surplus = surplus - required;
                            getRecipeLogic().setProgress(getProgress() + 20);
                        } else {
                            break;
                        }
                    }
                    if (heat > 298 && surplus >= required && inputSodiumPotassium(required)) {
                        heat--;
                    }
                    return value;
                }
            }
            heat += h * heatAdjacent;
        }
        return value;
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        if (isFormed()) {
            textList.add(Component.translatable("gtceu.machine.fission_reactor.fuel", fuel, heatAdjacent - 1));
            textList.add(Component.translatable("gtceu.machine.fission_reactor.cooler", cooler, coolerAdjacent));
            textList.add(Component.translatable("gtceu.machine.fission_reactor.heat", heat));
            textList.add(Component.translatable("gtceu.machine.fission_reactor.damaged", damaged).append("%"));
        }
    }
}
