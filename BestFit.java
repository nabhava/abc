import java.util.*;
public class BestFit {
    static void bestFit(int blockSize[], int m, int processSize[], int n) {
        int allocation[] = new int[n];
        Arrays.fill(allocation,-1);
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
        System.out.print("Enter block size: ");
        int m = sc.nextInt();
        System.out.print("Enter process size: ");
        int n = sc.nextInt();
        int blockSize[] = new int[m];
        int processSize[] = new int[n];
        System.out.print("Enter blocks: ");
        for (int i = 0; i < m; i++)
            blockSize[i] = sc.nextInt();
        System.out.print("Enter processes: ");
        for (int i = 0; i < n; i++)
            processSize[i] = sc.nextInt();
        bestFit(blockSize, m, processSize, n);
    }
}

/*OUTPUT
Enter block size: 5
Enter process size: 4
Enter blocks: 100 500 200 300 600
Enter processes: 212 417 112 426

Process No.	Process Size	Block no.
 	1			212				4
 	2			417				2
 	3			112				3
 	4			426				5

*/
