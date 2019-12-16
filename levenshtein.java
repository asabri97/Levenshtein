import static java.lang.Math.*;

public class levenshtein{

    public static int r =2,c=4;
    public static long[][] arr = new long[r][c];
    public static int z = 0; //index 
    
    public static int dist(String token1, String token2){
        byte t1[], t2[];
        try{
            t1 = token1.getBytes("UTF-8");
            t2 = token2.getBytes("UTF-8");
            long startTime = System.nanoTime();
            int dist = Levenshtein(t1, t2, -1);
            long endTime = System.nanoTime();
            long finaltime = endTime-startTime;
            arr[0][z] = finaltime;
            z++;
            System.out.printf("Time Taken: %d\n", finaltime);
            return dist;
        }
        catch (Exception e){
            System.out.println(e);
        }
        
        return 0;
    }

    public static int distmax(String token1, String token2, int max){
        byte t1[], t2[];
        try{
            t1 = token1.getBytes("UTF-8");
            t2 = token2.getBytes("UTF-8");
            long startTime = System.nanoTime();
            int maxDist = Levenshtein(t1, t2, max);
            long endTime = System.nanoTime();
            long finaltime = endTime-startTime;
            arr[1][z] = finaltime;
            z++;
            System.out.printf("Time Taken: %d\n", finaltime);
            return maxDist;
        }
        catch (Exception e){
            System.out.println(e);
        }
        return 0;
        //distance = distance <=max;
        //return distance;
    }

    
    public static int Levenshtein(byte[] t1, byte[] t2, int max) {
        if(t1 == null || t2 == null){
            throw new NullPointerException("Tokens must not be empty!");
        }

        // token1 = token1.toLowerCase();
        // token2 = token2.toLowerCase();

        if(t1.equals(t2)){
            return 0;   //Same tokens no differences
        }
       
        if (max >= 0 && abs(t1.length-t2.length) > max) {
            return max+1;
        }

        if (t1.length == 0){  //Replace token1 with token2 if token1 is empty or vice versa
            return t2.length;
        }
        if(t2.length == 0){
            return t1.length;
        }

        int len1 = t1.length;
        int len2 = t2.length;

        //Swap strings to consume less memory
        if (len1 < len2) {
            int templen = len1;
            byte[] temptok = t1;
            len1 = len2;
            len2 = templen;
            t1 = t2;
            t2 = temptok;
        }

        
 

        /*
        cost vectors to keep track of distances
        cost0 keeps track of characters that will be removed from token2 ; A[0][i] - row
        cost1 keeps track of current characters in the row; A[i+1][0]
        (i+1) - characters deleted from token1 to match empty token2 
        */

        int[] cost0 = new int[len2+1]; 
        //int[] cost1 = new int[token2.length()+1];
        //int[] costemp;
        
        for(int i=1; i<=len1;i++){
            cost0[0] = i;
            int last = i-1;
            int min = last;
            for(int j =1; j<=len2;j++){
                //int sub = last + (token1.charAt(i-1) == token2.charAt(j-1) ? 0:1);
                int est = 1; //estimate cost
                if(t1[i-1] == t2[j-1]){
                    est = 0;
                }
                int sub = last + est; //subsitution cost
                last = cost0[j];
                cost0[j] = Math.min(1+last,Math.min(1+cost0[j-1],sub)); 
                //System.out.println(sub);
                /*find the minimum between insertion, deletion and substitution method
                add 1 to each method because we are performing a task
                */

                if (min > last){
                    min = last;
                } 
            }  
            if (max >= 0 && min > max){
                return max+1;
            }
        } 


        if(max >= 0 && cost0[len2]>max){
            return max+1;
        }            
        return cost0[len2];        
    }

    public static String replaceUmlaut(String input) {

        //replace all lower Umlauts
        String output = input.replace("ü", "ue")
                             .replace("ö", "oe")
                             .replace("ä", "ae")
                             .replace("ß", "ss");
   
   
        //now replace all the other capital umlaute
        output = output.replace("Ü", "UE")
                       .replace("Ö", "OE")
                       .replace("Ä", "AE");
   
        return output;
    }
    
    
    public static void main(String[] args) {
		System.out.println(
			dist(replaceUmlaut("Haus"),replaceUmlaut("Maus")) + " " + // 1
			dist(replaceUmlaut("Haus"),replaceUmlaut("Mausi")) + " " + // 2
			dist(replaceUmlaut("Haus"),replaceUmlaut("Häuser")) + " " + // 3
			dist(replaceUmlaut("Kartoffelsalat"),replaceUmlaut("Runkelrüben")) // 12
        );
        z = 0;
		System.out.println(
			distmax(replaceUmlaut("Haus"),replaceUmlaut("Maus"),2) + " " + // 1
			distmax(replaceUmlaut("Haus"),replaceUmlaut("Mausi"),2) + " " + // 2
			distmax(replaceUmlaut("Haus"),replaceUmlaut("Häuser"),2) + " " + // 3
			distmax(replaceUmlaut("Kartoffelsalat"),replaceUmlaut("Runkelrüben"),2) // 3
        );
        for(int i=0; i<arr[0].length;i++){
            long check;
            check = arr[1][i]-arr[0][i];
            if (check < 0) {
                System.out.println("Implementation 2 is faster for testcase: "+(i+1));
            }
            else {
                System.out.println("Implementation 1 is faster for testcase: "+(i+1));
            }

        }
	}    

}