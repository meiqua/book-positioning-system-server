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
		double se=ParseLocation.parseLocationForAverage(location);
           //find items according to location of shelf
		//then check which se is close
		List<Item> list=getItemsByLocation(location);
		double[] seList=new double[list.size()];
		double temp=0.0;
		for(int i=0;i<list.size();i++){
			if((list.get(i).getSe()-se)>=0)
			    seList[i]=list.get(i).getSe()-se;
			else
				seList[i]=se-list.get(i).getSe();
		}
		temp=seList[0];
		int min=0;
		for(int i=0;i<list.size();i++){
			if(seList[i]<temp){
				temp=seList[i];
				min=i;
			}
		}
		
		itemDao.updateForDownItem(list.get(min));
		
	}

	
	//下面俩个upitem是为了将传感器传过来的location跟扫描的location+item匹配
	//每个产生独特的state 最后会进行state匹配
	//这是为了应对多个人同时在一排书架上还书的情况
	//不过还是有一种情况可能造成错误：一个人先扫描二维码后放入
	//但是考虑到一排书架本来就不太可能同时有多人还书
	//有多人一般也会先扫描先放入
	
	//当然，最好加入预警代码来提示管理员查看来100%解决这种情况
	//seem too 麻烦..
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
		
		
		//匹配state
		List<Item> matchState=itemDao.findByState(state,head);
		if(matchState.size()==2){
			if(matchState.get(0).getThumbnailUrl().equals("abc")){
				matchState.get(1)
				.setLocation(matchState.get(0).getLocation());
				matchState.get(1).setState(1);//匹配完 state恢复成1
				itemDao.delete(matchState.get(0));
				//itemDao.update(matchState.get(1));
				itemDao.updateForUpItem(matchState.get(1));
			}else if(matchState.get(1).getThumbnailUrl().equals("abc")){
				matchState.get(0)
				.setLocation(matchState.get(1).getLocation());
				
				matchState.get(0).setState(1);//匹配完 state恢复成1
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
		
		//当这个state下没有之前为了保存位置随便设置的id 
		//说明这个state是这个函数提供的 
		//为了保证一个临时state下只有两个来自不同函数的item以供匹配
		//state-1
		while(((!(itemList.contains(itemDao.findByMyId(idTemp))))
				&&itemList.size()==1)
				//>1说明两个正在配对
				//=1但不是idTemp说明是这个函数产生的state
				//state-1避开
				||itemList.size()>1){
			state=state-1;
			itemList=itemDao.findByState(state,location);
			idTemp=idTemp+"1";
		}
		Item item=itemDao.findByMyId(id);
		item.setState(state);
		item.setLocation(location);
		
		itemDao.update(item);
		
		//匹配state
		List<Item> matchState=itemDao.findByState(state,location);
		if(matchState.size()==2){
			if(matchState.get(0).getThumbnailUrl().equals("abc")){
				matchState.get(1)
				.setLocation(matchState.get(0).getLocation());
				matchState.get(1).setState(1);//匹配完 state恢复成1
				itemDao.delete(matchState.get(0));
				//itemDao.update(matchState.get(1));
				itemDao.updateForUpItem(matchState.get(1));
			}else if(matchState.get(1).getThumbnailUrl().equals("abc")){
				matchState.get(0)
				.setLocation(matchState.get(1).getLocation());			
				matchState.get(0).setState(1);//匹配完 state恢复成1
				itemDao.delete(matchState.get(1));
				//itemDao.update(matchState.get(0));
				itemDao.updateForUpItem(matchState.get(0));
			}
		}
	}






}