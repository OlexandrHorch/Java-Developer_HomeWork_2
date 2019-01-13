package menu;

import dao.SkillDAO;
import entity.Skill;

import java.util.List;
import java.util.Scanner;

public class SkillsMenu {
    private SkillDAO skillDAO = new SkillDAO();
    private Scanner input = new Scanner(System.in);

    public SkillsMenu() {
        show();
    }

    private void show() {
        System.out.println("\n * Меню skils * ");
        System.out.println("Виберіть дію з переліку:" +
                "\n 1 - створити вміння" +
                "\n 2 - показати інформацію про вміння" +
                "\n 3 - показати інформацію про всі вміння" +
                "\n 4 - оновити інформацію про вміння" +
                "\n 5 - видалити інформацію про вміння" +
                "\n s - повернутися до головного меню" +
                "\n x - завершити роботу програми"
        );

        System.out.print("\nВведіть символ вибраної дії: ");
        String numberObj = input.next();
        switch (numberObj) {
            case "1":
                createSkill();
                show();
                break;
            case "2":
                getSkillById();
                show();
                break;
            case "3":
                getAllSkills();
                show();
                break;
            case "4":
                updateSkillById();
                show();
                break;
            case "5":
                deleteSkillById();
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


    private void createSkill() {
        System.out.println("-------------------------------------------");
        System.out.println("Введіть наступну інформацію про вміння:");
        System.out.print("\tназву - ");
        String area = input.next();

        System.out.print("\tрівень - ");
        String level = input.next();

        Skill skill = new Skill();
        skill.setArea(area);
        skill.setLevel(level);
        skillDAO.createSkill(skill);

        System.out.println("Iнформацію про вміння внесено в базу даних");
        System.out.println("-------------------------------------------");
    }


    private void getSkillById() {
        System.out.println("-------------------------------------------");
        System.out.print("Введіть id вміння: ");
        Long id = input.nextLong();
        Skill skill = skillDAO.getSkillById(id);

        if (skill != null) {
            System.out.println("Під id " + id + " в базі наявна наступна інформація: \n" + skill);
        } else {
            System.out.println("Вміння з id " + id + " відсутнє в базі даних!");
        }
        System.out.println("-------------------------------------------");
    }


    private void getAllSkills() {
        System.out.println("-------------------------------------------");
        List<Skill> skills = skillDAO.getAllSkills();

        if (skills != null) {
            System.out.println("В базі даних є інформація про " + skills.size() + " вміннь:");
            for (Skill skill : skills) {
                System.out.println(skill);
            }
        } else {
            System.out.println("Вміння відсутнє в базі даних!");
        }
        System.out.println("-------------------------------------------");
    }


    private void updateSkillById() {
        System.out.println("-------------------------------------------");
        System.out.print("Введіть id вміння: ");
        Long id = input.nextLong();
        Skill skill = skillDAO.getSkillById(id);

        if (skill != null) {
            System.out.println("Під id " + id + " в базі даних наявна наступна інформація: \n" + skill);
            System.out.println("Введіть нову інформацію про вміння:");
            System.out.print("\tім'я - ");
            String name = input.next();

            System.out.print("\tадреса - ");
            String address = input.next();

            skill.setArea(name);
            skill.setLevel(address);
            skillDAO.updateSkill(skill);

            System.out.println("Інформацію про вміння оновлено");
        } else {
            System.out.println("Вміння з id " + id + " відсутнє в базі даних!");
        }
        System.out.println("-------------------------------------------");
    }


    private void deleteSkillById() {
        System.out.println("-------------------------------------------");
        System.out.print("Введіть id вміння: ");
        Long id = input.nextLong();
        Skill skill = skillDAO.getSkillById(id);

        if (skill != null) {
            skillDAO.deleteSkillById(id);
            System.out.println("Інформацію про вміння з id " + id + " видалено з бази даних");
        } else {
            System.out.println("Вміння з id " + id + " відсутнє в базі даних!");
        }
        System.out.println("-------------------------------------------");
    }
}