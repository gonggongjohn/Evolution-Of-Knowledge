package com.gonggongjohn.eok.render;

import codechicken.lib.vec.Vector3;
import com.gonggongjohn.eok.EOK;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = EOK.MODID, value = Side.CLIENT)
public class StoneWorkRenderer {
    @SubscribeEvent
    public static void onDrawBlockHighlight(DrawBlockHighlightEvent e) {
        EntityPlayer player = e.getPlayer();
        World world = player.world;
        RayTraceResult target = e.getTarget();
        if (target.typeOfHit != RayTraceResult.Type.BLOCK || target.sideHit != EnumFacing.UP) {
            return;
        }
        BlockPos pos = target.getBlockPos();
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (!(state.isFullBlock() && state.getMaterial() == Material.ROCK)) {
            return;
        }
        ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
        if (stack.getItem() != Items.FLINT) {
            return;
        }
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(2.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        double dx = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) e.getPartialTicks();
        double dy = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) e.getPartialTicks();
        double dz = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) e.getPartialTicks();
        AxisAlignedBB box = state.getSelectedBoundingBox(world, pos).grow(0.002).offset(-dx, -dy, -dz);
        RenderGlobal.drawSelectionBoundingBox(box, 0.0F, 0.0F, 0.0F, 0.4F);
        drawOverlayLines(box);
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        e.setCanceled(true);
    }

    private static void drawOverlayLines(AxisAlignedBB box) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        Vector3 aa = new Vector3(box.minX + 0.25, box.maxY, box.minZ + 0.25);
        Vector3 ab = new Vector3(box.minX + 0.25, box.maxY, box.minZ + 0.75);
        Vector3 ba = new Vector3(box.minX + 0.75, box.maxY, box.minZ + 0.25);
        Vector3 bb = new Vector3(box.minX + 0.75, box.maxY, box.minZ + 0.75);
        drawLine(buffer, aa.copy());
        drawLine(buffer, ab.copy());
        drawLine(buffer, bb.copy());
        drawLine(buffer, ba.copy());
        drawLine(buffer, aa.copy());
        tessellator.draw();
    }

    private static void drawLine(BufferBuilder buffer, Vector3 vec) {
        buffer.pos(vec.x, vec.y, vec.z).color(0, 0, 0, 0.5F).endVertex();
    }
}