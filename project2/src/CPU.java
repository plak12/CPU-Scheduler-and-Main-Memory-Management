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
        int i=0;


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
                System.out.println("ok");
                break;
            }

            for (Process curProcess : processes)
            {
                if ( curProcess.getPCB().getState() == ProcessState.NEW && CPU.clock == curProcess.getArrivalTime() )
                {

                    tick();
                } // metafera apo to new ready



                boolean isRunning = false;
                for (Process pp: processes) {
                    if (pp.getPCB().getState() == ProcessState.RUNNING) {
                        isRunning = true;
                        running(pp);
                        break;
                    }
                }
                if (! isRunning) {
                    Process p = scheduler.getNextProcess();
                    p.run();
                    running(p);
                    scheduler.removeProcess(p);

                }


            }



        }
//        boolean flag=true;
//        Process currentProcess2=processes[0];
//        clock=currentProcess2.getArrivalTime();
//        while(flag==true){
//            for(int i=0;i<scheduler.getQuantum();i++) {
//                for (Process curProcess : processes) {
//                    if (clock == curProcess.getArrivalTime()) {
//                        scheduler.addProcess(curProcess);
//                    }
//
//
//
//                }
//                currentProcess2.setBurstTime(currentProcess2.getBurstTime()-1);
//                if(currentProcess2.getBurstTime()==0){
//                    scheduler.removeProcess(currentProcess2);
//                    break;
//                }
//                clock++; //isws prepei na eine pio panw
//            }
//           currentProcess2= scheduler.getNextProcess();
//
//            if(processes.length==0)
//                flag=false;




    }

    public void tick() {
        /* TODO: you need to add some code here
         * Hint: this method should run once for every CPU cycle */

        //apo to new sto ready
        if (mmu.loadProcessIntoRAM(processes[currentProcess])) {
            scheduler.addProcess(processes[currentProcess]);
            clock++;
        }

        //running
    }

    public void running(Process p)
    {
        clock++;
        p.minusRestTime();
        if (p.getRestTime() == 0)
        {
            p.getPCB().setState(ProcessState.TERMINATED,CPU.clock);
            mmu.removeFromMemory(p);
    // or otan ginete running
        }
    }

}
