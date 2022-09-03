package com.mraof.minestuck.world.lands;

import com.mraof.minestuck.player.PlayerIdentifier;
import com.mraof.minestuck.skaianet.SkaianetHandler;
import com.mraof.minestuck.world.gen.LandChunkGenerator;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

/**
 * Container for land dimension information between info being loaded,
 * and the dimensions actually being registered.
 */
public class LandInfo
{
	private static final Logger LOGGER = LogManager.getLogger();
	
	public static final String LAND_ENTRY = "minestuck.land_entry";
	
	public final PlayerIdentifier identifier;
	private final LandTypePair.LazyInstance landAspects;
	private final ResourceKey<Level> dimension;
	@Nullable
	private BlockPos gatePos = null;
	private int spawnY = -1;
	@Nullable
	private LandTypePair cachedAspects;
	
	public LandInfo(PlayerIdentifier identifier, LandTypePair landTypes, ResourceKey<Level> dimensionType, Random random)
	{
		this.identifier = Objects.requireNonNull(identifier);
		cachedAspects = Objects.requireNonNull(landTypes);
		this.landAspects = landTypes.createLazy();
		dimension = Objects.requireNonNull(dimensionType);
	}
	
	private LandInfo(SkaianetHandler handler, PlayerIdentifier identifier, LandTypePair.LazyInstance landAspects, ResourceKey<Level> dimensionType)
	{
		this.identifier = identifier;
		this.landAspects = landAspects;
		dimension = dimensionType;
	}
	
	@Nullable
	public BlockPos getGatePos()
	{
		return gatePos;
	}
	
	public void setGatePos(BlockPos pos)
	{
		gatePos = pos;
	}
	
	/**
	 * Should NOT be called during a very early loading stage (such as when reading data through {@link com.mraof.minestuck.MSWorldPersistenceHook}).
	 * Because world persistence is loaded alongside world-specific registries, there's not a guarrantee that it is loaded and ready before skaianet is loading data.
	 * (Though the only thing that might change in the registry would be missing land aspects that may get a dummy lanspect created for them)
	 */
	public LandTypePair getLandAspects()
	{
		if(cachedAspects == null)
			cachedAspects = landAspects.create();
		return cachedAspects;
	}
	
	public LandTypePair.LazyInstance getLazyLandAspects()
	{
		return landAspects;
	}
	
	public ResourceKey<Level> getDimensionType()
	{
		return dimension;
	}
	
	public BlockPos getSpawn()
	{
		return spawnY == -1 ? new BlockPos(0, 127, 0) : new BlockPos(0, spawnY, 0);
	}
	
	public void setSpawn(int y)
	{
		if(spawnY == -1)
			spawnY = y;
		else throw new IllegalStateException("Has already set spawn for dimension " + dimension);
	}
	
	/**
	 * Saves the info container to nbt, except for the identifier
	 */
	public CompoundTag write(CompoundTag nbt)
	{
		landAspects.write(nbt);
		ResourceLocation.CODEC.encodeStart(NbtOps.INSTANCE, dimension.location()).resultOrPartial(LOGGER::error)
				.ifPresent(tag -> nbt.put("dim_type", tag));
		if(gatePos != null)
		{
			nbt.putInt("gate_x", gatePos.getX());
			nbt.putInt("gate_y", gatePos.getY());
			nbt.putInt("gate_z", gatePos.getZ());
		}
		nbt.putInt("spawn_y", spawnY);
		
		return nbt;
	}
	
	public static LandInfo read(CompoundTag nbt, SkaianetHandler handler, PlayerIdentifier identifier)
	{
		LandTypePair.LazyInstance aspects = LandTypePair.LazyInstance.read(nbt);
		ResourceKey<Level> dimension = Level.RESOURCE_KEY_CODEC.parse(NbtOps.INSTANCE, nbt.get("dim_type")).resultOrPartial(LOGGER::error).get();	//TODO properly use optional, maybe by writing LandInfo with codec
		
		LandInfo info = new LandInfo(handler, identifier, aspects, dimension);
		
		if(nbt.contains("gate_x", Tag.TAG_ANY_NUMERIC))
		{
			info.gatePos = new BlockPos(nbt.getInt("gate_x"), nbt.getInt("gate_y"), nbt.getInt("gate_z"));
		}
		info.spawnY = nbt.getInt("spawn_y");
		
		return info;
	}
	
	public void sendLandEntryMessage(ServerPlayer player)
	{
		LandChunkGenerator chunkGenerator = (LandChunkGenerator) player.getLevel().getChunkSource().getGenerator();
		player.sendMessage(new TranslatableComponent(LAND_ENTRY, chunkGenerator.namedTypes.asComponent()), Util.NIL_UUID);
	}
}