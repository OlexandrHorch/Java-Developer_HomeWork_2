package menu;

import java.util.Scanner;

import static service.ConnectionDB.*;

public class PreStartMenu {
    private Scanner input = new Scanner(System.in);

    public PreStartMenu() {
        currentSettingsWithoutPass();
        show();
    }

    private void show() {
        System.out.println("\n * ПЕРЕДСТАРТОВЕ МЕНЮ * ");
        System.out.println("Виберіть дію з переліку:" +
                "\n 1 - перейти до головного меню" +
                "\n 2 - змінити назву бази даних" +
                "\n 3 - змінити логін" +
                "\n 4 - змінити пароль" +
                "\n 5 - показати пароль" +
                "\n 6 - приховати пароль" +
                "\n 7 - повернути початкові налаштування" +
                "\n x - завершити роботу програми"
        );

        System.out.print("\nВведіть символ вибраної дії: ");
        String numberObj = input.next();
        switch (numberObj) {
            case "1":
                new StartMenu();
                break;
            case "2":
                changeDB();
                currentSettingsWithoutPass();
                show();
                break;
            case "3":
                changeLogin();
                currentSettingsWithoutPass();
                show();
                break;
            case "4":
                changePass();
                currentSettingsWithoutPass();
                show();
                break;
            case "5":
                currentSettingsWithPass();
                show();
                break;
            case "6":
                currentSettingsWithoutPass();
                show();
                break;
            case "7":
                returnSettings();
                currentSettingsWithoutPass();
                show();
                break;
            case "x":
                System.out.print("Програму завершено");
                break;
            default:
                System.out.println("* * * * * * * * * * * * * * * * * * * *\n" +
                        "* Невідома команда! Спробуйте ще раз. *\n" +
                        "* * * * * * * * * * * * * * * * * * * *");
                show();
        }
    }

    private void currentSettingsWithoutPass() {
        System.out.println("\n*************************************" +
                "\n*       ПОТОЧНІ  НАЛАШТУВАННЯ       *" +
                "\n*************************************" +
                "\nНазва бази даних: " + DB_NAME +
                "\nЛогін підключення: " + DB_LOGIN +
                "\nПароль підключення: ****" +
                "\n*************************************");
    }

    private void currentSettingsWithPass() {
        System.out.println("\n*************************************" +
                "\n*       ПОТОЧНІ  НАЛАШТУВАННЯ       *" +
                "\n*************************************" +
                "\nНазва бази даних: " + DB_NAME +
                "\nЛогін підключення: " + DB_LOGIN +
                "\nПароль підключення: " + DB_PASSWORD +
                "\n*************************************");
    }

    private void changeDB() {
        System.out.print("Введіть назву бази даних: ");
        DB_NAME = input.next();
    }

    private void changeLogin() {
        System.out.print("Введіть логін підключення: ");
        DB_LOGIN = input.next();
    }

    private void changePass() {
        System.out.print("Введіть пароль підключення: ");
        DB_PASSWORD = input.next();
    }

    private void returnSettings() {
        DB_NAME = "it_relationship";
        DB_LOGIN = "root";
        DB_PASSWORD = "root";
    }
}