package fr.kinjer.kinomod.tileentitys;

import fr.kinjer.kinomod.recipes.RecipesExtractor;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class TileExtractor extends TileEntityLockable implements ITickable
{
	
	private NonNullList<ItemStack> stacks = NonNullList.withSize(1, ItemStack.EMPTY);
	private String customName; //contient le nom personnalisé du bloc si il en a un stacks contient les ItemStack de votre bloc autrement dit tout les slots, c’est ici que sont stockés les items
	private int	timePassed = 0; //contient l’avancement de la recette, il représente le temps passé
	private int	burningTimeLeft	= 0; //contient le temps restant avant avant qu’il n’y est plus de feux
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
	    super.readFromNBT(compound);
	    this.stacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
	    ItemStackHelper.loadAllItems(compound, this.stacks);
	 
	    if (compound.hasKey("CustomName", 8)) {
	        this.customName = compound.getString("CustomName");
	    }
	    this.burningTimeLeft = compound.getInteger("burningTimeLeft");
	    this.timePassed = compound.getInteger("timePassed");
	}
	 
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
	    super.writeToNBT(compound);
	    ItemStackHelper.saveAllItems(compound, this.stacks);
	 
	    if (this.hasCustomName()) {
	        compound.setString("CustomName", this.customName);
	    }
	 
	    compound.setInteger("burningTimeLeft", this.burningTimeLeft);
	    compound.setInteger("timePassed", this.timePassed);
	 
	    return compound;
	}
	
	// Pour gerer et get le custom name du block
	
	@Override
	public boolean hasCustomName() {
	    return this.customName != null && !this.customName.isEmpty();
	}
	 
	@Override
	public String getName() {
	    return hasCustomName() ? this.customName : "tile.extractor";
	}
	 
	public void setCustomName(String name) {
	    this.customName = name;
	}
	
	//les fonctions qui vont permettre d’accéder aux variables burningTimeLeft et timePassed
	
	@Override
	public int getField(int id) {
	    switch (id) {
	        case 0:
	            return this.burningTimeLeft;
	        case 1:
	            return this.timePassed;
	    }
	    return 0;
	}
	 
	@Override
	public void setField(int id, int value) {
	    switch (id) {
	        case 0:
	            this.burningTimeLeft = value;
	            break;
	        case 1:
	            this.timePassed = value;
	    }
	}
	 
	@Override
	public int getFieldCount() {
	    return 2;
	}
	
	//les fonctions qui permettrons de manipuler les ItemStack de nos slots
	
	@Override
	public int getSizeInventory() {
	    return this.stacks.size();
	}
	 
	@Override
	public ItemStack getStackInSlot(int index) {
	    return this.stacks.get(index);
	}
	 
	@Override
	public ItemStack decrStackSize(int index, int count) {
	    return ItemStackHelper.getAndSplit(this.stacks, index, count);
	}
	 
	@Override
	public ItemStack removeStackFromSlot(int index) {
	    return ItemStackHelper.getAndRemove(stacks, index);
	}
	 
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
	    this.stacks.set(index, stack);
	 
	    if (stack.getCount() > this.getInventoryStackLimit()) {
	        stack.setCount(this.getInventoryStackLimit());
	    }
	}
	 
	@Override
	public int getInventoryStackLimit() {
	    return 64;
	}
	 
	@Override
	public boolean isEmpty() {
	    for(ItemStack stack : this.stacks) {
	        if (!stack.isEmpty()) {
	            return false;
	        }
	    }
	    return true;
	}
	 
	@Override
	public void clear() {
	    for(int i = 0; i < this.stacks.size(); i++) {
	        this.stacks.set(i, ItemStack.EMPTY);
	    }
	}
	// seront appelées lors de l’ouverture et de la fermeture de l’inventaire 
	@Override
	public void openInventory(EntityPlayer player) {}
	 
	@Override
	public void closeInventory(EntityPlayer player) {}

	//sert uniquement pour Minecraft
	
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
	    return null;
	}
	 
	@Override
	public String getGuiID() {
	    return null;
	}
	
	// définie ce que peut contenir chaque slot (cette fonction ne sert que pour l’automatisation, pas pour le GUI) 
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
	    // Le slot 3 je n'autorise que les planches de bois
	    if (index == 2)
	        return OreDictionary.getOres("plankWood").contains(
	                new ItemStack(stack.getItem(), 1,
	                        OreDictionary.WILDCARD_VALUE));
	    // Le slot 4 je n'autorise que le blé
	    if (index == 3)
	        return stack.getItem() == Items.WHEAT;
	    // Le slot 5 (celui du résultat) je n'autorise rien
	    if (index == 4)
	        return false;
	    // Sinon pour les slots 1 et 2 on met ce qu'on veut
	    return true;
	}
	
	/** Vérifie la distance entre le joueur et le bloc et que le bloc soit toujours présent */
	public boolean isUsableByPlayer(EntityPlayer player) {
	    return this.world.getTileEntity(this.pos) != this ? false : player
	            .getDistanceSq((double) this.pos.getX() + 0.5D,
	                    (double) this.pos.getY() + 0.5D,
	                    (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}
	
	// processus de cuisson
	public boolean hasFuelEmpty() {
	    return this.getStackInSlot(2).isEmpty()
	            || this.getStackInSlot(3).isEmpty();
	}
	
	//si le carburant est vide
	public ItemStack getRecipeResult() {
	    return RecipesExtractor.getRecipeResult(new ItemStack[] {
	            this.getStackInSlot(0), this.getStackInSlot(1) });
	}
	
	//récupére la recette associée aux ingrédients
	
	public boolean canSmelt() {
	    // On récupère le résultat de la recette
	    ItemStack result = this.getRecipeResult();
	 
	    // Le résultat est null si il n'y a pas de recette associée, donc on retourne faux
	    if (result != null) {
	 
	        // On récupère le contenu du slot de résultat
	        ItemStack slot4 = this.getStackInSlot(4);
	 
	        // Si il est vide on renvoie vrai
	        if (slot4.isEmpty())
	            return true;
	 
	        // Sinon on vérifie que ce soit le même objet, les même métadata et que la taille finale ne sera pas trop grande
	        if (slot4.getItem() == result.getItem() && slot4.getItemDamage() == result.getItemDamage()) {
	            int newStackSize = slot4.getCount() + result.getCount();
	            if (newStackSize <= this.getInventoryStackLimit() && newStackSize <= slot4.getMaxStackSize()) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
	
	//la fonction qui fait cuire les ingrédients (qui transforme les ingrédient en résultat de la recette) 
	public void smelt() {
	    // Cette fonction n'est appelée que si result != null, c'est pourquoi on ne fait pas de null check
	    ItemStack result = this.getRecipeResult();
	    // On enlève un item de chaque ingrédient
	    this.decrStackSize(0, 1);
	    this.decrStackSize(1, 1);
	    // On récupère le slot de résultat
	    ItemStack stack4 = this.getStackInSlot(4);
	    // Si il est vide
	    if (stack4.isEmpty()) {
	        // On y insère une copie du résultat
	        this.setInventorySlotContents(4, result.copy());
	    } else {
	        // Sinon on augmente le nombre d'objets de l'ItemStack
	        stack4.setCount(stack4.getCount() + result.getCount());
	    }
	}
	
	/** Temps de cuisson de la recette */
	public int getFullRecipeTime() {
	    return 200;
	}
	 
	/** Temps que dure 1 unité de carburant (ici : 1 planche + 1 blé) */
	public int getFullBurnTime() {
	    return 300;
	}
	 
	/** Renvoie vrai si le feu est allumé */
	public boolean isBurning() {
	    return burningTimeLeft > 0;
	}
	
	// Elle est appeller à chaque tick et devra etre executé uniquement coter serveur
	@Override
	public void tick() {
		if (!this.world.isRemote) {
			 
	        /* Si le carburant brûle, on réduit réduit le temps restant */
	        if (this.isBurning()) {
	            this.burningTimeLeft--;
	        }
	        
	        /*
	            * Si la on peut faire cuire la recette et que le four ne cuit pas
	            * alors qu'il peut, alors on le met en route
	            */
	        if (!this.isBurning() && this.canSmelt() && !this.hasFuelEmpty()) {
	            this.burningTimeLeft = this.getFullBurnTime();
	            this.decrStackSize(2, 1);
	            this.decrStackSize(3, 1);
	        }
	        
	        /* Si on peut faire cuire la recette et que le feu cuit */
	        if (this.isBurning() && this.canSmelt()) {
	            this.timePassed++;
	            if (timePassed >= this.getFullRecipeTime()) {
	                timePassed = 0;
	                this.smelt();
	            }
	        } else {
	            timePassed = 0;
	        }
	        this.markDirty();
	    }
	}

}
