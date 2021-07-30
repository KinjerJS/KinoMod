package fr.kinjer.kinomod.helper;

import com.google.common.base.Optional;

import fr.kinjer.kinomod.KinoMod;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.Consumer;

public class HelperNBT {

    @Nonnull
    public static NBTTagCompound getPersistentData(Entity entity) {
        return getPersistentData(entity.getEntityData());
    }

    @Nonnull
    public static NBTTagCompound getPersistentData(ItemStack item) {
        return getPersistentData(getData(item));
    }

    @Nonnull
    public static NBTTagCompound getPersistentData(NBTTagCompound base) {
        NBTTagCompound compound;
        if (hasPersistentData(base)) {
            compound = base.getCompoundTag(KinoMod.MODID);
        } else {
            compound = new NBTTagCompound();
            base.setTag(KinoMod.MODID, compound);
        }
        return compound;
    }

    public static boolean hasPersistentData(Entity entity) {
        return hasPersistentData(entity.getEntityData());
    }

    public static boolean hasPersistentData(ItemStack item) {
        return item.getTagCompound() != null && hasPersistentData(item.getTagCompound());
    }

    public static boolean hasPersistentData(NBTTagCompound base) {
        return base.hasKey(KinoMod.MODID) && base.getTag(KinoMod.MODID) instanceof NBTTagCompound;
    }


    public static void removePersistentData(Entity entity) {
        removePersistentData(entity.getEntityData());
    }

    public static void removePersistentData(ItemStack item) {
        if (item.hasTagCompound()) removePersistentData(item.getTagCompound());
    }

    public static void removePersistentData(NBTTagCompound base) {
        base.removeTag(KinoMod.MODID);
    }


    public static NBTTagCompound getData(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null) {
            compound = new NBTTagCompound();
            stack.setTagCompound(compound);
        }
        return compound;
    }

    public static void setBlockState(NBTTagCompound cmp, String key, IBlockState state) {
        NBTTagCompound serialized = getBlockStateNBTTag(state);
        if (serialized != null) {
            cmp.setTag(key, serialized);
        }
    }

    @Nonnull
    public static NBTTagCompound getBlockStateNBTTag(IBlockState state) {
        if(state.getBlock().getRegistryName() == null) {
            state = Blocks.AIR.getDefaultState();
        }
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("registryName", state.getBlock().getRegistryName().toString());
        NBTTagList properties = new NBTTagList();
        for (IProperty property : state.getPropertyKeys()) {
            NBTTagCompound propTag = new NBTTagCompound();
            try {
                propTag.setString("value", property.getName(state.getValue(property)));
            } catch (Exception exc) {
                continue;
            }
            propTag.setString("property", property.getName());
            properties.appendTag(propTag);
        }
        tag.setTag("properties", properties);
        return tag;
    }

    public static void setAsSubTag(NBTTagCompound compound, String tag, Consumer<NBTTagCompound> applyFct) {
        NBTTagCompound newTag = new NBTTagCompound();
        applyFct.accept(newTag);
        compound.setTag(tag, newTag);
    }

    public static void setStack(NBTTagCompound compound, String tag, ItemStack stack) {
        setAsSubTag(compound, tag, stack::writeToNBT);
    }

    public static void removeUUID(NBTTagCompound compound, String key) {
        compound.removeTag(key + "Most");
        compound.removeTag(key + "Least");
    }

    public static ItemStack getStack(NBTTagCompound compound, String tag) {
        return getStack(compound, tag, ItemStack.EMPTY);
    }

    public static ItemStack getStack(NBTTagCompound compound, String tag, ItemStack defaultValue) {
        if (compound.hasKey(tag)) {
            return new ItemStack(compound.getCompoundTag(tag));
        }
        return defaultValue;
    }

    public static boolean getBoolean(NBTTagCompound compound, String tag, boolean defaultValue) {
        return compound.hasKey(tag) ? compound.getBoolean(tag) : defaultValue;
    }

    public static String getString(NBTTagCompound compound, String tag, String defaultValue) {
        return compound.hasKey(tag) ? compound.getString(tag) : defaultValue;
    }

    public static int getInteger(NBTTagCompound compound, String tag, int defaultValue) {
        return compound.hasKey(tag) ? compound.getInteger(tag) : defaultValue;
    }

    public static double getDouble(NBTTagCompound compound, String tag, double defaultValue) {
        return compound.hasKey(tag) ? compound.getDouble(tag) : defaultValue;
    }

    public static float getFloat(NBTTagCompound compound, String tag, float defaultValue) {
        return compound.hasKey(tag) ? compound.getFloat(tag) : defaultValue;
    }

    public static byte getByte(NBTTagCompound compound, String tag, byte defaultValue) {
        return compound.hasKey(tag) ? compound.getByte(tag) : defaultValue;
    }

    public static short getShort(NBTTagCompound compound, String tag, short defaultValue) {
        return compound.hasKey(tag) ? compound.getShort(tag) : defaultValue;
    }

    public static long getLong(NBTTagCompound compound, String tag, long defaultValue) {
        return compound.hasKey(tag) ? compound.getLong(tag) : defaultValue;
    }

    public static void writeBlockPosToNBT(BlockPos pos, NBTTagCompound compound) {
        compound.setInteger("bposX", pos.getX());
        compound.setInteger("bposY", pos.getY());
        compound.setInteger("bposZ", pos.getZ());
    }

    public static BlockPos readBlockPosFromNBT(NBTTagCompound compound) {
        int x = compound.getInteger("bposX");
        int y = compound.getInteger("bposY");
        int z = compound.getInteger("bposZ");
        return new BlockPos(x, y, z);
    }

    public static void writeBoundingBox(AxisAlignedBB box, NBTTagCompound tag) {
        tag.setDouble("boxMinX", box.minX);
        tag.setDouble("boxMinY", box.minY);
        tag.setDouble("boxMinZ", box.minZ);
        tag.setDouble("boxMaxX", box.maxX);
        tag.setDouble("boxMaxY", box.maxY);
        tag.setDouble("boxMaxZ", box.maxZ);
    }

    public static AxisAlignedBB readBoundingBox(NBTTagCompound tag) {
        return new AxisAlignedBB(
                tag.getDouble("boxMinX"),
                tag.getDouble("boxMinY"),
                tag.getDouble("boxMinZ"),
                tag.getDouble("boxMaxX"),
                tag.getDouble("boxMaxY"),
                tag.getDouble("boxMaxZ"));
    }
}
