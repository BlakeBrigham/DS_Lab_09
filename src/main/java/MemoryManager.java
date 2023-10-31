public class MemoryManager
{
   protected MemoryAllocation head;
    
   protected final String Free = "Free";


    /* size- how big is the memory space.  
     *  Memory starts at 0
     *
     */
   public MemoryManager(long size)
   {
	   MemoryAllocation sen = new MemoryAllocation(null, "", -1, -1, null);
	   MemoryAllocation empty = new MemoryAllocation(sen, Free, 0, size, sen);
	   sen.prev = empty;
	   sen.next = empty;
	   head = sen;
   }



    /**
       takes the size of the requested memory and a string of the process requesting the memory
       returns a memory allocation that satisfies that request, or
       returns null if the request cannot be satisfied.
     */
    
   public MemoryAllocation requestMemory(long size,String requester)
   {
	  MemoryAllocation curr = head.next;
	  long curr_pos = 0;
	  while(curr != head) {
		  if((curr.getLength() >= size) && curr.getOwner() == Free) {
			  MemoryAllocation temp = new MemoryAllocation(curr.prev, requester, curr_pos, size, curr.next);
			  if(curr.getLength() > size) {
				  MemoryAllocation empty = new MemoryAllocation(temp, Free, (curr_pos + size), (curr.getLength() - size), curr.next);
				  temp.next = empty;
				  curr.prev.next = temp;
				  curr.next.prev = empty;
			  }
			  else {
				  curr.prev.next = temp;
				  curr.next.prev = temp;
			  }
			  return temp;
		  }
		  
		  curr_pos = curr_pos + curr.getLength();
		  curr = curr.next;
	  }
      return null;
   }


    
    /**
       takes a memoryAllcoation and "returns" it to the system for future allocations.
       Assumes that memory allocations are only returned once.       

     */
   public void returnMemory(MemoryAllocation mem)
   {
	   MemoryAllocation curr = head.next;
	   while(curr != head) {
		   if(curr.getOwner() == mem.getOwner()) {
			   mem.owner = Free;
			   if(curr.freeCheck(curr.prev)) {
				   curr.combine(curr.prev);
			   }
			   else if (curr.freeCheck(curr.next)) {
				   curr.combine(curr.next);
			   }
		   }
		   curr = curr.next;
	   }
	   
   }
    



}
