import java.util.ArrayList;

public class BestFit extends MemoryAllocationAlgorithm {

    public BestFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;
        int address = -1;
        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */
        int minFree = currentlyUsedMemorySlots(0).getBlockEnd()-currentlyUsedMemorySlots(0).getBlockStart();
        int currAddress = 0, counter = 0, minSlot = 0;
        /*for ( int size: availableBlockSizes )
        {
            if ( size == p.getBurstTime() )
            {
                minFree = p.getBurstTime();
                address = currAddress;
                break;
            }
            else if ( size - p.getBurstTime() < minFree && size - p.getBurstTime() > 0)
            {
                minFree = size - p.getBurstTime();
                address = currAddress;
            }
            currAddress += size;
        }*/

        for ( MemorySlot slots : currentlyUsedMemorySlots )
        {
            if (slots.getBlockEnd()-slots.getStart() == p.getBurstTime() ){
                minFree = slots.getBlockEnd()-slots.getStart();
                slots.setStart() = slots.getStart() + p.getBurstTime(); // update available start
                address = currAddress + slots.getStart(); // to proigoumeno block + torines theseis tou block
                fit = true;
                break; // xora akrivos ara den tha vroume kati pou na afinei ligoteri opi mnimis
            }
            else if ( minFree > slots.getBlockEnd() - slots.getStart() && slots.getBlockEnd() - slots.getStart() > p.getBurstTime() ) // prosoxi giati sti CPU allazoume to burst time
            {
                minFree = slots.getBlockEnd() - slots.getStart();
                address = currAddress + slots.getStart();
                fit = true;
                minSlot = counter;
            }
            currAddress += slots.getBlockEnd() - slots.getBlockStart();
            counter++;
        }
        if (fit == true)
        {
            currentlyUsedMemorySlots(minSlot).setStart() = currentlyUsedMemorySlots(minSlot).setStart() + p.getBurstTime(); //available start
        }

        return address;
    }

}
