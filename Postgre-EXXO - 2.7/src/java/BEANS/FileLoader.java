package BEANS;

import java.sql.*;
//import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class FileLoader {

    public boolean redirect = false;
    public boolean isOwner = false;
    public boolean isOwners = false;
    public String cat = "";
    public String V = "0";
    public String loads_list = "";
    public String rightMenu = "";
    public boolean isNotCopy = true;
    public int step = 0;
    public boolean hasVisas = false;
    public boolean isDocEditor = false;
    public String owner = null;
    public String albums = "";
    public boolean isImg = false;

    public String getList(HttpServletRequest r, String o, String g) throws ClassNotFoundException, SQLException {
        ServletContext sc = r.getServletContext();
        Connection con = BASE.VER.getServletConnection(sc);
        Statement stmt = con.createStatement();
        Statement stmt1 = con.createStatement();
        int def = 0;
        String emps = "<span class='emps'>ничего не выбрано</span>";
        String id = r.getParameter("id");
        String ver = r.getParameter("v");
        String late = r.getParameter("l");
        String role = (String) r.getSession().getAttribute("role");
        boolean isVer = false;
        if (id == null || id.equals("")) {
            id = "0";
        }
        if (ver != null && !ver.equals("")) {
            isVer = true;
        }
        boolean isLate = false;
        String visa_list = "";
        if (late != null) {
            isLate = true;
        }
        String verString = "";
        String vers = "";
        String ver_id = "-1";
        String own = "";
        int status = 0;
        boolean isSigned = false;
        boolean isSignable = false;
        boolean isSecurity = false;
        boolean isHead = false;
        if (role.indexOf("e") >= 0 || role.indexOf("s") >= 0) {
            isSecurity = true;
        }

        int ver_count = 0;
        boolean changed = false;
        String sql3 = "SELECT ver_id, properTime(files_vers.created) AS created FROM files_vers, files WHERE files.id='" + id + "' AND files.copy=files_vers.id ORDER BY ver_id DESC";
        ResultSet rs4 = stmt.executeQuery(sql3);
        while (rs4.next()) {
            if (rs4.isFirst()) {
                if (isVer) {
                    ver_id = ver;
                } else {
                    ver_id = rs4.getString("ver_id");
                }
                if (isVer && !rs4.getString("ver_id").equals(ver)) {
                    changed = true;
                }
                vers += "<a href='fileLoader.jsp?id=" + id + "&v=" + rs4.getString("ver_id") + "'>" + rs4.getString("created") + "</a>";
            } else {
                vers += "| <a href='fileLoader.jsp?id=" + id + "&v=" + rs4.getString("ver_id") + "'>" + rs4.getString("created") + "</a>";
            }
        }
        rs4.close();
        if (isVer) {
            verString = "AND ver_id=" + ver;
        }

        loads_list = "<div class='notOpen'>Никто не открыл этот документ</div>";
        String sql4 = "SELECT id, user_id, properTime(visited) AS visited, get_fio(user_id) AS fio FROM file_ver_reg WHERE id=" + ver_id + " ORDER BY fio";
        ResultSet rs5 = stmt.executeQuery(sql4);
        while (rs5.next()) {
            if (rs5.isFirst()) {
                loads_list = "<table>";
            }
            ver_count++;
            loads_list += "<tr><td>" + rs5.getString("fio") + "</td><td>" + rs5.getString("visited") + "</td></tr>";
            if (rs5.isLast()) {
                loads_list += "</table>";
            }
        }

        rs5.close();

        String discussions = "";
        sql4 = "SELECT name, id, properTime(time) FROM bloges WHERE refdoc=" + id + " ORDER BY time DESC";
        ResultSet rs7 = stmt.executeQuery(sql4);
        while (rs7.next()) {
            discussions += "<div class='blogs'><span class='timeB'>" + rs7.getString(3) + "</span> <a href='blog.jsp?id=" + rs7.getString("id") + "' class='discus'>" + rs7.getString("name") + "</a></div>";
        }
        rs7.close();
        String tags = "не заданы";
        String sql = "SELECT tags.name,tags.tag_id FROM global_tags, tags WHERE global_id = (SELECT global_id FROM files WHERE id='" + id + "') AND tags.tag_id=global_tags.tag_id"
                + " ORDER BY tags.name";
        ResultSet rs2 = stmt.executeQuery(sql);
        while (rs2.next()) {
            if (rs2.isFirst()) {
                tags = "";
            }
            tags += "<a href='docTag?el=" + rs2.getString(2) + "&s=files'>" + rs2.getString(1) + "</a> ";
        }
        rs2.close();
        sql = "SELECT files.id, files.owner, files.name, get_fio(files.owner), files.fname, files.descr, isBlogPermitted (dopusk_type, dopusk, " + g + "::Int) AS permitted,"
                + " type, properTime(files.created) AS create, files.superior AS superior, "
                + " catalog_name(superior),dopusk_type,sizeico, dopusk, vers, properTime(files_vers.created) AS versCreated, ver_id, status, step, "
                + "files.id = files.copy AS isCopy, copy,isSignable(files.id,dopusk_type,dopusk,owner) AS isSignable,"
                + " isSignedBy(ver_id," + o + ") AS isSigned, author, get_fio(author) AS author_fio, author is not null AS has_author, isHead(files.owner) AS isHead FROM files, files_vers WHERE file='1' AND files.copy=files_vers.id "
                + " AND files.id='" + id + "' " + verString + " ORDER BY files_vers.created DESC LIMIT 1";
        ResultSet rs = stmt.executeQuery(sql);
        String list = "Файл не найден, или у вас нет соответствующего допуска к этому файлу!";

        if (rs.next()) {
            owner = rs.getString(2);
            if (o.equals(rs.getString(2))) {
                isOwner = true;
            }
            if ((role.indexOf("o") >= 0 && rs.getString(2).equals("-100")) || (role.indexOf("p") >= 0 && rs.getString(2).equals("-101"))
                    || (role.indexOf("q") >= 0 && rs.getString(2).equals("-102")) || (role.indexOf("r") >= 0 && rs.getString(2).equals("-103"))) {
                isDocEditor = true;
            }
            if (isOwner || isDocEditor) {
                isOwners = true;
            }
            String t = rs.getString("type");
            String type = EXXOlib.DOCTYPES.getType(t, rs.getString(5));
            V = rs.getString("ver_id");
            status = rs.getInt("status");
            step = rs.getInt("step");
            cat = rs.getString(10);
            isNotCopy = rs.getBoolean("isCopy");
            isHead = rs.getBoolean("isHead");
            isSignable = rs.getBoolean("isSignable");
            isSigned = rs.getBoolean("isSigned");
            String img = "";
            String fio = rs.getString(4);

            String sub[] = fio.split(" ");
            String Fio = sub[0];
            if (sub.length > 1) {
                Fio += "<br>" + sub[1];
            }
            if (sub.length > 2) {
                Fio += "<br>" + sub[2];
            }

            String PreCat = "";
            if (rs.getInt(2) == -100) {
                PreCat = "Общие документы: ";
            } else if (rs.getInt(2) == -101) {
                PreCat = "Входящие документы: ";
            } else if (rs.getInt(2) == -102) {
                PreCat = "Исходящие документы: ";
            } else if (rs.getInt(2) == -103) {
                PreCat = "Внутренние документы: ";
            }
            if (rs.getInt(2) < 0) {
                own = "&owner=" + rs.getInt(2);
            }
            if (rs.getInt("sizeico") > 0) {
                img = "<tr><td class='first'>Изображение</td><td class='second ico'><img src='iconLoader?id=" + id + "&v=" + V + "'></td></tr>";
                isImg = true;
            }
            boolean trig = true;
            String dopusk_type = rs.getString("dopusk_type");
            java.sql.Array re = rs.getArray("dopusk");
            ResultSet dopusk;
            if (re != null) {
                dopusk = re.getResultSet();
            } else {
                dopusk = null;
            }
            if (dopusk != null) {
                while (dopusk.next()) {
                    if (dopusk.isFirst()) {
                        def = dopusk.getInt(2);
                    }
                    if (dopusk_type.equals("4")) {
                        sql = "SELECT fio, id FROM employee WHERE global_id='" + dopusk.getInt(2) + "'";
                        ResultSet rr = stmt1.executeQuery(sql);
                        if (rr.next()) {
                            if (dopusk.isFirst()) {
                                emps = "";
                            }
                            emps += " <a class='emps' href='empCard.jsp?id=" + rr.getString(2) + "'>" + EXXOlib.textLib.shortFIO(rr.getString(1)) + "</a>";
                        }
                        rr.close();
                    }
                }
                dopusk.close();
            }
            if (dopusk_type.equals("0")) {
                emps = "<span class='emps'>Общедоступный</span>";
            } else if (dopusk_type.equals("1")) {
                sql = "SELECT name FROM projects WHERE id=" + def;
                ResultSet rs3 = stmt1.executeQuery(sql);
                if (rs3.next()) {
                    emps = "<a class='emps' href='groups.jsp?id=" + def + "'>" + rs3.getString(1) + "</span>";
                }
                rs3.close();
            } else if (dopusk_type.equals("2")) {
                sql = "SELECT name FROM groups WHERE id=" + def;
                ResultSet rs3 = stmt1.executeQuery(sql);
                if (rs3.next()) {
                    emps = "<a class='emps' href='group.jsp?id=" + def + "'>" + rs3.getString(1) + "</span>";
                }
                rs3.close();
            } else if (dopusk_type.equals("3")) {
                sql = "SELECT unit FROM units WHERE unit_id=" + def;
                ResultSet rs3 = stmt1.executeQuery(sql);
                if (rs3.next()) {
                    emps = "<a class='emps' href='empListComp.jsp?uid=" + def + "'>" + rs3.getString(1) + "</span>";
                }
                rs3.close();
            }

            String copies = "";
            if (isOwners && isNotCopy) {
                String sql6 = "SELECT properTime(created) AS created, id FROM files WHERE id <> copy AND copy=" + id;
                ResultSet rs6 = stmt1.executeQuery(sql6);
                while (rs6.next()) {
                    copies += "<a href='fileLoader.jsp?id=" + rs6.getString(2) + "'>" + rs6.getString(1) + "</a> ";
                }

                rs6.close();
            }

            boolean isVisant = false;
            boolean isVisanted = false;

            String visa_span = "<span class=\"aui-icon aui-icon-closethick\"></span>";

            sql = "SELECT owner, answer, data, get_fio(owner) AS fio FROM visas WHERE ver_id=" + V;
            ResultSet rs8 = stmt1.executeQuery(sql);
            if (step >= 3) {
                visa_list = "Лист согласования пуст!";
            }
            while (rs8.next()) {
                hasVisas = true;
                if (rs8.getString("owner").equals(o)) {
                    isVisant = true;
                    if (rs8.getInt("answer") == 1) {
                        isVisanted = true;
                    }
                }

                if (rs8.getInt("answer") == 1) {
                    visa_span = "<span class=\"aui-icon aui-icon-check \"></span>";
                } else {
                    visa_span = "<span class=\"aui-icon aui-icon-closethick \"></span>";
                }
                if (rs8.isFirst()) {
                    visa_list = "<table class='visaTable'>";
                }
                visa_list += "<tr><td class='visa_fio'>" + rs8.getString("fio") + "</td><td class='visa_ansver'>" + visa_span + "</td>";
                if (rs8.isLast()) {
                    visa_list += "</table>";
                }

            }

            if (isOwners || rs.getBoolean("permitted") || isSecurity || isDocEditor) {
                list = "<table><tbody>";
                if (status == 2) {
                    list += "<tr><td class='first'></td><td class='second'>Заблокировано модератором</td></tr>";
                }
                if (changed) {
                    list += "<tr><td class='first'>Внимание!</td><td class='second alert'>Запрашиваемая вами версия файла доступна, но была изменена!<br>"
                            + "Актуальная версия находится <a href='fileLoader.jsp?id=" + id + "'>здесь</a></td></tr>";
                }
                if (isLate) {
                    list += "<tr><td class='first'>Внимание!</td><td class='second alert'>Запрашиваемой вами версии файла не существует!"
                            + "<br>Вместо нее загружена актуальная версия файла</td></tr>";
                }
                if (isOwners) {
                    if (isNotCopy) {
                        if (step < 6) {
                            rightMenu += "<a href='javascript:;' id='newVer'>Новая версия</a><a href='javascript:;' id='dropFile'>Удалить файл</a>";
                        }
                    } else {
                        list += "<tr><td class='first'></td><td class='second buts'>Это копия! см. <a href='fileLoader.jsp?id=" + rs.getString("copy") + "'>Оригинал документа</a></td></tr>";
                        rightMenu += "<a href='javascript:if(confirm(\"Удалить копию документа?\")) location=\"dropCopy?id=" + id + "&r=1&c=" + rs.getString("superior") + own + "\";' id='dropCopy'>Удалить копию</a>";
                    }
                }
                if (step == 6 || step == 7) {
                    list += "<tr><td class='first'></td><td class='second signed'><a href='javascript:;' id='step_6'>УТВЕРЖДЕН</a></td></tr>";
                }
                list += "<tr><td class='first'>Файл</td><td class='second " + type + " fil'>" + rs.getString(3) + " (" + rs.getString(5) + ")</td></tr>";
                list += "<tr><td class='first'>Создан</td><td class='second create'>" + rs.getString("create") + "</td></tr>";
                list += "<tr><td class='first'>Версия</td><td class='second create'>" + rs.getString("versCreated") + "</td></tr>";
                list += "<tr><td class='first'>Действия</td><td class='second'><div id=\"buttonsDons\"></div></td></tr>";
                if (!isOwner && step == 5 && !isSigned) {
                    list += "<tr><td class='first'></td><td class='second visas'><a href='makeSign?id=" + id + "&v=" + V + "'>УТВЕРДИТЬ</a><a href='makeNoSign?id=" + id + "&v=" + V + "'>НА ДОРАБОТКУ</a></td></tr>";
                } else if (step >= 3 && isVisant && isVisanted) {
                    list += "<tr><td class='first'></td><td class='second'>Документ визирован</td></tr>";
                } else if (step >= 3 && isVisant && !isVisanted) {
                    list += "<tr><td class='first'></td><td class='second'>Документ отправлен на доработку</td></tr>";
                } else if (!isOwner && (step == 3 || step == 4)) {
                    list += "<tr><td class='first'></td><td class='second visas'><a href='makeVisa?id=" + id + "&v=" + V + "'>ВИЗИРОВАТЬ</a><a href='makeNoVisa?id=" + id + "&v=" + V + "'>НА ДОРАБОТКУ</a></td></tr>";
                }
                list += img;
                list += "<tr><td class='first'>Описание</td><td class='second'>" + rs.getString(6) + "</td></tr>";
                list += "<tr><td class='first'>Владелец</td><td class='second fio'>";
                if (rs.getInt(2) > 0) {
                    list += "<img src='IMG?id=" + rs.getString(2) + "' class='exxo-shadow1'>";
                }
                list += "<a href='empCard.jsp?id=" + rs.getString(2) + "'>" + Fio + "</a></td></tr>";
                if (rs.getInt(2) < 0 && rs.getBoolean("has_author")) {
                    list += "<tr><td class='first'>Автор документа</td><td class='second fio'><a href='empCard.jsp?id=" + rs.getString("author") + "'>" + rs.getString("author_fio") + "</a></td></tr>";
                }
                String catOwner = "";
                if (!isOwner) {
                    catOwner = "owner=" + rs.getString(2) + "&";
                }
                list += "<tr><td class='first'>Каталог</td><td class='second'><a href=\"docClassic.jsp?" + catOwner + "id=" + rs.getString(10) + "\">" + PreCat + rs.getString(11) + "</a></td></tr>";
                String dopuskHref = "javascript:;";
                if (isOwners) {
                    dopuskHref = "fileProp.jsp?id=" + id;
                }
                list += "<tr><td class='first'><a href=\"" + dopuskHref + "\">Допуск</a></td><td class='second dop'><img src='" + BASE.Props.dopusks[rs.getInt("dopusk_type")] + "'>" + emps + "</td></tr>";
                list += "<tr><td class='first'><a href='docTags?el=" + id + "&&s=files'>Теги базы знаний</a></td><td class='second tags'>" + tags + "</td></tr>";
                if (isOwner) {
                    list += "<tr><td class='first'>Версии файла</td><td class='second vers'>" + vers + "</td></tr>";
                }
                list += "<tr><td class='first'><a href='javascript:;' id='loads'>Скачали</a></td><td class='second loads'>" + ver_count + "</td></tr>";
                String statusStr = "Не опубликована";
                if (status == 1) {
                    statusStr = "Опубликована";
                }
                String stepStr = "Документ в работе";
                if (step == 2) {
                    stepStr = "Документ готов";
                } else if (step == 3 || step == 4) {
                    stepStr = "Документ направлен на согласование";
                } else if (step == 5) {
                    stepStr = "Документ направлен на утверждение";
                } else if (step == 6) {
                    stepStr = "Документ утвержден";
                } else if (step == 7) {
                    stepStr = "Документ утвержден. Копия направлена во Внутренние";
                } else if (step == 8) {
                    stepStr = "Документ утвержден. Копия направлена в Исходящие";
                } else if (step == 9) {
                    stepStr = "Документ готов.  Копия направлена в Исходящие";
                } else if (step == 10) {
                    stepStr = "Документ готов.  Копия направлена во Внутренние";
                }
                if (isOwners) {
                    list += "<tr><td class='first'>Статус версии</td><td class='second'>" + statusStr + "</td></tr>";
                }
                list += "<tr><td class='first'>Этап подготовки</td><td class='second'>" + stepStr + "</td></tr>";
                if (status == 0 && isOwners && !changed) {
                    rightMenu += "<a href='docPublish?v=" + ver_id + "&id=" + id + "' id='public'>Опубликовать</a>";
                }
                if (isOwners) {
                    rightMenu += "<a href='sendDoc?id=" + id + "&v=" + V + "' id='send'>Отправить</a>";//а что с компанией, можно ли отправить не редактору
                }
                if (isOwner && (step == 2 || step == 3 || step == 4)) {
                    rightMenu += "<a href='visaDoc?id=" + id + "&v=" + V + "' id='send'>Отправить на согласование</a>";
                }
                if (isOwner && (step == 2 || step == 3 || step == 4) && isSignable) {
                    rightMenu += "<a href='signDoc?id=" + id + "&v=" + V + "' id='send'>Отправить на утверждение</a>";
                }
//if(isOwner&&step==2) rightMenu+="<a href='javascript:if(confirm(\"Отправить на регистрацию?\")){location=\"makeReg?v="+V+"&id="+id+"\"};' id='send'>Зарегистрировать</a>";
                if (isOwners && isNotCopy && step < 2) {
                    rightMenu += "<a href='javascript:if(confirm(\"Закончить работу над документом?\")){location=\"finDocWork?id=" + id + "\";};' id='finish'>Закончить разработку</a>";
                }
                if (isOwners && isNotCopy) {
                    rightMenu += "<a href='docCopy?id=" + id + "' id='copy'>Создать копию документа</a>";
                }
                if (isOwners && isNotCopy && !copies.equals("")) {
                    list += "<tr><td class='first'>Копии документа</td><td class='second buts'>" + copies + "</td></tr>";
                }
                if (!isOwner && status == 2 && isSecurity) {
                    rightMenu += "<a href='docUnblock?v=" + ver_id + "&id=" + id + "' id='copy'>Разблокировать</a>";
                } else if (!isOwner && status != 2 && isSecurity) {
                    rightMenu += "<a href='docBlock?v=" + ver_id + "&id=" + id + "' id='copy'>Заблокировать</a>";
                }
                if (!discussions.equals("")) {
                    list += "<tr><td class='first'>Обсуждения</td><td class='second buts'>" + discussions + "</td></tr>";
                }
                if (step >= 3 || hasVisas) {
                    list += "<tr><td class='first'>ВИЗЫ</td><td class='second buts'>" + visa_list + "</td></tr>";
                }
                list += "</tbody></table>";
                if (isOwner && isNotCopy && (step == 6 || (step >= 2 && step < 5 && isHead))) {
                    rightMenu += "<a href='javascript:if(confirm(\"Вы хотите зарегистрировать копию во внутренних документах компании?\")){location=\"innerDoc?id=" + id + "\";};' id='innerDoc'>Копия во Внутренние</a>";
                }
                if (isOwner && isNotCopy && (step == 6 || (step >= 2 && step < 5))) {
                    rightMenu += "<a href='javascript:if(confirm(\"Вы хотите зарегистрировать копию в исходящих документах компании?\")){location=\"innerDoc?id=" + id + "&owner=-102\";};' id='innerDoc'>Копия в Исходящие</a>";
                }
            }
        } else {
            if (isVer) {
                redirect = true;
            }
        }
        if (isImg) {
            String albumsOwner = owner;
            if (owner.contains("-")) {
                albumsOwner = "-100";
            }
            sql = "SELECT id, name FROM gallaries WHERE file=0 AND owner=" + albumsOwner + " ORDER BY name";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                albums += "<div class='item cat' id='" + rs.getString("id") + "'>";
                albums += "<div class=\"exxo-album-cover\" style='background-image: url(\"cover?id=" + rs.getString("id") + "&amp;t=" + System.currentTimeMillis() + "\");'></div>";
                albums += "<span>" + rs.getString("name") + "</span>";
                albums += "</div>";
            }
        }
        rs.close();
        stmt.close();
        stmt1.close();
        con.close();
        return list;

    }

}
//Чем V отличается от ver_id
