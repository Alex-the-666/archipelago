package com.github.alexthe666.archipelago.block;

import com.github.alexthe666.archipelago.Archipelago;
import com.github.alexthe666.archipelago.enums.TropicTreeType;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockArchipelagoLog extends BlockLog {

    public BlockArchipelagoLog(TropicTreeType tree) {
        super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
        this.setCreativeTab(Archipelago.tab);
        this.setUnlocalizedName("archipelago." + tree.name().toLowerCase() + "_log");
        this.setCreativeTab(Archipelago.tab);
        GameRegistry.registerBlock(this, tree.name().toLowerCase() + "_log");
        Archipelago.PROXY.addItemRender(Item.getItemFromBlock(this), tree.name().toLowerCase() + "_log");
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = this.getDefaultState();

        switch (meta & 0b1100) {
            case 0b0000:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
                break;

            case 0b0100:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
                break;

            case 0b1000:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
                break;

            case 0b1100:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
                break;
        }

        return state;
    }

    @Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        switch (state.getValue(LOG_AXIS)) {
            case X:
                return 0b0100;
            case Y:
                return 0b0000;
            case Z:
                return 0b1000;
            case NONE:
                return 0b1100;
        }
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LOG_AXIS);
    }
}