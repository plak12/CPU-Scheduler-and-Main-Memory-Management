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


      boolean flag=true;


        //?
        while (true)
        {

            flag = true;
            for (Process p: processes) {
                if (p.getPCB().getState() != ProcessState.TERMINATED) {
                    flag = false;
                    break;
                }
            }
            if (flag)
            {
                System.out.println("ok " +   CPU.clock);
                break;
            }


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
                         }
                    }
                    else i--;
                }
                // metafera apo to new ready

 

                boolean isRunning = false;
                for (int j=0; j < processes.length; j++) {
                    if (processes[j].getPCB().getState() == ProcessState.RUNNING) {
                        isRunning = true;
                        currentProcess = j;
                        tick();
                        break;
                    }
                }
                if (! isRunning && scheduler.getNextProcess() != null && !(scheduler instanceof RoundRobin)) {
                    Process p = scheduler.getNextProcess();
                    p.run();
                    running(p);
                    scheduler.removeProcess(p);
                }
                else if ( !isRunning && scheduler.getNextProcess() != null ){
                    Process p = scheduler.getNextProcess();
                    if (p.getRRTime() == 0) p.run();
                    running(p);
                    scheduler.removeProcess(p);
                }
            }
        }





    }

    public void tick() {
        /* TODO: you need to add some code here
         * Hint: this method should run once for every CPU cycle */
        ;
        //apo to new sto ready

        running(processes[currentProcess]);
        //running
    }

    private void running(Process p)
    {
        System.out.println("at running " + p.toString());
        clock++;
        p.setRestTime(p.getRestTime() - 1);
        if (p.getRestTime() <= 0)
        {
            p.getPCB().setState(ProcessState.TERMINATED,CPU.clock);

            ArrayList<MemorySlot> m = mmu.getCurrentlyUsedMemorySlots();
            m.removeIf(mm -> p.equals(mm.getP()));
            mmu.setCurrentlyUsedMemorySlots(m);
    // or otan ginete running
        }
    }
}
