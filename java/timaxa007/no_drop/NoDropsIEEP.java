package timaxa007.no_drop;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants.NBT;

public class NoDropsIEEP implements IExtendedEntityProperties {

	public static final String TAG = "NoDrops";

	public final ArrayList<ItemStack> save_drops = new ArrayList<ItemStack>();

	@Override
	public void saveNBTData(NBTTagCompound nbt) {
		if (save_drops.isEmpty()) return;
		NBTTagList list = new NBTTagList();
		for (ItemStack item : save_drops) {
			NBTTagCompound compound_item = new NBTTagCompound();
			item.writeToNBT(compound_item);
			list.appendTag(compound_item);
		}
		nbt.setTag(TAG, list);
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt) {
		if (!nbt.hasKey(TAG, NBT.TAG_LIST)) return;
		NBTTagList list = nbt.getTagList(TAG, NBT.TAG_COMPOUND);
		save_drops.clear();
		for (int i = 0; i < list.tagCount(); ++i)
			save_drops.add(ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i)));
	}

	@Override
	public void init(Entity entity, World world) {}

	public static void reg(EntityPlayer player) {
		player.registerExtendedProperties(NoDropsIEEP.TAG, new NoDropsIEEP());
	}

	public static NoDropsIEEP get(EntityPlayer player) {
		return player != null ? (NoDropsIEEP)player.getExtendedProperties(TAG) : null;
	}

}
