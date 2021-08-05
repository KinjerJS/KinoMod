package fr.kinjer.kinomod.client.gui;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.containers.ContainerExtractor;
import fr.kinjer.kinomod.tileentitys.TileExtractor;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiExtractor extends GuiContainer
{
	private static final ResourceLocation background = new ResourceLocation(KinoMod.MODID,"textures/gui/container/extractor.png");
	private TileExtractor tile;
	
	public GuiExtractor(TileExtractor tile, InventoryPlayer playerInv) {
        super(new ContainerExtractor(tile, playerInv));
        this.tile = tile;
	}
	
	/*
	 * Les deux fonctions suivantes permettent de dessiner le Gui :
	 * 
	 * drawGuiContainerBackgroundLayer permet de dessiner l’arrière plan
	 * drawGuiContainerForegroundLayer permet de dessiner le permier plan Vous
	 * pouvez dessiner à l’aide des fonctions :
	 * 
	 * @code this.drawTexturedModalRect(int x, int y, int textureX, int textureY,
	 * int width, int height) x correspond à la coordonnée x de l’endroit où vous
	 * voulez afficher votre texture y correspond à la coordonnée y de l’endroit où
	 * vous voulez afficher votre texture textureX correspond à la coordonnée x du
	 * morceau de texture que vous voulez afficher textureY correspond à la
	 * coordonnée y du morceau de texture que vous voulez afficher width correspond
	 * à largeur du morceau de texture que vous voulez afficher height correspond à
	 * la hauteur du morceau de texture que vous voulez afficher
	 * 
	 * Quand vous utilisez cette fonction, il faut associer la texture au
	 * textureManager de minecraft, il faut donc mettre 1 fois au début de la
	 * fonction
	 * 
	 * @code this.mc.getTextureManager().bindTexture(background);
	 * 
	 * On peut écrire à l’aide de cette fonction
	 * 
	 * @code this.fontRendererObj.drawString(String texte, int x, int, y, int color)
	 * 
	 * texte est le texte que vous voulez afficher 
	 * x est la coordonnée x de l’endroit où vous voulez l’afficher 
	 * y est la coordonnée y de l’endroit où vous voulez l’afficher color est la couleur du texte
	 * 
	 */

	//Dessine le Background du Gui
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	    int i = (this.width - this.xSize) / 2;
	    int j = (this.height - this.ySize) / 2;
	    this.drawDefaultBackground();
	    this.mc.getTextureManager().bindTexture(background);
	    this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
	 
	    int timePassed = this.tile.getField(1);
	    int textureWidth = (int) (23f / 200f * timePassed);
	    this.drawTexturedModalRect(i + 81, j + 24, 177, 18, textureWidth, 7);
	 
	    if (this.tile.isBurning()) {
	        int burningTime = this.tile.getField(0);
	        int textureHeight = (int) (12f / this.tile.getFullBurnTime() * burningTime);
	        this.drawTexturedModalRect(i + 37, j + 26 + 12 - textureHeight,
	                177, 12 - textureHeight, 27, textureHeight);
	    }
	 
	    this.fontRenderer.drawString(this.tile.getName(), i + 80, j + 45, 0xFFFFFF);
	}
}
