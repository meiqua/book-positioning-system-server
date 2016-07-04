package dao;

//import java.util.List;

import model.Item;

import java.util.List;

import common.dao.BaseDao;

public interface ItemDao extends BaseDao<Item>{

	List<Item> findItems(String query);

	List<Item> findByLocation(String query);

	void updateForDownItem(Item entity);

	void updateForUpItem(Item entity);
	
	List<Item> findByState(int state,String head);

	Item findByMyId(String id);

	Item findByMyIdPlus(String id);

	List<Item> findByLocationPlus(String query);


}
