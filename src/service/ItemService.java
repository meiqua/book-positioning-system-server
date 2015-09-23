package service;
import java.util.List;

import model.Item;


public interface ItemService
{
	
	int addItem(Item item);
	//it's for admin

	List<Item> getItemsByLocation(String query);
	List<Item> getItems(String query);
	//for query
	
	void deleteItem(String id);
	void deleteItem(Item item);
	//for admin
	
	void downItem(String location);
	//for fetch
	//to update items by location change
	
	void upItem(String location);
	void upItemJSON(org.json.JSONObject object);
	//for return

	List<Item> getItemsById(String query);

	List<Item> getAllItems();
	
//	void parseLocation(String location);
//	//for check location
}