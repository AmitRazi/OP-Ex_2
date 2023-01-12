package Part_2;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);

    @Test
    public void partialTest() {
        CustomExecutor service = new CustomExecutor();
        var task = Task.createTask(() -> {
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        }, TaskType.COMPUTATIONAL);
        Future<Integer> sumTask = service.submit(task);
        final int sum;
        try {
            sum =  sumTask.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        logger.info(() -> "Sum of 1 through 10 = " + sum);
        Callable<Double> callable1 = () -> {
            return 1000 * Math.pow(1.02, 5);
        };
        Callable<String> callable2 = () -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };
        // var is used to infer the declared type automatically
        var priceTask = service.submit(() -> {
            return 1000 * Math.pow(1.02, 5);
        }, TaskType.COMPUTATIONAL);
        var reverseTask = service.submit(callable2, TaskType.IO);
        final Double totalPrice;
        final String reversed;
        try {
            totalPrice = priceTask.get();
            reversed = reverseTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        logger.info(() -> "Reversed String = " + reversed);
        logger.info(() -> String.valueOf("Total Price = " + totalPrice));
        logger.info(() -> "Current maximum priority = " +
                service.getCurrentMaxPriority());
        CustomFuture<Integer> tasks = service.submit(() -> {
            int sums = 0;
            for (int i = 0; i < 100000; i++) {
                sums+= i;
            }
            return sums;
        });
        try {
            int test = tasks.get(1, TimeUnit.MILLISECONDS);
            System.out.println(test + " hey");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        Future<String> task2 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task3 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task4 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });

        Future<String> task25 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task24 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task23 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        System.out.println(service.toString());
        Future<String> task22 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task21 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        System.out.println(service.toString());
        Future<String> task20 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        try {
            sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Future<String> task5 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        Future<String> task6 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        }, TaskType.IO);

        Future<String> task8 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        }, TaskType.IO);

        Future<String> task9 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        }, TaskType.IO);
        System.out.println(service.toString());
        Future<String> task10 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        }, TaskType.IO);

        Future<String> task11 = service.submit(() -> {
            sleep(200);
            System.out.println("sleep task");
            return "sleep task";
        });
        System.out.println(service.toString());

        Future<String> task12 = service.submit(() -> {

            System.out.println("sleep task this");
            return "sleep task";
        }, TaskType.IO);
        try {
            sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(service.toString());
        service.submit(() -> {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, TaskType.COMPUTATIONAL);
        service.submit(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, TaskType.COMPUTATIONAL);
        service.submit(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, TaskType.COMPUTATIONAL);
        service.submit(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, TaskType.COMPUTATIONAL);
        service.submit(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, TaskType.COMPUTATIONAL);
        service.submit(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, TaskType.COMPUTATIONAL);
        service.submit(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, TaskType.COMPUTATIONAL);
        service.submit(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, TaskType.COMPUTATIONAL);
        service.submit(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, TaskType.COMPUTATIONAL);
        service.submit(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, TaskType.COMPUTATIONAL);
        service.submit(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, TaskType.COMPUTATIONAL);
        service.submit(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, TaskType.COMPUTATIONAL);
        System.out.println(service.toString());
        service.submit(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println(service.toString());
        service.submit(() -> {
            try {
                sleep(1090);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(service.toString());
        System.out.println(service.toString());
        System.out.println(service.toString());
        System.out.println(service.toString());
        System.out.println(service.toString());
        service.submit(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        service.submit(() -> {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println(service.toString());
        System.out.println(service.toString());
        System.out.println(service.toString());
        System.out.println(service.toString());
        System.out.println(service.toString());
        System.out.println(service.toString());
        System.out.println(service.toString());
        System.out.println(service.toString());
        System.out.println(service.toString());
        System.out.println(service.toString());
        System.out.println(service.toString());
        System.out.println(service.toString());
        System.out.println(service.toString());
        service.submit(() -> System.out.println("hello from runnable"));
        Future<String> task7 = service.submit(() -> {
            sleep(200);
            System.out.println("special sleep task");
            return "special sleep task";
        }, TaskType.IO);
        System.out.println(service.toString());


        service.gracefullyTerminate();
        try {
            sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(service.toString());


        System.out.println(service.getCurrentMaxPriority());
        System.out.println(task.toString());
        service.gracefullyTerminate();
    }
}
