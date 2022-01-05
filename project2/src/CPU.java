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
      /*  boolean flag=true;
        int i=0;

        // oso den exoume kamia diergasia sti NEW kai process[oles]==TERMINATED
        // exartatai an theloume i new na ginetai edo

        //?
        while (flag)
        {
            flag=false;
            for (Process curProcess : processes)
            {
                if (curProcess.getPCB().getState() == ProcessState.NEW )
                {
                    flag = true;
                    tick();
                    break;
                }
            }
        }*/
        boolean flag=true;
        Process currentProcess2=processes[0];
        clock=currentProcess2.getArrivalTime();
        while(flag==true){
            for(int i=0;i<scheduler.getQuantum();i++) {
                for (Process curProcess : processes) {
                    if (clock == curProcess.getArrivalTime()) {
                        scheduler.addProcess(curProcess);
                    }



                }
                currentProcess2.setBurstTime(currentProcess2.getBurstTime()-1);
                if(currentProcess2.getBurstTime()==0){
                    scheduler.removeProcess(currentProcess2);
                    break;
                }
                clock++; //isws prepei na eine pio panw
            }
           currentProcess2= scheduler.getNextProcess();

            if(processes.length==0)
                flag=false;
        }



    }

    public void tick() {
        /* TODO: you need to add some code here
         * Hint: this method should run once for every CPU cycle */
        if (mmu.loadProcessIntoRAM(processes[currentProcess])) {
            scheduler.addProcess(processes[currentProcess]);
            clock++;
        }
    }
}
