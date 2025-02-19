package com.mraof.minestuck.computer.editmode;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.gui.EditmodeSettingsScreen;
import com.mraof.minestuck.network.ServerEditPacket;
import com.mraof.minestuck.network.data.EditmodeLocationsPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = Minestuck.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class ClientEditmodeData
{
	private static boolean activated;
	@Nullable
	private static EditmodeLocations locations;
	@Nullable
	private static ResourceKey<Level> clientLand;
	
	public static boolean isInEditmode()
	{
		return activated;
	}
	
	@Nullable
	public static EditmodeLocations getLocations()
	{
		return locations;
	}
	
	@Nullable
	public static ResourceKey<Level> getClientLand()
	{
		return clientLand;
	}
	
	private static void disable()
	{
		activated = false;
		locations = null;
		clientLand = null;
	}
	
	public static void onActivatePacket()
	{
		activated = true;
	}
	
	public static void onExitPacket(ServerEditPacket.Exit ignored)
	{
		Player player = Minecraft.getInstance().player;
		if(player != null)
			player.fallDistance = 0;
		disable();
	}
	
	public static void onLocationsPacket(EditmodeLocationsPacket packet)
	{
		locations = packet.locations();
		clientLand = packet.land();
		if(Minecraft.getInstance().screen instanceof EditmodeSettingsScreen screen)
			screen.recreateTeleportButtons();
	}
	
	@SubscribeEvent
	public static void onWorldUnload(LevelEvent.Unload event)
	{
		if(event.getLevel().isClientSide())
			disable();
	}
}
