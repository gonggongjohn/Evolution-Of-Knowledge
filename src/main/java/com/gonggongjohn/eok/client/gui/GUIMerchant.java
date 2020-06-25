package com.gonggongjohn.eok.client.gui;

import com.github.zi_jing.cuckoolib.client.gui.Colors;
import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.inventory.ContainerMerchant;
import com.gonggongjohn.eok.network.PacketGUIMerchant;
import com.gonggongjohn.eok.utils.MerchantTradeData;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;

public class GUIMerchant extends GuiContainer {

    public static final int BUTTON_LEFT = 0; // paging left
    public static final int BUTTON_RIGHT = 1; // paging right
    public static final int BUTTON_BUY = 2; // purchase button
    public static final int DEAL_ITEM_1 = 3; // deal option (1 - 5)
    public static final int DEAL_ITEM_2 = 4;
    public static final int DEAL_ITEM_3 = 5;
    public static final int DEAL_ITEM_4 = 6;
    public static final int DEAL_ITEM_5 = 7;
    public final ResourceLocation TEXTURE = new ResourceLocation(EOK.MODID + ":textures/gui/container/merchant.png");
    public final ContainerMerchant container;

    public GUIMerchant(ContainerMerchant inventorySlotsIn) {
        super(inventorySlotsIn);
        this.container = inventorySlotsIn;
        this.xSize = 186;
        this.ySize = 226;
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("currentPage", container.currentPage);
        nbt.setInteger("currentDeal", container.currentDeal);
        nbt.setInteger("totalPages", container.totalPages);
        PacketGUIMerchant packet = new PacketGUIMerchant(nbt, container.tradeList);
        EOK.getNetwork().sendToServer(packet);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int offsetX = (this.width - this.xSize) / 2;
        int offsetY = (this.height - this.xSize) / 2;
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);

        // highlight the frame of the chosen deal
        switch (this.container.currentDeal) {
            case 1:
                this.drawTexturedModalRect(offsetX + 12, offsetY + 24, 102, 227, 106, 24);
                break;
            case 2:
                this.drawTexturedModalRect(offsetX + 12, offsetY + 46, 102, 227, 106, 24);
                break;
            case 3:
                this.drawTexturedModalRect(offsetX + 12, offsetY + 68, 102, 227, 106, 24);
                break;
            case 4:
                this.drawTexturedModalRect(offsetX + 12, offsetY + 90, 102, 227, 106, 24);
                break;
            case 5:
                this.drawTexturedModalRect(offsetX + 12, offsetY + 112, 102, 227, 106, 24);
                break;
        }

