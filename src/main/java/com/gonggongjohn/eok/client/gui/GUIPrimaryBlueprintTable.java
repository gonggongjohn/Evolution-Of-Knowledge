package com.gonggongjohn.eok.client.gui;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.api.structure.PrimaryStructureData;
import com.gonggongjohn.eok.inventory.ContainerPrimaryBlueprintTable;
import com.gonggongjohn.eok.network.PacketBlueprintTable;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3i;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;

public class GUIPrimaryBlueprintTable extends GuiContainer {
    private static final ResourceLocation TEXTURE_BACK = new ResourceLocation(EOK.MODID + ":" + "textures/gui/container/blueprint_table.png");
    private static final ResourceLocation TEXTURE_BLUEPRINT = new ResourceLocation(EOK.MODID + ":" + "textures/gui/container/blueprint_in_table.png");
    private ContainerPrimaryBlueprintTable inventoryIn;
    private int offsetX, offsetY;
    private int writeButtonOffsetX = 222;
    private int writeButtonOffsetY = 103;
    private int writeButtonSize = 24;
    private int writeButtonCommonTextureU = 3;
    private int writeButtonCommonTextureV = 214;
    private int writeButtonHoverTextureU = 40;
    private int writeButtonHoverTextureV = 214;
    private int componentBracketSize = 18;
    private int componentBracketCommonTextureU = 83;
    private int componentBracketCommonTextureV = 215;
    private int componentBracketChosenTextureU = 115;
    private int componentBracketChosenTextureV = 215;
    private int componentBracketOffsetX = 13;
    private int componentBracketOffsetY = 35;
    private int blueprintOffsetX = 53;
    private int blueprintOffsetY = 42;
    private int blueprintWidth = 150;
    private int blueprintHeight = 110;
    private int blueprintAnchorOffsetX = 22;
    private int blueprintAnchorOffsetY = 10;
    private int layerButtonSize = 24;
    private int layerButtonCommonTextureU = 153;
    private int layerButtonCommonTextureV = 214;
    private int layerButtonHoverTextureU = 190;
    private int layerButtonHoverTextureV = 214;
    private int layerUpButtonOffsetX = 222;
    private int layerUpButtonOffsetY = 142;
    private int layerDownButtonOffsetX = 222;
    private int layerDownButtonOffsetY = 173;
    private PrimaryStructureData onBuildingStructure;
    private int layerNum;
    private boolean isBlueprintSlotUsing;
    private Block activeBlock;

