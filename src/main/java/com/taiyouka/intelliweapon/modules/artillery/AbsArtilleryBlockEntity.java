package com.taiyouka.intelliweapon.modules.artillery;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public abstract class AbsArtilleryBlockEntity extends BlockEntity {
    public AbsArtilleryBlockEntity(BlockEntityType<?> type, BlockPos blockPos, BlockState blockState) {
        super(type, blockPos, blockState);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("rotV", rotV);
        tag.putFloat("rotH", rotH);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        rotV = tag.getFloat("rotV");
        rotH = tag.getFloat("rotH");
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
    }

    protected float rotVUp = -75.0f;
    protected float rotVDown = -15.0f;
    protected float rotHLeft = -180.0f;
    protected float rotHRight = 180.0f;

    protected float rotV = -30.0f;
    protected float rotH = 0.0f;

    public void rotate(float hor, float ver) {
        setRotation(hor + rotH, ver + rotV);
    }

    public void setRotation(float hor, float ver) {
        this.rotH = Mth.clamp(Mth.wrapDegrees(hor), rotHLeft, rotHRight);
        rotV = Mth.clamp(ver, rotVUp, rotVDown);
        setChanged();
    }

    public Vec2 getRotation() {
        return new Vec2(rotV, rotH);
    }

    public Vec3 getDirection() {
        return Vec3.directionFromRotation(rotV, rotH);
    }

    public void fire() {
    }

    public void fire(ItemStack itemStack) {
    }
}
