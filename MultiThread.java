/*DESCRIPTION
Multithreading is the ability of a program or an operating system to enable more than one user at a time without requiring multiple copies of the program running on the computer. Multithreading can also handle multiple requests from the same user.

Each user request for a program or system service is tracked as a thread with a separate identity. As programs work on behalf of the initial thread request and are interrupted by other requests, the work status of the initial request is tracked until the work is completed. In this context, a user can also be another program.

Fast central processing unit (CPU) speed and large memory capacities are needed for multithreading. The single processor executes pieces, or threads, of various programs so fast, it appears the computer is handling multiple requests simultaneously.
*/

public class MultiThread extends Thread {
    String task;
    MultiThread(String task) {
        this.task = task;
    }
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(task + " : " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                System.out.println(ie.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        MultiThread th1 = new MultiThread("\nCut the ticket");
        MultiThread th2 = new MultiThread("\nShow your seat number");
        Thread t1 = new Thread(th1);
        Thread t2 = new Thread(th2);
        t1.start();
        t2.start();
    }
}

/*OUTPUT

Cut the ticket : 1

Show your seat number : 1

Cut the ticket : 2

Show your seat number : 2

Cut the ticket : 3

Show your seat number : 3

Cut the ticket : 4

Show your seat number : 4

Cut the ticket : 5

Show your seat number : 5

*/
