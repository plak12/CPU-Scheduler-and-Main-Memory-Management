public class SRTF extends Scheduler {

    public SRTF() {
        /* TODO: you _may_ need to add some code here */
    }

    public void addProcess(Process p) {
        /* TODO: you need to add some code here */
        processes.add(p);
    }

    public Process getNextProcess() {
        /* TODO: you need to add some code here
         * and change the return value */
        //εάν δεν υπάρχει καμία δειργασία σε κατάσταση ready επιστρέφει null -> ενδεικτική τιμή για τερματισμό
        if(processes.isEmpty())
            return null;
        //βρίσκει την διεργασία με τον μικρότερο rest time
        int min = processes.get(0).getRestTime();
        Process rtn = processes.get(0);

        for (Process p: processes) {
            if(min > p.getRestTime()) {
                min = p.getRestTime();
                rtn = p;
            }
        }
        return rtn;
    }

    //anti gia true/false an trexei i srtf
    @Override
    public int getQuantum() {
        return -2;
    }

    //private boolean isSRTF(){ return true; }


}
