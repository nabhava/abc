/*DESCRIPTION
The best fit deals with allocating the smallest free partition which meets the requirement of the requesting process. This algorithm first searches the entire list of free partitions and considers the smallest hole that is adequate. It then tries to find a hole which is close to actual process size needed.
*/
import java.util.*;
public class BestFit {
    static void bestFit(int blockSize[], int m, int processSize[], int n) {
        int allocation[] = new int[n];
        for (int i = 0; i < allocation.length; i++)
            allocation[i] = -1;
        for (int i = 0; i < n; i++) {
            int bestIdx = -1;
            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    if (bestIdx == -1)
                        bestIdx = j;
                    else if (blockSize[bestIdx] > blockSize[j])
                        bestIdx = j;
                }
            }
            if (bestIdx != -1) {
                allocation[i] = bestIdx;
                blockSize[bestIdx] -= processSize[i];
            }
        }
        System.out.println("\nProcess No.\tProcess Size\tBlock no.");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (i + 1) + "\t\t" + processSize[i] + "\t\t");
            if (allocation[i] != -1)
                System.out.print(allocation[i] + 1);
            else
                System.out.print("Not Allocated");
            System.out.println();
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter block size: ");
        int n = sc.nextInt();
        System.out.println("Enter process size: ");
        int m = sc.nextInt();
        int blockSize[] = new int[n];
        int processSize[] = new int[m];
        System.out.println("Enter blocks: ");
        for (int i = 0; i < n; i++)
            blockSize[i] = sc.nextInt();
        System.out.println("Enter processes: ");
        for (int i = 0; i < m; i++)
            processSize[i] = sc.nextInt();
        bestFit(blockSize, n, processSize, m);
    }
}

/*OUTPUT
Enter block size: 
5
Enter process size: 
4
Enter blocks: 
100 500 200 300 600
Enter processes: 
212 417 112 426

Process No.	Process Size	Block no.
 	1			212				4
 	2			417				2
 	3			112				3
 	4			426				5

*/
