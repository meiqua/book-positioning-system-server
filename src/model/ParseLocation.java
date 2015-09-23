package model;

public class ParseLocation {

	 public static double parseLocationForAverage(String location){
		//location must have the format "12to34" or "1-2-12to34"
		double se=0;
		try {
			if(location.contains("-")){
				String start=location.substring(location.lastIndexOf("-")+1,location.indexOf("t"));
				String end=location.substring(location.indexOf("o")+1,location.length());
				int s=Integer.parseInt(start);
				int e=Integer.parseInt(end);
				se=(s+e)/2.0;
			}
			else{
			String start=location.substring(0,location.indexOf("t"));
			String end=location.substring(location.indexOf("o")+1,location.length());
			int s=Integer.parseInt(start);
			int e=Integer.parseInt(end);
			se=(s+e)/2.0;
			}
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			return se;
		
	}
	
	 public static int parseLocationForStart(String location){
		//location must have the format "12to34" or "1-2-12to34"
		int s=0;
		try {
			if(location.contains("-")){
				String start=location.substring
						(location.lastIndexOf("-")+1,location.indexOf("t"));		
				 s=Integer.parseInt(start);
			}
			else{
			String start=location.substring(0,location.indexOf("t"));
			 s=Integer.parseInt(start);
			}
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return s;
	}
	
	 public static int parseLocationForEnd(String location){
		//location must have the format "12to34" or "1-2-12to34"
		int e=0;
		try {
			if(location.contains("-")){
				String end=location.substring(location.indexOf("o")+1,location.length());
				 e=Integer.parseInt(end);
			}
			else{
			String end=location.substring
					(location.indexOf("o")+1,location.length());
			 e=Integer.parseInt(end);
			
			}
			
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
			return e;
		
	}
	
	 public static String parseLocationForHead(String location){
		//location must have the format "12to34" or "1-2-12to34"
		String head="";
		try {
			if(location.contains("-")){
			  head=location.substring(0,location.lastIndexOf("-"));
			}
			else{
		      head="";
			}
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			return head;	
	}
	
	 public static String parseStartEndForLocation(int start,int end,String head){
		//location must have the format "12to34" or "1-2-12to34"
        String location="";
        location=head+"-"+start+"to"+end;
			return location;	
	}
}
