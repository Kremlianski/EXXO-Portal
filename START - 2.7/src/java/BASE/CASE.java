package BASE;

//import java.sql.*;
public class CASE {

    public static boolean isKey(String S) {
        Boolean res = false;
        String[] KEYS;
        String A[] = more.getA();
        String B[] = more.getB();
        String C[] = more.getC();
        String D[] = more.getD();
        String E[] = more.getE();
        String F = S.substring(2);

        if (S.startsWith("a")) {
            KEYS = A;
        } else if (S.startsWith("b")) {
            KEYS = B;
        } else if (S.startsWith("c")) {
            KEYS = C;
        } else if (S.startsWith("d")) {
            KEYS = D;
        } else {
            KEYS = E;
        }

        for (int i = 0; i < KEYS.length; i++) {
            if (KEYS[i].equals(F)) {
                res = true;
            }
        }
        return res;
    }

}
