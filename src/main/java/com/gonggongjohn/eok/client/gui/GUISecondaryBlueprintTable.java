package com.gonggongjohn.eok.client.gui;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.api.structure.SecondaryStructureData;
import com.gonggongjohn.eok.inventory.ContainerSecondaryBlueprintTable;
import com.gonggongjohn.eok.network.PacketBlueprintTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3i;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;

public class GUISecondaryBlueprintTable extends GuiContainer {
    private static final ResourceLocation TEXTURE_BACK = new ResourceLocation(EOK.MODID + ":" + "textures/gui/container/secondary_blueprint_table.png");
    private static final ResourceLocation TEXTURE_BLUEPRINT = new ResourceLocation(EOK.MODID + ":" + "textures/gui/container/blueprint_in_table.png");
    private ContainerSecondaryBlueprintTable inventoryIn;
    private int offsetX, offsetY;
    private int pageNum;
    private int switchButtonWidth = 28;
    private int switchButtonHeight = 10;
    private int switchBackButtonOffsetX = 9;
    private int switchBackButtonOffsetY = 3;
    private int switchNextButtonOffsetX = 49;
    private int switchNextButtonOffsetY = 3;
    private int switchButtonCommonTextureU = 123;
    private int switchButtonCommonTextureV = 211;
    private int switchButtonHoverTextureU = 153;
    private int switchButtonHoverTextureV = 211;
    private int writeButtonOffsetX = 227;
    private int writeButtonOffsetY = 95;
    private int writeButtonSize = 18;
    private int writeButtonCommonTextureU = 3;
    private int writeButtonCommonTextureV = 207;
    private int writeButtonHoverTextureU = 23;
    private int writeButtonHoverTextureV = 207;
    private int componentBracketSize = 18;
    private int componentBracketCommonTextureU = 43;
    private int componentBracketCommonTextureV = 207;
    private int componentBracketChosenTextureU = 63;
    private int componentBracketChosenTextureV = 207;
    private int componentBracketOffsetX = 12;
    private int componentBracketOffsetY = 23;
    private int blueprintOffsetX = 76;
    private int blueprintOffsetY = 19;
    private int blueprintWidth = 141;
    private int blueprintHeight = 146;
    private int blueprintAnchorOffsetX = 12;
    private int blueprintAnchorOffsetY = 13;
    private int layerButtonSize = 18;
    private int layerButtonCommonTextureU = 83;
    private int layerButtonCommonTextureV = 207;
    private int layerButtonHoverTextureU = 103;
    private int layerButtonHoverTextureV = 207;
    private int layerUpButtonOffsetX = 227;
    private int layerUpButtonOffsetY = 130;
    private int layerDownButtonOffsetX = 227;
    private int layerDownButtonOffsetY = 155;
    private int componentNextButtonWidth = 12;
    private int componentNextButtonHeight = 8;
    private int componentNextButtonOffsetX = 49;
    private int componentNextButtonOffsetY = 153;
    private int componentNextButtonTextureU = 219;
    private int componentNextButtonTextureV = 207;
    private int componentBackButtonWidth = 12;
    private int componentBackButtonHeight = 8;
    private int componentBackButtonOffsetX = 15;
    private int componentBackButtonOffsetY = 153;
    private int componentBackButtonTextureU = 219;
    private int componentBackButtonTextureV = 216;
    private SecondaryStructureData onBuildingStructure;
    private int layerNum;
    private Item activeItem;
    private boolean isBlueprintSlotUsing;
    private int componentStartIndex;
    private int blueprintAnchorStartIndex;

