package com.gonggongjohn.eok.client.manual;

public class ElementItem extends Element {

    // private ItemStack stack;

    private ElementItem(String data) {
        // TODO Item
    }

    @Override
    protected Type getType() {
        return Type.ITEM;
    }

    @Override
    protected int getHeight() {
        return 20;
    }

    @Override
    protected void draw(int x, int y, DocumentRenderer renderer) {
        // TODO 自动生成的方法存根

    }
}
