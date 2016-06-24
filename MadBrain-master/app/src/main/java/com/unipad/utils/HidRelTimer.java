package com.unipad.utils;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 这个类是对ScheduledExecutorService的一个封装，用来替换原有的Timer类
 * Timer计时器有管理任务延迟执行("如1000ms后执行任务")以及周期性执行("如每500ms执行一次该任务")。 但是，Timer存在一些缺陷： <em>
 * <li>1.Timer对调度的支持是基于绝对时间,而不是相对时间的，由此任务对系统时钟的改变是敏感的;ScheduledThreadExecutor只支持相对时间。
 * <li>2.如果TimerTask抛出未检查的异常，Timer将会产生无法预料的行为。Timer线程并不捕获异常，所以
 * TimerTask抛出的未检查的异常会终止timer线程。此时，已经被安排但尚未执行的TimerTask永远不会再执行了，新的任务也不能被调度了。
 * <p>
 *  所有使用改Timer的定时器，必须调用cancel来取消此线程池，否则此定时器不会因为run方法运行完而自动释放。
 * @author y42579
 * @2013-3-4
 */
public class HidRelTimer
{
    public ScheduledExecutorService scheduExec = null;

    public HidRelTimer(final String name)
    {
        // 线程池能按时间计划来执行任务，允许用户设定计划执行任务的时间，int类型的参数是设定
        // 线程池中线程的最小数目。当任务较多时，线程池可能会自动创建更多的工作线程来执行任务
        scheduExec = Executors.newScheduledThreadPool(1, new DefaultThreadFactory(name));
    }

    /**
     * @param task 要执行的任务
     * 
     * @param delay 第一次执行前要延时的时间
     * @param period 每次周期执行延时的时间
     */
    public void schedule(TimerTask task, long delay, long period)
    {
        if (delay < 0)
            throw new IllegalArgumentException("Negative delay.");
        if (period <= 0)
            throw new IllegalArgumentException("Non-positive period.");
        scheduExec.scheduleWithFixedDelay(task, delay, period, TimeUnit.MILLISECONDS);
    }

    /**
     * @param task 要执行的任务
     * 
     * @param period 每次周期执行延时的时间
     */
    public void schedule(TimerTask task, long delay)
    {
        if (delay < 0)
            throw new IllegalArgumentException("Non-positive delay.");

        scheduExec.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * 要终止此定时器，必现调用此方法
     * 
     * <p>
     * Attempts to stop all actively executing tasks, halts the processing of waiting tasks, and returns a list of the
     * tasks that were awaiting execution.
     * 
     * <p>
     * There are no guarantees beyond best-effort attempts to stop processing actively executing tasks. For example,
     * typical implementations will cancel via {@link Thread#interrupt}, so any task that fails to respond to interrupts
     * may never terminate.
     * 
     * @return list of tasks that never commenced execution
     * @throws SecurityException if a security manager exists and shutting down this ExecutorService may manipulate
     *         threads that the caller is not permitted to modify because it does not hold
     *         {@link RuntimePermission}<tt>("modifyThread")</tt>, or the security manager's
     *         <tt>checkAccess</tt> method denies access.
     */
    public void cancel()
    {
        if (scheduExec != null)
        {
            scheduExec.shutdownNow();
        }
        scheduExec = null;
    }

    public int purge()
    {
        // 没作用,为了兼容以前的timer的接口
        return 1;
    }

    /**
     * The default thread factory 采用系统提供的默认实现，只是增加了线程名中 name的打印
     */
    static class DefaultThreadFactory implements ThreadFactory
    {
        static final AtomicInteger poolNumber = new AtomicInteger(1);

        final ThreadGroup group;

        final AtomicInteger threadNumber = new AtomicInteger(1);

        final String namePrefix;

        DefaultThreadFactory(String name)
        {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = name + "_pool-" + poolNumber.getAndIncrement() + "-thread-";
        }

        public Thread newThread(Runnable r)
        {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

}
