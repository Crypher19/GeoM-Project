package appserver;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SharedData {
	List<ThreadUser> users = new ArrayList<>();
	
	SharedData(){
		
	}
	
	public void addUser(ThreadUser t){
		users.add(t);
	}
}
