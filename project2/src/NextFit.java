/**
 * Next Fit algorithm in Memory Management
 * -----------------------------------------------------
 * Next fit is a modified version of ‘first fit’.
 * It begins as the first fit to find a free partition but when called next time
 * it starts searching from where it left off, not from the beginning.
 */

import java.util.ArrayList;

public class NextFit extends MemoryAllocationAlgorithm {

    public NextFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;
        int address = -1;
        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */

        int sum=getSumOfBlockSize();
        int pointer = 0, start = pointer, end = updateEnd(start, p);
        boolean repeat = false;
        int repeatInt=0;
        int blockStart = 0, blockEnd = availableBlockSizes[0];

        //pointer: begin from where it left off
        if(! currentlyUsedMemorySlots.isEmpty()) {
            pointer = currentlyUsedMemorySlots.get(currentlyUsedMemorySlots.size() - 1).getEnd();

            if (end > sum) {
                start = 0;
                blockStart = 0;
                blockEnd = availableBlockSizes[0];
            } else {
                blockStart = currentlyUsedMemorySlots.get(currentlyUsedMemorySlots.size() - 1).getBlockStart();
                blockEnd = currentlyUsedMemorySlots.get(currentlyUsedMemorySlots.size() - 1).getBlockEnd();
            }
        }
        while(!fit && !repeat)
        {
            if ( end <= blockEnd ) // check if fits in current block
            {
                fit = true; // it fits!!
                for (MemorySlot m: currentlyUsedMemorySlots) {
                    if (m.getStart() >= start && m.getStart() < end) // check if other process uses memory that I need
                    {
                        fit = false;
                        start = m.getEnd();
                        end = updateEnd(start, p);
                    }
                }
                //if process still fits, add slot in currently used memory slots
                if (fit)
                {
                    address = start;
                    MemorySlot slot = new MemorySlot(start,end,blockStart,blockEnd,p);
                    currentlyUsedMemorySlots.add(slot);
                }
            }
            else // if process doesn't fit at current block check next block
            { if (end > sum) {
                start = 0;
                blockStart = 0;
                blockEnd = availableBlockSizes[0];
            }
            else {
                blockStart = blockEnd;
                start = blockStart;
                end = updateEnd(start, p);

                int s = 0;
                for (int i = 0; i < availableBlockSizes.length; i++) {
                    s += availableBlockSizes[i];

                    if (s == blockStart) {
                        blockEnd = s + availableBlockSizes[i + 1];
                        break;
                    }
                }
            }
            }
            if(start<=pointer)
            repeatInt=1;
            if(pointer<=start && repeatInt == 1)
                repeat = true;
        }
        //in case the process doesn't fit return -1
        return address;
    }

    //sum up available block sizes -> continuous memory (for example: from 0 to 85 for blocks: 10,40,25,30)
    private int getSumOfBlockSize()
    {
        int sum=0;
        for (int i: availableBlockSizes) {
            sum+=i;
        }
        return sum;
    }
    private int updateEnd(int start, Process p)
    {
        return start + p.getMemoryRequirements();
    }
}
