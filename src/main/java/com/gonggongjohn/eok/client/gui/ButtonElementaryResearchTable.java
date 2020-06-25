package com.gonggongjohn.eok.client.gui;

import com.gonggongjohn.eok.EOK;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ButtonElementaryResearchTable extends GuiButton {
    private static final String TEXTURE_COMP = EOK.MODID + ":" + "textures/gui/container/elementary_research_table_components.png";
    private static final ResourceLocation TEXTURECOMP = new ResourceLocation(TEXTURE_COMP);
    private final int offsetY;
    private final int researchId;

    public ButtonElementaryResearchTable(int buttonId, int researchId, int x, int y, int widthIn, int heightIn, int offsetY) {
        super(buttonId, x, y, widthIn, heightIn, "");
        //this.researchNode = ResearchHandler.researchDict.get(researchId);
        this.researchId = researchId;
        this.offsetY = offsetY;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        this.visible = this.y >= (offsetY + 5) && this.y <= (offsetY + 140);

        if (this.visible) {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(TEXTURECOMP);
            this.drawTexturedModalRect(this.x, this.y, 66, 0, this.width, this.height);
            int relx = mouseX - this.x, rely = mouseY - this.y;
            if (relx >= 0 && rely >= 0 && relx < this.width && rely < this.height) {
                String name = I18n.format("eok.research.gui.pre") + I18n.format("research." + EOK.researchDict.researchNameDict.get(researchId) + ".name");
                mc.fontRenderer.drawString(name, mouseX + 5, mouseY + 5, 0xFF0000);
            }
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
    }

    public int getResearchId() {
        return this.researchId;
    }
}
