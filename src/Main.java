import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
       CustomExecutor service = new CustomExecutor();
       Future<String> task = service.submit(()->{
           sleep(200);
           System.out.println("sleep task");
           return "sleep task";
       });
        Future<String> task2 = service.submit(()->{
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task3 = service.submit(()->{
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task4= service.submit(()->{
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task25= service.submit(()->{
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task24= service.submit(()->{
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task23= service.submit(()->{
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task22= service.submit(()->{
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task21= service.submit(()->{
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task20= service.submit(()->{
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task5= service.submit(()->{
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task6 = service.submit(()->{
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });

        System.out.println(service.getCurrentMaxPriority());
        Future<String> task8 = service.submit(()->{
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task9 = service.submit(()->{
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task10 = service.submit(()->{
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task11 = service.submit(()->{
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task12 = service.submit(()->{
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        System.out.println(service.getCurrentMaxPriority());
        try {
            sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        TaskType.IO.setTypePriority(1);
        Future<String> task7 = service.submit(()->{
            sleep(200);
            System.out.println("special sleep task");
            return "special sleep task";
        },TaskType.IO);
        System.out.println(service.getCurrentMaxPriority());

        service.shutdown();
        try {
            task.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println(task.toString());
    }
}