package me.tewpingz.lmp.workload;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author https://www.spigotmc.org/threads/guide-on-workload-distribution-or-how-to-handle-heavy-splittable-tasks.409003/
 */
public class WorkloadRunnable implements Runnable {

    private static final double MAX_MILLIES_PER_TICK = 2.5;
    private static final int MAX_NANOS_PER_TICK = (int) (MAX_MILLIES_PER_TICK * 1E6);

    private final Deque<Workload> workloadDeque = new ArrayDeque<>();

    public void addWorkload(Workload workload) {
        this.workloadDeque.add(workload);
    }

    @Override
    public void run() {
        long stopTime = System.nanoTime() + MAX_NANOS_PER_TICK;
        Workload nextLoad;
        while (System.nanoTime() <= stopTime && (nextLoad = this.workloadDeque.poll()) != null) {
            nextLoad.compute();;
        }
    }
}
