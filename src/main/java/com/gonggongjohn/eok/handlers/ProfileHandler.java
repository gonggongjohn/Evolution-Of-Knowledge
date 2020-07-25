package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.utils.ResearchUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

import java.io.*;
import java.util.List;

public class ProfileHandler {
    public static String rootDir = System.getProperty("user.dir");
    public static String playerName;

    public ProfileHandler() {
        FMLCommonHandler.instance().bus().register(this);
    }

    /*@SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event){
        playerName = event.player.getGameProfile().getName();
        File profile = new File(rootDir + "//EOK//" + playerName + ".erec");
        File profileDir = new File(rootDir + "//EOK");
        if(!profileDir.exists()) profileDir.mkdirs();
        try {
            if(!profile.exists()){
                profile.createNewFile();
                Gson seq = new Gson();
                String content = seq.toJson(ResearchUtils.unlockedResearchID);
                PrintStream ps = new PrintStream(new FileOutputStream(profile));
                ps.print(content);
            }
            else {
                BufferedReader reader = new BufferedReader(new FileReader(profile));
                String data = reader.readLine();
                Gson seq = new Gson();
                ResearchUtils.unlockedResearchID = seq.fromJson(data, new TypeToken<List<Integer>>() {}.getType());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void recordProgress(){
        Gson seq = new Gson();
        String content = seq.toJson(ResearchUtils.unlockedResearchID);
        try {
            File profile = new File(rootDir + "//EOK//" + playerName + ".erec");
            PrintStream ps = new PrintStream(new FileOutputStream(profile));
            ps.print(content);
        } catch (Exception e){
            e.printStackTrace();
        }
    }*/
}
