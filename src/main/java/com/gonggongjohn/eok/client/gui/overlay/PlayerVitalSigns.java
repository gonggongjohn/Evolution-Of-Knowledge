package com.gonggongjohn.eok.client.gui.overlay;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.capabilities.IConsciousness;
import com.gonggongjohn.eok.capabilities.IMindActivity;
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
            double conV = 0.0D;
            double maV = 0.0D;
            if(player.hasCapability(CapabilityHandler.capConsciousness, null)) {
                IConsciousness consciousness = player.getCapability(CapabilityHandler.capConsciousness, null);
                conV = consciousness.getConsciousnessValue();
            }
            if(player.hasCapability(CapabilityHandler.capMindActivity, null)){
                IMindActivity mindActivity = player.getCapability(CapabilityHandler.capMindActivity, null);
                maV = mindActivity.getMindActivityValue();
            }
            Minecraft.getMinecraft().getTextureManager().bindTexture(ICONS);
            drawConsciousness(width, height, conV);
            drawMindActivity(width, height, maV);
        }
    }

    private void drawConsciousness(int width, int height, double conV){
        int left = width - 50;
        int top = height - GuiIngameForge.right_height;
        drawTexturedModalRect(left - 20, top - 2, 0, 19, 16, 10);
        drawTexturedModalRect(left, top, 0, 48, 48, 6);
        drawTexturedModalRect(left, top, 0, 64, (int)(46 * (conV / 100.0D)), 5);
    }

    private void drawMindActivity(int width, int height, double maV){
        int left = width - 50;
        int top = height - GuiIngameForge.right_height - 20;
        drawTexturedModalRect(left - 20, top - 2, 0, 1, 16, 15);
        drawTexturedModalRect(left, top, 0, 48, 48, 6);
        drawTexturedModalRect(left, top, 0, 68, (int)(46 * (maV / 100.0D)), 4);
    }

    private static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x + 0, y + height, 0.0D).tex((textureX + 0) * f, (textureY + height) * f1).endVertex();;
        buffer.pos(x + width, y + height, 0.0D).tex((textureX + width) * f, (textureY + height) * f1).endVertex();
        buffer.pos(x + width, y + 0, 0.0D).tex((textureX + width) * f, (textureY + 0) * f1).endVertex();
        buffer.pos(x + 0, y + 0, 0.0D).tex((textureX + 0) * f, (textureY + 0) * f1).endVertex();
        tessellator.draw();
    }
}
