package clientPackage.resources;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static final Object[][] contents = {
            {"Sign in","Авторизация"},
            {"Sign up","Регистрация"},
            {"Submit","Принять"},
            {"Login","Логин"},
            {"Password","Пароль"},
            {"Repeat password","Повторите пароль"},
            {"Exit","Выход"},
    };

    public Object[][] getContents() {
        return contents;
    }
}
