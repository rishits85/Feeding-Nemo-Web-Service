package controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.Time;
import service.FeedService;

@Controller
@RequestMapping( "/Feed")
public class FeedController {
	int difference ;
	int a;
	boolean schedule;
	//static as the variable is accessed across multiple threads
	@Autowired
	static FeedService fs;
	
	
	@RequestMapping(value = "/schedule/", method = RequestMethod.POST)
	public int ScheduleFeed(@RequestParam(value = "difference",required = false) String y) throws InterruptedException, ExecutionException{
		difference = Integer.parseInt(y);
		NewScheduledThreadPoolTest.mymethod(difference);
		return difference;
	}
	
	@RequestMapping(value = "/inquireSchedule", method = RequestMethod.GET)
	public ResponseEntity<Time> RequestFeed(){
		if(schedule == true){
			schedule = fs.setFalse();
			return new ResponseEntity<Time>(HttpStatus.OK);
		}
		return new ResponseEntity<Time>(HttpStatus.BAD_REQUEST);
		
	}
	//schedule the task to happen after a certain number of times
	static class NewScheduledThreadPoolTest {
	    public static void mymethod(int difference, final String... args) throws InterruptedException, ExecutionException {
	        // creates thread pool with 1 thread
	        final ScheduledExecutorService schExService = Executors.newScheduledThreadPool(1);
	        // Object creation of runnable thread.
	        final Runnable ob = new NewScheduledThreadPoolTest().new myclass();
	        // Thread scheduling ie run it after "difference" hours before and then after every 24 hours later on
	        schExService.scheduleWithFixedDelay(ob, difference, 24, TimeUnit.HOURS);
	        // waits for termination for 30 seconds only
	        schExService.awaitTermination(10, TimeUnit.SECONDS);
	        // shutdown now.
	        schExService.shutdownNow();
	        System.out.println("Shutdown Complete");
	    }
	
	class myclass implements Runnable{

		@Override
		public void run() {
			// the mechanism to give a positive feedback to the arduino service
				fs.setTrue();
		}}
	}
}

