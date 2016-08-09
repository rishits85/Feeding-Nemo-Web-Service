package service;


import org.springframework.stereotype.Service;

import model.Time;

@Service
public class FeedService {
	Time t = new Time();
	public boolean setTrue(){
		boolean y = t.getIsScheduled();
		y = true;
		t.setIsScheduled(y); 
		return true;
	}
	public boolean setFalse(){
		boolean y = t.getIsScheduled();
		y = false;
		t.setIsScheduled(y); 
		return true;
	}
}
