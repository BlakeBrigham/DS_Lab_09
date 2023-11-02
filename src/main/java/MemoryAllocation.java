public class MemoryAllocation
{
    //package access-reachable by memory manager
    //but not those that would use this module
    String owner;  //which process owns this memory
    long pos;      //where does it start
    long len;      //how long is the memory
    MemoryAllocation prev; // the previous MemoryAllocation
    MemoryAllocation next; // the next MemoryAllocation


    //You might want to add additional data/methods here


    //feel free to alter the constructor if you need/want to
    public MemoryAllocation(MemoryAllocation prev, String owner, long pos, long len, MemoryAllocation next)

    {
	this.owner = owner;
	this.pos = pos;
	this.len=len;
	this.prev = prev;
	this.next = next;
    }


    public String getOwner()
    {
	return owner;
    }

    public long getPosition()
    {
	return pos;
    }

    public long getLength()
    {
	return len;
    }
    


    public String toString()
    {
	return "Alloc "+owner+" at "+pos+" for "+len; 
    }

    public boolean freeCheck(MemoryAllocation mem) {
    	if(mem.owner == "Free") {
    		return true;
    	}
    	return false;
    }
    
    public MemoryAllocation combine(MemoryAllocation mem) {
    	if(this.pos > mem.pos) {
    		mem.len = mem.len + this.len;
    		mem.next = this.next;
    		this.next.prev = mem;
    		return mem;
    	}
    	else {
    		this.len = mem.len + this.len;
    		this.next = mem.next;
    		mem.next.prev = this;
    		return this;
    	}
    }
}
