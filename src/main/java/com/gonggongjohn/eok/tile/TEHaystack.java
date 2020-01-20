package com.gonggongjohn.eok.tile;

import com.gonggongjohn.eok.handlers.BlockHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TEHaystack extends TileEntity implements ITickable {
    private int sec=0,drystate=0;
    private boolean canDry()
    {
        return Minecraft.getMinecraft().world.canSeeSky(getPos().up());
    }
    @Override
    public void update() {
        final int dryseconds=60;//晾干时间（单位：秒）
        if (!world.isRemote) {
            if (sec < 20) sec++;
            else {
                if (this.canDry()) {
                    sec = 0;
                    if (drystate < dryseconds) {
                        ++drystate;
                    } else {
                        drystate = 0;
                        world.setBlockState(getPos(), BlockHandler.blockDriedHaystack.getDefaultState());
                    }
                }
            }
        }
    }
}
