package com.gonggongjohn.eok.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class StructureHighlightRenderer {
    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event){
        EntityPlayer player = Minecraft.getMinecraft().player;
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        double transX = player.posX;
        double transY = player.posY;
        double transZ = player.posZ;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        //GlStateManager.glLineWidth(5.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GlStateManager.translate(-transX, -transY, -transZ);
        drawCube(t, bb, 9.0D, 9.0D, 9.0D, 0.0F, 1.0F, 1.0F, 0.4F);
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public void drawCube(Tessellator t, BufferBuilder bb, double offsetX, double offsetY, double offsetZ, float r, float g, float b, float a){
        bb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        bb.pos(offsetX + 0.0D, offsetY + 0.0D, offsetZ + 0.0D).color(r, g, b, a).endVertex();
        bb.pos(offsetX + 1.0D, offsetY + 0.0D, offsetZ + 0.0D).color(r, g, b, a).endVertex();
        bb.pos(offsetX + 1.0D, offsetY + 1.0D, offsetZ + 0.0D).color(r, g, b, a).endVertex();
        bb.pos(offsetX + 0.0D, offsetY + 1.0D, offsetZ + 0.0D).color(r, g, b, a).endVertex();
        bb.pos(offsetX + 0.0D, offsetY + 0.0D, offsetZ + 0.0D).color(r, g, b, a).endVertex();

        bb.pos(offsetX + 0.0D, offsetY + 0.0D, offsetZ + 1.0D).color(r, g, b, a).endVertex();
        bb.pos(offsetX + 0.0D, offsetY + 1.0D, offsetZ + 1.0D).color(r, g, b, a).endVertex();
        bb.pos(offsetX + 0.0D, offsetY + 1.0D, offsetZ + 0.0D).color(r, g, b, a).endVertex();
        bb.pos(offsetX + 0.0D, offsetY + 0.0D, offsetZ + 0.0D).color(r, g, b, a).endVertex();

        bb.pos(offsetX + 0.0D, offsetY + 0.0D, offsetZ + 1.0D).color(r, g, b, a).endVertex();
        bb.pos(offsetX + 1.0D, offsetY + 0.0D, offsetZ + 1.0D).color(r, g, b, a).endVertex();
        bb.pos(offsetX + 1.0D, offsetY + 0.0D, offsetZ + 0.0D).color(r, g, b, a).endVertex();
        t.draw();
        bb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        bb.pos(offsetX + 1.0D, offsetY + 0.0D, offsetZ + 0.0D).color(r, g, b, a).endVertex();
        bb.pos(offsetX + 1.0D, offsetY + 0.0D, offsetZ + 1.0D).color(r, g, b, a).endVertex();
        bb.pos(offsetX + 1.0D, offsetY + 1.0D, offsetZ + 1.0D).color(r, g, b, a).endVertex();
        bb.pos(offsetX + 1.0D, offsetY + 1.0D, offsetZ + 0.0D).color(r, g, b, a).endVertex();

        bb.pos(offsetX + 1.0D, offsetY + 1.0D, offsetZ + 0.0D).color(r, g, b, a).endVertex();
        bb.pos(offsetX + 0.0D, offsetY + 1.0D, offsetZ + 0.0D).color(r, g, b, a).endVertex();
        bb.pos(offsetX + 0.0D, offsetY + 1.0D, offsetZ + 1.0D).color(r, g, b, a).endVertex();
        bb.pos(offsetX + 1.0D, offsetY + 1.0D, offsetZ + 1.0D).color(r, g, b, a).endVertex();
        t.draw();
        bb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        bb.pos(offsetX + 0.0D, offsetY + 0.0D, offsetZ + 1.0D).color(r, g, b, a).endVertex();
        bb.pos(offsetX + 1.0D, offsetY + 0.0D, offsetZ + 1.0D).color(r, g, b, a).endVertex();
        bb.pos(offsetX + 1.0D, offsetY + 1.0D, offsetZ + 1.0D).color(r, g, b, a).endVertex();
        bb.pos(offsetX + 0.0D, offsetY + 1.0D, offsetZ + 1.0D).color(r, g, b, a).endVertex();
        t.draw();
    }
}
