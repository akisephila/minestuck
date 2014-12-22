package com.mraof.minestuck.world.gen.lands.decorator;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.mraof.minestuck.block.BlockLayered;
import com.mraof.minestuck.util.Debug;
import com.mraof.minestuck.world.gen.ChunkProviderLands;

public class LayeredBlockDecorator implements ILandDecorator
{
	
	private Block block;
	private boolean baseHeight;
	
	public LayeredBlockDecorator(Block layeredBlock, boolean baseHeight)
	{
		this.block = layeredBlock;
		this.baseHeight = baseHeight;
	}
	
	@Override
	public void generate(World world, Random random, int chunkX, int chunkZ, ChunkProviderLands provider)
	{
//		byte[] heightMap = new byte[16*16];
//		for(int x = 0; x < 16; x++)
//			for(int z = 0; z < 16; z++)
//			{
//				byte height = (byte) (baseHeight ? 1 : 0);
//				BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x + (chunkX << 4) + 8, 0, z + (chunkZ << 4) + 8));
//				if(!(world.getBlockState(pos).getBlock().isAir(world, pos) || world.getBlockState(pos).getBlock() == block) || !block.canPlaceBlockAt(world, pos))
//					continue;
//				
//				for(EnumFacing side : new EnumFacing[] {EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST})
//				{
//					BlockPos newPos = pos.offset(side);
//					if(block.canPlaceBlockAt(world, newPos.up()))
//					{
//						height++;
//						if(block.canPlaceBlockAt(world, newPos.up(2)))
//							height++;
//					}
//				}
//				
//				heightMap[(x << 4) | z] = height;
//			}
//		for(int x = 0; x < 16; x++)
//			for(int z = 0; z < 16; z++)
//				setBlock(world, world.getTopSolidOrLiquidBlock(new BlockPos(x + (chunkX << 4) + 8, 0, z + (chunkZ << 4) + 8)), heightMap[(x << 4) | z]);
		for(int x = 0; x < 16; x++)
			for(int z = 0; z < 16; z++)
			{
				BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x + (chunkX << 4) + 8, 0, z + (chunkZ << 4) + 8));
				if(world.getBlockState(pos).getBlock().isAir(world, pos) && block.canPlaceBlockAt(world, pos))
					setBlock(world, pos, 1);
			}
	}
	
	@Override
	public float getPriority()
	{
		return 0.7F;
	}
	
	private void setBlock(World world, BlockPos pos, int height)
	{
		if(world.getBlockState(pos).getBlock() == block)
			height += block.getMetaFromState(world.getBlockState(pos)) + 1;
		if(height <= 0)
			return;
		int nextHeight = 0;
		if(height > 8)
		{
			nextHeight = height - 8;
			height = 8;
		}
		
		if(height == 8)
		{
			Block fullBlock = ((BlockLayered) block).fullBlock;
			world.setBlockState(pos, fullBlock.getDefaultState(), 2);
		}
		else
		{
			world.setBlockState(pos, block.getStateFromMeta(height - 1), 2);
		}
		
		if(nextHeight > 0)
			setBlock(world, pos.up(), nextHeight);
	}
	
}
