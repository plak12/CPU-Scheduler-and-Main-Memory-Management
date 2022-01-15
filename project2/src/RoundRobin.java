/**
 * In the Round Robin scheduling algorithm, each process is assigned a fixed time slot in a cyclic way.
 */

public class RoundRobin extends Scheduler {

    private int quantum;

    public RoundRobin() {
        this.quantum = 1; // default quantum
        /* TODO: you _may_ need to add some code here */
    }

    public RoundRobin(int quantum) {
        this();
        this.quantum = quantum;
    }

    public void addProcess(Process p) {
        /* TODO: you need to add some code here */
        processes.add(p);
    }

    public Process getNextProcess() {
        /* TODO: you need to add some code here
         * and change the return value */

        //no process with state: READY -> signal value
        if (processes.isEmpty())
            return null;

        //at processes list every processes (state: READY) is added according
        //its arrival time -> in the correct order to run
        return processes.get(0);
    }

    public int getQuantum(){ return quantum; }

}
