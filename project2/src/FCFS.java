
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
