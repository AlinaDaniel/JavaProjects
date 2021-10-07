package FilesAndPatterns;



public class Main {
    public static void main(String[] args) {
        FileAccountManager myManager = new FileAccountManager();
//регистрация
        Account account1 = new Account("Alina", "25.07.2001", "alinad.@mail.ru", "877777");
        try {
            myManager.register(account1);
        } catch (AccountAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(FileService.getInstance().readFile()); //ПРОВЕРКА

        // 5 раз неправильно вводим данные (такой почты нет, не считаем)
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(myManager.login("al.@mail.ru", "0000"));

            } catch (AccountBlockedException | WrongCredentialsException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(FailedLoginCounter.getCounter("al.@mail.ru"));
        //после этого пытаемся зайти в аккаунт
        try {
            System.out.println(myManager.login("alinad.@mail.ru", "877777"));
            System.out.println(FailedLoginCounter.getCounter("alinad.@mail.ru"));
        } catch (AccountBlockedException | WrongCredentialsException e) {
            System.out.println(e.getMessage());
        }
        // 5 раз неправильно вводим данные (такая почта есть, считаем)
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(myManager.login("alinad.@mail.ru", "8577"));

            } catch (AccountBlockedException | WrongCredentialsException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(FailedLoginCounter.getCounter("alinad.@mail.ru"));
        //после этого пытаемся зайти в аккаунт
        try {
            System.out.println(myManager.login("alinad.@mail.ru", "877777"));
        } catch (AccountBlockedException | WrongCredentialsException e) {
            System.out.println(e.getMessage());
        }
    }
}