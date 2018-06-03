package timaxa007.no_drop;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.item.ItemStack;

public class EventsFML {

	@SubscribeEvent
	public void doPlayerEventPlayerRespawnEvent(PlayerEvent.PlayerRespawnEvent event) {
		NoDropsIEEP noDropsIEEP = NoDropsIEEP.get(event.player);
		if (noDropsIEEP == null) return;
		if (noDropsIEEP.save_drops.isEmpty()) return;

		for (ItemStack itemStack : noDropsIEEP.save_drops)
			if (!event.player.inventory.addItemStackToInventory(itemStack))
				event.player.dropPlayerItemWithRandomChoice(itemStack, false);
		noDropsIEEP.save_drops.clear();
	}

}
