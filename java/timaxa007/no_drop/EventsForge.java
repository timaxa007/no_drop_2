package timaxa007.no_drop;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class EventsForge {

	@SubscribeEvent
	public void doEntityConstructing(EntityEvent.EntityConstructing event) {
		if (!(event.entity instanceof EntityPlayer)) return;
		EntityPlayer player = (EntityPlayer)event.entity;
		if (NoDropsIEEP.get(player) == null) NoDropsIEEP.reg(player);
	}

	@SubscribeEvent
	public void doPlayerDropsEvent(PlayerDropsEvent event) {
		if (event.drops.isEmpty()) return;

		NoDropsIEEP noDropsIEEP = NoDropsIEEP.get(event.entityPlayer);
		if (noDropsIEEP == null) return;

		for (int i = 0; i < event.drops.size(); ++i) {
			EntityItem ei = event.drops.get(i);
			ItemStack itemStack = ei.getEntityItem();
			if (!itemStack.hasTagCompound()) continue;
			if (itemStack.getTagCompound().hasKey("NoDrop")) {
				noDropsIEEP.save_drops.add(itemStack);
				event.drops.remove(i);
			}
		}

	}

	@SubscribeEvent
	public void doPlayerEventClone(PlayerEvent.Clone event) {
		NoDropsIEEP noDropsIEEP_old = NoDropsIEEP.get(event.original);
		if (noDropsIEEP_old == null) return;
		if (noDropsIEEP_old.save_drops.isEmpty()) return;

		NoDropsIEEP noDropsIEEP_new = NoDropsIEEP.get(event.entityPlayer);
		if (noDropsIEEP_new == null) return;

		noDropsIEEP_new.save_drops.addAll(noDropsIEEP_old.save_drops);
		noDropsIEEP_old.save_drops.clear();
	}

	@SubscribeEvent
	public void doItemTooltipEvent(ItemTooltipEvent event) {
		if (event.itemStack == null) return;
		if (!event.itemStack.hasTagCompound()) return;
		if (event.itemStack.getTagCompound().hasKey("NoDrop"))
			event.toolTip.add("No Drop");
	}

}
