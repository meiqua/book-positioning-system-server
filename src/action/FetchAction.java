package action;

import com.opensymphony.xwork2.Action;

import service.ItemService;

public class FetchAction implements Action{
			
			private String content;
			
			private ItemService itemService;
			
			@Override
			public String execute() throws Exception {
				// TODO Auto-generated method stub
				itemService.downItem(content);
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


