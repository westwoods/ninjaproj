package AssistClass;

import java.util.ArrayList;

public class ItemArray {
	static class UIButtonClass {
		static class GroupA {
			static ArrayList<ItemArray> AItem = new ArrayList<ItemArray>();
		}

		static class GroupD {
			ArrayList<ItemArray> DItem = new ArrayList<ItemArray>();
		}

		static class GroupU {
			ArrayList<ItemArray> GItem = new ArrayList<ItemArray>();
		}

		static class GroupS {
			ArrayList<ItemArray> SItem = new ArrayList<ItemArray>();
		}
	}

	public int itemcode;
	public int amount;
	public int price;
	public int dprice;
	public String iteminfo;
}
