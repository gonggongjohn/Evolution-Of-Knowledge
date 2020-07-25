package com.gonggongjohn.eok;

import com.gonggongjohn.eok.handlers.KeyHandler;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
    public void preInit(FMLPreInitializationEvent event){
        super.preInit(event);
    }

    public void init(FMLInitializationEvent event){
        super.init(event);
		new KeyHandler();
    }

    public void postInit(FMLPostInitializationEvent event){
        super.postInit(event);
    }
}
