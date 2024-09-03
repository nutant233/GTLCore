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
import net.minecraft.network.chat.Component;
import org.gtlcore.gtlcore.api.pattern.util.IValueContainer;
import org.gtlcore.gtlcore.utils.MachineIO;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FissionReactorMachine extends WorkableElectricMultiblockMachine implements IExplosionMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            FissionReactorMachine.class, WorkableMultiblockMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    private int heat = 298;
    @Persisted
    private int damaged = 0;
    @Persisted
    private int fuel = 0, cooler = 0;

    protected ConditionalSubscriptionHandler HeatSubs;

    public FissionReactorMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
        this.HeatSubs = new ConditionalSubscriptionHandler(this, this::HeatUpdate, this::isFormed);
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        IValueContainer<?> FuelContainer = getMultiblockState().getMatchContext()
                .getOrCreate("FuelAssemblyValue", IValueContainer::noop);
        if (FuelContainer.getValue() instanceof Integer Fuel) {
            this.fuel = Fuel;
        }
        IValueContainer<?> CoolerContainer = getMultiblockState().getMatchContext()
                .getOrCreate("CoolerValue", IValueContainer::noop);
        if (CoolerContainer.getValue() instanceof Integer Cooler) {
            this.cooler = Cooler * 8;
        }
        HeatSubs.initialize(getLevel());
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        fuel = 0;
        cooler = 0;
    }

    protected void HeatUpdate() {
        if (getOffsetTimer() % 20 == 0) {
            if (heat > 1500) {
                if (damaged > 99) {
                    doExplosion(20);
                } else {
                    damaged++;
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
            recipe1.data.putInt("parallel", result.getSecond());
            return recipe1;
        }
        return null;
    }

    private boolean inputWater(FissionReactorMachine machine, double amount) {
        boolean value = MachineIO.inputFluid(machine, GTMaterials.DistilledWater.getFluid((long) (amount * 800)));
        double steamMultiplier = heat > 373 ? 160 : 160 / Math.pow(1.4, 373 - heat);
        if (value) MachineIO.outputFluid(machine, GTMaterials.Steam.getFluid((long) (amount * 800 * steamMultiplier)));
        return value;
    }

    private boolean inputSodiumPotassium(FissionReactorMachine machine, double amount) {
        boolean value = MachineIO.inputFluid(machine, GTMaterials.SodiumPotassium.getFluid((long) (amount * 20)));
        if (heat > 825) {
            if (value) MachineIO.outputFluid(machine, GTMaterials.get("supercritical_sodium_potassium").getFluid((long) (amount * 20)));
        } else if (value) MachineIO.outputFluid(machine, GTMaterials.get("hot_sodium_potassium").getFluid((long) (amount * 20)));
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
            FissionReactorMachine machine = (FissionReactorMachine) getRecipeLogic().getMachine();
            int h = recipe.data.getInt("FRheat");
            int f = recipe.data.getInt("parallel");
            double required = (double) (h * f * heat) / 1500;
            double surplus = cooler - required;
            if (surplus >= 0) {
                if (inputWater(machine, required)) {
                    while (surplus >= required && getProgress() < getMaxProgress()) {
                        if (inputWater(machine, required)) {
                            surplus = surplus - required;
                            getRecipeLogic().setProgress(getProgress() + 20);
                        } else {
                            break;
                        }
                    }
                    if (heat > 298 && surplus >= required && inputWater(machine, required)) {
                        heat--;
                    }
                    return value;
                } else if (inputSodiumPotassium(machine, required)) {
                    while (surplus >= required && getProgress() < getMaxProgress()) {
                        if (inputSodiumPotassium(machine, required)) {
                            surplus = surplus - required;
                            getRecipeLogic().setProgress(getProgress() + 20);
                        } else {
                            break;
                        }
                    }
                    if (heat > 298 && surplus >= required && inputSodiumPotassium(machine, required)) {
                        heat--;
                    }
                    return value;
                }
            }
            heat += h;
        }
        return value;
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        if (isFormed()) {
            textList.add(Component.translatable("gtceu.machine.fission_reactor.fuel", fuel));
            textList.add(Component.translatable("gtceu.machine.fission_reactor.cooler", (cooler / 8)));
            textList.add(Component.translatable("gtceu.machine.fission_reactor.heat", heat));
            textList.add(Component.translatable("gtceu.machine.fission_reactor.damaged",
                    damaged).append("%"));
        }
    }
}
