package com.gonggongjohn.eok.client.gui.overlay;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.capabilities.IConsciousness;
import com.gonggongjohn.eok.handlers.CapabilityHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class PlayerVitalSigns {
    private static final ResourceLocation ICONS = new ResourceLocation(EOK.MODID, "textures/gui/overlay/icons.png");
    private static final PlayerVitalSigns INSTANCE = new PlayerVitalSigns();

    public static PlayerVitalSigns getInstance(){
        return INSTANCE;
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Pre event){
        if(event.getType() == RenderGameOverlayEvent.ElementType.AIR){
            ScaledResolution resolution = event.getResolution();
            int width = resolution.getScaledWidth();
            int height = resolution.getScaledHeight();
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            double conV = 0.0F;
            if(player.hasCapability(CapabilityHandler.capConsciousness, null)) {
                IConsciousness consciousness = (IConsciousness) player.getCapability(CapabilityHandler.capConsciousness, null);
                conV = consciousness.getConsciousnessValue();
            }
            Minecraft.getMinecraft().getTextureManager().bindTexture(ICONS);
            drawConsciousness(width, height, conV);
        }
    }

    private void drawConsciousness(int width, int height, double conV){
        int left = width - 50;
        int top = height - GuiIngameForge.right_height;
        drawTexturedModalRect(left - 20, top - 2, 0, 19, 16, 10);
        drawTexturedModalRect(left, top, 0, 48, 48, 6);
        drawTexturedModalRect(left, top, 0, 68, (int)(46 * (conV / 100.0F)), 5);
    }

    private static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos((double)(x + 0), (double)(y + height), 0.0D).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + height) * f1)).endVertex();;
        buffer.pos((double)(x + width), (double)(y + height), 0.0D).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f1)).endVertex();
        buffer.pos((double)(x + width), (double)(y + 0), 0.0D).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + 0) * f1)).endVertex();
        buffer.pos((double)(x + 0), (double)(y + 0), 0.0D).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + 0) * f1)).endVertex();
        tessellator.draw();
    }
}
