/**
 * Worst Fit algorithm in Memory Management
 * ------------------------------------------------------
 * Worst Fit allocates a process to the partition which is largest
 * sufficient among the freely available partitions available in the main memory.
 */

import java.util.ArrayList;

public class WorstFit extends MemoryAllocationAlgorithm {

    public WorstFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        //boolean fit = false;
        int address = -1;
        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */
        int max=0,i=0,flag=0;
        int max2=0,flag2=0;
        for (MemorySlot slot :currentlyUsedMemorySlots) {
            if(max<slot.getBlockEnd()-slot.getEnd()){
                max=slot.getBlockEnd()-slot.getEnd();
                flag=i;
            }
            i++;
        }
        i=0;
        for (int block: availableBlockSizes ) {
            if(max2<block){
                max2=block;
                flag2=i;
            }
            i++;
        }
        for (MemorySlot slot :currentlyUsedMemorySlots) {
            if(getStartingPoint(flag2)==slot.getBlockStart()){
                max2=0;
                break;
            }
        }
        if(max>max2) {
            MemorySlot temp = currentlyUsedMemorySlots.get(flag);
            if (p.getMemoryRequirements() <= max) {
                address = temp.getBlockStart();
                temp.setEnd(temp.getEnd() + p.getMemoryRequirements());
                currentlyUsedMemorySlots.set(flag, temp);
            }
        }
        else {
            if (p.getMemoryRequirements() <= max2) {

                MemorySlot slot = new MemorySlot(getStartingPoint(flag2), getStartingPoint(flag2) + p.getMemoryRequirements(), getStartingPoint(flag2), getStartingPoint(flag2) + availableBlockSizes[flag2], p);

                currentlyUsedMemorySlots.add(slot);
                address = getStartingPoint(flag2);
            }
        }
        return address;

    }
    private int getStartingPoint(int flag2)
    {
        int start = 0;
        for (int k = 0; k < flag2; k++) {
            start += availableBlockSizes[k];
        }
        return start;
    }

}
