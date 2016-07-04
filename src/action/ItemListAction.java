package action;

import java.util.List;


import com.opensymphony.xwork2.ActionSupport;

import model.Item;

import service.ItemService;

public class ItemListAction extends ActionSupport
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ItemService itemService;

	private List<Item> items;
	
	private String content;

	public void list(){
		//add list method here
		if(content.substring(0,1).equals("~")){
			content=content.substring(1,content.length());
			setItems(itemService.getItemsByLocation(content));
		}
		else if(content.substring(0,1).equals("^")){
			content=content.substring(1,content.length());
			setItems(itemService.getItemsById(content));
		}
		else{		
			setItems(itemService.getItems(content));
		}
	}

	
	public ItemService getItemService() {
		return itemService;
	}
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public String execute(){
		list();
		return SUCCESS;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

}