package fr.kinjer.kinomod.utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

import net.minecraft.item.ItemStack;

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

}
