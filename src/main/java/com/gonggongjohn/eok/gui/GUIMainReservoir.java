package com.gonggongjohn.eok.gui;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.containers.ContainerMainReservoir;
import com.gonggongjohn.eok.tileEntities.TEMainReservoir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GUIMainReservoir extends GuiContainer {
    private ResourceLocation backTexture = new ResourceLocation(EOK.MODID, "textures/gui/guiMainReservoir.png");
    private int x,y;

    public GUIMainReservoir(TEMainReservoir te, EntityPlayer player) {
        super(new ContainerMainReservoir(te, player));
        this.xSize = 249;
        this.ySize = 215;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        Minecraft.getMinecraft().renderEngine.bindTexture(backTexture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        x = (width - xSize) / 2;
        y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 4, 4, xSize, ySize);
    }
}
