package com.gonggongjohn.eok.client.gui;

import com.github.zi_jing.cuckoolib.client.gui.widget.EasyButton;
import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.api.structure.SecondaryStructureData;
import com.gonggongjohn.eok.handlers.MetaItemHandler;
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
    private final int switchButtonWidth = 28;
    private final int switchButtonHeight = 10;
    private final int switchBackButtonOffsetX = 9;
    private final int switchBackButtonOffsetY = 3;
    private final int switchNextButtonOffsetX = 49;
    private final int switchNextButtonOffsetY = 3;
    private final int switchButtonCommonTextureU = 123;
    private final int switchButtonCommonTextureV = 211;
    private final int switchButtonHoverTextureU = 153;
    private final int switchButtonHoverTextureV = 211;
    private final int writeButtonOffsetX = 227;
    private final int writeButtonOffsetY = 95;
    private final int writeButtonSize = 18;
    private final int writeButtonCommonTextureU = 3;
    private final int writeButtonCommonTextureV = 207;
    private final int writeButtonHoverTextureU = 23;
    private final int writeButtonHoverTextureV = 207;
    private final int componentBracketSize = 18;
    private final int componentBracketCommonTextureU = 43;
    private final int componentBracketCommonTextureV = 207;
    private final int componentBracketChosenTextureU = 63;
    private final int componentBracketChosenTextureV = 207;
    private final int componentBracketOffsetX = 12;
    private final int componentBracketOffsetY = 23;
    private final int blueprintOffsetX = 76;
    private final int blueprintOffsetY = 19;
    private final int blueprintWidth = 141;
    private final int blueprintHeight = 146;
    private final int blueprintAnchorOffsetX = 12;
    private final int blueprintAnchorOffsetY = 13;
    private final int layerButtonSize = 18;
    private final int layerButtonCommonTextureU = 83;
    private final int layerButtonCommonTextureV = 207;
    private final int layerButtonHoverTextureU = 103;
    private final int layerButtonHoverTextureV = 207;
    private final int layerUpButtonOffsetX = 227;
    private final int layerUpButtonOffsetY = 130;
    private final int layerDownButtonOffsetX = 227;
    private final int layerDownButtonOffsetY = 155;
    private final int componentNextButtonWidth = 12;
    private final int componentNextButtonHeight = 8;
    private final int componentNextButtonOffsetX = 49;
    private final int componentNextButtonOffsetY = 153;
    private final int componentNextButtonTextureU = 219;
    private final int componentNextButtonTextureV = 207;
    private final int componentBackButtonWidth = 12;
    private final int componentBackButtonHeight = 8;
    private final int componentBackButtonOffsetX = 15;
    private final int componentBackButtonOffsetY = 153;
    private final int componentBackButtonTextureU = 219;
    private final int componentBackButtonTextureV = 216;
    private SecondaryStructureData onBuildingStructure;
    private int layerNum;
    private ItemStack activeItem;
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
                    ButtonSecondaryBlueprintTableCenter btnCenter = new ButtonSecondaryBlueprintTableCenter(i);
                    btnCenter.setPos(calcBlueprintAnchorX(i - blueprintAnchorStartIndex), calcBlueprintAnchorY(i - blueprintAnchorStartIndex));
                    btnCenter.setSize(18, 18);
                    this.buttonList.add(i, btnCenter);
                }
                NBTTagCompound compound = this.inventoryIn.getBlueprintSlot().getStack().getTagCompound();
                if(compound != null && compound.hasKey("blueprint.category") && compound.hasKey("blueprint.structure")){
                    if(compound.getString("blueprint.category").equals("secondary")) {
                        this.onBuildingStructure = new SecondaryStructureData(compound);
                        ArrayList<Vec3i> indexList = this.onBuildingStructure.getLayerIndexList(layerNum);
                        if (!indexList.isEmpty()) {
                            for (Vec3i index : indexList) {
                                int id = blueprintAnchorStartIndex + index.getY() * 3 + index.getZ();
                                ItemStack content = this.onBuildingStructure.query(index);
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
        EasyButton btnSwitchNext = new EasyButton(0);
        btnSwitchNext.setPos(offsetX + switchNextButtonOffsetX, offsetY + switchNextButtonOffsetY);
        btnSwitchNext.setSize(switchButtonWidth, switchButtonHeight);
        btnSwitchNext.setTexture(TEXTURE_BACK);
        btnSwitchNext.setCommonTextureUV(switchButtonCommonTextureU, switchButtonCommonTextureV);
        btnSwitchNext.setHoverTextureUV(switchButtonHoverTextureU, switchButtonHoverTextureV);
        this.buttonList.add(btnSwitchNext);
        EasyButton btnSwitchBack = new EasyButton(1);
        btnSwitchBack.setPos(offsetX + switchBackButtonOffsetX, offsetY + switchBackButtonOffsetY);
        btnSwitchBack.setSize(switchButtonWidth, switchButtonHeight);
        btnSwitchBack.setTexture(TEXTURE_BACK);
        btnSwitchBack.setCommonTextureUV(switchButtonCommonTextureU, switchButtonCommonTextureV);
        btnSwitchBack.setHoverTextureUV(switchButtonHoverTextureU, switchButtonHoverTextureV);
        this.buttonList.add(btnSwitchBack);
        EasyButton btnWrite = new EasyButton(2);
        btnWrite.setPos(offsetX + writeButtonOffsetX, offsetY + writeButtonOffsetY);
        btnWrite.setSize(writeButtonSize, writeButtonSize);
        btnWrite.setTexture(TEXTURE_BACK);
        btnWrite.setCommonTextureUV(writeButtonCommonTextureU, writeButtonCommonTextureV);
        btnWrite.setHoverTextureUV(writeButtonHoverTextureU, writeButtonHoverTextureV);
        this.buttonList.add(btnWrite);
        EasyButton btnLayerUp = new EasyButton(3);
        btnLayerUp.setPos(offsetX + layerUpButtonOffsetX, offsetY + layerUpButtonOffsetY);
        btnLayerUp.setSize(this.layerButtonSize, this.layerButtonSize);
        btnLayerUp.setTexture(TEXTURE_BACK);
        btnLayerUp.setCommonTextureUV(layerButtonCommonTextureU, layerButtonCommonTextureV);
        btnLayerUp.setHoverTextureUV(layerButtonHoverTextureU, layerButtonHoverTextureV);
        this.buttonList.add(btnLayerUp);
        EasyButton btnLayerDown = new EasyButton(4);
        btnLayerDown.setPos(offsetX + layerDownButtonOffsetX, offsetY + layerDownButtonOffsetY);
        btnLayerDown.setSize(this.layerButtonSize, this.layerButtonSize);
        btnLayerDown.setTexture(TEXTURE_BACK);
        btnLayerDown.setCommonTextureUV(layerButtonCommonTextureU, layerButtonCommonTextureV);
        btnLayerDown.setHoverTextureUV(layerButtonHoverTextureU, layerButtonHoverTextureV);
        this.buttonList.add(btnLayerDown);
        EasyButton btnComponentNext = new EasyButton(5);
        btnComponentNext.setPos(offsetX + componentNextButtonOffsetX, offsetY + componentNextButtonOffsetY);
        btnComponentNext.setSize(componentNextButtonWidth, componentNextButtonHeight);
        btnComponentNext.setTexture(TEXTURE_BACK);
        btnComponentNext.setCommonTextureUV(componentNextButtonTextureU, componentNextButtonTextureV);
        btnComponentNext.setHoverTextureUV(componentNextButtonTextureU, componentNextButtonTextureV);
        this.buttonList.add(btnComponentNext);
        EasyButton btnComponentBack = new EasyButton(6);
        btnComponentBack.setPos(offsetX + componentBackButtonOffsetX, offsetY + componentBackButtonOffsetY);
        btnComponentBack.setSize(componentBackButtonWidth, componentBackButtonHeight);
        btnComponentBack.setTexture(TEXTURE_BACK);
        btnComponentBack.setCommonTextureUV(componentBackButtonTextureU, componentBackButtonTextureV);
        btnComponentBack.setHoverTextureUV(componentBackButtonTextureU, componentBackButtonTextureV);
        this.buttonList.add(btnComponentBack);
        initGUIStructure();
    }

    private void initGUIStructure(){
        ButtonSecondaryBlueprintTableComponent btnComponent = new ButtonSecondaryBlueprintTableComponent(componentStartIndex);
        btnComponent.setPos(calcComponentX(0), calcComponentY(0));
        btnComponent.setSize(componentBracketSize, componentBracketSize);
        btnComponent.setTexture(TEXTURE_BACK);
        btnComponent.setCommonTextureUV(componentBracketCommonTextureU, componentBracketCommonTextureV);
        btnComponent.setActiveTextureUV(componentBracketChosenTextureU, componentBracketChosenTextureV);
        btnComponent.setContent(new ItemStack(MetaItemHandler.MATERIAL_ITEM, 1, 1027));
        this.buttonList.add(btnComponent);
    }

    private void initGUIFunction(){

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id == 0 && this.pageNum == 0){
            this.pageNum++;
            this.buttonList.remove(buttonList.size() - 1);
            initGUIFunction();
        }
        if(button.id == 1 && this.pageNum == 1){
            this.pageNum--;
            initGUIStructure();
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
                        ItemStack content = this.onBuildingStructure.query(index);
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
                        ItemStack content = this.onBuildingStructure.query(index);
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
