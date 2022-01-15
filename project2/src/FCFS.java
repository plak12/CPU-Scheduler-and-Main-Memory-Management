/**
 * In the first come, first served (FCFS) scheduling algorithm
 * the process that comes first will be executed first and next process starts only after the previous gets fully executed.
 */

public class FCFS extends Scheduler {

    public FCFS() {
        /* TODO: you _may_ need to add some code here */
        super();
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

        //process with minimum arrival time
        int min = processes.get(0).getArrivalTime();
        Process rtn = processes.get(0);
        for (Process p: processes) {
            if(min > p.getArrivalTime()) {
                min = p.getArrivalTime();
                rtn = p;
            }
            }
        return rtn;
    }

    @Override
    public int getQuantum() {
        return -1;
    }
}
