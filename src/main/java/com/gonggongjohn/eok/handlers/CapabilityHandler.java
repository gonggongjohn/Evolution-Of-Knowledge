package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.capabilities.CapabilityConsciousness;
import com.gonggongjohn.eok.capabilities.CapabilityMindActivity;
import com.gonggongjohn.eok.capabilities.IConsciousness;
import com.gonggongjohn.eok.capabilities.IMindActivity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityHandler {
    @CapabilityInject(IConsciousness.class)
    public static Capability<IConsciousness> capConsciousness;
    @CapabilityInject(IMindActivity.class)
    public static Capability<IMindActivity> capMindActivity;

    public static void setupCapabilities(){
        CapabilityManager.INSTANCE.register(IConsciousness.class, new CapabilityConsciousness.Storage(), CapabilityConsciousness.Implementation.class);
        CapabilityManager.INSTANCE.register(IMindActivity.class, new CapabilityMindActivity.Storage(), CapabilityMindActivity.Implementation.class);
    }
}
