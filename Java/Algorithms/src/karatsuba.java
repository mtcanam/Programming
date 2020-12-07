import java.math.BigInteger;

public class karatsuba {

    public karatsuba(){

    }

    public static void main(String[] args) {
        String n1 = "3141592653589793238462643383279502884197169399375105820974944592";
        String n2 = "2718281828459045235360287471352662497757247093699959574966967627";
        //String n1 = "1234";
        //String n2 = "5678";
        BigInteger ans = karatsubaMult(n1, n2);
        System.out.println(ans);
        System.out.println(new BigInteger(n1).multiply(new BigInteger(n2)));
    }
    public static BigInteger karatsubaMult(String n1, String n2){

        //a: first half of n1
        //b: second half of n1
        //c: first half of n2
        //d: second half of n2
        //need to recursively compute ac, bd, (a+b)(c+d)

        int n1Len = n1.length();
        int n2Len = n2.length();

        //Need to pad with zeros to match the length of the inputs and ensure length of input is even
        while (n1Len < n2Len || n1Len % 2 != 0){
            n1 = "0" + n1;
            n1Len++;
        }

        while (n2Len < n1Len || n2Len % 2 != 0){
            n2 = "0" + n2;
            n2Len++;
        }

        int n = Math.max(n1Len, n2Len);

        if(n1Len != n2Len){
            System.out.println("Whoops");
            System.out.println(n1Len +""+ n2Len);
        }

        BigInteger bn1 = new BigInteger(n1);
        BigInteger bn2 = new BigInteger(n2);

        //If either of the two numbers are already only one digit, just multiply and return
        if(n1Len < 3 || n2Len < 3){
            return bn1.multiply(bn2);
        }

        //Otherwise, we have at least two digits in each number, and can split them to multiply
        String a = n1.substring(0, n1Len/2);
        String b = n1.substring(n1Len/2, n1Len);
        String c = n2.substring(0, n2Len/2);
        String d = n2.substring(n2Len/2, n2Len);

        String apb = (new BigInteger(a).add(new BigInteger(b))).toString();
        String cpd = (new BigInteger(c).add(new BigInteger(d))).toString();

        BigInteger ac = karatsubaMult(a, c);
        BigInteger bd = karatsubaMult(b, d);
        BigInteger abcd = karatsubaMult(apb, cpd);
        BigInteger gauss = abcd.subtract(ac).subtract(bd);

        return (BigInteger.TEN.pow(n).multiply(ac)).add((BigInteger.TEN.pow(n / 2)).multiply(gauss)).add(bd);

    }

}
