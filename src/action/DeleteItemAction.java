package action;

import com.opensymphony.xwork2.Action;

import service.ItemService;

public class DeleteItemAction implements Action{

	private String id;
	private ItemService itemService;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		itemService.deleteItem(id);
		return SUCCESS;
	}
	public ItemService getItemService() {
		return itemService;
	}
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
