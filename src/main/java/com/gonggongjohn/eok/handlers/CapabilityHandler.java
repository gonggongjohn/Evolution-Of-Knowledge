package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.capabilities.CapabilityAnotherSeconds;
import com.gonggongjohn.eok.capabilities.CapabilityInspirations;
import com.gonggongjohn.eok.capabilities.CapabilityPlayerState;
import com.gonggongjohn.eok.capabilities.CapabilityResearchData;
import com.gonggongjohn.eok.capabilities.CapabilitySeconds;
import com.gonggongjohn.eok.capabilities.IAnotherSeconds;
import com.gonggongjohn.eok.capabilities.IInspirations;
import com.gonggongjohn.eok.capabilities.IPlayerState;
import com.gonggongjohn.eok.capabilities.IResearchData;
import com.gonggongjohn.eok.capabilities.ISeconds;
import com.gonggongjohn.eok.capabilities.StoragePlayerState;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityHandler {
	@CapabilityInject(IPlayerState.class)
	public static Capability<IPlayerState> capPlayerState;
	@CapabilityInject(IResearchData.class)
	public static Capability<IResearchData> capResearchData;
	@CapabilityInject(ISeconds.class)
	public static Capability<ISeconds> capSeconds;
	@CapabilityInject(IAnotherSeconds.class)
	public static Capability<IAnotherSeconds> capAnotherSeconds;
	@CapabilityInject(IInspirations.class)
	public static Capability<IInspirations> capInspirations;

	public static void setupCapabilities() {
		CapabilityManager.INSTANCE.register(IPlayerState.class, new StoragePlayerState(), CapabilityPlayerState::new);
		CapabilityManager.INSTANCE.register(IResearchData.class, new CapabilityResearchData.Storage(),
				CapabilityResearchData.Implementation::new);
		CapabilityManager.INSTANCE.register(ISeconds.class, new CapabilitySeconds.Storage(),
				CapabilitySeconds.Implementation::new);
		CapabilityManager.INSTANCE.register(IAnotherSeconds.class, new CapabilityAnotherSeconds.Storage(),
				CapabilityAnotherSeconds.Implementation::new);
		CapabilityManager.INSTANCE.register(IInspirations.class, new CapabilityInspirations.Storage(),
				CapabilityInspirations.Implementation::new);
	}
}