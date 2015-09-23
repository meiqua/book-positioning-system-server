package action;

import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.Action;

import service.ItemService;

public class ReturnAction implements Action{
	
	private String content;
	
	private ItemService itemService;
	
	public void ret(){
		if(content.contains("{")&&content.contains("}")
				&&content.contains(":")){
			JSONObject object = null;
			try {
				 object=new JSONObject(content);
				 //use json so don't need to parse string by myself
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			itemService.upItemJSON(object);
		}else{
			itemService.upItem(content);
			// location message from sensor
		}
			
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		ret();
		return null;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

}
