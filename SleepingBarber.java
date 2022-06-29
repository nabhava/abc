/*DESCRIPTION
This problem is based on a hypothetical scenario where there is a barbershop with one barber. The barbershop is divided into two rooms, the waiting room, and the workroom. The waiting room has n chairs for waiting customers, and the workroom only has a barber chair.

Now, if there is no customer, then the barber sleeps in his own chair(barber chair). Whenever a customer arrives, he has to wake up the barber to get his haircut. If there are multiple customers and the barber is cutting a customer's hair, then the remaining customers wait in the waiting room with "n" chairs(if there are empty chairs), or they leave if there are no empty chairs in the waiting room.

The sleeping barber problem may lead to a race condition. This problem has occurred because of the actions of both barber and customer.

Example to explain the problem:

Suppose a customer arrives and notices that the barber is busy cutting the hair of another customer, so he goes to the waiting room. While he is on his way to the waiting room, the barber finishes his job and sees the waiting room for other customers. But he(the barber) finds no one in the waiting room (as the customer has yet not arrived in the waiting room), so he sits down in his chair(barber chair) and sleeps. Now the barber is waiting for new customers to wake him up, and the customer is waiting as he thinks the barber is busy.

Here, both of them are waiting for each other, which leads to race conditions.
*/
import java.util.concurrent * ;
public class SleepingBarber extends Thread {
    public static Semaphore customers = new Semaphore(0);
    public static Semaphore barber = new Semaphore(0);
    public static Semaphore accessSeats = new Semaphore(1);
    public static final int CHAIRS = 5;
    public static int numberOfFreeSeats = CHAIRS;
    class Customer extends Thread {
        int iD;
        boolean notCut = true;
        public Customer(int i) {
            iD = i;
        }
        public void run() {
            while (notCut) {
                try {
                    accessSeats.acquire();
                    if (numberOfFreeSeats > 0) {
                        System.out.println("Customer " + this.iD + " just sat down.");
                        numberOfFreeSeats--;
                        customers.release();
                        accessSeats.release();
                        try {
                            barber.acquire();
                            notCut = false;
                            this.get_haircut();
                        } catch (InterruptedException ex) {}
                    } else {
                        System.out.println("There are no free seats. Customer " + this.iD + " has left the barbershop.");
                        accessSeats.release();
                        notCut = false;
                    }
                } catch (InterruptedException ex) {}
            }
        }
        public void get_haircut() {
            System.out.println("Customer " + this.iD + " is getting his hair cut");
            try {
                sleep(5050);
            } catch (InterruptedException ex) {}
        }
    }
    class Barber extends Thread {
        public Barber() {}
        public void run() {
            while (true) {
                try {
                    customers.acquire();
                    accessSeats.release();
                    numberOfFreeSeats++;
                    barber.release();
                    accessSeats.release();
                    this.cutHair();
                } catch (InterruptedException ex) {}
            }
        }
        public void cutHair() {
            System.out.println("The barber is cutting hair");
            try {
                sleep(5000);
            } catch (InterruptedException ex) {}
        }
    }
    public static void main(String args[]) {
        SleepingBarber barberShop = new SleepingBarber();
        barberShop.start();
    }
    public void run() {
        Barber b = new Barber();
        b.start();
        for (int i = 1; i < 6; i++) {
            Customer aCustomer = new Customer(i);
            aCustomer.start();
            try {
                sleep(2000);
            } catch (InterruptedException ex) {};
        }
    }
}

/*OUTPUT
Customer 1 just sat down.
The barber is cutting hair
Customer 1 is getting his hair cut
Customer 2 just sat down.
Customer 3 just sat down.
The barber is cutting hair
Customer 2 is getting his hair cut
Customer 4 just sat down.
Customer 5 just sat down.
The barber is cutting hair
Customer 3 is getting his hair cut
The barber is cutting hair
Customer 4 is getting his hair cut
The barber is cutting hair
Customer 5 is getting his hair cut
*/