    public GUISecondaryBlueprintTable(ContainerSecondaryBlueprintTable inventorySlotsIn) {
        super(inventorySlotsIn);
        this.inventoryIn = inventorySlotsIn;
        this.xSize = 251;
        this.ySize = 203;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE_BACK);
        offsetX = (this.width - this.xSize) / 2;
        offsetY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
        if(this.inventoryIn.getBlueprintSlot().getHasStack()) {
            this.mc.getTextureManager().bindTexture(TEXTURE_BLUEPRINT);
            this.drawTexturedModalRect(offsetX + this.blueprintOffsetX, offsetY + this.blueprintOffsetY, 0, 0, this.blueprintWidth, this.blueprintHeight);
            if (!isBlueprintSlotUsing) {
                for(int i = blueprintAnchorStartIndex; i <= blueprintAnchorStartIndex + 8; i++){
                    this.buttonList.add(i, new ButtonSecondaryBlueprintTableCenter(i, calcBlueprintAnchorX(i - blueprintAnchorStartIndex), calcBlueprintAnchorY(i - blueprintAnchorStartIndex), 18, 18, this));
                }
                NBTTagCompound compound = this.inventoryIn.getBlueprintSlot().getStack().getTagCompound();
                if(compound != null && compound.hasKey("blueprint.category") && compound.hasKey("blueprint.structure")){
                    if(compound.getString("blueprint.category").equals("secondary")) {
                        this.onBuildingStructure = new SecondaryStructureData(compound);
                        ArrayList<Vec3i> indexList = this.onBuildingStructure.getLayerIndexList(layerNum);
                        if (!indexList.isEmpty()) {
                            for (Vec3i index : indexList) {
                                int id = blueprintAnchorStartIndex + index.getY() * 3 + index.getZ();
                                Item content = this.onBuildingStructure.query(index);
                                ((ButtonSecondaryBlueprintTableCenter) this.buttonList.get(id)).setContent(content);
                            }
                        }
                    }
                }
                else {
                    this.onBuildingStructure = new SecondaryStructureData();
                }
                isBlueprintSlotUsing = true;
            }
        }
        else if(isBlueprintSlotUsing){
            this.buttonList.removeIf(button -> button instanceof ButtonSecondaryBlueprintTableCenter);
            this.layerNum = 0;
            isBlueprintSlotUsing = false;
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    @Override
    public void initGui() {
        super.initGui();
        isBlueprintSlotUsing = false;
        pageNum = 0;
        layerNum = 0;
        blueprintAnchorStartIndex = 7;
        componentStartIndex = 16;
        offsetX = (this.width - this.xSize) / 2;
        offsetY = (this.height - this.ySize) / 2;
        this.buttonList.add(new GuiButton(0, offsetX + this.switchNextButtonOffsetX, offsetY + this.switchNextButtonOffsetY, this.switchButtonWidth, this.switchButtonHeight, ""){
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
                if(this.visible){
                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    mc.getTextureManager().bindTexture(TEXTURE_BACK);
                    int relx = mouseX - this.x, rely = mouseY - this.y;
                    if (relx >= 0 && rely >= 0 && relx < this.width && rely < this.height)
                        this.drawTexturedModalRect(this.x, this.y, switchButtonHoverTextureU, switchButtonHoverTextureV, this.width, this.height);
                    else
                        this.drawTexturedModalRect(this.x, this.y, switchButtonCommonTextureU, switchButtonCommonTextureV, this.width, this.height);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glPopMatrix();
                }
            }
        });
        this.buttonList.add(new GuiButton(1, offsetX + this.switchBackButtonOffsetX, offsetY + this.switchBackButtonOffsetY, this.switchButtonWidth, this.switchButtonHeight, ""){
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
                if(this.visible){
                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    mc.getTextureManager().bindTexture(TEXTURE_BACK);
                    int relx = mouseX - this.x, rely = mouseY - this.y;
                    if (relx >= 0 && rely >= 0 && relx < this.width && rely < this.height)
                        this.drawTexturedModalRect(this.x, this.y, switchButtonHoverTextureU, switchButtonHoverTextureV, this.width, this.height);
                    else
                        this.drawTexturedModalRect(this.x, this.y, switchButtonCommonTextureU, switchButtonCommonTextureV, this.width, this.height);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glPopMatrix();
                }
            }
        });
        this.buttonList.add(new GuiButton(2, offsetX + this.writeButtonOffsetX, offsetY + this.writeButtonOffsetY, this.writeButtonSize, this.writeButtonSize, ""){
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
                if(this.visible){
                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    mc.getTextureManager().bindTexture(TEXTURE_BACK);
                    int relx = mouseX - this.x, rely = mouseY - this.y;
                    if (relx >= 0 && rely >= 0 && relx < this.width && rely < this.height)
                        this.drawTexturedModalRect(this.x, this.y, writeButtonHoverTextureU, writeButtonHoverTextureV, this.width, this.height);
                    else
                        this.drawTexturedModalRect(this.x, this.y, writeButtonCommonTextureU, writeButtonCommonTextureV, this.width, this.height);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glPopMatrix();
                }
            }
        });
        this.buttonList.add(new GuiButton(3, offsetX + this.layerUpButtonOffsetX, offsetY + this.layerUpButtonOffsetY, this.layerButtonSize, this.layerButtonSize, ""){
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
                if(this.visible){
                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    mc.getTextureManager().bindTexture(TEXTURE_BACK);
                    int relx = mouseX - this.x, rely = mouseY - this.y;
                    if (relx >= 0 && rely >= 0 && relx < this.width && rely < this.height)
                        this.drawTexturedModalRect(this.x, this.y, layerButtonHoverTextureU, layerButtonHoverTextureV, this.width, this.height);
                    else
                        this.drawTexturedModalRect(this.x, this.y, layerButtonCommonTextureU, layerButtonCommonTextureV, this.width, this.height);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glPopMatrix();
                }
            }
        });
        this.buttonList.add(new GuiButton(4, offsetX + this.layerDownButtonOffsetX, offsetY + this.layerDownButtonOffsetY, this.layerButtonSize, this.layerButtonSize, ""){
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
                if(this.visible){
                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    mc.getTextureManager().bindTexture(TEXTURE_BACK);
                    int relx = mouseX - this.x, rely = mouseY - this.y;
                    if (relx >= 0 && rely >= 0 && relx < this.width && rely < this.height)
                        this.drawTexturedModalRect(this.x, this.y, layerButtonHoverTextureU, layerButtonHoverTextureV, this.width, this.height);
                    else
                        this.drawTexturedModalRect(this.x, this.y, layerButtonCommonTextureU, layerButtonCommonTextureV, this.width, this.height);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glPopMatrix();
                }
            }
        });
        this.buttonList.add(new GuiButton(5, offsetX + this.componentNextButtonOffsetX, offsetY + this.componentNextButtonOffsetY, this.componentNextButtonWidth, this.componentNextButtonHeight, ""){
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
                if(this.visible){
                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    mc.getTextureManager().bindTexture(TEXTURE_BACK);
                    this.drawTexturedModalRect(this.x, this.y, componentNextButtonTextureU, componentNextButtonTextureV, this.width, this.height);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glPopMatrix();
                }
            }
        });
        this.buttonList.add(new GuiButton(6, offsetX + this.componentBackButtonOffsetX, offsetY + this.componentBackButtonOffsetY, this.componentBackButtonWidth, this.componentBackButtonHeight, ""){
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
                if(this.visible){
                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    mc.getTextureManager().bindTexture(TEXTURE_BACK);
                    this.drawTexturedModalRect(this.x, this.y, componentBackButtonTextureU, componentBackButtonTextureV, this.width, this.height);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glPopMatrix();
                }
            }
        });
        this.buttonList.add(new ButtonSecondaryBlueprintTableComponent(componentStartIndex, calcComponentX(0), calcComponentY(0), this.componentBracketCommonTextureU, this.componentBracketCommonTextureV,
                this.componentBracketChosenTextureU, this.componentBracketChosenTextureV, this.componentBracketSize, this.componentBracketSize, Items.IRON_INGOT, TEXTURE_BACK, this));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id == 0 && this.pageNum == 0){
            this.pageNum++;
        }
        if(button.id == 1 && this.pageNum == 1){
            this.pageNum--;
        }
        if(button.id == 2 && onBuildingStructure != null){
            EOK.getNetwork().sendToServer(new PacketBlueprintTable(1, onBuildingStructure.toNBT()));
        }
        if(button.id == 3){
            if(isBlueprintSlotUsing){
                layerNum++;
                for(int i = 0; i < 9; i++){
                    ((ButtonSecondaryBlueprintTableCenter)this.buttonList.get(i + blueprintAnchorStartIndex)).setContent(null);
                }
                ArrayList<Vec3i> indexList = this.onBuildingStructure.getLayerIndexList(layerNum);
                if(!indexList.isEmpty()) {
                    for (Vec3i index : indexList) {
                        int id = blueprintAnchorStartIndex + index.getY() * 3 + index.getZ();
                        Item content = this.onBuildingStructure.query(index);
                        ((ButtonSecondaryBlueprintTableCenter) this.buttonList.get(id)).setContent(content);
                    }
                }
            }
        }
        if(button.id == 4){
            if(isBlueprintSlotUsing && layerNum > 0) {
                layerNum--;
                for(int i = 0; i < 9; i++){
                    ((ButtonSecondaryBlueprintTableCenter)this.buttonList.get(i + blueprintAnchorStartIndex)).setContent(null);
                }
                ArrayList<Vec3i> indexList = this.onBuildingStructure.getLayerIndexList(layerNum);
                if(!indexList.isEmpty()) {
                    for (Vec3i index : indexList) {
                        int id = blueprintAnchorStartIndex + index.getY() * 3 + index.getZ();
                        Item content = this.onBuildingStructure.query(index);
                        ((ButtonSecondaryBlueprintTableCenter) this.buttonList.get(id)).setContent(content);
                    }
                }
            }
        }
        if(button.id >= componentStartIndex){
            ButtonSecondaryBlueprintTableComponent btn = ((ButtonSecondaryBlueprintTableComponent)button);
            btn.flipActive();
            if(btn.isActive()) activeItem = btn.getContent();
            else activeItem = null;
        }
        if(button.id >= blueprintAnchorStartIndex && button.id <= blueprintAnchorStartIndex + 8 && activeItem != null){
            if(button instanceof ButtonSecondaryBlueprintTableCenter) {
                if (((ButtonSecondaryBlueprintTableCenter) button).getContent() != activeItem) {
                    ((ButtonSecondaryBlueprintTableCenter) button).setContent(activeItem);
                    int row = (button.id - blueprintAnchorStartIndex) / 3;
                    int column = (button.id - blueprintAnchorStartIndex) % 3;
                    this.onBuildingStructure.set(this.layerNum, row, column, activeItem);
                }
            }
        }
    }

    public void drawItemStack(ItemStack stack, int x, int y, String altText)
    {
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        this.itemRender.zLevel = 200.0F;
        net.minecraft.client.gui.FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = fontRenderer;
        this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y, altText);
        this.zLevel = 0.0F;
        this.itemRender.zLevel = 0.0F;
    }

    private int calcComponentX(int index){
        return offsetX + this.componentBracketOffsetX + (index % 2) * 30;
    }

    private int calcComponentY(int index){
        return offsetY + this.componentBracketOffsetY + (index / 2) * 30;
    }

    private int calcBlueprintAnchorX(int index){
        return offsetX + this.blueprintOffsetX + this.blueprintAnchorOffsetX + (index % 3) * 49;
    }

    private int calcBlueprintAnchorY(int index){
        return offsetY + this.blueprintOffsetY + this.blueprintAnchorOffsetY + (index / 3) * 51;
    }
}
