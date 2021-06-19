package fr.kinjer.kinomod.init;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.fluids.KiniumFluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidsMod {

	public static final Fluid KINIUM = new KiniumFluid("kinium_molten");
	public static final Fluid BALIUM = new KiniumFluid("balium_molten");
	public static final Fluid SEMINIUM = new KiniumFluid("seminium_molten");
	public static final Fluid DALIUM = new KiniumFluid("dalium_molten");

	public static void registerFluids() {
		registerFluid(KINIUM);
		registerFluid(BALIUM);
		registerFluid(SEMINIUM);
		registerFluid(DALIUM);
	}

	public static void registerFluid(Fluid fluid) {
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
	}

}
