package com.gonggongjohn.eok.client.gui.overlay;

import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.capabilities.IPlayerState;
import com.gonggongjohn.eok.handlers.CapabilityHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerVitalSigns {
	private static final ResourceLocation ICONS = new ResourceLocation(EOK.MODID, "textures/gui/overlay/icons.png");
	private static final PlayerVitalSigns INSTANCE = new PlayerVitalSigns();

	public static PlayerVitalSigns getInstance() {
		return INSTANCE;
	}

	@SubscribeEvent
	public void render(RenderGameOverlayEvent.Pre event) {
		if (event.getType() == RenderGameOverlayEvent.ElementType.AIR) {
			ScaledResolution resolution = event.getResolution();
			int width = resolution.getScaledWidth();
			int height = resolution.getScaledHeight();
			EntityPlayer player = Minecraft.getMinecraft().player;
			if (player.hasCapability(CapabilityHandler.capPlayerState, null)) {
				IPlayerState state = player.getCapability(CapabilityHandler.capPlayerState, null);
				Minecraft.getMinecraft().getTextureManager().bindTexture(ICONS);
				drawConsciousness(width, height, state.getConsciousness());
				drawMindActivity(width, height, state.getMindActivity());
			}
		}
	}

	private void drawConsciousness(int width, int height, double conV) {
		int left = width - 50;
		int top = height - GuiIngameForge.right_height;
		drawTexturedModalRect(left - 20, top - 2, 0, 19, 16, 10);
		drawTexturedModalRect(left, top, 0, 48, 48, 6);
		drawTexturedModalRect(left, top, 0, 64, (int) (46 * (conV / 100.0D)), 5);
	}

	private void drawMindActivity(int width, int height, double maV) {
		int left = width - 50;
		int top = height - GuiIngameForge.right_height - 20;
		drawTexturedModalRect(left - 20, top - 2, 0, 1, 16, 15);
		drawTexturedModalRect(left, top, 0, 48, 48, 6);
		drawTexturedModalRect(left, top, 0, 68, (int) (46 * (maV / 100.0D)), 4);
	}

	private static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
		float f = 0.00390625F;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(x, y + height, 0).tex(textureX * f, (textureY + height) * f).endVertex();
		buffer.pos(x + width, y + height, 0).tex((textureX + width) * f, (textureY + height) * f).endVertex();
		buffer.pos(x + width, y + 0, 0).tex((textureX + width) * f, textureY * f).endVertex();
		buffer.pos(x, y, 0).tex(textureX * f, textureY * f).endVertex();
		tessellator.draw();
	}
}
