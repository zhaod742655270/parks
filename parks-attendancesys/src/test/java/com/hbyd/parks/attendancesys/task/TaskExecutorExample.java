package com.hbyd.parks.attendancesys.task;

import org.springframework.core.task.TaskExecutor;

/**
 * 测试 TaskExecutor
 */
public class TaskExecutorExample {
    /**
     * 自定义的任务
     */
    private class MessagePrinterTask implements Runnable{

        private String message;

        private MessagePrinterTask(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            System.out.println("Thread: "+ Thread.currentThread().getName() + " -> " + message);
        }
    }

    private TaskExecutor taskExecutor;

    public TaskExecutorExample(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void printMessage(){
        for (int i = 0; i < 25; i++) {
            taskExecutor.execute(new MessagePrinterTask("Message: " + i));
        }
    }
}
