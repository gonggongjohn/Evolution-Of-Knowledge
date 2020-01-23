package com.gonggongjohn.eok;

import com.gonggongjohn.eok.client.gui.overlay.PlayerVitalSigns;
import com.gonggongjohn.eok.handlers.AnotherEventHandler;
import com.gonggongjohn.eok.handlers.CapabilityHandler;
import com.gonggongjohn.eok.handlers.CommandHandler;
import com.gonggongjohn.eok.network.*;
import com.gonggongjohn.eok.tweakers.TweakersMain;

import com.gonggongjohn.eok.utils.MultiBlockDict;
import com.gonggongjohn.eok.utils.ResearchDict;
import net.minecraft.crash.CrashReport;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ReportedException;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = EOK.MODID, name = EOK.NAME, version = EOK.VERSION, dependencies = EOK.DEPENDENCIES, useMetadata = true)
public class EOK {
	public static final String MODID = "eok";
	public static final String NAME = "Evolution Of Knowledge";
	public static final String VERSION = "0.0.1";
	public static final String DEPENDENCIES = "required-after:tmc@[1.2.3,);";

	@Mod.Instance
	public static EOK instance;

	@SidedProxy(clientSide = "com.gonggongjohn.eok.ClientProxy", serverSide = "com.gonggongjohn.eok.CommonProxy")
	public static CommonProxy proxy;

	public static final CreativeTabs tabEOK = new EOKTab();
	private static org.apache.logging.log4j.Logger logger;

	private SimpleNetworkWrapper network;

	public static ResearchDict researchDict = new ResearchDict();

	public static MultiBlockDict multiBlockDict;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		if (Loader.isModLoaded("torcherino") || Loader.isModLoaded("projecte")) {
			CrashReport cr = CrashReport.makeCrashReport(new IllegalAccessError(),
					String.format("You have ENRAGED the FOREST BAT because some mods are loaded"));
			throw new ReportedException(cr);
		}
		proxy.preInit(event);
		MinecraftForge.EVENT_BUS.register(new AnotherEventHandler());
		researchDict.initName();
		researchDict.initRelation();
		CapabilityHandler.setupCapabilities();
		TweakersMain.preInit();
		network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		network.registerMessage(new PacketGuiButton.Handler(), PacketGuiButton.class, 0, Side.SERVER);
		network.registerMessage(new PacketConsciousness.Handler(), PacketConsciousness.class, 1, Side.CLIENT);
		network.registerMessage(new PacketMindActivity.Handler(), PacketMindActivity.class, 2, Side.CLIENT);
		network.registerMessage(new PacketResearchData.Handler(), PacketResearchData.class, 3, Side.CLIENT);
		network.registerMessage(new PacketGUIMerchant.Handler(), PacketGUIMerchant.class, 4, Side.CLIENT);
		network.registerMessage(new PacketGUIMerchant.Handler(), PacketGUIMerchant.class, 5, Side.SERVER);
		network.registerMessage(new PacketInverseReseachData.Handler(), PacketInverseReseachData.class, 6, Side.SERVER);
		network.registerMessage(new PacketTestGUIScreen.Handler(), PacketTestGUIScreen.class, 7, Side.CLIENT);
		network.registerMessage(new PacketHayTorchBase.Handler(), PacketHayTorchBase.class, 8, Side.CLIENT);
		network.registerMessage(new PacketSeconds.Handler(),PacketSeconds.class,999,Side.CLIENT);
		network.registerMessage(new PacketHayTorchBase.Handler(), PacketHayTorchBase.class, 8, Side.SERVER);
		network.registerMessage(new PacketHayTorchBase.Handler(), PacketHayTorchBase.class, 9, Side.CLIENT);
		network.registerMessage(new PacketAnotherSeconds.Handler(), PacketAnotherSeconds.class, 12, Side.SERVER);
		network.registerMessage(new PacketAnotherSeconds.Handler(), PacketAnotherSeconds.class, 13, Side.CLIENT);
		network.registerMessage(new PacketInspirations.Handler(), PacketInspirations.class, 14, Side.CLIENT);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		multiBlockDict = new MultiBlockDict();
		multiBlockDict.initStructure();
		multiBlockDict.initDict();
		TweakersMain.init();
		MinecraftForge.EVENT_BUS.register(PlayerVitalSigns.getInstance());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
		TweakersMain.postInit();
	}
	
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		CommandHandler.registerCommands(event);
	}

	public static CommonProxy getProxy() {
		return proxy;
	}

	public static SimpleNetworkWrapper getNetwork() {
		return instance.network;
	}

	public static org.apache.logging.log4j.Logger getLogger() {
		return logger;
	}
}
