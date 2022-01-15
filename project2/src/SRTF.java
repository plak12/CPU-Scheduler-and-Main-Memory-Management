/**
 * In the Shortest Remaining Time First (SRTF) scheduling algorithm,
 * the process with the smallest amount of time remaining until completion is selected to execute.
 */

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

        //no process with state: READY -> signal value
        if(processes.isEmpty())
            return null;

        //process with minimum remaining time
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

    //used as signal value -> scheduler = SRTF
    @Override
    public int getQuantum() {
        return -2;
    }


}
