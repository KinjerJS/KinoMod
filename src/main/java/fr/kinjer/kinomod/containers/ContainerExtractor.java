package fr.kinjer.kinomod.containers;

import fr.kinjer.kinomod.tileentities.TileExtractor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerExtractor extends Container{

	private TileExtractor tile;
	private int	timePassed = 0;
	private int	burnTimeLeft = 0;
	
	public ContainerExtractor(TileExtractor tile, InventoryPlayer playerInventory) {
	    this.tile = tile;
	 
	    int i = 0;
	    this.addSlotToContainer(new SlotOutput(tile, i, 79, 39));
	 
	    for(i = 0; i < 3; ++i) {
	        for(int j = 0; j < 9; ++j) {
	            this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 104 + i * 18));
	        }
	    }
	 
	    for(i = 0; i < 9; ++i) {
	        this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 162));
	    }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
	    return tile.isUsableByPlayer(player);
	}

	//les fonctions qui permet de mettre à jour les valeurs du TileEntity pour l’affichage sur le client (le gui)
	
	@Override
	public void addListener(IContainerListener listener) {
	    super.addListener(listener);
	    listener.sendAllWindowProperties(this, this.tile);
	}
	 
	@Override
	public void detectAndSendChanges() {
	    super.detectAndSendChanges();
	 
	    for(int i = 0; i < this.listeners.size(); ++i) {
	        IContainerListener icontainerlistener = (IContainerListener) this.listeners
	                .get(i);
	 
	        if (this.burnTimeLeft != this.tile.getField(0)) {
	            icontainerlistener.sendWindowProperty(this, 0,
	                    this.tile.getField(0));
	        }
	 
	        if (this.timePassed != this.tile.getField(1)) {
	            icontainerlistener.sendWindowProperty(this, 1,
	                    this.tile.getField(1));
	        }
	    }
	 
	    this.burnTimeLeft = this.tile.getField(0);
	    this.timePassed = this.tile.getField(1);
	}
	 
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
	    this.tile.setField(id, data);
	}
	
	// La fonction qui permet de shift + clic 
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int i)
    {
//        ItemStack itemstack = ItemStack.EMPTY;
//        Slot slot = this.inventorySlots.get(i);
//
//        if (slot != null && slot.getHasStack())
//        {
//            ItemStack itemstack1 = slot.getStack();
//            itemstack = itemstack1.copy();
//
//            if (!this.mergeItemStack(itemstack1, 0, 34, false))
//            {
//                return ItemStack.EMPTY;
//            }
//
//            if (itemstack1.isEmpty())
//            {
//                slot.putStack(ItemStack.EMPTY);
//            }
//            else
//            {
//                slot.onSlotChanged();
//            }
//
//            if (itemstack1.getCount() == itemstack.getCount())
//            {
//                return ItemStack.EMPTY;
//            }
//
//            slot.onTake(playerIn, itemstack1);
//        }

        return ItemStack.EMPTY;// itemstack;
    }
	
	public class SlotOutput extends Slot {
		 
	    public SlotOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
	        super(inventoryIn, index, xPosition, yPosition);
	    }
	 
	    @Override
	    public boolean isItemValid(ItemStack stack) {
	        return false;
	    }
	}
	
	public class SlotSingleItem extends Slot {
		 
	    private Item item;
	 
	    public SlotSingleItem(IInventory inventoryIn, int index, int xPosition, int yPosition, Item item) {
	        super(inventoryIn, index, xPosition, yPosition);
	        this.item = item;
	    }
	 
	    @Override
	    public boolean isItemValid(ItemStack stack) {
	        return stack.isEmpty() || stack.getItem() == item;
	    }
	}
	
}
