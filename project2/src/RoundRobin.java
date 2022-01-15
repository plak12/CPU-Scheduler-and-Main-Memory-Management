import java.util.ArrayList;

public class RoundRobin extends Scheduler {

    private int quantum;
    private ArrayList<Process> nextProcess = new ArrayList<>(processes);
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
        if (nextProcess.isEmpty())  return null;
        for (Process currProcess : nextProcess){
            if (currProcess.getPCB().getState() == ProcessState.TERMINATED) nextProcess.remove(currProcess);
        }
        Process currProcess = nextProcess.get(0);
        if (currProcess.getRRTime() < quantum)
        {
            currProcess.setRRTime(currProcess.getRRTime() + 1);
        }
        else {
            currProcess.setRRTime(1);
            for(int i = 0;i < nextProcess.size() - 1;i++){
                nextProcess.set(i,processes.get(i+1));
            }
            nextProcess.set(nextProcess.size() - 1, currProcess);
        }

        return processes.get(0); //prwta kaleitai h addprocess kai meta h getnext
    }
    public int getQuantum(){
        return quantum;
    }
}
