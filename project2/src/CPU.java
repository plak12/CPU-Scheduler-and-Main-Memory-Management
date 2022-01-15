import java.util.ArrayList;

public class CPU {

    public static int clock = 0; // this should be incremented on every CPU cycle

    private Scheduler scheduler;
    private MMU mmu;
    private Process[] processes;
    private int currentProcess;


    public CPU(Scheduler scheduler, MMU mmu, Process[] processes) {
        this.scheduler = scheduler;
        this.mmu = mmu;
        this.processes = processes;
    }

    public void run() {
        /* TODO: you need to add some code here
         * Hint: you need to run tick() in a loop, until there is nothing else to do... */

        boolean flag;
        while (true)
        {
            //checks if all processes are TERMINATED
            flag = true;
            for (Process p: processes) {
                if (p.getPCB().getState() != ProcessState.TERMINATED) {
                    flag = false;
                    break;
                }
            }
            //stops cpu if all processes are terminated
            if (flag)
            {
                System.out.println("ok " +   CPU.clock);
                break;
            }

            //change process' state: NEW -> READY
            for (int i=0; i < processes.length;i++) {

                System.out.println(processes[i].toString());

                if (processes[i].getPCB().getState() == ProcessState.NEW )
                {
                    if (CPU.clock >= processes[i].getArrivalTime())
                    {
                        currentProcess = i;
                        if (mmu.loadProcessIntoRAM(processes[i])) {
                            scheduler.addProcess(processes[i]);
                            clock++;
                            //if scheduling algorithm = RR -> create list with processes in order to run
                            if(scheduler instanceof RoundRobin){
                                ((RoundRobin) scheduler).setNextProcess(processes[i]);
                            }
                         }
                    }
                    else i--;
                }

                boolean isRunning = false;
                //check if scheduling algorithm is SRTF
                if (scheduler.getQuantum() == -2) {
                    //check if process' state is RUNNING
                    for (int j = 0; j < processes.length; j++) {
                        if (processes[j].getPCB().getState() == ProcessState.RUNNING) {
                            currentProcess = j;
                            isRunning = true;
                            break;
                        }
                    }
                    //find process with minimum remaining time (state: READY) -> from scheduler
                    //remaining time of currently RUNNING process < remaining time of READY processes
                    if(isRunning && scheduler.getNextProcess() != null) {
                        if (scheduler.getNextProcess().getRestTime() > processes[currentProcess].getRestTime()) {
                            tick();
                        }
                        else{
                            isRunning = false;
                            //process' state = READY
                            processes[currentProcess].waitInBackground();
                            scheduler.addProcess(processes[currentProcess]);
                        }
                    }
                    if(isRunning && scheduler.getNextProcess() == null) {
                        while (true) {
                            if (processes[currentProcess].getPCB().getState() != ProcessState.TERMINATED) {
                                tick();
                            }
                            else{
                                break;
                            }
                        }
                    }
                    if (!isRunning && scheduler.getNextProcess() != null) {
                        //continue with next process
                        Process p = scheduler.getNextProcess();
                        p.run();
                        running(p);
                        scheduler.removeProcess(p);
                    }
                }else if (scheduler.getQuantum() == -1){ //scheduling algorithm = FCFS
                    //check if process' state is RUNNING
                    for (int j = 0; j < processes.length; j++) {
                        if (processes[j].getPCB().getState() == ProcessState.RUNNING) {
                            isRunning = true;
                            currentProcess = j;
                            tick();
                            break;
                        }
                    }
                    //no process RUNNING + list with processes at READY state not empty
                    if ( !isRunning && scheduler.getNextProcess() != null ){
                        //continue with the next process
                        Process p = scheduler.getNextProcess();
                        p.run();
                        running(p);
                        scheduler.removeProcess(p);
                    }
                } else{ //scheduling algorithm = Round Robin
                    //check if process' state is RUNNING
                    for (int j = 0; j < processes.length; j++) {
                        if (processes[j].getPCB().getState() == ProcessState.RUNNING) {
                            isRunning = true;
                            currentProcess = j;
                            break;
                        }
                    }
                    //ean i diergasia mou pou trexei einai idia me tin diergasia pou prepei na trexei continue
                    if(isRunning && scheduler.getNextProcess() != null){
                        if (processes[currentProcess].equals(scheduler.getNextProcess())){
                            tick();
                        }
                        else{
                                isRunning = false;
                                //process' state = READY
                                processes[currentProcess].waitInBackground();
                                scheduler.addProcess(processes[currentProcess]);
                        }
                    }
                    if(isRunning && scheduler.getNextProcess() == null) {
                        while (true) {
                            if (processes[currentProcess].getPCB().getState() != ProcessState.TERMINATED) {
                                tick();
                            } else {
                                break;
                            }
                        }
                    }
                    //no process RUNNING + list with processes at READY state not empty
                    if ( !isRunning && scheduler.getNextProcess() != null ){
                        //continue with the next process
                        Process p = scheduler.getNextProcess();
                        p.run();
                        running(p);
                        scheduler.removeProcess(p);
                    }

                }
            }
        }
    }

    public void tick() {
        /* TODO: you need to add some code here
         * Hint: this method should run once for every CPU cycle */

        //state: NEW -> READY
        running(processes[currentProcess]);
        //running
    }

    private void running(Process p)
    {
        System.out.println("at running " + p.toString());
        //process currently running -> increase clock and decrease remaining time by 1
        clock++;
        p.setRestTime(p.getRestTime() - 1);
        //process completed
        if (p.getRestTime() <= 0)
        {
            //state = TERMINATED
            p.getPCB().setState(ProcessState.TERMINATED,CPU.clock);

            //free used memory
            ArrayList<MemorySlot> m = mmu.getCurrentlyUsedMemorySlots();
            m.removeIf(mm -> p.equals(mm.getP()));
            mmu.setCurrentlyUsedMemorySlots(m);
    // or otan ginete running
        }
    }

}