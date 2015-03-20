package stage.wstp.search.tools;
import java.util.*;

public class TestRetainAll {

	    public static void main(String[] args)
	    {
	        // Create a linked list object:
	        LinkedList ll =  new LinkedList();

	        // Create a HashSet object:
	        Set hs = new HashSet();

	        // Add some elements to hs:
	        hs.add("Isabella");
	        hs.add("Angelina");
	        hs.add("Pille");
	        hs.add("Hazem");

	        // Add some elements to ll:
	        ll.add("Isabella");
	        ll.add("Angelina");
	        ll.add("test");
	        ArrayList hsTwo = new ArrayList<String>(hs);
	        // Retain elements from ll into hs:
	        
	        hsTwo.retainAll(ll);

	        // Display hs:
	        System.out.println("hsTwo contains  u: " + hsTwo);
	        System.out.println("hs contains  u: " + hs);
	        System.out.println("ll contains: " + ll);
	    }
}
