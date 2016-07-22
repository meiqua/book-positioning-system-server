package service;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import dao.ItemDao;
import model.*;

public class ItemServiceImpl implements ItemService
{
	private ItemDao itemDao;
	private MatchSystem matchSystem;
	
	public MatchSystem getMatchSystem() {
		return matchSystem;
	}

	public void setMatchSystem(MatchSystem matchSystem) {
		this.matchSystem = matchSystem;
	}

	public ItemDao getItemDao() {
		return itemDao;
	}

	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}


	@Override
	public int addItem(Item item) {
		// TODO Auto-generated method stub
		///itemDao.update(item);
		 itemDao.update(item);
		//update will call update or save method
		return 1;
	}

	@Override
	public List<Item> getItemsByLocation(String query) {
		// TODO Auto-generated method stub
		 return itemDao.findByLocationPlus(query);
	}
	@Override
	public List<Item> getItems(String query) {
		// TODO Auto-generated method stub
		return itemDao.findItems(query);
	}
	
	@Override
	public List<Item> getItemsById(String query) {
		// TODO Auto-generated method stub
		String id= query;
		List<Item> list=new  ArrayList<Item>();
		list.add(itemDao.findByMyIdPlus(id));
		return list;
	}

	@Override
	public List<Item> getAllItems()
	{
		return itemDao.findAll(Item.class);
	}
	
	@Override
	public void deleteItem(String id) {
		// TODO Auto-generated method stub
		itemDao.delete(Item.class, id);
	}

	@Override
	public void deleteItem(Item item) {
		// TODO Auto-generated method stub
		itemDao.delete(item);
	}

	@Override
	public void downItem(String location) {
		// TODO Auto-generated method stub
		int id=ParseLocation.parseLocationForId(location);
		List<Item> locationList=getItemsByLocation(location);
		Item targetItem=new Item();
		for(int i=0;i<locationList.size();i++){
			if(id==ParseLocation.parseLocationForId(locationList.get(i).getLocation())){
				targetItem=locationList.get(i);
				break;
			}			
		}
		itemDao.updateForDownItem(targetItem);	
		
		matchSystem.addToMatchList(targetItem);
	}

	@Override
	public void upItem(String location) {
		// called when an item is put into the shelf
		Item item=new Item();

		int state=-100;

		item.setState(state);
		item.setLocation(location);
	//	itemDao.update(item);
		
		//start match
		matchSystem.match(item);
		
		itemDao.updateForUpItem(item);
	}

	@Override
	public void upItemJSON(org.json.JSONObject object) {
		//the jsonObject get from return action
		//format {"id":itemId,"location":shelfLocation}
		String id="0";
		try {
			id = object.getString("id");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String location = "";
		try {
			location = object.getString("location");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int state=-10;

		Item item=itemDao.findByMyId(id);
		item.setState(state);
		item.setLocation(location);
		
		itemDao.update(item);
		
		matchSystem.addToMatchList(item);
	}

	@Override
	public void initialItem(String location) {
		// TODO Auto-generated method stub
		Item item=new Item();

		int state=10;

		item.setState(state);
		item.setLocation(location);
		itemDao.update(item);
		
		itemDao.updateForUpItem(item);
	}






}