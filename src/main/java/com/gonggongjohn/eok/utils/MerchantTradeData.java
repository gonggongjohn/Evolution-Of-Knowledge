package com.gonggongjohn.eok.utils;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class MerchantTradeData {

    /*
     * NPC交易列表
     */
    public static final ArrayList<MerchantTradeData> TradeList = new ArrayList<>();
    private ItemStack cost1;
    private ItemStack cost2;
    private ItemStack result;
    /* 解锁交易所需要的最低好感度等级 */
    private int level;

    /**
     * @param cost1  交易费用第一栏
     * @param cost2  交易费用第二栏
     * @param result 交易结果
     * @param level  解锁交易所需要的最低好感度等级（最小值为1，最大值为5）
     */
    public MerchantTradeData(ItemStack cost1, ItemStack cost2, ItemStack result, int level) {
        super();
        this.cost1 = cost1;
        this.cost2 = cost2;
        this.result = result;
        this.level = level;
    }

    /**
     * 注册一个交易
     *
     * @param data 要注册的交易
     */
    public static void registerTrade(MerchantTradeData data) {
        TradeList.add(data);
    }

    public static ArrayList<MerchantTradeData> getRandomTrades(int level) {

        ArrayList<MerchantTradeData> availableTrades = new ArrayList<>();
        ArrayList<MerchantTradeData> result = new ArrayList<>();

        // 选出所有符合等级要求的交易
        for (MerchantTradeData data : TradeList) {
            if (data.getLevel() <= level) {
                availableTrades.add(data);
            }
        }

        Random rand = new Random();

        int tradeQuantity;
        int a;
        int b;

        // 先选出随机交易数量
        while (true) {

            // 若可用交易总数不多于7，则输出全部可用交易
            if (availableTrades.size() <= 7) {
                tradeQuantity = availableTrades.size();
                break;
            } else {
                // 若可用交易总数大于7，则输出随机数量的交易（一定大于7个）
                tradeQuantity = rand.nextInt(16);
                if (tradeQuantity > 7) {
                    break;
                }
            }
        }

        // 随机选出数量为tradeQuantity的交易
        for (a = 0; a < tradeQuantity; a++) {
            b = rand.nextInt(availableTrades.size());
            result.add(availableTrades.get(b));
            availableTrades.remove(b);
        }

        return result;

    }

    public ItemStack getCost1() {
        return cost1;
    }

    public void setCost1(ItemStack cost1) {
        this.cost1 = cost1;
    }

    public ItemStack getCost2() {
        return cost2;
    }

    public void setCost2(ItemStack cost2) {
        this.cost2 = cost2;
    }

    public ItemStack getResult() {
        return result;
    }

    public void setResult(ItemStack result) {
        this.result = result;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
