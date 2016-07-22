package service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import dao.ItemDao;
import model.*;

public class MatchSystemImpl implements MatchSystem {
	private ItemDao itemDao;
	public ItemDao getItemDao() {
		return itemDao;
	}
	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}
	
	private LinkedHashMap<Long,Item> linkedHashMap = new LinkedHashMap<Long,Item>();
	
	@Override
	public void addToMatchList(Item item) {
		long sysDate = System.currentTimeMillis();
		linkedHashMap.put(sysDate, item);
		
		//remove item too long ago
        for (long value : linkedHashMap.keySet()) {  
            if(sysDate-value>30000){//>30s
            	linkedHashMap.remove(value);
            }else{
            	break;
            }
        }  
	}
	@Override
	public void match(Item item) {
		//try to find time<30 to match
		//if false,try to find closest to match.
		String[] locSplite = item.getLocation().split("-");
		Item matchedItem = null;
		long keyMatch=0;
		int matchLevel=0;
		for (long value : linkedHashMap.keySet()){
			String location = linkedHashMap.get(value).getLocation();
			String[] storedLocSplite = location.split("-");
			if(storedLocSplite[0]==locSplite[0]&&
					storedLocSplite[1]==locSplite[1]&&
					storedLocSplite[2]==locSplite[2]){
				if(matchLevel<=2){
					matchedItem=linkedHashMap.get(value);
					keyMatch=value;
					matchLevel=2;
				}
				for(int i=3;i<6;i++){
					if(storedLocSplite[i]==locSplite[i]){
						if(matchLevel<=i){
							matchLevel=i;
							matchedItem=linkedHashMap.get(value);
							keyMatch=value;
						}
					}
				}
			}
		} 
		if(matchLevel>0){
			matchedItem.setState(100);
			matchedItem.setLocation(item.getLocation());
			itemDao.update(matchedItem);
			linkedHashMap.remove(keyMatch);
		}else{
			String searchArea = locSplite[0]+"-"+locSplite[1];
			Item itemWanted=null;
			List<Item> itemCandidates = itemDao.findByLocationMinus(searchArea);
			int disMin=1000;
			int confidence=100;
			ArrayList<Integer> disDistribute = new ArrayList<Integer>();
			for(int i=0;i<itemCandidates.size();i++){
				String locTemp = itemCandidates.get(i).getLocation();
				String[] locTempSplite = locTemp.split("-");
				int rowsDis = java.lang.Math.abs(Integer.parseInt(locTempSplite[2]) - 
						Integer.parseInt(locSplite[2]));
				int colsDis = java.lang.Math.abs(Integer.parseInt(locTempSplite[3]) - 
						Integer.parseInt(locSplite[3]));
				disDistribute.add(rowsDis+colsDis);
				if((rowsDis+colsDis)<disMin){
					disMin = rowsDis+colsDis;
					itemWanted=itemCandidates.get(i);
				}
			}
			for(int i=0;i<disDistribute.size();i++){
				int disTemp = java.lang.Math.abs(disDistribute.get(i)-disMin);
				if(disTemp<5){
					confidence=(int)(confidence*(0.4+0.1*disTemp));
				}
			}
			if(itemWanted!=null){
				itemWanted.setLocation(item.getLocation());
				itemWanted.setState(confidence);
				itemDao.update(itemWanted);
			}
		}
	}	
}
