package menu;

import dao.DeveloperDAO;
import dao.ProjectDAO;
import entity.Developer;
import entity.Project;

import java.util.List;
import java.util.Scanner;

public class OtherMenu {
    private DeveloperDAO developerDAO = new DeveloperDAO();
    private ProjectDAO projectDAO = new ProjectDAO();
    private Scanner input = new Scanner(System.in);

    public OtherMenu() {
        show();
    }

    private void show() {
        System.out.println("\n * Меню інших запитів * ");
        System.out.println("Виберіть, яку інформацію відобразити:" +
                "\n 1 - зарплату (суму) всіх розробників окремого проекта" +
                "\n 2 - список розробників окремого проекта" +
                "\n 3 - список всіх JAVA розробників" +
                "\n 4 - список всіх middle розробників" +
                "\n s - повернутися до головного меню" +
                "\n x - завершити роботу програми"
        );

        System.out.print("\nВведіть символ вибраної дії: ");
        String numberObj = input.next();
        switch (numberObj) {
            case "1":
                getSumDevelopersSalaryInProjectByProjectId();
                show();
                break;
            case "2":
                getAllDevelopersByProjectId();
                show();
                break;
            case "3":
                getAllJavaDevelopers();
                show();
                break;
            case "4":
                getAllMiddleDevelopers();
                show();
                break;
            case "s":
                new StartMenu();
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


    private void getSumDevelopersSalaryInProjectByProjectId() {
        System.out.print("Введіть id проекта: ");
        long id = input.nextLong();

        Project project = projectDAO.getProjectById(id);
        List<Developer> developers = developerDAO.getAllDevelopersByProjectId(id);

        System.out.println("--------------------------------------------------------------------------------------");
        if (project != null) {

            if (developers != null) {
                int sumSalary = developerDAO.getSumDevelopersSalaryInProjectByProjectId(id);
                System.out.println("Сума зарплат всіх розробників, що працюють над проєктом з id " + id + " = " + sumSalary);

            } else {
                System.out.println("В базі даних відсутня інформація про розробників проекту з id " + id + "!");
            }

        } else {
            System.out.println("Проект з id " + id + " відсутній в базі даних!");
        }
        System.out.println("--------------------------------------------------------------------------------------");
    }


    private void getAllDevelopersByProjectId() {
        System.out.print("Введіть id проекта: ");
        long id = input.nextLong();

        Project project = projectDAO.getProjectById(id);
        List<Developer> developers = developerDAO.getAllDevelopersByProjectId(id);

        System.out.println("-------------------------------------------");
        if (project != null) {

            if (developers != null) {
                System.out.println("В базі даних є інформація про " + developers.size() + " розробників, що працюють над проєктом з id " + id + ": ");
                for (Developer developer : developers) {
                    System.out.println(developer);
                }
            } else {
                System.out.println("Розробники відсутні в базі даних!");
            }

        } else {
            System.out.println("Проект з id " + id + " відсутній в базі даних!");
        }
        System.out.println("-------------------------------------------");
    }


    private void getAllJavaDevelopers() {
        List<Developer> developers = developerDAO.getAllJavaDevelopers();

        System.out.println("-------------------------------------------");
        if (developers != null) {
            System.out.println("В базі даних є інформація про " + developers.size() + " JAVA розробників:");
            for (Developer developer : developers) {
                System.out.println(developer);
            }
        } else {
            System.out.println("Розробники відсутні в базі даних!");
        }
        System.out.println("-------------------------------------------");
    }


    private void getAllMiddleDevelopers() {
        List<Developer> developers = developerDAO.getAllMiddleDevelopers();

        System.out.println("-------------------------------------------");
        if (developers != null) {
            System.out.println("В базі даних є інформація про " + developers.size() + " middle розробників: ");
            for (Developer developer : developers) {
                System.out.println(developer);
            }
        } else {
            System.out.println("Розробники відсутні в базі даних!");
        }
        System.out.println("-------------------------------------------");
    }
}