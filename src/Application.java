import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {
	public static void main(String[] args) {
		Assignment8 a8 = new Assignment8();
		
		
//		List<Integer> allNumbers = new ArrayList<>();
		//new ArrayList of integers is not enough,
		//this step was the hardest, but
		//I found a way to help the ArrayList to collect all numbers:
		List<Integer> allNumbers = Collections.synchronizedList(new ArrayList<>());
		
		//not CPU but I/O bound task1
		//executor to use with CompletableFuture, instead of default one
		ExecutorService eService = Executors.newCachedThreadPool();
		
		List<CompletableFuture<Void>> tasks = new ArrayList<>();
		
		for(int i = 0; i < 1000; i++) {
			CompletableFuture<Void> task = CompletableFuture.supplyAsync(() -> a8.getNumbers(), eService)
					   										.thenAcceptAsync(allNumbers::addAll);
			tasks.add(task);
			}
		
		
		//to keep the main thread going until all threads are completed (lesson 8)
		while(tasks.stream().filter(CompletableFuture::isDone).count() < 1000) {
		}
		//to check if all numbers are processed
		System.out.println("All numbers fetched: " + allNumbers.size());
		
		//counting the numbers
		//Collections.frequency applied to a HashSet will work, 
		//assign the list to a Set
		Set<Integer> mySet = new HashSet<>(allNumbers);
		//and iterate through the loop 
		//printing out the number of repetitions
		for(Integer number: mySet){
		 System.out.print(number + "=" + Collections.frequency(allNumbers,number)+", ");
		}
	}
}
