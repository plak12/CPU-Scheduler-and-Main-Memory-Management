/**
 * This class represents BestFit algorithm
 */

import java.util.ArrayList;

public class BestFit extends MemoryAllocationAlgorithm {

    private ArrayList<MemorySlot> MemorySlots = new ArrayList<MemorySlot>(); // βοηθητική λίστα με τιμές που θα ενημερώνονται μόλις κάποια διαδικασία μπει στη μνήμη
    public BestFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
        for (int i : availableBlockSizes){
            MemorySlots.add(new MemorySlot(0,0,0, i,null)); // αρχικοποιούμε τη λίστα ανάλογα με το availableBlockSizes - ακόμη δεν έχει γίνει χρήση κάποιου πόρου μνήμης
        }
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false; // μεταβλητή για να ξέρω αν βρέθηκε κάποιος πόρος στον οποίο χωρά η διεργασία
        int address = -1; // επιστρεφόμενη μεταβλητή
        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */

        int minFree = availableBlockSizes[0]; // μεταβλητή που ελέγχει το διαθέσιμο χώρο και αρχικά παίρνει μια τιμή μεγάλη αλλά και πιθανή
        int currAddress = 0, counter = 0, minSlot = 0;



        for ( MemorySlot slots : MemorySlots ) // τρέχω μέσα στη λίστα με τα διαθέσιμα μπλοκς μνήμης
        {
            if (slots.getBlockEnd() - slots.getEnd() == p.getMemoryRequirements() ){ // αν ταιριάζει απόλυτα
                minFree = slots.getBlockEnd()-slots.getEnd(); // και = με τη μνήμη που παίρνει η διεργασία

                minSlot = counter; // για να ξέρουμε σε ποιο στοιχείο αναφερόμαστε
                address = slots.getStart() + currAddress; // εκεί που είχε μείνει ο μετρητής + έναρξη του τωρινού μπλοκ
                fit = true;
                break; // χωρά ακριβώς άρα δεν θα βρούμε πόρο που θα αφήνει λιγότερη μνήμη ανεκμετάλλευτη αφού δεν αφήνει ανεκμετάλλευτη μνήμη - οπή
            }
            else if ( minFree >= slots.getBlockEnd() - slots.getEnd() && slots.getBlockEnd() - slots.getEnd() > p.getMemoryRequirements() ) // αν έχουμε μικρότερο διαθέσιμο χώρο και η διεργασία χωράει σε αυτόν
            {
                minFree = slots.getBlockEnd() - slots.getEnd(); // ανανέωση τιμής μικρότερου διαθέσιμου χώρου
                address = slots.getStart() + currAddress;
                fit = true;
                minSlot = counter;
            }
            currAddress += slots.getBlockEnd() - slots.getBlockStart(); // δείκτης που δίνει σε ποιο σημείο της μνήμης βρισκόμαστε, με το πέρας μιας επανάληψης προσθέτουμε το μπλοκ που περάσαμε
            counter++; //αύξηση μετρητή που σημειώνει για να βρούμε μετά με το μινΣλοτ τη θέση που βρήκαμε την ελάχιστη οπή
        }
        if (fit == true)
        {
            MemorySlots.get(minSlot).setStart(MemorySlots.get(minSlot).getEnd()); // ανανέωση της τιμής που αναλογεί στη λίστα
            MemorySlots.get(minSlot).setEnd(MemorySlots.get(minSlot).getStart()+p.getMemoryRequirements());
            if (MemorySlots.get(minSlot).getStart() == 0) { // αν είναι η πρώτη φορά που χρησιμοποιείται την προσθέτουμε στη λίστα currentlyUsedMemorySlot
                currentlyUsedMemorySlots.add(new MemorySlot(MemorySlots.get(minSlot).getEnd(), MemorySlots.get(minSlot).getStart() + p.getMemoryRequirements(), 0, MemorySlots.get(minSlot).getBlockEnd(), p));
            }
            else { //αν έχει ξαναχρησιμοποιηθεί ο ίδιος πόρος μνήμης
                for (MemorySlot slot : currentlyUsedMemorySlots){ //βρίσκουμε σε ποιο πόρο αναφερόμαστε
                    if (slot.getP().equals(p)){
                        slot.setStart(slot.getEnd()); // και του ανανεώνουμε τις τιμές
                        slot.setEnd(slot.getStart()+p.getMemoryRequirements());
                    }
                }
            }
        }
        return address;
    }
}
