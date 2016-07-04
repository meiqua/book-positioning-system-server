package action;

import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.Action;

import service.ItemService;

public class ReturnAction implements Action{
	
	private String content;
	
	private ItemService itemService;

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		if(content.contains("{")&&content.contains("}")
				&&content.contains(":")){
			JSONObject object = null;
			try {
				 object=new JSONObject(content);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			itemService.upItemJSON(object);
		}else{
			itemService.upItem(content);
			// location message from sensor
		}
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
