package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.capabilities.CapabilityConsciousness;
import com.gonggongjohn.eok.capabilities.IConsciousness;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityHandler {
    @CapabilityInject(IConsciousness.class)
    public static Capability<IConsciousness> capConsciousness;

    public static void setupCapabilities(){
        CapabilityManager.INSTANCE.register(IConsciousness.class, new CapabilityConsciousness.Storage(), CapabilityConsciousness.Implementation.class);
    }
}