    public GUIPrimaryBlueprintTable(ContainerPrimaryBlueprintTable inventorySlotsIn) {
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
        this.drawTexturedModalRect(offsetX, offsetY, 3, 3, this.xSize, this.ySize);
        if(this.inventoryIn.getBlueprintSlot().getHasStack()){
            this.mc.getTextureManager().bindTexture(TEXTURE_BLUEPRINT);
            this.drawTexturedModalRect(offsetX + this.blueprintOffsetX, offsetY + this.blueprintOffsetY, 0, 0, this.blueprintWidth, this.blueprintHeight);
            if(!isBlueprintSlotUsing){
                for(int i = 4; i <= 12; i++){
                    this.buttonList.add(new ButtonPrimaryBlueprintTableCenter(i, calcBlueprintAnchorX(i - 4), calcBlueprintAnchorY(i - 4), 18, 18, this));
                }
                NBTTagCompound compound = this.inventoryIn.getBlueprintSlot().getStack().getTagCompound();
                if(compound != null && compound.hasKey("blueprint.category") && compound.hasKey("blueprint.structure")){
                    if(compound.getString("blueprint.category").equals("primary")) {
                        this.onBuildingStructure = new PrimaryStructureData(compound);
                        ArrayList<Vec3i> indexList = this.onBuildingStructure.getLayerIndexList(layerNum);
                        if (!indexList.isEmpty()) {
                            for (Vec3i index : indexList) {
                                int id = 4 + index.getY() * 3 + index.getZ();
                                Block content = this.onBuildingStructure.query(index);
                                ((ButtonPrimaryBlueprintTableCenter) this.buttonList.get(id)).setContent(content);
                            }
                        }
                    }
                }
                else {
                    this.onBuildingStructure = new PrimaryStructureData();
                }
                isBlueprintSlotUsing = true;
            }
        }
        else if(isBlueprintSlotUsing){
            for(int i = 0; i < 9; i++) this.buttonList.remove(buttonList.size() - 1);
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
        layerNum = 0;
        offsetX = (this.width - this.xSize) / 2;
        offsetY = (this.height - this.ySize) / 2;
        this.buttonList.add(new GuiButton(0, offsetX + this.writeButtonOffsetX, offsetY + this.writeButtonOffsetY, this.writeButtonSize, this.writeButtonSize, ""){
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
        this.buttonList.add(new GuiButton(1, offsetX + this.layerUpButtonOffsetX, offsetY + this.layerUpButtonOffsetY, this.layerButtonSize, this.layerButtonSize, ""){
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
        this.buttonList.add(new GuiButton(2, offsetX + this.layerDownButtonOffsetX, offsetY + this.layerDownButtonOffsetY, this.layerButtonSize, this.layerButtonSize, ""){
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
        this.buttonList.add(new ButtonPrimaryBlueprintTableComponent(3, offsetX + componentBracketOffsetX, offsetY + componentBracketOffsetY, this.componentBracketCommonTextureU, this.componentBracketCommonTextureV,
                this.componentBracketChosenTextureU, this.componentBracketChosenTextureV, this.componentBracketSize, this.componentBracketSize, "", TEXTURE_BACK, this));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id == 0 && onBuildingStructure != null){
            EOK.getNetwork().sendToServer(new PacketBlueprintTable(onBuildingStructure.toNBT()));
        }
        if(button.id == 1){
            if(isBlueprintSlotUsing){
                layerNum++;
                for(int i = 0; i < 9; i++){
                    ((ButtonPrimaryBlueprintTableCenter)this.buttonList.get(i + 4)).setContent(null);
                }
                ArrayList<Vec3i> indexList = this.onBuildingStructure.getLayerIndexList(layerNum);
                if(!indexList.isEmpty()) {
                    for (Vec3i index : indexList) {
                        int id = 4 + index.getY() * 3 + index.getZ();
                        Block content = this.onBuildingStructure.query(index);
                        ((ButtonPrimaryBlueprintTableCenter) this.buttonList.get(id)).setContent(content);
                    }
                }
            }
        }
        if(button.id == 2){
            if(isBlueprintSlotUsing && layerNum > 0) {
                layerNum--;
                for(int i = 0; i < 9; i++){
                    ((ButtonPrimaryBlueprintTableCenter)this.buttonList.get(i + 4)).setContent(null);
                }
                ArrayList<Vec3i> indexList = this.onBuildingStructure.getLayerIndexList(layerNum);
                if(!indexList.isEmpty()) {
                    for (Vec3i index : indexList) {
                        int id = 4 + index.getY() * 3 + index.getZ();
                        Block content = this.onBuildingStructure.query(index);
                        ((ButtonPrimaryBlueprintTableCenter) this.buttonList.get(id)).setContent(content);
                    }
                }
            }
        }
        if(button.id == 3){
            ButtonPrimaryBlueprintTableComponent btn = ((ButtonPrimaryBlueprintTableComponent)button);
            btn.flipActive();
            if(btn.isActive()) activeBlock = Blocks.IRON_BLOCK;
            else activeBlock = null;
        }
        if(button.id >= 4 && button.id <= 12 && activeBlock != null){
            if(((ButtonPrimaryBlueprintTableCenter)button).getContent() != activeBlock) {
                ((ButtonPrimaryBlueprintTableCenter) button).setContent(activeBlock);
                int row = (button.id - 4) / 3;
                int column = (button.id - 4) % 3;
                this.onBuildingStructure.set(this.layerNum, row, column, activeBlock);
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

    private int calcBlueprintAnchorX(int index){
        return offsetX + this.blueprintOffsetX + this.blueprintAnchorOffsetX + (index % 3) * 41;
    }

    private int calcBlueprintAnchorY(int index){
        return offsetY + this.blueprintOffsetY + this.blueprintAnchorOffsetY + (index / 3) * 36;
    }
}
