package Servlets;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


@ManagedBean
@RequestScoped
public class alertBean {

    public String alert(String p) {
        String re = "";
        if (p.equals("notImage")) {
            re = "<p>Файл не является изображением</p>";
        } else if (p.equals("notImageFormat")) {
            re = "<p>Формат файла не поддерживается. Сохраните изображение в формате jpeg, gif, png, bmp</p>";
        } else if (p.equals("bigSize")) {
            re = "<p>Размер файла превышает максимальный допустимый размер. Изображение необходимо уменьшить.</p>";
        } else if (p.equals("bigSizeF")) {
            re = "<p>Размер файла превышает максимальный допустимый размер.</p>";
        } else if (p.equals("unknownError")) {
            re = "<p>Неизвестная ошибка!</p>";
        } else if (p.equals("sqlError")) {
            re = "<p>Ошибка базы данных!</p>";
        } else if (p.equals("mailError")) {
            re = "<p>Сообщение не отправлено! Проверьте настройки почтового сервера.</p>";
        } else if (p.equals("notFileFormat")) {
            re = "<p>Тип файла не соответствует типу добавляемой версии. Версия не сохранена.</p>";
        } else if (p.equals("tooMoreFiles")) {
            re = "<p>Общий объем хранимых вами файлов превысил максимально допустимый. Прежде чем что-то делать, освободите место.</p>";
        } else if (p.equals("noconnection")) {
            re = "<p>Соединение с  хранилищем документов не может быть установлено.</p>";
        } else if (p.equals("noEmp")) {
            re = "<p>Такого сотрудника не существует!</p>";
        } else if (p.equals("nopermit")) {
            re = "<p>Вам запрещена эта операция!</p>";
        } else if (p.equals("itsdemo")) {
            re = "<p>Вы сейчас работаете в демонстрационном портале. В нем отключена возможность добавления сотрудников.\n"
                    + "            <p>Чтобы начать работу в портале, сперва <a href=\"http://exxo.ru/install/howto/#win\">удалите демонстрацию</a>.";
        } else if (p.equals("countup")) {
            re = "<p>Количество сотрудников достигло максимального значения, позволенного вашей лицензией.\n"
                    + "            <p>Прежде чем добавлять новых сотрудников, активируйте лицензию на дополнительных сотрудников.";
        } else if (p.equals("noRole")) {
            re = "<p>Такой роли не существует!</p>";
        }
        return re;
    }
}
