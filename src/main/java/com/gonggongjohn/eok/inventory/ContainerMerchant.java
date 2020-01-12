package com.gonggongjohn.eok.inventory;

import java.util.ArrayList;

import com.gonggongjohn.eok.utils.MerchantTradeData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMerchant extends Container {

	private ItemStackHandler items = new ItemStackHandler(18);

	public int currentPage = 1;
	public int currentDeal = 1;
	public int totalPages;
	private int currentSlotId = 0;
	/* 玩家可以进行的全部交易列表 */
	public ArrayList<MerchantTradeData> tradeList;

	protected Slot slot1 = new SlotItemHandler(items, currentSlotId++, 130, 48) {

		@Override
		public boolean isItemValid(ItemStack stack) {
			return true;
		}

		@Override
		public int getItemStackLimit(ItemStack stack) {
			return 64;
		}

	};

	protected Slot slot2 = new SlotItemHandler(items, currentSlotId++, 156, 48) {
		@Override
		public boolean isItemValid(ItemStack stack) {
			return true;
		}

		@Override
		public int getItemStackLimit(ItemStack stack) {
			return 64;
		}

	};
	protected Slot slot3 = new SlotItemHandler(items, currentSlotId++, 144, 105) {
		@Override
		public boolean isItemValid(ItemStack stack) {
			return true;
		}

		@Override
		public int getItemStackLimit(ItemStack stack) {
			return 64;
		}

		@Override
		public boolean canTakeStack(EntityPlayer playerIn) {
			return true;
		}

	};

	public ContainerMerchant(EntityPlayer player) {
		super();
		this.addSlotToContainer(slot1);
		this.addSlotToContainer(slot2);
		this.addSlotToContainer(slot3);

		for (int i = 0; i < 5; i++) {
			this.addSlotToContainer(new SlotItemHandler(items, currentSlotId++, 21, i * 22 + 48) {

				@Override
				public boolean isItemValid(ItemStack stack) {
					return false;
				}

				@Override
				public boolean canTakeStack(EntityPlayer playerIn) {
					return false;
				}

			});
			this.addSlotToContainer(new SlotItemHandler(items, currentSlotId++, 41, i * 22 + 48) {

				@Override
				public boolean isItemValid(ItemStack stack) {
					return false;
				}

				@Override
				public boolean canTakeStack(EntityPlayer playerIn) {
					return false;
				}

			});
			this.addSlotToContainer(new SlotItemHandler(items, currentSlotId++, 90, i * 22 + 48) {

				@Override
				public boolean isItemValid(ItemStack stack) {
					return false;
				}

				@Override
				public boolean canTakeStack(EntityPlayer playerIn) {
					return false;
				}

			});
		}

		// 快捷栏
		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(player.inventory, i, 13 + 18 * i, 223));
		}

		// 背包第一行
		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(player.inventory, 9 + i, 13 + 18 * i, 165));
		}

		// 背包第二行
		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(player.inventory, 18 + i, 13 + 18 * i, 183));
		}

		// 背包第三行
		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(player.inventory, 27 + i, 13 + 18 * i, 201));
		}

		tradeList = MerchantTradeData.getRandomTrades(3);
		totalPages = tradeList.size() / 5;
		if (totalPages % 5 != 0)
			totalPages++;

	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		return ItemStack.EMPTY;
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);

		if (playerIn.isServerWorld()) {
			ItemStack s1 = this.slot1.getStack();
			ItemStack s2 = this.slot2.getStack();

			if (s1 != ItemStack.EMPTY) {
				playerIn.dropItem(s1, false);
				this.slot1.putStack(ItemStack.EMPTY);
			}
			if (s2 != ItemStack.EMPTY) {
				playerIn.dropItem(s2, false);
				this.slot2.putStack(ItemStack.EMPTY);
			}

		}
	}

	public void previousPage() {
		if (totalPages == 0)
			return;

		if (currentPage <= 1) {
			currentPage = 1;
			return;
		}

		if (currentPage > totalPages) {
			currentPage = totalPages;
			return;
		}

		currentPage--;
	}

	public void nextPage() {
		if (totalPages == 0)
			return;

		if (currentPage >= totalPages) {
			currentPage = totalPages;
			return;
		}

		if (currentPage < 1) {
			currentPage = 1;
			return;
		}

		currentPage++;
	}

	/**
	 * 获取某一页的所有交易
	 * 
	 * @param page 页码
	 * @return 交易列表
	 */
	public ArrayList<MerchantTradeData> getTrades(int page) {
		// 起始点和结束点（从1开始）
		int s = page * 5 - 4;
		int e = page * 5;
		ArrayList<MerchantTradeData> result = new ArrayList<MerchantTradeData>();
		for (int i = s - 1; i <= e - 1; i++) {
			if (i < 0 || i > tradeList.size() - 1)
				break;
			result.add(tradeList.get(i));
			if (i + 1 == tradeList.size())
				break;
		}
		for (int i = 0; i < result.size() % 5; i++) {
			result.add(null);
		}
		return result;
	}

	/**
	 * 获取当前玩家选中的交易
	 * 
	 * @return 当前交易
	 */
	public MerchantTradeData getCurrentTrade() {
		return getTrades(currentPage).get(currentDeal - 1);
	}

	/**
	 * 判断一个ItemStack能否完成交易
	 * 
	 * @param currentStack 当前给定的ItemStack
	 * @param tradeData    正常交易的价格
	 * @return
	 */
	private boolean canTrade(ItemStack currentStack, ItemStack tradeData) {
		if (currentStack.getItem() != tradeData.getItem())
			return false;
		if (currentStack.getCount() < tradeData.getCount())
			return false;
		return true;
	}

	/**
	 * 在玩家单击购买按钮时调用
	 */
	public void buy() {

		if (slot3.getHasStack() && slot3.getStack().getItem() != getCurrentTrade().getResult().getItem()) {
			return;
		}

		if (slot3.getHasStack()
				&& slot3.getStack().getCount() + getCurrentTrade().getResult().getCount() >= getCurrentTrade()
						.getResult().getItem().getItemStackLimit(slot3.getStack())) {
			return;
		}

		if (slot3.getHasStack() && slot3.getStack().getCount() + getCurrentTrade().getResult().getCount() >= slot3
				.getSlotStackLimit()) {
			return;
		}

		if (canTrade(slot1.getStack(), getCurrentTrade().getCost1())
				&& canTrade(slot2.getStack(), getCurrentTrade().getCost2())) {

			ItemStack s1 = slot1.getStack();
			ItemStack s2 = slot2.getStack();
			ItemStack result = slot3.getStack();

			if (slot1.getStack().getCount() == getCurrentTrade().getCost1().getCount()
					|| slot2.getStack().getCount() == getCurrentTrade().getCost2().getCount()) {

				if (slot1.getStack().getCount() == getCurrentTrade().getCost1().getCount()) {
					s1 = ItemStack.EMPTY;
				} else {
					s1 = new ItemStack(slot1.getStack().getItem(),
							slot1.getStack().getCount() - getCurrentTrade().getCost1().getCount());
				}

				if (slot2.getStack().getCount() == getCurrentTrade().getCost2().getCount()) {
					s2 = ItemStack.EMPTY;
				} else {
					s2 = new ItemStack(slot2.getStack().getItem(),
							slot2.getStack().getCount() - getCurrentTrade().getCost2().getCount());
				}

			} else {
				s1 = new ItemStack(slot1.getStack().getItem(),
						slot1.getStack().getCount() - getCurrentTrade().getCost1().getCount());
				s2 = new ItemStack(slot2.getStack().getItem(),
						slot2.getStack().getCount() - getCurrentTrade().getCost2().getCount());
			}

			if (slot3.getStack().isEmpty()) {
				result = getCurrentTrade().getResult();
			} else {
				result = new ItemStack(getCurrentTrade().getResult().getItem(),
						slot3.getStack().getCount() + getCurrentTrade().getResult().getCount());
			}

			slot1.putStack(s1);
			slot2.putStack(s2);
			slot3.putStack(result);

		}
	}

}
