package fr.kinjer.kinomod.init;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.fluid.FluidKinium;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class InitFluids {

	public static final Fluid KINIUM = new FluidKinium("kinium_molten");
	public static final Fluid BALIUM = new FluidKinium("balium_molten");
	public static final Fluid SEMINIUM = new FluidKinium("seminium_molten");
	public static final Fluid DALIUM = new FluidKinium("dalium_molten");

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
