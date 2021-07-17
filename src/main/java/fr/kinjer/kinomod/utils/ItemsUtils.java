package fr.kinjer.kinomod.utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemsUtils {
	
	public DataInputStream datain;
	
	public void ItemUtils() {
	}
	
	public UUID getUUID() {

		try {
			long msb = datain.readLong();
			long lsb = datain.readLong();
			return new UUID(msb, lsb);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	 public static ItemStack getItem(String oreDict, int stackSize){
	        ItemStack item = ItemStack.EMPTY;
	 		List<ItemStack> list = OreDictionary.getOres(oreDict);
	        if(!list.isEmpty()){
	            item = list.get(0).copy(); {
	                item.setCount(stackSize);
	            }
	        }
	        return item;
	    }

}
