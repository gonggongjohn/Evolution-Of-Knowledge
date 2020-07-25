package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.data.GrassTweaker;
import com.gonggongjohn.eok.data.GravelTweaker;
import com.gonggongjohn.eok.data.EventTweaker;
import com.gonggongjohn.eok.data.KeyTweaker;
import com.gonggongjohn.eok.data.LeavesTweaker;
import com.gonggongjohn.eok.data.TreeTweaker;

//
//用于注册事件
//作者：zi_jing
//主要用于修改原版
//

public class EventHandler {
	public EventHandler() {
		// 砍树只能用斧头
		new TreeTweaker();
		// 挖草掉植物纤维
		new GrassTweaker();
		// 挖树叶掉木棍
		new LeavesTweaker();
		// 挖沙砾掉燧石碎片
		new GravelTweaker();
		new KeyTweaker();
		new EventTweaker();
	}
}