package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.capabilities.*;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityHandler {
    @CapabilityInject(IConsciousness.class)
    public static Capability<IConsciousness> capConsciousness;
    @CapabilityInject(IMindActivity.class)
    public static Capability<IMindActivity> capMindActivity;
    @CapabilityInject(IResearchData.class)
    public static Capability<IResearchData> capResearchData;
    @CapabilityInject(ISeconds.class)
    public static Capability<ISeconds> capSeconds;
    @CapabilityInject(IHayTorch.class)
    public static Capability<IHayTorch> capHayTorchBase;
    @CapabilityInject(IAnotherSeconds.class)
    public static Capability<IAnotherSeconds> capAnotherSeconds;

    @SuppressWarnings("deprecation")
	public static void setupCapabilities(){
        CapabilityManager.INSTANCE.register(IConsciousness.class, new CapabilityConsciousness.Storage(), CapabilityConsciousness.Implementation.class);
        CapabilityManager.INSTANCE.register(IMindActivity.class, new CapabilityMindActivity.Storage(), CapabilityMindActivity.Implementation.class);
        CapabilityManager.INSTANCE.register(IResearchData.class, new CapabilityResearchData.Storage(), CapabilityResearchData.Implementation.class);
        CapabilityManager.INSTANCE.register(ISeconds.class, new CapabilitySeconds.Storage(),CapabilitySeconds.Implementation.class);
        CapabilityManager.INSTANCE.register(IHayTorch.class, new CapabilityHayTorchBase.Storage(), CapabilityHayTorchBase.Implementation.class);
        CapabilityManager.INSTANCE.register(IAnotherSeconds.class, new CapabilityAnotherSeconds.Storage(), CapabilityAnotherSeconds.Implementation.class);
        
    }
}
