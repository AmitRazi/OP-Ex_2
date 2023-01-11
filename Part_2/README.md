# OOP Assignment 2 part 2
This is the second part of the second assignment.
## Overview 
 Our goal was to create a priority based concurrent execution system. We had to make a Task class that represent a generic task that may return a value of some type, and a CustomExecutor class - An executor that can run the tasks asynchronously, and do so in the order of their priority.

## The Classes
### Task
This class represents to the task performed. The class implements the Runnanle and comparable interfaces. The Runnable interface to allow the execution of the task to be performed by another thread, and the comparable interface to allow the CustomExecutor to compare tasks by their priority. 
The class variables are:
1. ```TaskType type``` - Enum, responsible for the tasks type and priority.
2. ```Callable<V> op``` - The class also wraps a callable instance which actually performs the actual task.
3. ```CountDownLatch latch``` - This latch is shared between a Task instance and it's associated CustomFuture instance. The latch facilitates the ability to time out when the timed ```get``` method is used.
4. ```boolean cancelled``` - Indicates whether the task has been cancelled. Initiated to false.
5. ```boolean finished``` - Indicates whether the task has been performed. Intiated to false.
6. ```T res``` - A generic type the hold the callable operation return value.

The class constructors are private. In order to create a Task instance, the class exposes four factory creation methods:
1. Receives a Callable operation and TaskType.
2. Receives only a Callable operation. The method adds a default TaskType of type ```OTHER``` and delegates the creation to the first method.
3. Receives a Runnable operation. The method wraps the Runnable using Executors static method ```callable()```, adds a default TaskType of type ```OTHER```, and delegates the creation to the first method.
4. Same as 3 only it recieves a TaskType as well.

### CustomFuture
This class responsibility is to return the value of the operation performed by Task. The class is a generic class and it implements the Future interfcae.
The class variables are:
1. ```Task task``` - The associated task. The instance is will retrieve the result of this task.
2. ```CountDownLatch latch``` - This latch is shared between a Task instance and it's associated CustomFuture instance. The latch facilitates the ability to time out when the timed ```get``` method is used.
3. ```boolean cancel``` - Indicates whether the user wants to cancel the task.

### CustomExecutor
A custom priority based ThreadPoolExecutor. 
The class variables are:
1. ```ThreadPoolExecutor executor``` - Most of the class's responsibility and logic is delegated to this ThreadPoolExecutor instance.
2. ```int currentMaxPriority``` - Holds the highest priority currently in the executors thread pool.
3. ```boolean stopped``` - Indicates whether the executor is shutting down or shut down.
### Class Diagram
![This is an image](package.png)

## Design and Difficulties
Most the functionalty required for such a priority based concurrent execution system, is already provided in other class of JAVA. Thus, the main design principle we tried to adhere to, was composition over inheritance. Our instinct was to extend known classes provided by JAVA, but that produced very messy code that was hard to follow, adjust, and each class was tightly coupled with numerous base classes. Again and again we encourated situtions where one extension in a class, limited our ability to implemenet desired functionality in a second class that has to communicate with the first. Then we decided to implement everything top-down by ourselves. The Thread Pool, the Executor, the Task, the Future. And even though the code did work, and we've learned a lot about these systems in the proccess - everything we can do, JAVA engineers can do better. So finally, we've decided to rely on the previously tried and tested impelmantions of JAVA, only this time, using composition and not inheritance. The customExecutor uses an instance of ThreadPoolExecutor to which it delegates most of the operations it needs to perform, and the logic behind them. Overall, our design doesn't use inheritance at all. This allows our classes to stay very small and very concise. Furthermore, each class has a very clear and single responsibility. 

Task - Responsible for task specifition.

CustomFuture - Responsible to retreive the result.

CustomExecuter - Responsible for priority based scheduling.

Because we don't use inheritance, our classes are lossly coupled. We aren't dependent on base classes that may change and force us to adjuct our code accordingly. Are code is also flexable. Because we use compsition and delegate the logic, we can easily change the executor we use in one line and the system will work the same. Also, our design means the number of lines in our code are very small. Excluding basic functions such as, ```HashCode()```,```equals()```. and ```toString()```, each class consists of about 50 lines. Small classes - higher maintainability.
