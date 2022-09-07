package com.taiyouka.intelliweapon.modules.artillery;

import com.taiyouka.intelliweapon.IntelliWeapon;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class MachineGun extends AbsArtilleryBlockEntity{

    public MachineGun(BlockPos blockPos, BlockState blockState) {
        super(IntelliWeapon.BlockEntityRegistry.MACHINE_GUN.get(), blockPos, blockState);
    }

    private static final Random random = new Random();
    private int nextShoot = 10;

    @Override
    public void fire() {
        Vec3 direction = this.getDirection();
        double x = this.getBlockPos().getX() + direction.x + 0.5d;
        double y = this.getBlockPos().getY() + direction.y + 0.5d;
        double z = this.getBlockPos().getZ() + direction.z + 0.5d;
        AbstractArrow arrow = new Arrow(level, x, y, z);
        //arrow.set
        arrow.shoot(direction.x, direction.y, direction.z, 2.0f, 0.0f);
        level.addFreshEntity(arrow);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if(level.isClientSide()) {
            return;
        }
        MachineGun machineGun = (MachineGun)blockEntity;
        if(machineGun == null) {
            return;
        }
        machineGun.rotate(6.0f, 0.0f);
        if(level.getServer().getTickCount() % machineGun.nextShoot == 0)
        {
            machineGun.fire();
            machineGun.nextShoot = random.nextInt(6, 15);
        }
    }
}
