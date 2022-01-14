import java.util.ArrayList;

public class MMU {

    private final int[] availableBlockSizes;
    private MemoryAllocationAlgorithm algorithm;
    private ArrayList<MemorySlot> currentlyUsedMemorySlots;

    public MMU(int[] availableBlockSizes, MemoryAllocationAlgorithm algorithm) {
        this.availableBlockSizes = availableBlockSizes;
        this.algorithm = algorithm;
        this.currentlyUsedMemorySlots = new ArrayList<MemorySlot>();
    }

    public boolean loadProcessIntoRAM(Process p) {
        boolean fit = false;
        /* TODO: you need to add some code here
         * Hint: this should return true if the process was able to fit into memory
         * and false if not */

        int address = algorithm.fitProcess(p,currentlyUsedMemorySlots);
        if (address != -1)
            fit = true;
        if (fit)
            p.getPCB().setState(ProcessState.READY,CPU.clock);
        else {
            System.out.println("  "+p.getMemoryRequirements()+" NOT FIT  "); //////
            p.getPCB().setState(ProcessState.TERMINATED,CPU.clock);////
        }
        return fit;
    }

    public ArrayList<MemorySlot> getCurrentlyUsedMemorySlots() {
        return currentlyUsedMemorySlots;
    }

    public void setCurrentlyUsedMemorySlots(ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        this.currentlyUsedMemorySlots = currentlyUsedMemorySlots;
    }
}
