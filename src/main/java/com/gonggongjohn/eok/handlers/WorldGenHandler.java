package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.worldgen.WorldGenBlockStone;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WorldGenHandler {

    public WorldGenHandler()
    {
        GameRegistry.registerWorldGenerator(new WorldGenBlockStone(),10000);
    }
}
