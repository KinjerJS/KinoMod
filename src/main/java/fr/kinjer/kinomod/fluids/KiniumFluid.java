package fr.kinjer.kinomod.fluids;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.utils.MaterialsMod;
import net.minecraft.block.material.Material;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class KiniumFluid extends Fluid
{
//    protected static int mapColor = 0xFFFFFFFF;
//    protected static float overlayAlpha = 0.2F;
//    protected static SoundEvent emptySound = SoundEvents.ITEM_BUCKET_EMPTY;
//    protected static SoundEvent fillSound = SoundEvents.ITEM_BUCKET_FILL;
//    protected static Material material = Material.WATER;
 
    public KiniumFluid(String fluidName) 
    {
        super(fluidName, new ResourceLocation(KinoMod.MODID + ":blocks/"+fluidName+"_still"), new ResourceLocation(KinoMod.MODID + ":blocks/"+fluidName+"_flow"));
//        this.setUnlocalizedName(fluidName);
        
//		setDensity(1100);
//		setGaseous(false);
		setLuminosity(15);
//		setViscosity(6000);
//		setTemperature(500);
    }
    
    
    
 
//    public KiniumFluid(String fluidName, ResourceLocation still, ResourceLocation flowing, int mapColor) 
//    {
//        this(fluidName, still, flowing);
//        setColor(mapColor);
//    }
// 
//    public KiniumFluid(String fluidName, ResourceLocation still, ResourceLocation flowing, int mapColor, float overlayAlpha) 
//    {
//        this(fluidName, still, flowing, mapColor);
//        setAlpha(overlayAlpha);
//    }
// 
//    @Override
//    public int getColor()
//    {
//        return mapColor;
//    }
// 
//    public KiniumFluid setColor(int parColor)
//    {
//        mapColor = parColor;
//        return this;
//    }
// 
//    public float getAlpha()
//    {
//        return overlayAlpha;
//    }
// 
//    public KiniumFluid setAlpha(float parOverlayAlpha)
//    {
//        overlayAlpha = parOverlayAlpha;
//        return this;
//    }
// 
//    @Override
//    public KiniumFluid setEmptySound(SoundEvent parSound)
//    {
//        emptySound = parSound;
//        return this;
//    }
// 
//    @Override
//    public SoundEvent getEmptySound()
//    {
//        return emptySound;
//    }
// 
//    @Override
//    public KiniumFluid setFillSound(SoundEvent parSound)
//    {
//        fillSound = parSound;
//        return this;
//    }
// 
//    @Override
//    public SoundEvent getFillSound()
//    {
//        return fillSound;
//    }
// 
//    public KiniumFluid setMaterial(Material parMaterial)
//    {
//        material = parMaterial;
//        return this;
//    }
// 
//    public Material getMaterial()
//    {
//        return material;
//    }
// 
//    @Override
//    public boolean doesVaporize(FluidStack fluidStack)
//    {
//        if (block == null)
//            return false;
//        return block.getDefaultState().getMaterial() == getMaterial();
//    }

}
