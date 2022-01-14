import java.util.ArrayList;

public class FirstFit extends MemoryAllocationAlgorithm {

    public FirstFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    //sum up available block sizes για να έχουμε την μνήμη σε συνεχόμενες θέσεις (πχ: από 0 μέχρι 85 για blocks: 10,40,25,30)
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
            if ( end <= blockEnd ) // ελέγχω αν χωράει στο block που είμαι
            {
                fit = true; // θεωρώ ότι χωράει
                for (MemorySlot m: currentlyUsedMemorySlots) {
                    if (m.getStart() >= start && m.getStart() < end) // ελέγχω αν άλλη διεργασία παίρνει χώρο τον οποίο χρειάζομαι
                    {
                        fit = false;
                        start = m.getEnd();
                        end = updateEnd(start, p);

                    }
                }
                //εάν η διεργασία χωράει, προσθέτω το slot που πρόκειται να δεσμεύσει στην μνήμη στην λίστα με τα currently used memory slots
                if(fit) {
                    //ορίζω την διεύθυνση που θα επιστρέψω
                    address = start;
                    MemorySlot slot = new MemorySlot(start, end, blockStart, blockEnd, p);
                    currentlyUsedMemorySlots.add(slot);
                }
            } else { // εάν δεν χωράει στο block που είμαι


                // διαφορετικά πάω στον επόμενο διαθέσιμο χώρο μνήμης
                    blockStart = blockEnd;
                    start = blockStart;
                    end = updateEnd(start, p);

                if(start == sumOfBlockSize)
                    break;
                    //εάν υπάρχει διαθέσιμος χώρος μνήμης στο ίδιο block δεν θα χρειαστεί να αλλάξω το blockEnd
                    //εάν όμως το block μνήμης στο οποίο βρίσκομαι είναι ήδη κατηλειμένο τότε παώ στο επόμενο block
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
