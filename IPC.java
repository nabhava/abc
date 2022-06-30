class Bucket {
    int x;
    boolean full = false;
    synchronized void get() {
        if (full == false) {
            try {
                Thread.sleep(500);
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        System.out.println("Consumed: " + x);
        full = false;
        notify();
    }
    synchronized void put(int n) {
        if (full == true) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        x = n;
        System.out.println("Produced: " + x);
        full = true;
        notify();
    }
}
class Producer extends Thread {
    Bucket b;
    Producer(Bucket b) {
        this.b = b;
    }
    public void run() {
        int i;
        for (i = 1; i <= 10; i++) {
            b.put(i);
        }
    }
}
class Consumer extends Thread {
    Bucket b;
    Consumer(Bucket b) {
        this.b = b;
    }
    public void run() {
        int i;
        for (i = 1; i <= 10; i++) {
            b.get();
        }
    }
}
class IPC {
    public static void main(String args[]) {
        Bucket b = new Bucket();
        Producer p = new Producer(b);
        Consumer c = new Consumer(b);
        c.start();
        p.start();
    }
}

/*OUTPUT
Produced: 1
Consumed: 1
Produced: 2
Consumed: 2
Produced: 3
Consumed: 3
Produced: 4
Consumed: 4
Produced: 5
Consumed: 5
Produced: 6
Consumed: 6
Produced: 7
Consumed: 7
Produced: 8
Consumed: 8
Produced: 9
Consumed: 9
Produced: 10
Consumed: 10
*/
