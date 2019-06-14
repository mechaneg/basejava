@SuppressWarnings("ALL")
public class Deadlock {
    public static void main(String... args) {

        Object mutex1 = new Object();
        Object mutex2 = new Object();

        Thread thread1 = new Thread(() -> {
            synchronized (mutex1) {
                System.out.println("1st thread holding 1st mutex");

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                }

                System.out.println("1st thread waiting for the 2nd mutex");

                synchronized (mutex2) {
                    System.out.println("1st thread holding 2nd mutex");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (mutex2) {
                System.out.println("2nd thread holding 2nd mutex");

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                }

                System.out.println("2nd thread waiting for the 1st mutex");

                synchronized (mutex1) {
                    System.out.println("2nd thread holding 1st mutex");
                }
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
