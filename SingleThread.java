public class SingleThread implements Runnable {
    int a = 20, b = 10;
    public void run() {
        addition();
        subtraction();
        multiplication();
    }
    void addition() {
        int sum = a + b;
        System.out.println("Addition of two numbers: " + sum);
    }
    void subtraction() {
        int sub = a - b;
        System.out.println("Subtraction of two numbers: " + sub);
    }
    void multiplication() {
        int multiply = a * b;
        System.out.println("Multiplication of two numbers: " + multiply);
    }
    public static void main(String[] args) {
        System.out.println("Main thread running");
        SingleThread th = new SingleThread();
        Thread t = new Thread(th);
        t.start();
    }
}

/*OUTPUT
Main thread running
Addition of two numbers: 30
Subtraction of two numbers: 10
Multiplication of two numbers: 200
*/
