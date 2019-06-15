
public class Deadlock {
    public static void main(String... args) {

        Object mutex1 = new Object();
        Object mutex2 = new Object();

        Thread thread1 = new Thread(() -> {
            lockMutexesSequentially(mutex1, mutex2);
        });

        Thread thread2 = new Thread(() -> {
            lockMutexesSequentially(mutex2, mutex1);
        });

        thread1.start();
        thread2.start();

        System.out.println("Unreachable");
    }

    private static void lockMutexesSequentially(Object firstMutex, Object secondMutex) {
        String threadName = Thread.currentThread().getName();
        synchronized (firstMutex) {
            System.out.println(threadName + " holding " + firstMutex.toString() + " mutex");

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }

            System.out.println(threadName + " waiting for the " + secondMutex.toString() + " mutex");

            synchronized (secondMutex) {
                System.out.println(threadName + " holding " + secondMutex.toString() + " mutex");
            }
        }
    }
}
