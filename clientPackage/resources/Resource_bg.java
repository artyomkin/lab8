package clientPackage.resources;

import java.util.ListResourceBundle;

public class Resource_bg extends ListResourceBundle {
    private static final Object[][] contents = {
            {"Sign in","Разрешение"},
            {"Sign up","Регистрация"},
            {"Submit","Взема"},
            {"Login","Вход"},
            {"Password","Парола"},
            {"Repeat password","Повторете паролата"},
            {"Exit","Изход"},
    };

    public Object[][] getContents() {
        return contents;
    }
}
