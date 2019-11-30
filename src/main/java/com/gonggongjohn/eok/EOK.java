package com.gonggongjohn.eok;

import com.gonggongjohn.eok.client.gui.overlay.PlayerVitalSigns;
import com.gonggongjohn.eok.handlers.AnotherEventHandler;
import com.gonggongjohn.eok.handlers.CapabilityHandler;
import com.gonggongjohn.eok.handlers.ResearchHandler;
import com.gonggongjohn.eok.network.PacketConsciousness;
import com.gonggongjohn.eok.network.PacketGUIMerchant;
import com.gonggongjohn.eok.network.PacketGuiButton;
import com.gonggongjohn.eok.network.PacketMindActivity;
import com.gonggongjohn.eok.network.PacketResearchData;
import com.gonggongjohn.eok.tweakers.TweakersMain;

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

	public static ResearchHandler researches;

	public ResearchDict researchDict = new ResearchDict();

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
		researches = new ResearchHandler();
		researchDict.initName();
		researchDict.initRelation();
		CapabilityHandler.setupCapabilities();
		network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		network.registerMessage(new PacketGuiButton.Handler(), PacketGuiButton.class, 0, Side.SERVER);
		network.registerMessage(new PacketConsciousness.Handler(), PacketConsciousness.class, 1, Side.CLIENT);
		network.registerMessage(new PacketMindActivity.Handler(), PacketMindActivity.class, 2, Side.CLIENT);
		network.registerMessage(new PacketResearchData.Handler(), PacketResearchData.class, 3, Side.CLIENT);
		network.registerMessage(new PacketGUIMerchant.Handler(), PacketGUIMerchant.class, 4, Side.CLIENT);
		network.registerMessage(new PacketGUIMerchant.Handler(), PacketGUIMerchant.class, 5, Side.SERVER);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		TweakersMain.setup();
		MinecraftForge.EVENT_BUS.register(PlayerVitalSigns.getInstance());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
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
