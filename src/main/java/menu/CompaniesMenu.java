package menu;

import dao.CompanyDAO;
import entity.Company;

import java.util.List;
import java.util.Scanner;

public class CompaniesMenu {
    private CompanyDAO companyDAO = new CompanyDAO();
    private Scanner input = new Scanner(System.in);

    public CompaniesMenu() {
        show();
    }

    private void show() {
        System.out.println("\n * Меню companies * ");
        System.out.println("Виберіть дію з переліку:" +
                "\n 1 - створити компанію" +
                "\n 2 - показати інформацію про компанію" +
                "\n 3 - показати інформацію про всі компанії" +
                "\n 4 - оновити інформацію про компанію" +
                "\n 5 - видалити інформацію про компанію" +
                "\n s - повернутися до головного меню" +
                "\n x - завершити роботу програми"
        );

        System.out.print("\nВведіть символ вибраної дії: ");
        String numberObj = input.next();
        switch (numberObj) {
            case "1":
                createCompany();
                show();
                break;
            case "2":
                getCompanyById();
                show();
                break;
            case "3":
                getAllCompanies();
                show();
                break;
            case "4":
                updateCompanyById();
                show();
                break;
            case "5":
                deleteCompanyById();
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


    private void createCompany() {
        System.out.println("Введіть наступну інформацію про компанію:");
        System.out.print("\tім'я - ");
        String name = input.next();

        System.out.print("\tадреса - ");
        String address = input.next();

        Company company = new Company();
        company.setName(name);
        company.setAddress(address);
        companyDAO.createCompany(company);

        System.out.println("-------------------------------------------");
        System.out.println("Iнформацію про компанію внесено в базу");
        System.out.println("-------------------------------------------");
    }


    private void getCompanyById() {
        System.out.println("-------------------------------------------");
        System.out.print("Введіть id компанії: ");
        Long id = input.nextLong();
        Company company = companyDAO.getCompanyById(id);

        if (company != null) {
            System.out.println("Під id " + id + " в базі наявна наступна інформація: \n" + company);
        } else {
            System.out.println("Компанія з id " + id + " відсутня в базі!");
        }
        System.out.println("-------------------------------------------");
    }


    private void getAllCompanies() {
        List<Company> companies = companyDAO.getAllCompanies();

        System.out.println("-------------------------------------------");
        if (companies != null) {
            System.out.println("В базі є інформація про " + companies.size() + " компаній:");
            for (Company company : companies) {
                System.out.println(company);
            }
        } else {
            System.out.println("Компанії відсутні в базі!");
        }
        System.out.println("-------------------------------------------");
    }


    private void updateCompanyById() {
        System.out.println("-------------------------------------------");
        System.out.print("Введіть id компанії: ");
        Long id = input.nextLong();
        Company company = companyDAO.getCompanyById(id);

        if (company != null) {
            System.out.println("Під id " + id + " в базі наявна наступна інформація: \n" + company);
            System.out.println("Введіть нову інформацію про компанію:");

            System.out.print("\tім'я - ");
            String name = input.next();

            System.out.print("\tадреса - ");
            String address = input.next();

            company.setName(name);
            company.setAddress(address);
            companyDAO.updateCompany(company);

            System.out.println("Інформацію про компанію оновлено");
        } else {
            System.out.println("Компанія з id " + id + " відсутня в базі!");
        }
        System.out.println("-------------------------------------------");
    }


    private void deleteCompanyById() {
        System.out.println("-------------------------------------------");
        System.out.print("Введіть id компанії: ");
        Long id = input.nextLong();
        Company company = companyDAO.getCompanyById(id);

        if (company != null) {
            companyDAO.deleteCompanyById(id);
            System.out.println("Інформацію про компанію з id " + id + " видалено з бази");
        } else {
            System.out.println("Компанія з id " + id + " відсутня в базі!");
        }
        System.out.println("-------------------------------------------");
    }
}