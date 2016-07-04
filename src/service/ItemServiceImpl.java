package service;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import dao.ItemDao;
import model.*;

public class ItemServiceImpl implements ItemService
{
	private ItemDao itemDao;
	
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
	}

	@Override
	public void upItem(String location) {
		// called when an item is put into the shelf
		String head=ParseLocation.parseLocationForHead(location);
		Item item=new Item();
		String id="123321";//just create a temp item to save location
		int state=-10;
		String mThumbnailUrl="abc";//just convenient for finding
		
		while(itemDao.findByMyId(id)!=null){
			id=id+"1";//id can't repeat
			state=state-1;
		}
		item.setId(id);
		item.setState(state);
		item.setLocation(location);
		item.setThumbnailUrl(mThumbnailUrl);
		
		itemDao.update(item);
		
		
		//state
		List<Item> matchState=itemDao.findByState(state,head);
		if(matchState.size()==2){
			if(matchState.get(0).getThumbnailUrl().equals("abc")){
				matchState.get(1)
				.setLocation(matchState.get(0).getLocation());
				matchState.get(1).setState(1);
				itemDao.delete(matchState.get(0));
				//itemDao.update(matchState.get(1));
				itemDao.updateForUpItem(matchState.get(1));
			}else if(matchState.get(1).getThumbnailUrl().equals("abc")){
				matchState.get(0)
				.setLocation(matchState.get(1).getLocation());
				
				matchState.get(0).setState(1);
				itemDao.delete(matchState.get(1));
				//itemDao.update(matchState.get(0));
				itemDao.updateForUpItem(matchState.get(0));
			}
			
		}
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
	//	String head=ParseLocation.parseLocationForHead(location);
//don't need parse location,because location here is just a head.
		int state=-10;
		String idTemp="123321";
		

		List<Item> itemList=itemDao.findByState(state,location);

		//state-1
		while(((!(itemList.contains(itemDao.findByMyId(idTemp))))
				&&itemList.size()==1)
				||itemList.size()>1){
			state=state-1;
			itemList=itemDao.findByState(state,location);
			idTemp=idTemp+"1";
		}
		Item item=itemDao.findByMyId(id);
		item.setState(state);
		item.setLocation(location);
		
		itemDao.update(item);
		
		List<Item> matchState=itemDao.findByState(state,location);
		if(matchState.size()==2){
			if(matchState.get(0).getThumbnailUrl().equals("abc")){
				matchState.get(1)
				.setLocation(matchState.get(0).getLocation());
				matchState.get(1).setState(1);
				itemDao.delete(matchState.get(0));
				//itemDao.update(matchState.get(1));
				itemDao.updateForUpItem(matchState.get(1));
			}else if(matchState.get(1).getThumbnailUrl().equals("abc")){
				matchState.get(0)
				.setLocation(matchState.get(1).getLocation());			
				matchState.get(0).setState(1);
				itemDao.delete(matchState.get(1));
				//itemDao.update(matchState.get(0));
				itemDao.updateForUpItem(matchState.get(0));
			}
		}
	}

	@Override
	public void initialItem(String content) {
		// TODO Auto-generated method stub
		
	}






}