        GL11.glPopMatrix();
    }

    @Override
    public void initGui() {
        super.initGui();

        int offsetX = (this.width - this.xSize) / 2;
        int offsetY = (this.height - this.xSize) / 2;

        // This is a complete button.
        // We discard it because we have a better solution : ButtonBuilder
        /*
         * this.buttonList.add(new Button(BUTTON_LEFT, offsetX + 126, offsetY + 116,
         * 22, 20, "<") {
         *
         * @Override public void drawButton(Minecraft mc, int mouseX, int mouseY, float
         * partialTicks) { if (this.visible) { GL11.glPushMatrix(); GL11.glColor4f(1.0F,
         * 1.0F, 1.0F, 1.0F); mc.getTextureManager().bindTexture(TEXTURE); if (mouseX >=
         * this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y
         * + this.height) { this.drawTexturedModalRect(this.x, this.y, 209, 40,
         * this.width, this.height); } else { this.drawTexturedModalRect(this.x, this.y,
         * 187, 40, this.width, this.height); } FontRenderer fontrenderer =
         * mc.fontRenderer; this.drawCenteredString(fontrenderer, this.displayString,
         * this.x + this.width / 2, this.y + (this.height - 8) / 2, 0xE0E0E0);
         * GL11.glPopMatrix(); } }
         *
         * });
         */

        // --------Button--------
        // TODO ButtonData和ButtonBuilder类已被删除，以下代码等待重构
		/*
		ButtonData bd = new ButtonData();

		// Paging left button
		bd.setPosX(offsetX + 126);
		bd.setPosY(offsetY + 116);
		bd.setWidth(11);
		bd.setHeight(11);
		bd.setTexture(TEXTURE);
		bd.setTextureX(209);
		bd.setTextureY(75);
		bd.setTextureX2(209);
		bd.setTextureY2(86);
		bd.setText("<");
		this.buttonList.add(ButtonBuilder.CommonButton(BUTTON_LEFT, bd));

		// Paging right button
		bd.setPosX(offsetX + 140);
		bd.setPosY(offsetY + 116);
		bd.setWidth(11);
		bd.setHeight(11);
		bd.setTexture(TEXTURE);
		bd.setTextureX(209);
		bd.setTextureY(75);
		bd.setTextureX2(209);
		bd.setTextureY2(86);
		bd.setText(">");
		this.buttonList.add(ButtonBuilder.CommonButton(BUTTON_RIGHT, bd));

		// Purchase button
		bd.setPosX(offsetX + 158);
		bd.setPosY(offsetY + 116);
		bd.setWidth(22);
		bd.setHeight(11);
		bd.setTexture(TEXTURE);
		bd.setTextureX(187);
		bd.setTextureY(75);
		bd.setTextureX2(187);
		bd.setTextureY2(86);
		bd.setText(I18n.format("eok.gui.merchant.button.buy"));
		this.buttonList.add(ButtonBuilder.CommonButton(BUTTON_BUY, bd));

		// Deal item 1
		bd.setPosX(offsetX + 14);
		bd.setPosY(offsetY + 26);
		bd.setWidth(102);
		bd.setHeight(20);
		bd.setTexture(TEXTURE);
		bd.setTextureX(14);
		bd.setTextureY(26);
		bd.setTextureX2(0);
		bd.setTextureY2(227);
		bd.setText("");
		this.buttonList.add(ButtonBuilder.CommonButton(DEAL_ITEM_1, bd));

		// Deal item 2
		bd.setPosX(offsetX + 14);
		bd.setPosY(offsetY + 48);
		bd.setWidth(102);
		bd.setHeight(20);
		bd.setTexture(TEXTURE);
		bd.setTextureX(14);
		bd.setTextureY(26);
		bd.setTextureX2(0);
		bd.setTextureY2(227);
		bd.setText("");
		this.buttonList.add(ButtonBuilder.CommonButton(DEAL_ITEM_2, bd));

		// Deal item 3
		bd.setPosX(offsetX + 14);
		bd.setPosY(offsetY + 70);
		bd.setWidth(102);
		bd.setHeight(20);
		bd.setTexture(TEXTURE);
		bd.setTextureX(14);
		bd.setTextureY(26);
		bd.setTextureX2(0);
		bd.setTextureY2(227);
		bd.setText("");
		this.buttonList.add(ButtonBuilder.CommonButton(DEAL_ITEM_3, bd));

		// Deal item 4
		bd.setPosX(offsetX + 14);
		bd.setPosY(offsetY + 92);
		bd.setWidth(102);
		bd.setHeight(20);
		bd.setTexture(TEXTURE);
		bd.setTextureX(14);
		bd.setTextureY(26);
		bd.setTextureX2(0);
		bd.setTextureY2(227);
		bd.setText("");
		this.buttonList.add(ButtonBuilder.CommonButton(DEAL_ITEM_4, bd));

		// Deal item 5
		bd.setPosX(offsetX + 14);
		bd.setPosY(offsetY + 114);
		bd.setWidth(102);
		bd.setHeight(20);
		bd.setTexture(TEXTURE);
		bd.setTextureX(14);
		bd.setTextureY(26);
		bd.setTextureX2(0);
		bd.setTextureY2(227);
		bd.setText("");
		this.buttonList.add(ButtonBuilder.CommonButton(DEAL_ITEM_5, bd));
		*/
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        GL11.glPushMatrix();
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        String title = I18n.format("eok.gui.merchant.title");
        this.fontRenderer.drawString(title, 15, 30, Colors.DEFAULT_BLACK);
        this.fontRenderer.drawString(String.valueOf(container.currentPage), 65, 154, Colors.DEFAULT_BLACK);
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        ArrayList<MerchantTradeData> tradeList = container.getTrades(container.currentPage);
        for (int i = 3; i <= 17; i++) {
            this.inventorySlots.putStackInSlot(i, ItemStack.EMPTY);
        }
        if (tradeList.get(0) != null) {
            this.inventorySlots.putStackInSlot(3, tradeList.get(0).getCost1());
            this.inventorySlots.putStackInSlot(4, tradeList.get(0).getCost2());
            this.inventorySlots.putStackInSlot(5, tradeList.get(0).getResult());
            this.drawTexturedModalRect(60, 48, 187, 60, 22, 15);
        }
        if (tradeList.get(1) != null) {
            this.inventorySlots.putStackInSlot(6, tradeList.get(1).getCost1());
            this.inventorySlots.putStackInSlot(7, tradeList.get(1).getCost2());
            this.inventorySlots.putStackInSlot(8, tradeList.get(1).getResult());
            this.drawTexturedModalRect(60, 70, 187, 60, 22, 15);
        }
        if (tradeList.get(2) != null) {
            this.inventorySlots.putStackInSlot(9, tradeList.get(2).getCost1());
            this.inventorySlots.putStackInSlot(10, tradeList.get(2).getCost2());
            this.inventorySlots.putStackInSlot(11, tradeList.get(2).getResult());
            this.drawTexturedModalRect(60, 92, 187, 60, 22, 15);
        }
        if (tradeList.get(3) != null) {
            this.inventorySlots.putStackInSlot(12, tradeList.get(3).getCost1());
            this.inventorySlots.putStackInSlot(13, tradeList.get(3).getCost2());
            this.inventorySlots.putStackInSlot(14, tradeList.get(3).getResult());
            this.drawTexturedModalRect(60, 114, 187, 60, 22, 15);
        }
        if (tradeList.get(4) != null) {
            this.inventorySlots.putStackInSlot(15, tradeList.get(4).getCost1());
            this.inventorySlots.putStackInSlot(16, tradeList.get(4).getCost2());
            this.inventorySlots.putStackInSlot(17, tradeList.get(4).getResult());
            this.drawTexturedModalRect(60, 136, 187, 60, 22, 15);
        }
        GL11.glPopMatrix();

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case DEAL_ITEM_1:
                selectDeal(1);
                break;
            case DEAL_ITEM_2:
                selectDeal(2);
                break;
            case DEAL_ITEM_3:
                selectDeal(3);
                break;
            case DEAL_ITEM_4:
                selectDeal(4);
                break;
            case DEAL_ITEM_5:
                selectDeal(5);
                break;
            case BUTTON_LEFT:
                previousPage();
                break;
            case BUTTON_RIGHT:
                nextPage();
                break;
            case BUTTON_BUY:
                buy();
                break;
        }

        super.actionPerformed(button);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    /**
     * choose deal item. the chosen item will be highlighted.
     *
     * @param deal The position of the chosen deal in the trade column GUI (1 - 5)
     */
    public void selectDeal(int deal) {
        container.currentDeal = deal;
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("currentPage", container.currentPage);
        nbt.setInteger("currentDeal", container.currentDeal);
        EOK.getNetwork().sendToServer(new PacketGUIMerchant(nbt, container.tradeList));
    }

    public void previousPage() {
        container.previousPage();
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("currentPage", container.currentPage);
        nbt.setInteger("currentDeal", container.currentDeal);
        EOK.getNetwork().sendToServer(new PacketGUIMerchant(nbt, container.tradeList));
    }

    public void nextPage() {
        container.nextPage();
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("currentPage", container.currentPage);
        nbt.setInteger("currentDeal", container.currentDeal);
        EOK.getNetwork().sendToServer(new PacketGUIMerchant(nbt, container.tradeList));
    }

    public void buy() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("currentPage", container.currentPage);
        nbt.setInteger("currentDeal", container.currentDeal);
        nbt.setInteger("operation", 2);
        EOK.getNetwork().sendToServer(new PacketGUIMerchant(nbt, container.tradeList));
        container.buy();
    }

}
