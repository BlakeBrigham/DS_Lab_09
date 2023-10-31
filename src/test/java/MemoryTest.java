import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemoryTest {

	MemoryManager manager;
	MemoryManager small;
	MemoryAllocation a;
	MemoryAllocation b;
	MemoryAllocation c; 
	
	@BeforeEach
	void setUp() throws Exception {
		manager = new MemoryManager(100);
		small = new MemoryManager(10);
	

	}
	
	@Test
	void test() {
		a = manager.requestMemory(100, "something");
		assertEquals(0, a.getPosition());
		assertTrue(a.getOwner() == "something");
		assertNull(manager.requestMemory(10, "anything"));
		
		manager.returnMemory(a);
		b = manager.requestMemory(10, "anything");
		assertTrue(b.getOwner() == "anything");
		manager.returnMemory(b);
		
		//Filling up our manager
		MemoryAllocation temp1 = manager.requestMemory(20, "a");
		MemoryAllocation temp2 = manager.requestMemory(20, "b");
		assertTrue(temp2.getPosition() == temp1.getLength());
		MemoryAllocation temp3 = manager.requestMemory(20, "c");
		MemoryAllocation temp4 = manager.requestMemory(20, "d");
		MemoryAllocation temp5 = manager.requestMemory(20, "e");
		
		//Attempting to overfill memory, should null
		assertNull(manager.requestMemory(10, "Papercut"));
		
		//Testing free memory combination for right and releasing
		manager.returnMemory(temp3);
		assertNull(manager.requestMemory(30, "f")); //Over available size
		manager.returnMemory(temp4);
		MemoryAllocation temp6 = manager.requestMemory(30, "f"); // More size that should combine. can fit
		assertTrue(temp6.getOwner() == "f");
		
		//Testing combination for middle side
		manager.returnMemory(temp2);
		manager.returnMemory(temp6);
		MemoryAllocation temp9 = manager.requestMemory(55, "k");
		assertTrue(temp9.getOwner() == "k");
		assertTrue(temp9.getPosition() == 20);
		
		//Left
		manager.returnMemory(temp9);
		
		
		MemoryAllocation temp10 = manager.requestMemory(60, "j");
		assertTrue(temp10.getOwner() == "j");
		assertTrue(temp10.getPosition() == 20);
		manager.returnMemory(temp10);
		
		manager.requestMemory(50, "k");
		
		
		// Testing first-fit-scheme
		manager.returnMemory(temp5);
		MemoryAllocation temp7 = manager.requestMemory(25, "g"); // Should be 30 at end, this leaves us at 5
		assertTrue(temp7.getOwner() == "g");
		manager.returnMemory(temp1); // free first 20
		assertNull(manager.requestMemory(25, "h")); // no block of free memory over 20 atm
		MemoryAllocation temp8 = manager.requestMemory(5, "h"); //Both blocks shuld fit 5, should go in first
		assertTrue(temp8.getOwner() == "h");
		assertEquals(0, temp8.getPosition());
		assertNull(manager.requestMemory(20, "i")); //Biggest free space after should be 15, if we mess up it would not be null
		
	}

}
