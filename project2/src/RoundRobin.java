/**
 * In the Round Robin scheduling algorithm, each process is assigned a fixed time slot in a cyclic way.
 */
import java.util.ArrayList;
public class RoundRobin extends Scheduler {

    private int quantum;
    //ascending listing of processes according arrival time
    private ArrayList<Process> nextProcess = new ArrayList<>();

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

        //stin processes oi diergasies mpainoun me tin seira pou erxontai kai ginontai READY
        //ara ego trexo ousiastika tin proti diergasia kathe fora

        //no process with state: READY -> signal value
        if (processes.isEmpty())
            return null;

        //if all processes are executed return null -> signal value
        //return the next processes to be executed
        return processes.get(0);
    }

    public int getQuantum(){ return quantum; }

    protected void setNextProcess(Process p){ nextProcess.add(p); }
}
