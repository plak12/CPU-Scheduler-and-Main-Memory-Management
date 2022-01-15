import java.util.ArrayList;
import java.util.Arrays;

public class Process {
    private ProcessControlBlock pcb;
    private int arrivalTime;
    private int burstTime;
    private int memoryRequirements;
    private int restTime;

    public Process(int arrivalTime, int burstTime, int memoryRequirements) {
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        restTime = burstTime;
        this.memoryRequirements = memoryRequirements;
        this.pcb = new ProcessControlBlock();
        pcb.setState(ProcessState.NEW,CPU.clock);
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public ProcessControlBlock getPCB() {
        return this.pcb;
    }

    public void run() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process starts running */

        // change process state to RUNNING + increase clock by 2
        pcb.setState(ProcessState.RUNNING,CPU.clock);
        CPU.clock += 2;

    }

    // for SRTF
    public void waitInBackground() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process stops running */

        //set state to READY
        pcb.setState(ProcessState.READY,CPU.clock);
        //CPU.clock += 2; //γίνεται ταυτόχρονα με το RUNNING -> READY άρα δεν χρειάζεται ???

    }

    public double getWaitingTime() {
        /* TODO: you need to add some code here
         * and change the return value */

        ArrayList<Integer> start = pcb.getStartTimes();
        ArrayList<Integer> stop = pcb.getStopTimes();
        if(!(start.isEmpty() || stop.isEmpty())) {
            double sum = start.get(0) - arrivalTime;

            for (int i = 0; i < (stop.size() - 2); i++)
                sum += stop.get(i) - start.get(i + 1);
            return sum;
        }
        return 0;
    }

    public double getResponseTime() {
        /* TODO: you need to add some code here
         * and change the return value */
        int start = pcb.getStartTimes().get(0);

        return start - arrivalTime;
    }

    public double getTurnAroundTime() {
        /* TODO: you need to add some code here
         * and change the return value */

        return burstTime + getWaitingTime();
    }
    public int getArrivalTime(){
        return arrivalTime;
    }
    public int getMemoryRequirements() { return memoryRequirements;}

    protected int getRestTime(){return restTime;}
    protected void setRestTime(int n) {restTime = n;}
    public String toString()
    {
        return getMemoryRequirements() + " " + getPCB().getState() + " " + getRestTime(); }

    
    
}