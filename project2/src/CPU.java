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
            //βρίσκει αν έχουν τερματιστεί όλες οι διεργασίες
            flag = true;
            for (Process p: processes) {
                if (p.getPCB().getState() != ProcessState.TERMINATED) {
                    flag = false;
                    break;
                }
            }
            //αν ναι σταματά η εκτέλεση της cpu
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
                //an trexei ton srtf
                if (scheduler.getQuantum() == -2) {

                    //στον srtf θέλω να ελέξω αν υπάρχει διεργασία με μικρότερο rest time για να την βάλω να τρέξει

                    //έλεγχος εάν τρέχει κάποια διεργασία
                    for (int j = 0; j < processes.length; j++) {
                        if (processes[j].getPCB().getState() == ProcessState.RUNNING) {
                            currentProcess = j;
                            isRunning = true;
                            //αυξάνω το ρολόι και μειώνω το rest time της διεργασίας
                            //και αν η διεργασία έχει ολοκληρώσει την εκτέλεση της την βάζω σε κατάσταση TERMINATED
                            //και βγαίνει από την λίστα currently used memory slots
                            //tick();
                            break;
                        }
                    }
                    //βρίσκω την διεργασία με το μικρότερο rest time (state: READY)
                    //Process pr = scheduler.getNextProcess();
                    //εάν η διεργασία που τρέχει τώρα έχει μικρότερο rest time από τις διεργασίες που είναι έτοιμες για να εκτελεστούν
                    if(isRunning && scheduler.getNextProcess() != null) {
                        if (scheduler.getNextProcess().getRestTime() > processes[currentProcess].getRestTime()) {
                            tick();
                        }
                        else{
                            isRunning = false;
                            //θέτει την διεργασία σε κατάσταση READY
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
                        //συνεχίζω με την επόμενη διεργασία
                        Process p = scheduler.getNextProcess();
                        //η οποία μπαίνει σε κατάσταση RUNNING + αυξάνω το ρολόι κατά 2
                        p.run();
                        running(p);
                        scheduler.removeProcess(p);
                    }




                }else{


                    //έλεγχος εάν τρέχει κάποια διεργασία
                    for (int j = 0; j < processes.length; j++) {
                        if (processes[j].getPCB().getState() == ProcessState.RUNNING) {
                            isRunning = true;
                            currentProcess = j;
                            //αυξάνω το ρολόι και μειώνω το rest time της διεργασίας
                            //και αν η διεργασία έχει ολοκληρώσει την εκτέλεση της την βάζω σε κατάσταση TERMINATED
                            //και βγαίνει από την λίστα currently used memory slots
                            tick();
                            break;
                        }
                    }
                    //εάν δεν έχω κάποια διεργασία σε κατάσταση RUNNING και υπάρχει διεργασία προς εκτέλεση
                    if (!isRunning && scheduler.getNextProcess() != null) {
                        //συνεχίζω με την επόμενη διεργασία
                        Process p = scheduler.getNextProcess();
                        //η οποία μπαίνει σε κατάσταση RUNNING + αυξάνω το ρολόι κατά 2
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
        ;
        //apo to new sto ready
        running(processes[currentProcess]);
        //running
    }

    private void running(Process p)
    {
        System.out.println("at running " + p.toString());
        //όταν τρέχει μια διεργασία -> αυξάνει το ρολόι και μειώνει το rest time κατά 1
        clock++;
        p.setRestTime(p.getRestTime() - 1);
        //εάν η διεργασία έχει ολοκληρωθεί
        if (p.getRestTime() <= 0)
        {
            //μπαίνει σε κατάσταση TERMINATED
            p.getPCB().setState(ProcessState.TERMINATED,CPU.clock);

            //και ελευθερώνεται ο χώρος που χρησιμοποιεί στην μνήμη
            ArrayList<MemorySlot> m = mmu.getCurrentlyUsedMemorySlots();
            m.removeIf(mm -> p.equals(mm.getP()));
            mmu.setCurrentlyUsedMemorySlots(m);
    // or otan ginete running
        }
    }

}
//        boolean flag=true;
//        Process currentProcess2=processes[0];
//        clock=currentProcess2.getArrivalTime();
//        while(flag==true){
//            for(int i=0;i<scheduler.getQuantum();i++) {
//                for (Process processes[i] : processes) {
//                    if (clock == processes[i].getArrivalTime()) {
//                        scheduler.addProcess(processes[i]);
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