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
        Process temp;
        int i;
        temp= processes.get(0);
        for(i=0;i<processes.size()-1;i++){
            processes.set(i,processes.get(i+1));
        }
        processes.set(i,temp);
        return processes.get(0); //prwta kaleitai h addprocess kai meta h getnext
    }
    public int getQuantum(){
        return quantum;
    }
}
