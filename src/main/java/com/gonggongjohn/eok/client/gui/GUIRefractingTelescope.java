package com.gonggongjohn.eok.client.gui;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.inventory.ContainerRefractingTelescope;
import com.gonggongjohn.eok.items.ItemPapyrus;
import com.gonggongjohn.eok.network.PacketGuiButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

public class GUIRefractingTelescope extends GuiContainer {
    public static final String TEXTURE_BACK = EOK.MODID + ":" + "textures/gui/container/refractingtelescope.png";
    public static final String TEXTURE_COMP = EOK.MODID + ":" + "textures/gui/container/componenttelescope.png";
    public static final ResourceLocation TEXTUREBACK = new ResourceLocation(TEXTURE_BACK);
    public static final ResourceLocation TEXTURECOMP = new ResourceLocation(TEXTURE_COMP);
    public ContainerRefractingTelescope containerRT;

    public GUIRefractingTelescope(ContainerRefractingTelescope inventorySlotsIn) {
        super(inventorySlotsIn);
        this.xSize = 256;
        this.ySize = 256;
        this.containerRT = inventorySlotsIn;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTUREBACK);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
        GL11.glPopMatrix();
    }

    @Override
    public void initGui(){
        super.initGui();
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.buttonList.add(new GuiButton(0, offsetX + 10, offsetY + 200, 18, 18, ""){
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks){
                if(this.visible){
                    GlStateManager.color(1.0F, 1.0F, 1.0F);
                    mc.getTextureManager().bindTexture(TEXTURECOMP);
                    this.drawTexturedModalRect(this.x, this.y, 0, 0, this.width, this.height);
                }
            }
        });
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id == 0){
            EOK.getNetwork().sendToServer(new PacketGuiButton(button.id));
        }
    }
}
