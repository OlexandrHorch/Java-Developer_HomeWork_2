package menu;

import java.util.Scanner;

public class StartMenu {
    private Scanner input = new Scanner(System.in);

    public StartMenu() {
        show();
    }

    private void show() {
        System.out.println("\n\n * ГОЛОВНЕ МЕНЮ * ");
        System.out.println("Виберіть дію з переліку:" +
                "\n 1 - перейти до developers" +
                "\n 2 - перейти до skills" +
                "\n 3 - перейти до projects" +
                "\n 4 - перейти до companies" +
                "\n 5 - перейти до customers" +
                "\n 6 - перейти до інших запитів" +
                "\n x - завершити роботу програми"
        );

        System.out.print("\nВведіть символ вибраного об'єкта: ");
        String numberObj = input.next();
        switch (numberObj) {
            case "1":
                new DevelopersMenu();
                break;
            case "2":
                new SkillsMenu();
                break;
            case "3":
                new ProjectsMenu();
                break;
            case "4":
                new CompaniesMenu();
                break;
            case "5":
                new CustomersMenu();
                break;
            case "6":
                new OtherMenu();
                break;
            case "x":
                System.out.print("\nПрограму завершено");
                break;
            default:
                System.out.println("* * * * * * * * * * * * * * * * * * * *\n" +
                        "* Невідома команда! Спробуйте ще раз. *\n" +
                        "* * * * * * * * * * * * * * * * * * * *");
                show();
        }
    }
}