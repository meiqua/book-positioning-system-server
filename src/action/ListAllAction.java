package action;

import java.util.List;

import com.opensymphony.xwork2.Action;

import service.ItemService;
import model.*;

public class ListAllAction implements Action{
	
	private ItemService itemService;
	List<Item> items;

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		items=itemService.getAllItems();
		
		for(int i=0;i<items.size();i++){
			items.get(i).setAuthor(java.net.URLDecoder
					.decode(items.get(i).getAuthor(), "UTF-8"));
			items.get(i).setTitle(java.net.URLDecoder
					.decode(items.get(i).getTitle(), "UTF-8"));
		}

		return SUCCESS;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
    
}
