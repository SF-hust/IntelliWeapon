package com.taiyouka.intelliweapon;

import com.mojang.logging.LogUtils;
import com.taiyouka.intelliweapon.modules.artillery.MachineGun;
import com.taiyouka.intelliweapon.modules.artillery.MachineGunBlock;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(IntelliWeapon.MODID)
public class IntelliWeapon
{
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final String MODID = "intelliweapon";

    public static final class BlockRegistry {
        private static final DeferredRegister<Block> BLOCKS =
                DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

        public static final RegistryObject<Block> MACHINE_GUN_BLOCK =
                BLOCKS.register("machine_gun_block", MachineGunBlock::new);
    }

    public static final class ItemRegistry {
        private static final DeferredRegister<Item> ITEMS =
                DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

        private static RegistryObject<Item> registerBlockItem(String name, RegistryObject<Block> blockRegistryObject, CreativeModeTab tab) {
            return ITEMS.register(name, ()->new BlockItem(blockRegistryObject.get(), new Item.Properties().tab(tab)));
        }

        public static final RegistryObject<Item> MACHINE_GUN_ITEM = registerBlockItem(
                "machine_gun_block", BlockRegistry.MACHINE_GUN_BLOCK, CreativeModeTab.TAB_COMBAT);
    }

    public static final class EntityRegistry {
        private static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
                DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);
    }

    public static final class BlockEntityRegistry {
        private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
                DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);

        private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> registerNoDataFix(
                String name, BlockEntityType.BlockEntitySupplier<T> supplier, RegistryObject<Block> blockRegistryObject) {
            return BLOCK_ENTITY_TYPES.register(name, () -> BlockEntityType.Builder.of(supplier, blockRegistryObject.get()).build(null));
        }

        public static final RegistryObject<BlockEntityType<MachineGun>> MACHINE_GUN = registerNoDataFix(
                "machine_gun_block_entity",
                MachineGun::new,
                BlockRegistry.MACHINE_GUN_BLOCK
        );
    }

    public IntelliWeapon() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        MinecraftForge.EVENT_BUS.register(this);

        BlockRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        EntityRegistry.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockEntityRegistry.BLOCK_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

}
