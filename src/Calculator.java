import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {
    private static final Map<String, Integer> romanNumerals = new HashMap<>();

    static {
        romanNumerals.put("I", 1);
        romanNumerals.put("II", 2);
        romanNumerals.put("III", 3);
        romanNumerals.put("IV", 4);
        romanNumerals.put("V", 5);
        romanNumerals.put("VI", 6);
        romanNumerals.put("VII", 7);
        romanNumerals.put("VIII", 8);
        romanNumerals.put("IX", 9);
        romanNumerals.put("X", 10);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите выражение в формате 'число знак число' (числа от 1 до 10): ");
        String input = scanner.nextLine();

        String[] tokens = input.split(" ");

        if(tokens.length != 3) {
            System.out.println("Ошибка: Некорректный формат ввода.");
            return;
        }

        int num1, num2, result;
        boolean isRoman = false;

        try {
            num1 = parseNumber(tokens[0]);
            num2 = parseNumber(tokens[2]);
            isRoman = !tokens[0].matches("[0-9]+");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: Калькулятор умеет работать только с арабскими или римскими цифрами одновременно.");
            return;
        }

        if (num1 == -1 || num2 == -1) {
            System.out.println("Ошибка: Некорректные числа.");
            return;
        }

        char operator = tokens[1].charAt(0);

        switch(operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if(num2 != 0) {
                    result = num1 / num2;
                } else {
                    System.out.println("Ошибка: деление на ноль.");
                    return;
                }
                break;
            default:
                System.out.println("Ошибка: некорректный оператор.");
                return;
        }

        if (isRoman) {
            if (result <= 0) {
                System.out.println("Ошибка: Результат работы с римскими числами должен быть положительным.");
                return;
            }
            System.out.println("Результат: " + convertToRoman(result));
        } else {
            System.out.println("Результат: " + result);
        }

        scanner.close();
    }

    private static int parseNumber(String str) {
        if (str.matches("[0-9]+")) {
            int num = Integer.parseInt(str);
            if (num < 1 || num > 10) {
                throw new IllegalArgumentException("Числа должны быть от 1 до 10.");
            }
            return num;
        } else {
            int result = 0;
            int prevValue = 0;

            for (int i = str.length() - 1; i >= 0; i--) {
                char currentChar = str.charAt(i);
                int numericValue = romanNumerals.get(String.valueOf(currentChar));

                if (numericValue < prevValue) {
                    result -= numericValue;
                } else {
                    result += numericValue;
                }

                prevValue = numericValue;
            }

            return result;
        }
    }

    private static String convertToRoman(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Результат работы с римскими числами должен быть положительным.");
        }

        String[] romanNumerals = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C"};
        int[] values = {1, 4, 5, 9, 10, 40, 50, 90, 100};

        StringBuilder sb = new StringBuilder();
        int i = values.length - 1;

        while (number > 0) {
            if (number - values[i] >= 0) {
                sb.append(romanNumerals[i]);
                number -= values[i];
            } else {
                i--;
            }
        }

        return sb.toString();
    }

}
