package fr.kinjer.kinomod.client;

import java.io.File;

import org.lwjgl.input.Keyboard;

import fr.kinjer.kinomod.handler.HandlerSounds;
import fr.kinjer.kinomod.init.InitItems;
import fr.kinjer.kinomod.common.CommonProxy;
import fr.kinjer.kinomod.entity.projectile.ProjectileEntityBismuthBall;
import fr.kinjer.kinomod.handler.HandlerRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.entity.RenderSpectralArrow;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.SidedProxy;

public class ClientProxy extends CommonProxy {

	@SidedProxy(clientSide = "fr.kinjer.kinomod.client.ClientProxy", serverSide = "fr.kinjer.kinomod.common.CommonProxy")
	public static CommonProxy proxy;

	public static final String PACKAGE = "fr.kinjer.kinomod.client.ClientProxy";

	public EntityPlayer getPlayer() {
		return Minecraft.getMinecraft().player;
	}

	public void registerEventHandlers() {
		super.registerEventHandlers();
	}
	
	@Override
	public void render() {
		
//		RenderingRegistry.registerEntityRenderingHandler(ProjectileEntityBismuthBall.class, new RenderSnowball<ProjectileEntityBismuthBall>(Minecraft.getMinecraft().getRenderManager(), InitItems.bismuth_ball, Minecraft.getMinecraft().getRenderItem()));
		
	}

	@Override
	public void preInit(File file) {
		super.preInit(file);
		MinecraftForge.EVENT_BUS.register(new HandlerRender());
		MinecraftForge.EVENT_BUS.register(new HandlerSounds());
	}

	@Override
	public void init() {
		super.init();

	}

	public static KeyBinding[] keyBindings;
	{

		keyBindings = new KeyBinding[2];

		keyBindings[0] = new KeyBinding("key.changemod", Keyboard.KEY_F4, "key.kinomod.category");
		keyBindings[1] = new KeyBinding("key.misc", Keyboard.KEY_F8, "key.kinomod.category");

		for (int i = 0; i < keyBindings.length; ++i) {
			ClientRegistry.registerKeyBinding(keyBindings[i]);
		}
	}
}
