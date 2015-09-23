package action;

import com.opensymphony.xwork2.ActionSupport;



@SuppressWarnings("serial")
public class DispatchAction extends ActionSupport{
private String content;
private String key;
public String getKey() {
	return key;
}


public void setKey(String key) {
	this.key = key;
}

private String tag;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		 
	          //actually only one parameter will be get
			   
		if(key.equals("query"))
		    	 tag="query";
		     else if (key.equals("shelfQuery")){
		    	 tag="query";
		    	 content="~"+content;
		    	 //the head is just for 做个记号..
		     } 
		     else if (key.equals("return"))
		    	 tag="return";
		     else if (key.equals("fetch"))
		    	 tag="fetch";
		     else if (key.equals("update")) {
		    	 tag="query";
		    	 content="^"+content;
		    	 //the head is just for 做个记号..
		     }
		 
		
		return tag;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
