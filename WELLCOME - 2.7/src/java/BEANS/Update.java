package BEANS;

import BASE.VER;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ManagedBean
@ApplicationScoped
public class Update implements Serializable {

    private int flag = 0;

    public void update() throws ClassNotFoundException, SQLException {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        Connection con = startSQL();
        Statement stmt = con.createStatement();
        int ver = 0;
        if (flag == 0) {
            try {
                String sql = "SELECT ver FROM ver WHERE prog='EXXO'";
                ResultSet rs = stmt.executeQuery(sql);
                flag = 2;
                if (rs.next()) {
                    ver = rs.getInt(1);
                    if (ver >= VER.newVer) {
                        flag = 1;
                    } else {
                        flag = 2;
                    }
                }
            } catch (SQLException ex) {
                flag = 1;
            }
        }
        if (flag == 2) {
            flag = 1;
            if (ver < 3) {
                con.setAutoCommit(false);

                String sql = "DROP SEQUENCE IF EXISTS events_seq  CASCADE;";
                stmt.executeUpdate(sql);

                sql = "CREATE SEQUENCE events_seq;";
                stmt.executeUpdate(sql);

                sql = "DROP TABLE IF EXISTS events CASCADE";
                stmt.executeUpdate(sql);

                sql = "CREATE TABLE events ( "
                        + "id VARCHAR NOT NULL, "
                        + "user_id INT NOT NULL REFERENCES employee(id) ON DELETE CASCADE , "
                        + "day date NOT NULL, "
                        + "hours int NOT NULL, "
                        + "minutes int NOT NULL, "
                        + "long int NOT NULL, "
                        + "name text DEFAULT '', "
                        + "descr text DEFAULT '', "
                        + "type int DEFAULT 0, "
                        + "PRIMARY KEY (id) );";
                stmt.executeUpdate(sql);

                sql = "DROP INDEX IF EXISTS events_day_idx;";
                stmt.executeUpdate(sql);

                sql = "DROP INDEX IF EXISTS events_user_idx;";
                stmt.executeUpdate(sql);

                sql = "CREATE INDEX events_day_idx ON events (day);";
                stmt.executeUpdate(sql);

                sql = "CREATE INDEX events_user_idx ON events (user_id);";
                stmt.executeUpdate(sql);

                sql = "DROP SEQUENCE IF EXISTS eventsres_seq  CASCADE;";
                stmt.executeUpdate(sql);
                sql = "CREATE SEQUENCE eventsres_seq;";
                stmt.executeUpdate(sql);

                sql = "DROP TABLE IF EXISTS eventsres CASCADE";
                stmt.executeUpdate(sql);

                sql = "CREATE TABLE eventsres ( "
                        + "id VARCHAR NOT NULL, "
                        + "id_res int, "
                        + "user_id INT NOT NULL REFERENCES employee(id) ON DELETE CASCADE , "
                        + "day date NOT NULL, "
                        + "hours int NOT NULL, "
                        + "minutes int NOT NULL, "
                        + "long int NOT NULL, "
                        + "name text DEFAULT '', "
                        + "descr text DEFAULT '', "
                        + "fio text NOT NULL, "
                        + "PRIMARY KEY (id) );";
                stmt.executeUpdate(sql);

                sql = "DROP INDEX IF EXISTS eventsres_day_idx";
                stmt.executeUpdate(sql);

                sql = "DROP INDEX IF EXISTS eventsres_user_idx";
                stmt.executeUpdate(sql);

                sql = "DROP INDEX IF EXISTS eventsres_res_idx;";
                stmt.executeUpdate(sql);

                sql = "CREATE INDEX eventsres_day_idx ON eventsres (day);";
                stmt.executeUpdate(sql);

                sql = "CREATE INDEX eventsres_user_idx ON eventsres (user_id);";
                stmt.executeUpdate(sql);

                sql = "CREATE INDEX eventsres_res_idx ON eventsres (id_res);";
                stmt.executeUpdate(sql);

                sql = "UPDATE ver SET ver=3 WHERE prog = 'EXXO'";
                stmt.executeUpdate(sql);

                con.commit();

            }
            if (ver < 4) {
                con.setAutoCommit(false);
                String sql = "ALTER TABLE events ADD outside boolean DEFAULT 'f', ADD important boolean DEFAULT 'f', ADD nophone boolean DEFAULT 'f'";
                stmt.executeUpdate(sql);

                sql = "DROP TABLE IF EXISTS ips CASCADE";
                stmt.executeUpdate(sql);

                sql = "CREATE TABLE ips (id int,t timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,ip varchar)";
                stmt.executeUpdate(sql);

                sql = "DROP INDEX IF EXISTS ips_id_idx;";
                stmt.executeUpdate(sql);

                sql = "CREATE INDEX ips_id_idx ON ips (id)";
                stmt.executeUpdate(sql);

                sql = "DROP INDEX IF EXISTS ips_t_idx;";
                stmt.executeUpdate(sql);

                sql = "CREATE INDEX ips_t_idx ON ips (t)";
                stmt.executeUpdate(sql);

                sql = "UPDATE ver SET ver=4 WHERE prog = 'EXXO'";
                stmt.executeUpdate(sql);

                con.commit();
            }
            if (ver < 5) {
                con.setAutoCommit(false);

                String sql = "DROP TABLE IF EXISTS docPoket CASCADE";
                stmt.executeUpdate(sql);

                sql = "CREATE TABLE docPoket ("
                        + "id int not null REFERENCES employee(id) ON DELETE CASCADE, "
                        + "doc int not null REFERENCES files(id) ON DELETE CASCADE, "
                        + "ser varchar(10) not NULL DEFAULT 'blog', "
                        + "PRIMARY KEY (id, doc, ser))";
                stmt.executeUpdate(sql);

                sql = "CREATE OR REPLACE FUNCTION docBlog (o Int, i Int, d Int) RETURNS varchar AS "
                        + "$$ "
                        + "DECLARE "
                        + "list varchar; "
                        + "r RECORD; "
                        + "r1 RECORD; "
                        + "BEGIN "
                        + "list:=''; "
                        + "CASE d "
                        + "WHEN 1 THEN "
                        + "INSERT INTO docPoket VALUES(o,i,'blog'); "
                        + "WHEN 2 THEN "
                        + "DELETE FROM docPoket WHERE id=o AND doc=i AND ser='blog'; "
                        + "ELSE NULL; "
                        + "END CASE; "
                        + "FOR r IN SELECT doc FROM docPoket WHERE id=o AND ser='blog' "
                        + "LOOP "
                        + "SELECT name, type INTO r1 FROM files WHERE id=r.doc; "
                        + "list:=list||'<div class=\"listDoc\" id=\"d'||r.doc||'\">'||r1.name||'</div>'; "
                        + "END LOOP;  "
                        + "IF list='' THEN "
                        + "list:='<div id=\"empty\">Список пуст</div>'; "
                        + "END IF; "
                        + "RETURN list; "
                        + "EXCEPTION WHEN unique_violation THEN "
                        + "FOR r IN SELECT doc FROM docPoket WHERE id=o AND ser='ser' "
                        + "LOOP "
                        + "SELECT name, type INTO r1 FROM files WHERE id=r.doc; "
                        + "list:=list||'<div class=\"listDoc\" id=\"d'||r.doc||'\">'||r1.name||'</div>'; "
                        + "END LOOP; "
                        + "IF list='' THEN "
                        + "list:='<div id=\"empty\">Список пуст</div>'; "
                        + "END IF; "
                        + "RETURN list; "
                        + "END; "
                        + "$$ "
                        + "LANGUAGE 'plpgsql'";
                stmt.executeUpdate(sql);

                sql = "DROP TABLE IF EXISTS docsBlog CASCADE";
                stmt.executeUpdate(sql);

                sql = "CREATE TABLE docsBlog ("
                        + "id int not null  REFERENCES bloges(id) ON DELETE CASCADE, "
                        + "owner int not null REFERENCES employee(id) ON DELETE CASCADE, "
                        + "doc int not null REFERENCES files(id) ON DELETE CASCADE, "
                        + "PRIMARY KEY (id, owner,doc))";
                stmt.executeUpdate(sql);

                sql = "CREATE INDEX docsBlog_id_idx ON docsBlog (id)";
                stmt.executeUpdate(sql);
                sql = "CREATE INDEX docsBlog_owber_idx ON docsBlog(owner)";
                stmt.executeUpdate(sql);
                sql = "CREATE INDEX docsBlog_doc_idx ON docsBlog(doc)";
                stmt.executeUpdate(sql);

                sql = "CREATE OR REPLACE FUNCTION addtoblog(o integer, s integer) RETURNS void AS "
                        + "$BODY$ "
                        + "DECLARE "
                        + "r record; "
                        + "r1 record; "
                        + "i Int; "
                        + "BEGIN "
                        + "SELECT * INTO r FROM blogesTMP WHERE owner=o; "
                        + "i=(SELECT nextval('bloges_seq')); "
                        + "IF NOT FOUND THEN NULL; "
                        + "ELSE INSERT INTO bloges (id, owner, name, text, dopusk_type, dopusk, bus, ref, refdoc, refVer, status) VALUES (i, r.owner, r.name, r.text, r.dopusk_type, r.dopusk, r.bus, r.ref, r.refdoc, r.refVer, r.status);"
                        + "DELETE from blogesTMP WHERE owner=o;"
                        + "INSERT INTO blog_structure VALUES(i,o,s);"
                        + "FOR r1 IN SELECT * FROM docPoket WHERE id=o AND ser='blog' "
                        + "LOOP "
                        + "INSERT INTO docsBlog (id, owner, doc) VALUES (i, r1.id, r1.doc);"
                        + "END LOOP;"
                        + "DELETE FROM docPoket WHERE id=o AND ser='blog';"
                        + "END IF;"
                        + "END;"
                        + "$BODY$ "
                        + "LANGUAGE plpgsql";
                stmt.executeUpdate(sql);

                sql = "CREATE OR REPLACE FUNCTION ReStructure() RETURNS void AS "
                        + "$$ "
                        + "DECLARE "
                        + "r record;"
                        + "BEGIN "
                        + "FOR r IN SELECT page_id, structure FROM structure "
                        + "LOOP "
                        + "UPDATE structure SET structure='<table id=\"grid\" class=\"grid\">'||r.structure||'</table>' WHERE page_id=r.page_id;"
                        + "END LOOP;"
                        + "END;"
                        + "$$ "
                        + "LANGUAGE plpgsql";
                stmt.executeUpdate(sql);

                sql = "SELECT ReStructure();";
                stmt.execute(sql);
                sql = "DROP FUNCTION ReStructure()";
                stmt.executeUpdate(sql);

                sql = "ALTER TABLE events ADD UNIQUE (id)";
                stmt.executeUpdate(sql);

                sql = "DROP TABLE IF EXISTS docsEvent CASCADE";
                stmt.executeUpdate(sql);
                sql = "CREATE TABLE docsEvent ("
                        + "id varchar not null  REFERENCES events(id) ON DELETE CASCADE, "
                        + "owner int not null REFERENCES employee(id) ON DELETE CASCADE, "
                        + "doc int not null REFERENCES files(id) ON DELETE CASCADE, "
                        + "PRIMARY KEY (id, owner,doc))";
                stmt.executeUpdate(sql);

                sql = "CREATE INDEX docsEvent_id_idx ON docsEvent (id)";
                stmt.executeUpdate(sql);

                sql = "CREATE INDEX docsEvent_owber_idx ON docsEvent(owner)";
                stmt.executeUpdate(sql);

                sql = "CREATE INDEX docsEvent_doc_idx ON docsEvent(doc)";
                stmt.executeUpdate(sql);

                sql = "CREATE OR REPLACE FUNCTION docEvent (o Int, i Int, d Int, e varchar) RETURNS varchar AS $$"
                        + "DECLARE "
                        + "list varchar; "
                        + "r RECORD; "
                        + "r1 RECORD; "
                        + "BEGIN "
                        + "list:=''; "
                        + "CASE d "
                        + "WHEN 1 THEN "
                        + "INSERT INTO docsEvent VALUES(e,o,i); "
                        + "WHEN 2 THEN "
                        + "DELETE FROM docsEvent WHERE id=e AND doc=i AND owner=o; "
                        + "ELSE NULL; "
                        + "END CASE; "
                        + "FOR r IN SELECT doc FROM docsEvent WHERE id=e "
                        + "LOOP "
                        + "SELECT name, type INTO r1 FROM files WHERE id=r.doc; "
                        + "list:=list||'<div class=\"listDoc\" id=\"d'||r.doc||'\">'||r1.name||'</div>'; "
                        + "END LOOP; "
                        + "IF list='' THEN "
                        + "list:='<div id=\"empty\">Список пуст</div>'; "
                        + "END IF; "
                        + "RETURN list; "
                        + "EXCEPTION WHEN unique_violation THEN "
                        + "FOR r IN SELECT doc FROM docsEvent WHERE id=e "
                        + "LOOP "
                        + "SELECT name, type INTO r1 FROM files WHERE id=r.doc; "
                        + "list:=list||'<div class=\"listDoc\" id=\"d'||r.doc||'\">'||r1.name||'</div>'; "
                        + "END LOOP; "
                        + "IF list='' THEN "
                        + "list:='<div id=\"empty\">Список пуст</div>'; "
                        + "END IF; "
                        + "RETURN list; "
                        + "END; "
                        + "$$ "
                        + "LANGUAGE 'plpgsql'";
                stmt.executeUpdate(sql);

                sql = "DROP TABLE IF EXISTS calCimpare CASCADE";
                stmt.executeUpdate(sql);
                sql = "CREATE TABLE calCimpare ("
                        + "owner int not null REFERENCES employee(id) ON DELETE CASCADE, "
                        + "id int not null REFERENCES employee(id) ON DELETE CASCADE, "
                        + "PRIMARY KEY (owner, id))";
                stmt.executeUpdate(sql);

                sql = "INSERT INTO css1 (page) VALUES ('blogDopusk.jsp'),('blogDopPhoto.jsp'),"
                        + "('blogDopComp.jsp'),('blogDopCompany.jsp'),('addDopusk.jsp'),('addDopPhoto.jsp'),('addDopCompany.jsp'),('addDopComp.jsp');";
                stmt.executeUpdate(sql);
                sql = "UPDATE ver SET ver=5 WHERE prog = 'EXXO'";
                stmt.executeUpdate(sql);
                con.commit();

            }
            if (ver < 6) {
                con.setAutoCommit(false);
                String menu = "";
                String sql = "SELECT menu FROM menu";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    menu = rs.getString(1);
                }
                rs.close();

                menu = menu.replaceAll("aui", "yui3");
                sql = "UPDATE menu SET menu='" + menu + "'";
                stmt.executeUpdate(sql);
                sql = "UPDATE ver SET ver=6 WHERE prog = 'EXXO'";
                stmt.executeUpdate(sql);
                con.commit();
            }
            if (ver < 7) {
                con.setAutoCommit(false);
                String sql = "CREATE OR REPLACE FUNCTION docblog(o integer, i integer, d integer) "
                        + "  RETURNS character varying AS "
                        + "$BODY$ "
                        + "          DECLARE "
                        + "          list varchar; "
                        + "          r RECORD; "
                        + "		  r1 RECORD; "
                        + "		  BEGIN "
                        + "		  list:=''; "
                        + "		  CASE d "
                        + "		  WHEN 1 THEN  "
                        + "		  INSERT INTO docPoket VALUES(o,i,'blog'); "
                        + "		  WHEN 2 THEN  "
                        + "		  DELETE FROM docPoket WHERE id=o AND doc=i AND ser='blog'; "
                        + "		  ELSE NULL; "
                        + "		  END CASE; "
                        + "          FOR r IN SELECT doc FROM docPoket WHERE id=o AND ser='blog' "
                        + "		  LOOP "
                        + "		  SELECT name, type INTO r1 FROM files WHERE id=r.doc; "
                        + "		  list:=list||'<div class=\"listDoc\" id=\"d'||r.doc||'\">'||r1.name||'</div>'; "
                        + "		  END LOOP; "
                        + "		  IF list='' THEN  "
                        + "		  list:='<div id=\"empty\">Список пуст</div>'; "
                        + "		  END IF; "
                        + "		  RETURN list; "
                        + "		  EXCEPTION WHEN unique_violation THEN "
                        + "		  FOR r IN SELECT doc FROM docPoket WHERE id=o AND ser='blog' "
                        + "		  LOOP "
                        + "		  SELECT name, type INTO r1 FROM files WHERE id=r.doc; "
                        + "          list:=list||'<div class=\"listDoc\" id=\"d'||r.doc||'\">'||r1.name||'</div>'; "
                        + "		  END LOOP; "
                        + "		  IF list='' THEN  "
                        + "		  list:='<div id=\"empty\">Список пуст</div>'; "
                        + "		  END IF; "
                        + "		  RETURN list; "
                        + "		  END; "
                        + "		  $BODY$ "
                        + "  LANGUAGE plpgsql";
                stmt.executeUpdate(sql);

                sql = "DELETE FROM employeeTMP";
                stmt.executeUpdate(sql);

                sql = "ALTER TABLE employeeTMP  "
                        + "  ADD photo_type VARCHAR NOT NULL DEFAULT ''";
                stmt.executeUpdate(sql);

                sql = "UPDATE ver SET ver=7 WHERE prog = 'EXXO'";
                stmt.executeUpdate(sql);
                con.commit();
            }
            if (ver < 8) {
                con.setAutoCommit(false);
                String sql = "DROP FUNCTION addtoblog(integer, integer);";
                stmt.executeUpdate(sql);
                sql = "CREATE OR REPLACE FUNCTION addtoblog(o integer, s integer) "
                        + "  RETURNS text[] AS "
                        + "$BODY$ "
                        + "DECLARE "
                        + "r record; "
                        + "r1 record; "
                        + "r2 record; "
                        + "i Int; "
                        + "t text; "
                        + "x int; "
                        + "mail varchar; "
                        + "res text[3]; "
                        + "BEGIN "
                        + "t:=''; "
                        + "SELECT * INTO r FROM blogesTMP WHERE owner=o; "
                        + "i:=(SELECT nextval('bloges_seq')); "
                        + "IF NOT FOUND THEN NULL; "
                        + "ELSE INSERT INTO bloges (id, owner, name, text, dopusk_type, dopusk, bus, ref, refdoc, refVer, status) VALUES (i, r.owner, r.name, r.text, r.dopusk_type, r.dopusk, r.bus, r.ref, r.refdoc, r.refVer, r.status); "
                        + "DELETE from blogesTMP WHERE owner=o; "
                        + "INSERT INTO blog_structure VALUES(i,o,s); "
                        + "FOR r1 IN SELECT * FROM docPoket WHERE id=o AND ser='blog' "
                        + "LOOP "
                        + "INSERT INTO docsBlog (id, owner, doc) VALUES (i, r1.id, r1.doc); "
                        + "END LOOP; "
                        + "DELETE FROM docPoket WHERE id=o AND ser='blog'; "
                        + "END IF; "
                        + "IF r.dopusk_type = 4 THEN "
                        + "FOREACH x IN ARRAY r.dopusk "
                        + "LOOP		   "
                        + "mail:=(SELECT email FROM employee WHERE global_id=x); "
                        + "IF mail<>'' THEN "
                        + "t=t||mail||','; "
                        + "END IF; "
                        + "END LOOP; "
                        + "END IF; "
                        + "res=ARRAY[t, r.name, r.text]; "
                        + "RETURN res; "
                        + "END; "
                        + "$BODY$ "
                        + "  LANGUAGE plpgsql";
                stmt.executeUpdate(sql);
                sql = "UPDATE ver SET ver=8 WHERE prog = 'EXXO'";
                stmt.executeUpdate(sql);
                con.commit();
            }
            if (ver < 9) {
                con.setAutoCommit(false);
                String sql = "INSERT INTO css1 (page) VALUES ('blogInsert.jsp'),('editor.jsp'),"
                        + "('unitEditor.jsp'),('allDayR.xhtml'),('eventsRAdd.xhtml'),('eventR.xhtml'),('allDay.xhtml'),('calendarCompare.xhtml'),"
                        + "('five.xhtml'),('month.xhtml'),('eventsAdd.xhtml'),('event.xhtml'),('addFile.xhtml'),('addPic.xhtml')";
                stmt.executeUpdate(sql);
                sql = "UPDATE ver SET ver=9 WHERE prog = 'EXXO'";
                stmt.executeUpdate(sql);
                con.commit();

            }
            if (ver < 10) {
                con.setAutoCommit(false);
                String sql = "CREATE TABLE roles ( "
                        + "name varchar not null, "
                        + "description text default '', "
                        + "inne boolean default 'f' "
                        + ")";
                stmt.executeUpdate(sql);
                sql = "INSERT INTO roles VALUES ('portal_admin','Пока не используется','t'),('portal_user','Позволяет пользователю открывать страницы портала','t')";
                stmt.executeUpdate(sql);
                sql = "ALTER TABLE roles ADD UNIQUE (name)";
                stmt.executeUpdate(sql);
                sql = "ALTER TABLE user_roles ADD CONSTRAINT user_roles_role_fkey FOREIGN KEY (role) REFERENCES roles (name) ON DELETE CASCADE;";
                stmt.executeUpdate(sql);
                sql = "INSERT INTO roles VALUES ('boss','допущен почти ко всем функциям портала. Может назначать роли сотрудникам. Лишен надзорных функций в фотоальбомах, документообороте, сообщениях (но может, при желании добавить их, присвоив себе соответствующие роли). Выполняет контрольные функции в сервисе «Стандартные бизнес-процессы»','t'),('master','допущен к редактированию страниц портала, меню, таблиц стилей, добавлению и удалению страниц, изменению логотипа и слогана, редактированию страниц подразделений. Осуществляет модерацию сервиса «Доска объявлений»','t'),('staff','допущен ко всем операциям с корпоративным справочником, кроме изменения ролей сотрудников и редактирования проектов и рабочих групп','t'),('photo_editor','допущен к редактированию фотоальбомов компании. Может блокировать фотографии других пользователей, выполняя надзорные функции','t'),('resources_editor','допущен к редактированию сервиса «Переговорные». Может изменять записи других сотрудников в журналах использования переговорных','t'),('news_editor','допущен к добавлению, редактированию и удалению новостей компании','t'),('business_process_editor','добавляет, удаляет и редактирует бизнес-процессы','t'),('business_process_inspector','выполняет контроль исполнения операций в сервисе «Стандартные бизнес-процессы»','t'),('tags_editor','формирует структуру тегов Базы знаний','t'),('tags_expert','не используется','t'),('unit_editor','редактирует страницу подразделения, сотрудником которого он является','t'),('projects_editor','формирует и редактирует списки проектов и рабочих групп','t'),('files_security','осуществляет контрольные функции в сервисе «Документооборот». Может блокировать документы других сотрудников. Поскольку имеет доступ ко всем документам всех сотрудников, следует использовать данную роль осторожно','t'),('general_files_editor','осуществляет операции с документами в разделе «общие компании»','t'),('in_files_editor','осуществляет операции с документами в разделе «входящие компании»','t'),('out_files_editor',' осуществляет операции с документами в разделе «исходящие компании»','t'),('inner_files_editor','осуществляет операции с документами в разделе «внутренние компании»','t'),('security','осуществляет контрольные функции в сервисах «Документооборот», «Фотоальбомы», «Собщения». Может блокировать документы, сообщения, фотографии других сотрудников. Поскольку имеет доступ ко всем документам и сообщениям всех сотрудников, следует использовать данную роль осторожно','t'),('blog_security','осуществляет контрольные функции в сервисе «Сообщения». Может блокировать сообщения других сотрудников. Поскольку имеет доступ ко всем сообщениям всех сотрудников, следует использовать данную роль осторожно','t'),('roles_editor','редактирует список ролей','t'),('roles_staff','назначает роли сотрудникам','t'),('vote_editor','добавляет, редактирует опросы в сервисе «Опросы и голосования». Имеет доступ к результатам опросов','t')";
                stmt.executeUpdate(sql);
                sql = "CREATE OR REPLACE FUNCTION add_role_to(i integer, role varchar) RETURNS void AS "
                        + "$BODY$ "
                        + " BEGIN "
                        + " INSERT INTO user_roles VALUES ((SELECT login FROM users WHERE id = i), role); "
                        + " EXCEPTION WHEN unique_violation THEN NULL; "
                        + " END "
                        + " $BODY$ "
                        + " LANGUAGE plpgsql";
                stmt.executeUpdate(sql);
                sql = "UPDATE ver SET ver=10 WHERE prog = 'EXXO'";
                stmt.executeUpdate(sql);
                con.commit();
            }
            if (ver < 11) {
                con.setAutoCommit(false);
                String sql = "SELECT users.login, role FROM employee, users WHERE employee.id = users.id";
                ResultSet rs = stmt.executeQuery(sql);
                StringBuilder sb = new StringBuilder();
                while (rs.next()) {
                    char[] ar = rs.getString(2).toCharArray();
                    String login = rs.getString(1);
                    for (int i = 0; i < ar.length; i++) {
                        sb.append("('");
                        sb.append(login);
                        sb.append("','");
                        switch (ar[i]) {
                            case 'a':
                                sb.append("boss");
                                break;
                            case 'b':
                                sb.append("master");
                                break;
                            case 'c':
                                sb.append("staff");
                                break;
                            case 'd':
                                sb.append("photo_editor");
                                break;
                            case 'f':
                                sb.append("resources_editor");
                                break;
                            case 'g':
                                sb.append("news_editor");
                                break;
                            case 'h':
                                sb.append("business_process_editor");
                                break;
                            case 'i':
                                sb.append("business_process_inspector");
                                break;
                            case 'k':
                                sb.append("tags_editor");
                                break;
                            case 'l':
                                sb.append("tags_expert");
                                break;
                            case 'm':
                                sb.append("unit_editor");
                                break;
                            case 'n':
                                sb.append("projects_editor");
                                break;
                            case 'e':
                                sb.append("files_security");
                                break;
                            case 'o':
                                sb.append("general_files_editor");
                                break;
                            case 'p':
                                sb.append("in_files_editor");
                                break;
                            case 'q':
                                sb.append("out_files_editor");
                                break;
                            case 'r':
                                sb.append("inner_files_editor");
                                break;
                            case 's':
                                sb.append("security");
                                break;
                            case 't':
                                sb.append("blog_security");
                                break;
                            case 'j':
                                sb.append("vote_editor");
                                break;
                        }
                        sb.append("'),");
                    }
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sql = "INSERT INTO user_roles VALUES " + sb.toString();
                stmt.executeUpdate(sql);

                sql = "UPDATE employee SET role = ''";
                stmt.executeUpdate(sql);
                sql = "UPDATE ver SET ver=11 WHERE prog = 'EXXO'";
                stmt.executeUpdate(sql);
                con.commit();
            }
            if (ver < 11) {
                con.setAutoCommit(false);
                String sql = "CREATE SEQUENCE rating_seq;";
                stmt.executeUpdate(sql);

                sql = "CREATE TABLE rating ("
                        + "id int NOT NULL DEFAULT NEXTVAL('rating_seq'),"
                        + "name varchar NOT NULL,"
                        + "description text,"
                        + "type varchar NOT NULL,"
                        + "UNIQUE(id))";
                stmt.executeUpdate(sql);

                sql = "CREATE TABLE employeeVoice ("
                        + "target_id int NOT NULL REFERENCES employee(id) ON DELETE CASCADE,"
                        + "rating_id int NOT NULL REFERENCES rating(id) ON DELETE CASCADE,"
                        + "employee_id int NOT NULL REFERENCES employee(id) ON DELETE CASCADE,"
                        + "result int NOT NULL,"
                        + "PRIMARY KEY (target_id,rating_id,employee_id)"
                        + ")";
                stmt.executeUpdate(sql);

                sql = "ALTER TABLE gallaries ADD COLUMN width int DEFAULT 0, ADD COLUMN height int DEFAULT 0;";
                stmt.executeUpdate(sql);

                sql = "DROP FUNCTION IF EXISTS propertime(t timestamp without time zone);";
                stmt.executeUpdate(sql);

                sql = "CREATE OR REPLACE FUNCTION propertime(t timestamp without time zone)"
                        + "  RETURNS character varying AS "
                        + "$BODY$ DECLARE res varchar;d varchar;dweek varchar;y varchar;m varchar;h varchar;min varchar;BEGIN IF t IS NULL THEN t:=now(); END IF;h:=EXTRACT(HOUR FROM t);min:=EXTRACT(MINUTE FROM t);IF char_length(min)=1 THEN min:='0'||min;END IF;d:=EXTRACT(DAY FROM t);CASE EXTRACT(DOW FROM t) WHEN 0 THEN dweek:='Воскресенье';WHEN 1 THEN dweek:='Понедельник';WHEN 2 THEN dweek:='Вторник';WHEN 3 THEN dweek:='Среда';WHEN 4 THEN dweek:='Четверг';WHEN 5 THEN dweek:='Пятница';WHEN 6 THEN dweek:='Суббота';END CASE;CASE EXTRACT(MONTH FROM t) WHEN 1 THEN m:='января';WHEN 2 THEN m:='февраля';WHEN 3 THEN m:='марта';WHEN 4 THEN m:='апреля';WHEN 5 THEN m:='мая';WHEN 6 THEN m:='июня';WHEN 7 THEN m:='июля';WHEN 8 THEN m:='августа';WHEN 9 THEN m:='сентября';WHEN 10 THEN m:='октября';WHEN 11 THEN m:='ноября';WHEN 12 THEN m:='декабря';END CASE;y:=EXTRACT(YEAR FROM t);IF EXTRACT(DOY FROM t) = EXTRACT(DOY FROM current_timestamp::TIMESTAMP) THEN res:='Сегодня'||', <span class=\"hm\">'||h||':'||min||'</span>';ELSIF EXTRACT(WEEK FROM t) = EXTRACT(WEEK FROM current_timestamp::TIMESTAMP) THEN res:=dweek||', <span class=\"hm\">'||h||':'||min||'</span>';ELSIF EXTRACT(YEAR FROM t)=EXTRACT(YEAR FROM current_timestamp::TIMESTAMP) THEN res:=d||' '||m;ELSE res:=d||' '||m||' '||y;END IF;RETURN '<nobr>'||res||'</nobr>';END;$BODY$"
                        + "  LANGUAGE plpgsql VOLATILE; ";
                stmt.executeUpdate(sql);

                sql = "UPDATE ver SET ver=12 WHERE prog = 'EXXO'";
                stmt.executeUpdate(sql);
                con.commit();
            }
            if (ver < 14) {
                con.setAutoCommit(false);

                String sql = "UPDATE roles SET description='допущен почти ко всем функциям портала. Может назначать роли сотрудникам. Не может осуществлять операции с документами в разделах документов компании. Лишен надзорных функций в фотоальбомах, документообороте, сообщениях (но может добавить их, присвоив себе соответствующие роли). Выполняет контрольные функции в сервисе «Стандартные бизнес-процессы»' WHERE name='boss'";
                stmt.executeUpdate(sql);

                sql = " UPDATE roles SET description='осуществляет контрольные функции в сервисах «Документооборот», «Фотоальбомы», «Сообщения». Может блокировать документы, сообщения, фотографии других сотрудников. Поскольку имеет доступ ко всем документам и сообщениям всех сотрудников, следует использовать данную роль осторожно' WHERE name='security'";
                stmt.executeUpdate(sql);

                sql = "UPDATE ver SET ver=14 WHERE prog = 'EXXO'";
                stmt.executeUpdate(sql);
                con.commit();
            }
            stmt.close();
            con.close();
        }
    }

    private Connection startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://" + BASE.VER.getDBHost() + "/" + BASE.VER.getDBName();
        Properties properties = BASE.VER.getDBProp();
        return DriverManager.getConnection(url, properties);
    }
}
