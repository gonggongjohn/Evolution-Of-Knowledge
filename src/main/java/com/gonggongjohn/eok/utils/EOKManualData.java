package com.gonggongjohn.eok.utils;

import java.util.ArrayList;

import com.gonggongjohn.eok.EOK;

public class EOKManualData {

	private static ArrayList<String> listOfPath = new ArrayList<>();
	
	public EOKManualData() {

	}

	public static ArrayList<String> getPath() {
		
		listOfPath.add(EOK.MODID + ":" + "textures/gui/container/eok_manual.png");
		listOfPath.add(EOK.MODID + ":" + "textures/gui/container/label.png");
		listOfPath.add(EOK.MODID + ":" + "textures/items/papyrus.png");
		
		return listOfPath;
	}

	public static void setPath(ArrayList<String> path) {
		
		EOKManualData.listOfPath = path;
	}
}
