/*DESCRIPTION
The readers-writers problem relates to an object such as a file that is shared between multiple processes. Some of these processes are readers i.e. they only want to read the data from the object and some of the processes are writers i.e. they want to write into the object.

The readers-writers problem is used to manage synchronization so that there are no problems with the object data. For example - If two readers access the object at the same time there is no problem. However if two writers or a reader and writer access the object at the same time, there may be problems.

To solve this situation, a writer should get exclusive access to an object i.e. when a writer is accessing the object, no reader or writer may access it. However, multiple readers can access the object at the same time.
*/

import java.util.concurrent.Semaphore;
public class ReaderWriter {
    public static final int NUM_OF_READERS = 3;
    public static final int NUM_OF_WRITERS = 2;
    public static void main(String args[]) {
        RWLock database = new Database();
        Thread[] readerArray = new Thread[NUM_OF_READERS];
        Thread[] writerArray = new Thread[NUM_OF_WRITERS];
        for (int i = 0; i < NUM_OF_READERS; i++) {
            readerArray[i] = new Thread(new Reader(i, database));
            readerArray[i].start();
        }
        for (int i = 0; i < NUM_OF_WRITERS; i++) {
            writerArray[i] = new Thread(new Writer(i, database));
            writerArray[i].start();
        }
    }
}
interface RWLock {
    public abstract void acquireReadLock(int readerNum);
    public abstract void acquireWriteLock(int writerNum);
    public abstract void releaseReadLock(int readerNum);
    public abstract void releaseWriteLock(int writerNum);
}
class Database implements RWLock {
    private int readerCount;
    private Semaphore mutex;
    private Semaphore db;
    public Database() {
        readerCount = 0;
        mutex = new Semaphore(1);
        db = new Semaphore(1);
    }
    public void acquireReadLock(int readerNum) {
        try {

            mutex.acquire();
        } catch (InterruptedException e) {}
        ++readerCount;
        if (readerCount == 1) {
            try {
                db.acquire();
            } catch (InterruptedException e) {}
        }
        System.out.println("Reader " + readerNum + " is reading. Reader count = " + readerCount);
        mutex.release();
    }
    public void releaseReadLock(int readerNum) {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {}
        --readerCount;
        if (readerCount == 0) {
            db.release();
        }
        System.out.println("Reader " + readerNum + " is done reading. Reader count = " + readerCount);
        mutex.release();
    }
    public void acquireWriteLock(int writerNum) {
        try {
            db.acquire();
        } catch (InterruptedException e) {}
        System.out.println("Writer " + writerNum + " is writing.");
    }
    public void releaseWriteLock(int writerNum) {
        System.out.println("Writer " + writerNum + " is done writing.");
        db.release();
    }
}
class Reader implements Runnable {
    private RWLock database;
    private int readerNum;
    public Reader(int readerNum, RWLock database) {
        this.readerNum = readerNum;
        this.database = database;
    }
    public void run() {
        while (true) {
            SleepUtilities.nap();

            System.out.println("reader " + readerNum + " wants to read.");
            database.acquireReadLock(readerNum);
            SleepUtilities.nap();
            database.releaseReadLock(readerNum);
        }
    }
}
class Writer implements Runnable {
    private RWLock database;
    private int writerNum;
    public Writer(int w, RWLock d) {
        writerNum = w;
        database = d;
    }
    public void run() {
        while (true) {
            SleepUtilities.nap();
            System.out.println("writer " + writerNum + " wants to write.");
            database.acquireWriteLock(writerNum);
            SleepUtilities.nap();
            database.releaseWriteLock(writerNum);
        }
    }
}
class SleepUtilities {
    public static void nap() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
    }
}

/*OUTPUT
reader 1 wants to read.Reader 1 is reading. Reader count = 1reader 0 wants to read.
Reader 0 is reading. Reader count = 2
writer 1 wants to write.
Reader 0 is done reading. Reader count = 1
reader 0 wants to read.
Reader 0 is reading. Reader count = 2
Reader 0 is done reading. Reader count = 1
writer 0 wants to write.
reader 2 wants to read.
Reader 2 is reading. Reader count = 2
Reader 2 is done reading. Reader count = 1
*/
