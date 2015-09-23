package dao;

//import java.util.List;

import dao.ItemDao;
import model.Item;
import model.ParseLocation;

import java.util.List;

import common.dao.impl.BaseDaoHibernate4;

public class ItemDaoHibernate4 extends BaseDaoHibernate4<Item>
	implements ItemDao{
	@Override
	public List<Item> findItems(String query)
	{
		//add find by title 模糊查询
		
		String[] arr = query.split(" |,");
		
		String myQuery1="";
		String myQuery2="";
		
		for(int i=0;i<arr.length;i++){
			myQuery1=myQuery1+" en.author like '%"+ arr[i].trim()+"%'"+" or ";
			myQuery2=myQuery2+" en.title like '%"+ arr[i].trim()+"%'"+" or ";
		}
		myQuery2=myQuery2.substring(0,myQuery2.length()-3);
		//remove "or " at last
		
		return find("select distinct en from "+"Item"
			 + " en where "+myQuery1.trim()+" "+myQuery2.trim()
			 );
	}
	@Override
	public List<Item> findByLocation(String query) {
        //this is for system query
		//get location of shelf
//		String shelf="";
		try {
			//if contain detail location in shelf
			if(query.contains("t")||query.contains("o")){
				query=query.substring(0,query.lastIndexOf("-"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return find("select en from "+"Item"
				 + " en "+"where en.location like '"+query.trim()+"%'");
		//find all items in the shelf 
	}
	
	@Override
	public List<Item> findByLocationPlus(String query) {
      //plus is for reader query,should think about state
		//get location of shelf
		//String shelf="";
		try {
			//if contain detail location in shelf
			if(query.contains("t")||query.contains("o")){
				query=query.substring(0,query.lastIndexOf("-"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return find("select en from "+"Item"
				 + " en "+"where en.location like '"+query.trim()+"%'"
				 + " and en.state>=0");
		//find all items in the shelf 
	}
	
	@Override
	public List<Item> findByState(int state,String head) {
		// find specified item by state
		//head is the shelfLocation
		return find("select en from "+"Item"
				 + " en "+"where en.location like '"+head.trim()+"%'"
				 +" and en.state="+state);
	}
	
	
	@Override
	public Item findByMyId(String id) {
		//for system query
		//find item by id
		List<Item> list= find("select en from "+"Item"
				 + " en "+"where en.id= '"+id.trim()+ "' ");
		//pay attention to space around id can't be ' id '
		
		if(list.size()==0){
			return null;
		}
		else
		return list.get(0);
	}
	
	@Override
	public Item findByMyIdPlus(String id) {
		//plus is for reader query,should think about state
		//find item by id
		
		
		//but finally I find it does not need to query with state here
		//so now it is same to findByMyId
		List<Item> list= find("select en from "+"Item"
				 + " en "+"where en.id= '"+id.trim()+ "' ");
		//pay attention to space around id can't be ' id '
		if(list.size()==0){
			return null;
		}
		else
		return list.get(0);
	}
	

	
	
	@Override
	public void updateForDownItem(Item entity) {
		// TODO Auto-generated method stub
		String entityLocation=entity.getLocation();
		String entityHead=ParseLocation.parseLocationForHead(entityLocation);
		int entityStart=ParseLocation.parseLocationForStart(entityLocation);
		int entityEnd=ParseLocation.parseLocationForEnd(entityLocation);
		int entityStoE=entityEnd-entityStart+1;
		
		List<Item> list=findByLocationPlus(entityHead);
		//state<0 needn't to update
		if(list.contains(entity))
		list.remove(entity);
		//get list in the same book shelf
		
		String[] locList=new String[list.size()]; 
		int[] locListStart=new int[list.size()]; 
		int[] locListEnd=new int[list.size()]; 
		
		for(int i=0;i<list.size();i++){
			locList[i]=list.get(i).getLocation();
			locListStart[i]=ParseLocation.parseLocationForStart(locList[i]);
			locListEnd[i]=ParseLocation.parseLocationForEnd(locList[i]);
			if(locListStart[i]>=entityStart){
				//>= is better,espatially for upItem
				
				locListStart[i]=locListStart[i]-entityStoE;
				locListEnd[i]=locListEnd[i]-entityStoE;
				locList[i]=ParseLocation.parseStartEndForLocation
						(locListStart[i], locListEnd[i], entityHead);
				list.get(i).setLocation(locList[i]);
				update(list.get(i));
			}
		}
		
		entity.setState(-1);
		update(entity);
		
		
	}
	@Override
	public void updateForUpItem(Item entity) {
		// TODO Auto-generated method stub
		String entityLocation=entity.getLocation();
		String entityHead=ParseLocation.parseLocationForHead(entityLocation);
		int entityStart=ParseLocation.parseLocationForStart(entityLocation);
		int entityEnd=ParseLocation.parseLocationForEnd(entityLocation);
		int entityStoE=entityEnd-entityStart+1;
		
		List<Item> list=findByLocationPlus(entityHead);
		if(list.contains(entity))
		list.remove(entity);
		//get list in the same book shelf 
		
		String[] locList=new String[list.size()]; 
		int[] locListStart=new int[list.size()]; 
		int[] locListEnd=new int[list.size()]; 
		
		for(int i=0;i<list.size();i++){
			locList[i]=list.get(i).getLocation();
			locListStart[i]=ParseLocation.parseLocationForStart(locList[i]);
			locListEnd[i]=ParseLocation.parseLocationForEnd(locList[i]);
			if(locListStart[i]>=entityStart){
				//>= is better,espatially for upItem
				
				locListStart[i]=locListStart[i]+entityStoE;
				locListEnd[i]=locListEnd[i]+entityStoE;
				locList[i]=ParseLocation.parseStartEndForLocation
						(locListStart[i], locListEnd[i], entityHead);
				list.get(i).setLocation(locList[i]);
				update(list.get(i));
				//update book shelf behind the book
			}
		}
		
		entity.setState(1);
		update(entity);
	}

}
