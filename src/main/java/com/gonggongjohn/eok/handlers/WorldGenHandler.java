package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.worldgen.WorldGenBlockStoneAndStick;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class WorldGenHandler {

    public WorldGenHandler()
    {
        GameRegistry.registerWorldGenerator(new WorldGenBlockStoneAndStick(), 10002);
    }
}
