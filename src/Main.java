import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Main {
    static int num1;
    static int num2;
    static boolean isRomeNumbers = false;
    static int result;
    static int operation_index = -1;
    static char operation;
    static char[] operations = {'+', '-', '*', '/'};
    static String INPUT_ERROR = "\nОшибка ввода. Убедись, что формат введенной строки соответствует a <oper> b,\n" +
            "где a и b - римские или целые арабские числа от 1 до 10, а \"<oper>\" одна из четырех\n" +
            "арифмитических операций: + - * /";

    public static void main(String[] args) {
        var console = new Scanner(System.in);
        System.out.println("Введи строку формата a <oper> b, где a и b - римские или арабские числа от 1 до 10, а <oper> одна из арифмтических операций: +,-,*,/");
        System.out.println("Для остановки программы введи слово \"stop\"");
        while (console.hasNext()) {
                var input = console.nextLine();
                if (input.equals("stop")) break;
                System.out.println(calc(input));
        }

    }

    public static String calc(String input) {
        parseInput(input);
        if (num2 == 0) throw new ArithmeticException("Делить на 0 нельзя");
        if (num1 > 10 || num1 < 1 && num2 > 10 || num2 < 1) throw new IllegalArgumentException("Числа должны быть от 1 до 10 включительно");


        switch (operation) {
            case '+' -> result = num1 + num2;
            case '-' -> result = num1 - num2;
            case '/' -> result = num1 / num2;
            case '*' -> result = num1 * num2;
        }



        if (!isRomeNumbers) return Integer.toString(result);

        return convertArabicToRoman(result);

    }

    public static void parseInput(String input) {
        for (char c : operations) {
            operation_index = input.indexOf(c);
            if (operation_index > 0) {
                operation = input.charAt(operation_index);
                break;
            }
        }

        if (operation_index == -1) throw new IllegalArgumentException(INPUT_ERROR);
        String operand1 = input.substring(0, operation_index).trim();
        String operand2 = input.substring(operation_index + 1).trim();

        if (isNumber(operand1) && isNumber(operand2)) {
            num1 = Integer.parseInt(operand1);
            num2 = Integer.parseInt(operand2);
        } else {
                try {
                    num1 = convertRomanToArabic(operand1);
                    num2 = convertRomanToArabic(operand2);
                } catch (Exception e) {
                    throw new IllegalArgumentException(INPUT_ERROR);
                }
        }

    }

    public static String convertArabicToRoman(int arabic_value) {

        if (arabic_value <= 0) throw new ArithmeticException("Римское число не может быть меньше 1");

        StringBuilder buffer = new StringBuilder();

        final RomeNum[] values = RomeNum.values();
        for (int i = values.length - 1; i >= 0; i--) {
            while (arabic_value >= values[i].weight) {
                buffer.append(values[i]);
                arabic_value -= values[i].weight;
            }
        }
        return buffer.toString();
    }

    public static int convertRomanToArabic(String romanNumber) {
        Map<Character, Integer> romanToArabicMap = new HashMap<>();
        romanToArabicMap.put('I', 1);
        romanToArabicMap.put('V', 5);
        romanToArabicMap.put('X', 10);
        romanToArabicMap.put('L', 50);
        romanToArabicMap.put('C', 100);
        romanToArabicMap.put('D', 500);
        romanToArabicMap.put('M', 1000);

        int arabicNumber = 0;
        int previousValue = 0;

        for (int i = romanNumber.length() - 1; i >= 0; i--) {
            char currentChar = romanNumber.charAt(i);
            int currentValue = romanToArabicMap.get(currentChar);

            if (currentValue < previousValue) {
                arabicNumber -= currentValue;
            } else {
                arabicNumber += currentValue;
                previousValue = currentValue;
            }
        }

        isRomeNumbers = true;
        return arabicNumber;
    }
    public static boolean isNumber(String number) {
        return number.matches("[0-9]+");
    }

    public enum RomeNum {
        I(1), II(2), III(3), IV(4), V(5), VI(6),
        VII(7), VIII(8), IX(9), X(10), XL(40),
        L(50), XC(90), C(100);

        final int weight;
        RomeNum(int weight) {
            this.weight = weight;
        }
    }
}