package org.gtlcore.gtlcore.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class GTLExplosion extends Explosion {

    private final BlockPos center;
    private final Level level;
    private final int radius;

    public GTLExplosion(BlockPos center, Level level, int radius) {
        super(level, null, null, null, center.getX(), center.getY(), center.getZ(), radius, false, BlockInteraction.DESTROY);
        this.center = center;
        this.level = level;
        this.radius = radius;
    }

    @Override
    public void finalizeExplosion(boolean spawnParticles) {
        int radiusSquared = (radius - 2) * (radius - 2);
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + y * y + z * z <= radiusSquared) {
                        BlockPos pos = center.offset(x, y, z);
                        BlockState state = level.getBlockState(pos);
                        if (state.isAir()) continue;
                        state.onBlockExploded(level, pos, this);
                    }
                }
            }
        }
        super.explode();
        super.finalizeExplosion(spawnParticles);
    }
}
