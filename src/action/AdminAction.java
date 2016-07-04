package action;

import java.io.File;

import javax.servlet.ServletContext;

import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.Action;

import model.Item;
import service.ItemService;


public class AdminAction implements Action, ServletContextAware{

	private Item itemBean;
	private ItemService itemService;
	ServletContext servletContext;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
//		itemBean.setThumbnailUrl(
//				servletContext.getRealPath("images")+ 
//				File.separator + itemBean.getThumbnailUrl());
		
		itemBean.setThumbnailUrl(
				"http://115.28.205.144:8080/library/images"+ 
				File.separator + itemBean.getThumbnailUrl());
		//title author 
		itemBean.setTitle(java.net.URLEncoder
				.encode(itemBean.getTitle(), "UTF-8"));
		itemBean.setAuthor(java.net.URLEncoder
				.encode(itemBean.getAuthor(), "UTF-8"));
		itemService.addItem(itemBean);
		return SUCCESS;
	}
	
	public ServletContext getServletContext() {
		return servletContext;
	}



	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}


	public Item getItemBean() {
		return itemBean;
	}



	public void setItemBean(Item itemBean) {
		this.itemBean = itemBean;
	}




	public ItemService getItemService() {
		return itemService;
	}



	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}









}
