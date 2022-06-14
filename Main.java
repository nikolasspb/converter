package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        String answer = "";
        String answerBack = "/back";
        int sourceBase = 0;
        int targetBase = 0;
        String rez = "";
        BigDecimal fractionalPart;
        Scanner sc = new Scanner(System.in);
        while (!"/exit".equals(answer)) {
            if ("/back".equals(answerBack)) {
                System.out.print("\nEnter two numbers in format: {source base} {target base} (To quit type /exit) ");
                answer = sc.next();
                if ("/exit".equals(answer)) {
                    continue;
                }
                sourceBase = Integer.parseInt(answer);
                targetBase = Integer.parseInt(sc.next());
            }
            System.out.print("Enter number in base " + sourceBase + " to convert to base " + targetBase + " (To go back type /back) ");
            answerBack = sc.next();
            if (!"/back".equals(answerBack)) {
                var sourceArray = answerBack.split("\\.");
                rez = new BigInteger(String.valueOf(new BigInteger(sourceArray[0], sourceBase))).toString(targetBase);
                if (sourceArray.length > 1) {
                    var s = new StringBuilder();
                    if (sourceBase != 10) {
                        BigDecimal twos = new BigDecimal(sourceBase);
                        BigDecimal as = BigDecimal.ZERO;
                        for (int i = 0; i < sourceArray[1].length(); i++) {
                            BigDecimal tempB = new BigDecimal((sourceArray[1].charAt(i) < 97 && sourceArray[1].charAt(i) > 9 ? sourceArray[1].charAt(i) - '0' : sourceArray[1].charAt(i) - 87));
                            as = as.add(tempB.divide(twos, MathContext.DECIMAL128));
                            twos = twos.multiply(new BigDecimal(sourceBase));
                        }
                        fractionalPart = as;
                    } else {
                        fractionalPart = new BigDecimal("0." + sourceArray[1]);
                    }
                    if (fractionalPart.compareTo(new BigDecimal(1)) == 1) {
                        BigInteger plus = new BigInteger(fractionalPart.toBigInteger().toString(10));
                        fractionalPart = fractionalPart.subtract(new BigDecimal(plus));
                        var trez = new BigInteger(sourceArray[0], sourceBase);
                        rez = (trez.add(plus)).toString(targetBase);
                    }
                    s.append(rez);
                    s.append(".");
                    int k = fractionalPart.toString().length();
                    var news = new StringBuilder();
                    while (k != 0) {
                        var integralPart = (fractionalPart.multiply(new BigDecimal(targetBase))).toBigInteger();
                        if (integralPart.compareTo(BigInteger.valueOf(9)) == 1) {
                            var ot = (integralPart.intValue());
                            news.append((char) (ot + 87));
                        } else {
                            news.append(integralPart);
                        }
                        var number = fractionalPart.multiply(new BigDecimal(targetBase));
                        fractionalPart = number.subtract(new BigDecimal(integralPart));
                        k--;
                    }
                    if (news.length() < 5) {
                        for (int i = news.length(); i < 5; i++) {
                            news.append("0");
                        }
                        s.append(news);
                    } else if (news.length() > 5) {
                        s.append(news.subSequence(0, 5));
                    } else {
                        s.append(news);
                    }
                    rez = s.toString();
                }
            }
            System.out.println("Conversion result: " + rez + "\n");
        }
    }
}
