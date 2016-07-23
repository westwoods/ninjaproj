package AssistClass;

public class ItemSet  {
	
	public ItemSet(int itemcode, int amount, String iteminfo) {
		super();
		this.itemcode = itemcode;
		this.amount = amount;
		this.iteminfo = iteminfo;
	}

	public ItemSet() {
		// TODO Auto-generated constructor stub
	}

	public int itemcode;
	public int amount;
	public String iteminfo;

	public ItemSet CopyFrom(ItemSet i) {
		return this;
	}
}
