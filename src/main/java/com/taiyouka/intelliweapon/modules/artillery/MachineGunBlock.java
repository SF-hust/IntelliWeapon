package com.taiyouka.intelliweapon.modules.artillery;

import com.taiyouka.intelliweapon.IntelliWeapon;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class MachineGunBlock extends BaseEntityBlock {

    public MachineGunBlock() {
        super(Properties.of(Material.METAL));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MachineGun(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return type == IntelliWeapon.BlockEntityRegistry.MACHINE_GUN.get() ? MachineGun::tick : null;
    }
}
