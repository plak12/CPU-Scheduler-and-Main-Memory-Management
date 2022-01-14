
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
        //?

        pcb.setState(ProcessState.RUNNING,CPU.clock);
        CPU.clock += 2;

    }

    // gia to SRTF
    public void waitInBackground() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process stops running */
        //?
        pcb.setState(ProcessState.READY,CPU.clock);
        CPU.clock += 1; //?


    }

    public double getWaitingTime() {
        /* TODO: you need to add some code here
         * and change the return value */
        ArrayList<Integer> start = pcb.getStartTimes();
        ArrayList<Integer> stop = pcb.getStopTimes();
        double sum = start.get(0) - arrivalTime;

        for(int i=0; i < stop.size() -1 ; i++)
            sum += stop.get(i) - start.get(i+1);

        return sum;
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

    public int getRestTime(){return restTime;}
    public void setRestTime(int n) {restTime = n;}
    public String toString()
    {
        return getMemoryRequirements() + " " + getPCB().getState() + " " + getRestTime(); }

    
    
}