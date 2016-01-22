package appserver;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class sharedData {
	List<threadUser> users = new ArrayList<>();
	
	sharedData(){
		
	}
	
	public void addUser(threadUser t){
		users.add(t);
	}
}
