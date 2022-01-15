/**
 * First Fit algorithm in Memory Management
 * -------------------------------------------
 * In the first fit, the partition is allocated which is first sufficient from the top of Main Memory.
 */

import java.util.ArrayList;

public class FirstFit extends MemoryAllocationAlgorithm {

    public FirstFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    //sum up available block sizes -> continuous memory (for example: from 0 to 85 for blocks: 10,40,25,30)
    private int getSumOfBlockSize()
    {
        int sum = 0;
        for (int i: availableBlockSizes) {
            sum += i;
        }
        return sum;
    }

    private int updateEnd(int start, Process p)
    {
        return start + p.getMemoryRequirements();
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;
        int address = -1;
        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */

        int sumOfBlockSize = getSumOfBlockSize();
        int start = 0, end = updateEnd(start, p);
        int blockStart = 0, blockEnd = availableBlockSizes[0];


        while(!fit) {
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
                if(fit) {
                    address = start;
                    MemorySlot slot = new MemorySlot(start, end, blockStart, blockEnd, p);
                    currentlyUsedMemorySlots.add(slot);
                }
            } else { // if process doesn't fit at current block
                // check next block
                    blockStart = blockEnd;
                    start = blockStart;
                    end = updateEnd(start, p);

                if(start == sumOfBlockSize)
                    break;
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

        //in case the process doesn't fit return -1
        return address;
    }

}
