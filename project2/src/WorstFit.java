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
        //MemorySlot temp =null;
        for (MemorySlot slot :currentlyUsedMemorySlots) {
            if(max<slot.getBlockEnd()-slot.getEnd()){
                max=slot.getBlockEnd()-slot.getEnd();
                flag=i;
            }
            i++;
            //temp=slot;
        }
        MemorySlot temp = currentlyUsedMemorySlots.get(flag);
        if(p.getMemoryRequirements()<=max){
            address=temp.getEnd();
            temp.setEnd(temp.getEnd()+p.getMemoryRequirements());
            currentlyUsedMemorySlots.set(flag,temp);
        }
        return address;
    }

}
