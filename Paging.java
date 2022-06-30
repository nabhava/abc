import java.util.*;
class Paging {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter no of bits in logical address : ");
        int m = sc.nextInt();
        System.out.print("Enter the page size in bytes : ");
        int p = sc.nextInt();
        System.out.print("Enter no of bits in physical address : ");
        int n = sc.nextInt();
        int laddrspace = (int) Math.pow(2, m);
        int paddrspace = (int) Math.pow(2, n);
        int pages = laddrspace / p;
        int frames = paddrspace / p;
        int page[] = new int[pages];
        int frame[] = new int[frames];
        table(page, pages, frame, frames);
        System.out.print("Enter the logical address : ");
        int laddr = sc.nextInt();
        int pageno = laddr / p;
        int d = laddr % p;
        int paddrr = page[pageno] * p + d;
        System.out.println("Physical Address : "+paddrr);
    }
    static void table(int page[], int pages, int frame[], int frames) {
        int f, i;
        Scanner sc = new Scanner(System.in);
        Arrays.fill(frame,-1);
        for (i = 0; i < pages; i++) {
            System.out.print("Enter frame number for page - " + i + ":");
            f = sc.nextInt();
            page[i] = f;
            frame[f] = i;
        }
        System.out.println("Pageno\tFrameno");
        for (i = 0; i < pages; i++) {
            System.out.println(i + "\t" + page[i]);
        }
        System.out.println("Frameno\tpageno");
        for (i = 0; i < frames; i++) {
            System.out.println(i + "\t" + frame[i]);
        }
    }
}

/*OUTPUT
Enter no of bits in logical address : 4
Enter the page size in bytes : 4
Enter no of bits in physical address : 4
Enter frame number for page - 0:0
Enter frame number for page - 1:2
Enter frame number for page - 2:1
Enter frame number for page - 3:2
Pageno	Frameno
0	0
1	2
2	1
3	2
Frameno	pageno
0	0
1	2
2	3
3	-1
Enter the logical address : 5
Physical Address : 9
*/
