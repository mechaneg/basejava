@SuppressWarnings("ALL")
public class Deadlock {
    public static void main(String... args) {

        Object mutex1 = new Object();
        Object mutex2 = new Object();

        Thread thread1 = new Thread(() -> {
            synchronized (mutex2) {
                try {
                    mutex2.wait();
                } catch (InterruptedException ex) {
                }
            }

            synchronized (mutex1) {
                mutex1.notifyAll();
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (mutex1) {
                try {
                    mutex1.wait();
                } catch (InterruptedException ex) {
                }
            }
            synchronized (mutex2) {
                mutex2.notifyAll();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }


        System.out.println("Unreachable");
    }
}
