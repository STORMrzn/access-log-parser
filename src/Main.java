import java.util.Scanner;

public class Main {
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String RESET = "\u001B[0m";

    public static void main(String[] args) {
        //вводим числа и отображаем их пользователю
        System.out.println(BLUE + "-+*/Простой калькулятор целых чисел/*+-" +
                RED
                + "\nЧисла и произведения должны лежать в границах от -2147483648 до 2147483647"
                + RESET);
        System.out.println("Введите первое число = ");
            int number1 = new Scanner(System.in).nextInt();
            System.out.println("Введите второе число = ");
            int number2 = new Scanner(System.in).nextInt();
            //Выполняем математические действия
            System.out.println(YELLOW + "Вычисление..." + RESET);
            int sum = number1 + number2;
            System.out.println("Сумма(+) чисел = " + sum);
            int diff = number1 - number2;
            System.out.println("Разность(-) чисел = " + diff);
            int multi = number1 * number2;
                System.out.println("Произведение(*) чисел = " + multi);
            double division = (double) number1 / number2;
                System.out.println("Частное чисел(/) = " + division);
        }
        }
