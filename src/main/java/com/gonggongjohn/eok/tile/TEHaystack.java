package com.gonggongjohn.eok.tile;

import com.gonggongjohn.eok.handlers.BlockHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TEHaystack extends TileEntity implements ITickable {
    private int sec=0,drystate=0,decomposedstate=0;
    private boolean canDry()
    {
        return world.canSeeSky(getPos().up())&&!world.isRaining();
    }
    @Override
    public void update() {
        final int dryseconds=60;//晾干时间（单位：秒）
        final int decomposedseconds=60;//腐烂时间（单位：秒）
        if (!world.isRemote) {
            if (sec < 20) sec++;
            else {
                sec = 0;
                if (this.canDry()) {
                    if (drystate < dryseconds) {
                        ++drystate;
                    } else {
                        drystate = 0;
                        world.setBlockState(getPos(), BlockHandler.blockDriedHaystack.getDefaultState());
                    }
                }
                else
                    if(decomposedstate<60){++decomposedstate;}
                    else
                    {
                        decomposedstate=0;
                        world.setBlockState(getPos(),BlockHandler.blockDecomposedHaystack.getDefaultState());
                    }
            }
        }
    }
}
