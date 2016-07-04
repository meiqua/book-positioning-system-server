package model;

public class ParseLocation {
	 public static int parseLocationForId(String location){
		int id=-1;
		try {
			String[] parts = location.split("-");
			id = Integer.parseInt(parts[parts.length]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}	
			return id;		
	}
	
	 public static String parseLocationForHead(String location){
		//B2  -6   -1  -1     -1   -2
		//room-area-row-column-side-id
		String head="";
		try {
			String[] parts = location.split("-");
			if(parts.length>5){
			  head=location.substring(0,location.lastIndexOf("-"));
			}	
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
			return head;	
	}
	
	 public static String parseIdForLocation(String head,int id){
        String location="";
		String[] parts = location.split("-");
		if(parts.length>5){
		  head=location.substring(0,location.lastIndexOf("-"));
		}
        location=head+"-"+id;
		return location;	
	}
}
