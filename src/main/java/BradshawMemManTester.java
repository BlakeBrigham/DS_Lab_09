public class BradshawMemManTester
{



   public static void check(MemoryAllocation alloc, long pos,long size,String ow)
   {
      assert pos == alloc.getPosition();
      assert ow.equals(alloc.getOwner());
      assert size == alloc.getLength();
   }


   public static void main(String [] args)
   {
      MemoryManager man = new MemoryManager(100);
   
      String owner = "fred";
      MemoryAllocation A = man.requestMemory(100,owner);
      check(A,0,100,owner);
   
      MemoryAllocation B = man.requestMemory(1,owner);
      assert B== null;
   
      man.returnMemory(A);
      A = null;
      A = man.requestMemory(100,owner);
      check(A,0,100,owner);
   
      B = man.requestMemory(1,owner);
      assert B== null;
      man.returnMemory(A);
   //now more nuanced
   
      MemoryAllocation X1 = man.requestMemory(5,owner);
      check(X1,0,5,owner);
      MemoryAllocation X2 = man.requestMemory(35,owner);
      check(X2,5,35,owner);
      MemoryAllocation X3 = man.requestMemory(10,owner);
      check(X3,40,10,owner);
      MemoryAllocation X4 = man.requestMemory(20,owner);
      check(X4,50,20,owner);
      MemoryAllocation X5 = man.requestMemory(30,owner);
      check(X5,70,30,owner);
   
   
      B = man.requestMemory(1,owner);
      assert B== null;
   
   //now work with releases
      man.returnMemory(X1);
      man.returnMemory(X3);
      X1 = man.requestMemory(1,owner);
      check(X1,0,1,owner);
   
   
      X3 = man.requestMemory(5,owner);
      check(X3,40,5,owner);
   
   //check for merge - right
      man.returnMemory(X4);
      X4 = man.requestMemory(20,owner);
      System.out.println(X4.toString());
      check(X4,45,20,owner);
   //check for merge - left
      man.returnMemory(X2);
      X2 = man.requestMemory(39,owner);
      check(X2,1,39,owner);
   //check for mid
      man.returnMemory(X1);
      man.returnMemory(X3);
      man.returnMemory(X2);
   
      X1 = man.requestMemory(45,owner);
      check(X1,0,45,owner);
   
   
   
  
   
   }


}
