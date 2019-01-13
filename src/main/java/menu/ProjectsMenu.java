package menu;

import dao.CompanyDAO;
import dao.CustomerDAO;
import dao.DeveloperDAO;
import dao.ProjectDAO;
import entity.Company;
import entity.Customer;
import entity.Developer;
import entity.Project;

import java.util.List;
import java.util.Scanner;

public class ProjectsMenu {
    private ProjectDAO projectDAO = new ProjectDAO();
    private CompanyDAO companyDAO = new CompanyDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    private DeveloperDAO developerDAO = new DeveloperDAO();
    private Scanner input = new Scanner(System.in);

    public ProjectsMenu() {
        show();
    }

    private void show() {
        System.out.println("\n * Меню projects * ");
        System.out.println("Виберіть дію з переліку:" +
                "\n 1 - створити проект" +
                "\n 2 - показати інформацію про проект" +
                "\n 3 - показати інформацію про всі проекти" +
                "\n 4 - оновити інформацію про проект" +
                "\n 5 - видалити інформацію про проект" +
                "\n 6 - призначити розробника на проект" +
                "\n s - повернутися до головного меню" +
                "\n x - завершити роботу програми"
        );

        System.out.print("\nВведіть символ вибраної дії: ");
        String numberObj = input.next();
        switch (numberObj) {
            case "1":
                createProject();
                show();
                break;
            case "2":
                getProjectById();
                show();
                break;
            case "3":
                getAllProjects();
                show();
                break;
            case "4":
                updateProjectById();
                show();
                break;
            case "5":
                deleteProjectById();
                show();
                break;
            case "6":
                assignDeveloperToProject();
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


    private void createProject() {
        System.out.println("Введіть наступну інформацію про проект:");
        System.out.print("\tназва - ");
        String name = input.next();

        System.out.print("\tid компанії виконавця - ");
        Long idCompany = input.nextLong();
        idCompany = checkExistingIdCompany(idCompany);

        System.out.print("\tid компанії клієнта - ");
        Long idCustomer = input.nextLong();
        idCustomer = checkExistingIdCustomer(idCustomer);

        System.out.print("\tвартість - ");
        int cost = input.nextInt();

        Project project = new Project();
        project.setName(name);
        project.setIdCompany(idCompany);
        project.setIdCustomer(idCustomer);
        project.setCost(cost);
        projectDAO.createProject(project);

        System.out.println("-------------------------------------------");
        System.out.println("Iнформацію про проект внесено в базу");
        System.out.println("-------------------------------------------");
    }


    private void getProjectById() {
        System.out.println("-------------------------------------------");
        System.out.print("Введіть id проекта: ");
        Long id = input.nextLong();
        Project project = projectDAO.getProjectById(id);

        if (project != null) {
            System.out.println("Під id " + id + " в базі даних наявна наступна інформація: \n" + project);
        } else {
            System.out.println("Проект з id " + id + " відсутній в базі даних!");
        }
        System.out.println("-------------------------------------------");
    }


    private void getAllProjects() {
        List<Project> projects = projectDAO.getAllProjects();

        System.out.println("-------------------------------------------");
        if (projects != null) {
            System.out.println("В базі даних є інформація про " + projects.size() + " проектів:");
            for (Project project : projects) {
                System.out.println(project);
            }
        } else {
            System.out.println("Проекти відсутні в базі даних!");
        }
        System.out.println("-------------------------------------------");
    }


    private void updateProjectById() {
        System.out.println("-------------------------------------------");
        System.out.print("Введіть id проекта: ");
        Long id = input.nextLong();
        Project project = projectDAO.getProjectById(id);

        if (project != null) {
            System.out.println("Під id " + id + " в базі даних наявна наступна інформація: \n" + project);
            System.out.println("Введіть нову інформацію про проект:");

            System.out.print("\tназва - ");
            String name = input.next();

            System.out.print("\tid компанії виконавця - ");
            Long idCompany = input.nextLong();
            idCompany = checkExistingIdCompany(idCompany);

            System.out.print("\tid компанії клієнта - ");
            Long idCustomer = input.nextLong();
            idCustomer = checkExistingIdCustomer(idCustomer);

            System.out.print("\tвартість - ");
            int cost = input.nextInt();

            project.setName(name);
            project.setIdCompany(idCompany);
            project.setIdCustomer(idCustomer);
            project.setCost(cost);
            projectDAO.updateProjectById(project);

            System.out.println("Інформацію про проект оновлено");

        } else {
            System.out.println("Проект з id " + id + " відсутній в базі даних!");
        }
        System.out.println("-------------------------------------------");
    }


    private void deleteProjectById() {
        System.out.println("-------------------------------------------");
        System.out.print("Введіть id проекта: ");
        Long id = input.nextLong();
        Project project = projectDAO.getProjectById(id);

        if (project != null) {
            projectDAO.deleteProjectById(id);
            System.out.println("Інформацію про проект з id " + id + " видалено з бази даних");

        } else {
            System.out.println("Проект з id " + id + " відсутній в базі даних!");
        }
        System.out.println("-------------------------------------------");

    }


    private void assignDeveloperToProject() {
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.print("Введіть id проекта - ");
        long idProject = input.nextLong();
        Project project = projectDAO.getProjectById(idProject);
        long idDeveloper = 0;

        if (project != null) {
            showExistingDevelopersInCompany(idProject);
            idDeveloper = inputDeveloper(idProject);
        } else {
            System.out.println("Проект з id " + idProject + " відсутній в базі даних!");
        }

        projectDAO.assignDeveloperToProject(idDeveloper, idProject);
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Розробника призначено на проект");
        System.out.println("--------------------------------------------------------------------------------------");
    }


    private long checkExistingIdCompany(long idCompany) {
        long newIdCompany = idCompany;
        Company company = companyDAO.getCompanyById(idCompany);

        if (company != null) {
        } else {
            System.out.print("Інформація про компанію з id " + newIdCompany + " відсутня в базі даних!" +
                    "\nВведіть id компанії виконавця - ");
            newIdCompany = input.nextLong();
            newIdCompany = checkExistingIdCompany(newIdCompany);
        }

        return newIdCompany;
    }


    private long checkExistingIdCustomer(long idCustomer) {
        long newIdCustomer = idCustomer;
        Customer customer = customerDAO.getCustomerById(idCustomer);

        if (customer != null) {
        } else {
            System.out.print("Інформація про клієнта з id " + newIdCustomer + " відсутня в базі даних!" +
                    "\nВведіть id компанії клієнта - ");
            newIdCustomer = input.nextLong();
            newIdCustomer = checkExistingIdCustomer(newIdCustomer);
        }

        return newIdCustomer;
    }


    private void showExistingDevelopersInCompany(long idProject) {
        List<Developer> developers = developerDAO.getDevelopersFromCompanyByProjectId(idProject);

        System.out.println("--------------------------------------------------------------------------------------");
        if (developers != null) {
            System.out.println("В базі даних є інформація про " + developers.size() + " розробників, " +
                    "яких можливо призначати на проект з id " + idProject + ":");
            for (Developer developer : developers) {
                System.out.println(developer);
            }

        } else {
            System.out.print("В базі даних відсутня інформація про розробників, яких можливо призначати на проект!" +
                    "Змініть компанію, що виконує проект або додайте розробника до компанії.");
        }
        System.out.println("--------------------------------------------------------------------------------------");
    }


    private long inputDeveloper(long idProject) {
        long idDeveloper = 0;
        System.out.print("Введіть id розробника - ");
        idDeveloper = input.nextLong();
        idDeveloper = checkExistingIdDeveloperInCompany(idDeveloper, idProject);

        return idDeveloper;
    }


    private long checkExistingIdDeveloperInCompany(long idDeveloper, long idProject) {
        long newIdDeveloper = idDeveloper;
        List<Developer> developers = developerDAO.getDevelopersFromCompanyByProjectId(idProject);

        int count = 0;
        for (Developer developer : developers) {
            long id = developer.getId();
            if (id == newIdDeveloper) {
                count++;
            }
        }

        if (count == 1) {
        } else {
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.print("Неможливо призначити розробника з id " + newIdDeveloper + " для проекту з id " + idProject + "!" +
                    "\n--------------------------------------------------------------------------------------" +
                    "\nВведіть id розробника - ");
            newIdDeveloper = input.nextLong();
            newIdDeveloper = checkExistingIdDeveloperInCompany(newIdDeveloper, idProject);
        }

        return newIdDeveloper;
    }

}