package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.capabilities.*;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityHandler {
    @CapabilityInject(IPlayerState.class)
    public static Capability<IPlayerState> capPlayerState;
    @CapabilityInject(IResearchData.class)
    public static Capability<IResearchData> capResearchData;

    public static void setupCapabilities() {
        CapabilityManager.INSTANCE.register(IPlayerState.class, new StoragePlayerState(), CapabilityPlayerState::new);
        CapabilityManager.INSTANCE.register(IResearchData.class, new StorageResearchData(),
                CapabilityResearchData::new);
    }
}