package com.gonggongjohn.eok.client.gui;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.inventory.ContainerRefractingTelescope;
import com.gonggongjohn.eok.items.ItemPapyrus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

public class GUIRefractingTelescope extends GuiContainer {
    public static final String TEXTURE_PATH = EOK.MODID + ":" + "textures/gui/container/refractingtelescope.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_PATH);
    private Slot paperSlot;

    public GUIRefractingTelescope(ContainerRefractingTelescope inventorySlotsIn) {
        super(inventorySlotsIn);
        this.xSize = 256;
        this.ySize = 240;
        this.paperSlot = inventorySlotsIn.getPaperSlot();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(TEXTURE);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    public void initGui(){
        super.initGui();
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.buttonList.add(new GuiButton(0, offsetX + 195, offsetY + 180, 45, 8, ""){
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks){
                if(this.visible){
                    GlStateManager.color(1.0F, 1.0F, 1.0F);
                    mc.getTextureManager().bindTexture(TEXTURE);
                    this.drawTexturedModalRect(this.x, this.y, 0, 248, this.width, this.height);
                }
            }
        });
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id == 0){
            ItemStack stack = this.paperSlot.getStack();
            if(stack == null| stack.isEmpty()) return;
            NBTTagCompound compound = stack.getTagCompound();
            int temp;
            if(compound == null) {
                temp = 0;
                compound = new NBTTagCompound();
            }
            else temp = compound.getInteger("data.universe");
            final int num = temp + 1;
            Item paperTT = new ItemPapyrus(){
                @Override
                public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
                    tooltip.add("Experiment Data:Universe x " + num);
                }
            };
            ItemStack stackTT = new ItemStack(paperTT);
            compound.setInteger("data.universe", num);
            stackTT.setTagCompound(compound);

            this.paperSlot.putStack(stackTT);
        }
    }
}
