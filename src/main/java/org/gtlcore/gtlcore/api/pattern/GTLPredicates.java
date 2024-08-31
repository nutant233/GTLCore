package org.gtlcore.gtlcore.api.pattern;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.MultiblockState;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate;
import com.gregtechceu.gtceu.api.pattern.error.PatternStringError;
import com.gregtechceu.gtceu.api.pattern.predicates.PredicateBlocks;
import com.gregtechceu.gtceu.api.pattern.predicates.SimplePredicate;
import com.lowdragmc.lowdraglib.utils.BlockInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import org.gtlcore.gtlcore.api.pattern.util.IValueContainer;
import org.gtlcore.gtlcore.api.pattern.util.SimpleValueContainer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class GTLPredicates {

    public static Map<Integer, Supplier<Block>> createTierCasingsMap() {
        return new HashMap<>();
    }

    public static TraceabilityPredicate tierCasings(Map<Integer, Supplier<Block>> map, String tierType) {
        return new TraceabilityPredicate(blockWorldState -> {
            var blockState = blockWorldState.getBlockState();
            for (var entry : map.entrySet()) {
                if (blockState.is(entry.getValue().get())) {
                    var stats = entry.getKey();
                    Object currentCoil = blockWorldState.getMatchContext().getOrPut(tierType, stats);
                    if (!currentCoil.equals(stats)) {
                        blockWorldState.setError(new PatternStringError("gtceu.multiblock.pattern.error.tier"));
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }, () -> map.values().stream()
                .map(blockSupplier -> BlockInfo.fromBlockState(blockSupplier.get().defaultBlockState()))
                .toArray(BlockInfo[]::new))
                .addTooltips(Component.translatable("gtceu.multiblock.pattern.error.tier"));
    }

    public static TraceabilityPredicate countBlock(String name, Block... blocks) {
        TraceabilityPredicate inner = Predicates.blocks(blocks);
        Predicate<MultiblockState> predicate = state -> {
            if (inner.test(state)) {
                IValueContainer<?> currentContainer = state.getMatchContext().getOrPut(name + "Value",
                        new SimpleValueContainer<>(0, (integer, block, tierType) -> ++integer));
                currentContainer.operate(state.getBlockState().getBlock(), null);
                return true;
            }
            return false;
        };
        BlockInfo[] candidates = inner.common.stream()
                .map(p -> p.candidates)
                .filter(Objects::nonNull)
                .map(Supplier::get)
                .flatMap(Arrays::stream)
                .toArray(BlockInfo[]::new);
        return new TraceabilityPredicate(new SimplePredicate(predicate, () -> candidates));
    }

    public static TraceabilityPredicate RotorBlock() {
        return new TraceabilityPredicate(
                new PredicateBlocks(
                        PartAbility.ROTOR_HOLDER.getAllBlocks().toArray(Block[]::new)) {

                    @Override
                    public boolean test(MultiblockState blockWorldState) {
                        if (super.test(blockWorldState)) {
                            var level = blockWorldState.getWorld();
                            var pos = blockWorldState.getPos();
                            var machine = MetaMachine.getMachine(level, pos);
                            if (machine instanceof ITieredMachine tieredMachine) {
                                int tier = blockWorldState
                                        .getMatchContext()
                                        .getOrPut("ReinforcedRotor", tieredMachine.getTier());
                                if (tier != tieredMachine.getTier()) {
                                    return false;
                                }
                                return level
                                        .getBlockState(pos.relative(machine.getFrontFacing()))
                                        .isAir();
                            }
                        }
                        return false;
                    }
                });
    }
}
