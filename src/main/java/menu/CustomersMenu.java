package menu;

import dao.CustomerDAO;
import entity.Customer;

import java.util.List;
import java.util.Scanner;

public class CustomersMenu {
    private CustomerDAO customerDAO = new CustomerDAO();
    private Scanner input = new Scanner(System.in);

    public CustomersMenu() {
        show();
    }

    private void show() {
        System.out.println("\n * Меню customers * ");
        System.out.println("Виберіть дію з переліку:" +
                "\n 1 - створити клієнта" +
                "\n 2 - показати інформацію про клієнта" +
                "\n 3 - показати інформацію про всіх клієнтів" +
                "\n 4 - оновити інформацію про клієнта" +
                "\n 5 - видалити інформацію про клієнта" +
                "\n s - повернутися до головного меню" +
                "\n x - завершити роботу програми"
        );

        System.out.print("\nВведіть символ вибраної дії: ");
        String numberObj = input.next();
        switch (numberObj) {
            case "1":
                createCustomer();
                show();
                break;
            case "2":
                getCustomerById();
                show();
                break;
            case "3":
                getAllCustomers();
                show();
                break;
            case "4":
                updateCustomerById();
                show();
                break;
            case "5":
                deleteCustomerById();
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


    private void createCustomer() {
        System.out.println("Введіть наступну інформацію про клієнта:");
        System.out.print("\tім'я - ");
        String name = input.next();

        System.out.print("\tадреса - ");
        String address = input.next();

        Customer customer = new Customer();
        customer.setName(name);
        customer.setAddress(address);
        customerDAO.createCustomer(customer);

        System.out.println("-------------------------------------------");
        System.out.println("Iнформацію про клієнта внесено в базу");
        System.out.println("-------------------------------------------");
    }


    private void getCustomerById() {
        System.out.println("-------------------------------------------");
        System.out.print("Введіть id клієнта: ");
        Long id = input.nextLong();
        Customer customer = customerDAO.getCustomerById(id);

        if (customer != null) {
            System.out.println("Під id " + id + " в базі наявна наступна інформація: \n" + customer);
        } else {
            System.out.println("Клієнт з id " + id + " відсутній в базі!");
        }
        System.out.println("-------------------------------------------");
    }


    private void getAllCustomers() {
        List<Customer> customers = customerDAO.getAllCustomers();

        System.out.println("-------------------------------------------");
        if (customers != null) {
            System.out.println("В базі є інформація про " + customers.size() + " клієнтів:");
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        } else {
            System.out.println("Клієнти відсутні в базі!");
        }
        System.out.println("-------------------------------------------");
    }


    private void updateCustomerById() {
        System.out.println("-------------------------------------------");
        System.out.print("Введіть id клієнта: ");
        Long id = input.nextLong();
        Customer customer = customerDAO.getCustomerById(id);

        if (customer != null) {
            System.out.println("Під id " + id + " в базі наявна наступна інформація: \n" + customer);
            System.out.println("Введіть нову інформацію про клієнта:");
            System.out.print("\tім'я - ");
            String name = input.next();

            System.out.print("\tадреса - ");
            String address = input.next();

            customer.setName(name);
            customer.setAddress(address);
            customerDAO.updateCustomer(customer);

            System.out.println("Інформацію про клієнта оновлено");
        } else {
            System.out.println("Клієнт з id " + id + " відсутній в базі!");
        }
        System.out.println("-------------------------------------------");
    }


    private void deleteCustomerById() {
        System.out.println("-------------------------------------------");
        System.out.print("Введіть id клієнта: ");
        Long id = input.nextLong();
        Customer customer = customerDAO.getCustomerById(id);

        if (customer != null) {
            customerDAO.deleteCustomerById(id);
            System.out.println("Інформацію про клієнта з id " + id + " видалено з бази");
        } else {
            System.out.println("Клієнт з id " + id + " відсутній в базі!");
        }
        System.out.println("-------------------------------------------");

    }
}