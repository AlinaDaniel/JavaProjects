package FilesAndPatterns;

import java.io.File;
import java.util.List;

public class FileAccountManager implements AccountManager{

    @Override
    public void register(Account newAccount) throws AccountAlreadyExistsException {
        /*
         * Метод проверяет по полю email, что данный аккаунт в базе
         * отсутствует, и создает новую запись, в противном случае
         * выбрасывает ошибку AccountAlreadyExistsException
         */
        List<Account> accounts = FileService.getInstance().readFile();
        for (Account account : accounts) {
            if (account.getEmail().equals(newAccount.getEmail())){
                throw new AccountAlreadyExistsException("На данный email уже зарегистрирован аккаунт.");
            }
        }
        FileService.getInstance().writeInFile(newAccount+";"+"\n",true);
        System.out.println("Регистрация выполнена успешно!");
    }

    @Override
    public Account login(String email, String password) throws AccountBlockedException, WrongCredentialsException {
        /*
         * Метод возвращает Account, если для email+пароль есть
         * подходящая запись в базе и аккаунт не заблокирован.
         * Если неверно введены email и/или пароль - выбрасывается
         * исключение WrongCredentialsException.
         * Если email и пароль верны, но аккаунт заблокирован -
         * выбрасывается исключение AccountBlockedException.
         * Если для конкретного пользователя больше 5 неудачных
         * попыток авторизоваться, то аккаунт блокируется.
         */
        List<Account> accounts = FileService.getInstance().readFile();
        for (Account account : accounts) {
            if (account.getEmail().equals(email) && account.getPassword().equals(password)){
                if (account.getBlocked()){
                    throw new AccountBlockedException("Невозможно авторизоваться, аккаунт заблокирован.");
                } else {
                    if (FailedLoginCounter.getCounter(email)>=5){
                        System.out.println("Ранее вы совершили 5 неудачных попыток авторизоваться," +
                                " ваш аккаунт блокируется.");
                        this.removeAccount(account.getEmail(),account.getPassword());
                        FileService.getInstance().writeInFile(new Account(account.getFullName(),
                                account.getBirthday(),account.getEmail(),account.getPassword(),
                                true)+";"+"\n",true);
                        return null;
                    }
                    return account;
                }
            } else if (account.getEmail().equals(email)){
                FailedLoginCounter.getInstance(email);
                throw new WrongCredentialsException("Данные введены неверно.");
            }
        }
        throw new WrongCredentialsException("Данные введены неверно.");
    }

    @Override
    public void removeAccount(String email, String password) throws WrongCredentialsException {
        /*
         * Метод удаляет пользователя из базы, если логин и пароль
         * введены верно. В противном случае выбрасывает
         * ошибку WrongCredentialsException
         */
        List<Account> accounts = FileService.getInstance().readFile();
        for (Account account : accounts) {
            if (account.getEmail().equals(email) && account.getPassword().equals(password)){
                accounts.remove(account);
                FileService.getInstance().writeInFile("",false);
                for (Account updatedAccount : accounts){
                    FileService.getInstance().writeInFile(updatedAccount+";"+"\n",true);
                }
                return;
            }
        }
        throw new WrongCredentialsException("Данные введены неверно.");
    }
}
