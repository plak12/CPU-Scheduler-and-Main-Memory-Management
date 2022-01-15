/**
 * Best Fit algorithm in Memory Management
 * -----------------------------------------
 * Best fit allocates the process to a partition which is
 * the smallest sufficient partition among the free available partitions.
 */
import java.util.ArrayList;

public class BestFit extends MemoryAllocationAlgorithm {

    private ArrayList<MemorySlot> MemorySlots = new ArrayList<MemorySlot>();
    public BestFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
        for (int i : availableBlockSizes){
            MemorySlots.add(new MemorySlot(0,0,0, i,null)); // we could update blockStart
        }
    }

    /*public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        //boolean fit = false;
        int address = -1;
        int min=0,i=0,flag=0;
        //MemorySlot temp =null;
        for (MemorySlot slot : currentlyUsedMemorySlots) {
            if(min > slot.getBlockEnd() - slot.getEnd()){
                min = slot.getBlockEnd()-slot.getEnd();
                flag = i;
            }
            i++;
            //temp=slot;
        }
        MemorySlot temp = currentlyUsedMemorySlots.get(flag);
        if(p.getMemoryRequirements()<=min){
            address=temp.getEnd();
            temp.setEnd(temp.getEnd()+p.getMemoryRequirements());
            currentlyUsedMemorySlots.set(flag,temp);
        }
        return address;
    }*/



    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;
        int address = -1;
        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */

        int pointer = 0;
        //int sum = 0, start = pointer, blockStart = 0, blockEnd =  availableBlockSizes[0] ,end = start + p.getMemoryRequirements();
        int minFree = 0;//currentlyUsedMemorySlots(0).getBlockEnd()-currentlyUsedMemorySlots(0).getBlockStart();
        int currAddress = 0, counter = 0, minSlot = 0;



        for ( MemorySlot slots : MemorySlots )
        {
            if (slots.getBlockEnd() - slots.getEnd() == p.getMemoryRequirements() ){ // fits perfectly
                minFree = slots.getBlockEnd()-slots.getEnd();
                slots.setStart(slots.getEnd());
                slots.setEnd(slots.getStart()+p.getMemoryRequirements());
                address = slots.getStart() + currAddress;
                fit = true;
                break; // xora akrivos ara den tha vroume kati pou na afinei ligoteri opi mnimis
            }
            else if ( minFree > slots.getBlockEnd() - slots.getEnd() && slots.getBlockEnd() - slots.getEnd() > p.getMemoryRequirements() ) // ||minFree>slots.getBlockStart()-slots.getBlockStart()
            {
                minFree = slots.getBlockEnd() - slots.getEnd();
                address = slots.getStart() + currAddress;
                fit = true;
                minSlot = counter;
            }
            currAddress += slots.getBlockEnd() - slots.getBlockStart();
            counter++;
        }
        if (fit == true)
        {
            MemorySlots.get(minSlot).setStart(MemorySlots.get(minSlot).getEnd());
            MemorySlots.get(minSlot).setEnd(MemorySlots.get(minSlot).getStart()+p.getMemoryRequirements());
            if (MemorySlots.get(minSlot).getStart() == 0) { //an den exei xanaxrisimopoihthei
                currentlyUsedMemorySlots.add(new MemorySlot(MemorySlots.get(minSlot).getEnd(), MemorySlots.get(minSlot).getStart() + p.getMemoryRequirements(), 0, MemorySlots.get(minSlot).getBlockEnd(), p));
            }
            else {
                for (MemorySlot slot : currentlyUsedMemorySlots){
                    if (slot.getP().equals(p)){
                        slot.setStart(slot.getEnd());
                        slot.setEnd(slot.getStart()+p.getMemoryRequirements());
                    }
                }
            }
        }
        return address;
    }

}