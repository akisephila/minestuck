package com.mraof.minestuck.network;

import com.mraof.minestuck.computer.editmode.ClientDeployList;
import com.mraof.minestuck.computer.editmode.ClientEditmodeData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Objects;

public final class ServerEditPacket
{
	public record Activate() implements MSPacket.PlayToClient
	{
		@Override
		public void encode(FriendlyByteBuf ignored)
		{}
		
		public static Activate decode(FriendlyByteBuf ignored)
		{
			return new Activate();
		}
		
		@Override
		public void execute()
		{
			ClientEditmodeData.onActivatePacket();
		}
	}
	
	public record UpdateDeployList(CompoundTag data) implements MSPacket.PlayToClient
	{
		@Override
		public void encode(FriendlyByteBuf buffer)
		{
			buffer.writeNbt(this.data);
		}
		
		public static UpdateDeployList decode(FriendlyByteBuf buffer)
		{
			CompoundTag data = Objects.requireNonNull(buffer.readNbt());
			return new UpdateDeployList(data);
		}
		
		@Override
		public void execute()
		{
			ClientDeployList.load(this);
		}
	}
	
	public record Exit() implements MSPacket.PlayToClient
	{
		@Override
		public void encode(FriendlyByteBuf ignored)
		{}
		
		public static Exit decode(FriendlyByteBuf ignored)
		{
			return new Exit();
		}
		
		@Override
		public void execute()
		{
			ClientEditmodeData.onExitPacket(this);
		}
	}
}
