package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.worldgen.WorldGenBlockStoneAndStick;
import com.gonggongjohn.eok.worldgen.WorldGenRedstoneAppleTree;
import com.gonggongjohn.eok.worldgen.WorldGenVein;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WorldGenHandler {

    public WorldGenHandler() {
        GameRegistry.registerWorldGenerator(new WorldGenBlockStoneAndStick(), 10002);
        GameRegistry.registerWorldGenerator(new WorldGenRedstoneAppleTree(), 10002);
//        GameRegistry.registerWorldGenerator(new WorldGenVein(), 10001);
    }
}
