package BEANS;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ManagedBean
@RequestScoped
public class Start {

    private Connection con = null;
    private Statement stmt = null;

    public void start() throws ClassNotFoundException, SQLException, IOException {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        if (isDemo()) {
            startSQL();
            String sql = "";
            sql = "DELETE FROM register WHERE key='iLikeEXXO'";
            stmt.executeUpdate(sql);
            try {
                sql = "DROP EXTENSION IF EXISTS plpgsql CASCADE";
                stmt.executeUpdate(sql);

                sql = "CREATE EXTENSION plpgsql";
                stmt.execute(sql);

            } catch (SQLException sQLException) {
                sql = "DROP LANGUAGE IF EXISTS plpgsql CASCADE";
                stmt.executeUpdate(sql);

                sql = "CREATE LANGUAGE plpgsql";
                stmt.execute(sql);
            }
            con.setAutoCommit(false);
            sql = "DROP TABLE IF EXISTS Pics CASCADE";
            stmt.executeUpdate(sql);

            sql = "create table Pics (id int NOT NULL,name varchar(100) DEFAULT NULL,photo bytea,PRIMARY KEY (id))";

            stmt.executeUpdate(sql);

            sql = "DROP SEQUENCE IF EXISTS global_seq CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE SEQUENCE global_seq START 1";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS files CASCADE";
            stmt.executeUpdate(sql);

            sql = "DROP SEQUENCE IF EXISTS files_seq  CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE SEQUENCE files_seq START 2";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE files ("
                    + "  id int NOT NULL DEFAULT NEXTVAL('files_seq'),"
                    + "  global_id int NOT NULL DEFAULT nextval('global_seq'),"
                    + "  owner int NOT NULL,"
                    + "  name varchar(250) NOT NULL,"
                    + "  file smallint DEFAULT '0',"
                    + "  superior int DEFAULT '1',"
                    + "  type varchar(250),"
                    + "  longname varchar(100),"
                    + "  tags text,"
                    + "  descr text,"
                    + "  fname varchar(250),"
                    + "  created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "dopusk_type int DEFAULT '0',"
                    + "dopusk int [], "
                    + "vers boolean not null default false, "
                    + "versmax int null, "
                    + "author INT, "
                    + "step INT NOT NULL DEFAULT 0, "
                    + "copy int NULL ,"
                    + "  PRIMARY KEY (id,owner),"
                    + " UNIQUE(id)"
                    + ")";
            stmt.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS bloges CASCADE";
            stmt.executeUpdate(sql);

            sql = "DROP SEQUENCE IF EXISTS bloges_seq CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE SEQUENCE bloges_seq START 2";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE bloges ("
                    + "id int NOT NULL DEFAULT nextval('bloges_seq'),"
                    + "global_id int NOT NULL DEFAULT nextval('global_seq'),"
                    + "owner int NOT NULL,"
                    + "ref int NULL,"
                    + "name varchar DEFAULT 'Тема не задана',"
                    + "dopusk_type int DEFAULT '0',"
                    + "dopusk int [],"
                    + "text text,"
                    + "time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "bus int NOT NULL DEFAULT '0',"
                    + "blocked boolean DEFAULT 'f',"
                    + "blockedText text,"
                    + "blockedPerson int, "
                    + "refdoc int NULL REFERENCES files(id) ON DELETE CASCADE, "
                    + "refVer int NULL, "
                    + " status int DEFAULT 0,"
                    + "PRIMARY KEY (id,owner),"
                    + "UNIQUE (id),"
                    + "UNIQUE (global_id)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS calendar CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE calendar ("
                    + "user_id int NOT NULL,"
                    + "day date NOT NULL,"
                    + "xml text NOT NULL,"
                    + "PRIMARY KEY (user_id,day)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS desk CASCADE";
            stmt.executeUpdate(sql);

            sql = "DROP SEQUENCE IF EXISTS desc_seq  CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE SEQUENCE desc_seq START 2";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE desk ("
                    + "id int NOT NULL DEFAULT NEXTVAL('desc_seq'),"
                    + "global_id int NOT NULL DEFAULT nextval('global_seq'),"
                    + "owner int NOT NULL,"
                    + "fio varchar(255) NOT NULL,"
                    + "text varchar(255) NOT NULL,"
                    + "time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "PRIMARY KEY (id,owner)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS employee CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE employee ("
                    + "  fio varchar(250) NOT NULL,"
                    + "  unit int,"
                    + "  position varchar(250) NOT NULL,"
                    + "  head smallint DEFAULT '0',"
                    + "  birthday date NOT NULL,"
                    + "  education varchar(250),"
                    + "  tellocal varchar(25),"
                    + "  tellmob varchar(25),"
                    + "  email varchar(50),"
                    + "  id serial,"
                    + "  global_id int NOT NULL DEFAULT nextval('global_seq'),"
                    + "  photo bytea,"
                    + "  hobby text,"
                    + "  comment text,"
                    + "  responsibility text,"
                    + "  tel varchar(25),"
                    + "  office varchar(250) DEFAULT 'Переезд',"
                    + "  room varchar(250),"
                    + "  workSince date,"
                    + "  login varchar(250),"
                    + "  pass varchar(50) DEFAULT '7a5f7cb2d2ae0a9657876e460b2342a4',"
                    + "  role varchar(250) NOT NULL DEFAULT '',"
                    + "  fired smallint NOT NULL DEFAULT '0',"
                    + "  firedSince date,"
                    + "  PRIMARY KEY (id),"
                    + "  UNIQUE (fio),"
                    + "  UNIQUE (login),"
                    + "UNIQUE (global_id)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS employeeTMP CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE employeeTMP ("
                    + "  fio varchar(250),"
                    + "  unit int,"
                    + "  position varchar(250),"
                    + "  head smallint DEFAULT '0',"
                    + "  birthday date,"
                    + "  education varchar(250),"
                    + "  tellocal varchar(25),"
                    + "  tellmob varchar(25),"
                    + "  email varchar(50),"
                    + "  id int NOT NULL,"
                    + "  photo bytea,"
                    + "  hobby text,"
                    + "  comment text,"
                    + "  responsibility text,"
                    + "  supervizor varchar(250),"
                    + "  tel varchar(25),"
                    + "  office varchar(250),"
                    + "  room varchar(250),"
                    + "  workSince date,"
                    + "photo_type VARCHAR NOT NULL DEFAULT '', "
                    + "  PRIMARY KEY (id),"
                    + "  UNIQUE (fio)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS blogesC CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE blogesC ("
                    + "id serial, "
                    + "bloges_id int NOT NULL REFERENCES bloges(id) ON DELETE CASCADE, "
                    + "text text, "
                    + "owner int NOT NULL REFERENCES employee(id) ON DELETE CASCADE, "
                    + "time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                    + "PRIMARY KEY (id))";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS blogesTMP CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE blogesTMP ("
                    + "owner int NOT NULL REFERENCES employee(id) ON DELETE CASCADE ,"
                    + "name varchar DEFAULT 'Тема не задана',"
                    + "ref int NULL,"
                    + "dopusk_type int DEFAULT '0',"
                    + "dopusk int [],"
                    + "text text,"
                    + "bus int NOT NULL DEFAULT '0', "
                    + "refdoc int NULL REFERENCES files(id) ON DELETE CASCADE,"
                    + "refVer int NULL,  "
                    + " status int DEFAULT 0,"
                    + "PRIMARY KEY (owner)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS tags CASCADE";
            stmt.executeUpdate(sql);

            sql = "DROP SEQUENCE IF EXISTS tags_seq  CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE SEQUENCE tags_seq START 2";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE tags ("
                    + "  tag_id int NOT NULL DEFAULT NEXTVAL('tags_seq'),"
                    + "  owner int NOT NULL,"
                    + "  name varchar(250) NOT NULL,"
                    + "  tag int DEFAULT '0',"
                    + "  status int DEFAULT '0',"
                    + "  superior int DEFAULT '1',"
                    + "  created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "  PRIMARY KEY (tag_id,owner),"
                    + "  UNIQUE (tag_id)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS found_tags CASCADE";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE found_tags ("
                    + "  owner int NOT NULL,"
                    + "  tag_id int NOT NULL REFERENCES tags(tag_id) ON DELETE CASCADE,"
                    + "  status int DEFAULT '1',"
                    + "  PRIMARY KEY (owner, tag_id))";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS global_tags CASCADE";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE global_tags ("
                    + "  global_id int NOT NULL,"
                    + "  tag_id int NOT NULL REFERENCES tags(tag_id) ON DELETE CASCADE,"
                    + "  PRIMARY KEY (global_id, tag_id))";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS gallaries CASCADE";
            stmt.executeUpdate(sql);

            sql = "DROP SEQUENCE IF EXISTS gallaries_seq  CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE SEQUENCE gallaries_seq START 2";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE gallaries ("
                    + "  id int NOT NULL DEFAULT NEXTVAL('gallaries_seq'),"
                    + "  global_id int NOT NULL DEFAULT nextval('global_seq'),"
                    + "  owner int NOT NULL,"
                    + "  name varchar(250) NOT NULL,"
                    + "  file smallint DEFAULT '0',"
                    + "  superior int DEFAULT '1',"
                    + "  type varchar(250),"
                    + "  longname varchar(100),"
                    + "  tags text,"
                    + "  descr text,"
                    + "  bin bytea,"
                    + "  size int,"
                    + "  binsm bytea,"
                    + "  sizesm int, "
                    + "ico bytea DEFAULT null, "
                    + "sizeico Int DEFAULT 0, "
                    + "  fname varchar(250),"
                    + "  created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "  notshow smallint DEFAULT '0',"
                    + "cover boolean DEFAULT 'f', "
                    + "blocked boolean DEFAULT 'f',"
                    + "blockedtext varchar,"
                    + "blockedperson int,"
                    + "  PRIMARY KEY (id,owner),"
                    + "UNIQUE(id)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS projects CASCADE";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE projects ("
                    + "id serial,"
                    + "global_id int NOT NULL DEFAULT nextval('global_seq'),"
                    + "name varchar not null,"
                    + "boss int NULL REFERENCES employee(global_id) ON DELETE CASCADE,"
                    + "text text,"
                    + "PRIMARY KEY (id)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS groups CASCADE";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE groups ("
                    + "id serial, "
                    + "global_id int NOT NULL DEFAULT nextval('global_seq'),"
                    + "project int NOT NULL REFERENCES projects(id) ON DELETE CASCADE, "
                    + "name varchar not null,"
                    + "boss int NULL REFERENCES employee(global_id) ON DELETE CASCADE,"
                    + "text text,"
                    + "PRIMARY KEY (id)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS wgroup CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE wgroup ("
                    + "id int NOT NULL REFERENCES employee(global_id) ON DELETE CASCADE, "
                    + "group_id int NOT NULL REFERENCES groups(id) ON DELETE CASCADE, "
                    + "class varchar NOT NULL DEFAULT 'notaboss', "
                    + "UNIQUE (id, group_id)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS logo CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE logo ("
                    + "  img bytea,"
                    + "   slogan varchar(250)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS menu CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE menu (  menu text )";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS news CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE news ("
                    + "  id serial,"
                    + "  global_id int NOT NULL DEFAULT nextval('global_seq'),"
                    + "  text text,"
                    + "  time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "  PRIMARY KEY (id)"
                    + ");";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS offices CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE offices ("
                    + "  short varchar(255) NOT NULL,"
                    + "  adress text,"
                    + "  tel varchar(20),"
                    + "  PRIMARY KEY (short)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS pjornal CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE pjornal (  id serial,"
                    + "  timeEnter timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "  timeupd timestamp,"
                    + "  customer int NOT NULL,"
                    + "  pid int NOT NULL,"
                    + "  text text,"
                    + "  status int NOT NULL DEFAULT '0',"
                    + "  why text,"
                    + "  comment text,"
                    + "  PRIMARY KEY (id)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS process CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE process ("
                    + "  id serial,"
                    + "  name varchar(250) NOT NULL,"
                    + "  description text,"
                    + "   owner int NOT NULL,"
                    + "  supervisor int NOT NULL,"
                    + "  template text,"
                    + "  type varchar(100) NOT NULL DEFAULT 'simple',"
                    + "  minToDo int NOT NULL DEFAULT '0',"
                    + "  minToDeside int NOT NULL DEFAULT '0',"
                    + "  minToOpen int NOT NULL DEFAULT '0',"
                    + "  stoped smallint NOT NULL DEFAULT '0',"
                    + "  PRIMARY KEY (id)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS registor CASCADE";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE registor ("
                    + "global_id int NOT NULL,"
                    + "id int NOT NULL REFERENCES employee(id) ON DELETE CASCADE, "
                    + "service VARCHAR DEFAULT 'bloges', "
                    + "created timestamp DEFAULT now(),"
                    + "UNIQUE (global_id, id))";

            stmt.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS respects CASCADE";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE respects ("
                    + "global_id int NOT NULL,"
                    + "id int NOT NULL REFERENCES employee(id) ON DELETE CASCADE, "
                    + "service VARCHAR DEFAULT 'bloges',"
                    + "created timestamp DEFAULT now(),"
                    + "UNIQUE (global_id, id))";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS resource CASCADE";
            stmt.executeUpdate(sql);

///////////////////////////////////////////////////////////////////
            sql = "CREATE TABLE resource ("
                    + "  id serial,"
                    + "  day date NOT NULL,"
                    + "  ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "  xml text NOT NULL,"
                    + "  PRIMARY KEY (id,day)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION resource_ts() RETURNS trigger AS $$BEGIN "
                    + "NEW.ts:=current_timestamp;"
                    + "RETURN NEW;"
                    + "END;"
                    + "$$ LANGUAGE plpgsql";
            stmt.execute(sql);

            sql = "DROP TRIGGER  IF EXISTS  resource_t ON resource";
            stmt.executeUpdate(sql);

            sql = "CREATE TRIGGER resource_t BEFORE UPDATE ON resource FOR EACH ROW EXECUTE PROCEDURE resource_ts()";
            stmt.executeUpdate(sql);

            sql = "DROP SEQUENCE IF EXISTS resources_seq  CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE SEQUENCE resources_seq START 2";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS resources CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE resources ("
                    + " id int NOT NULL DEFAULT NEXTVAL('resources_seq'),"
                    + " short varchar(255),"
                    + " comment text,"
                    + " PRIMARY KEY (id)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS restimer CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE restimer ("
                    + "  id int NOT NULL,"
                    + "  resource int NOT NULL,"
                    + "  day date NOT NULL,"
                    + "  ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "  PRIMARY KEY (id,resource,day)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TRIGGER  IF EXISTS  restimer_t ON restimer";
            stmt.executeUpdate(sql);

            sql = "CREATE TRIGGER restimer_t BEFORE UPDATE ON restimer FOR EACH ROW EXECUTE PROCEDURE resource_ts()";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS rp CASCADE";
            stmt.executeUpdate(sql);

            sql = "DROP SEQUENCE IF EXISTS structure_seq CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE SEQUENCE structure_seq START 3";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS structure CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE structure ("
                    + "  page_id int NOT NULL DEFAULT NEXTVAL('structure_seq'),"
                    + "  global_id int NOT NULL DEFAULT nextval('global_seq'),"
                    + "  user_id int NOT NULL,"
                    + "  structure text,"
                    + "  title varchar(255) NOT NULL,"
                    + "  css text default '',"
                    + "  PRIMARY KEY (page_id)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS tempImg CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE tempImg ("
                    + "  id int NOT NULL,"
                    + "  img bytea,"
                    + "  PRIMARY KEY (id)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS texts CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE texts ("
                    + "  user_id int NOT NULL,"
                    + "  id varchar(100) NOT NULL,"
                    + "  text text NOT NULL,"
                    + "  time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "  PRIMARY KEY (user_id,id)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION texts_ts() RETURNS trigger AS $$BEGIN "
                    + "NEW.time:=current_timestamp;"
                    + "RETURN NEW;"
                    + "END;"
                    + "$$ LANGUAGE plpgsql";
            stmt.execute(sql);

            sql = "DROP TRIGGER  IF EXISTS  texts_t ON texts";
            stmt.executeUpdate(sql);

            sql = "CREATE TRIGGER texts_t BEFORE UPDATE ON texts FOR EACH ROW EXECUTE PROCEDURE texts_ts()";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS units CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE units ("
                    + "  unit_id serial,"
                    + "  global_id int NOT NULL DEFAULT nextval('global_seq'),"
                    + "  unit varchar(250) NOT NULL,"
                    + "  superior int,"
                    + "  PRIMARY KEY (unit_id)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS unitTexts CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE unitTexts ("
                    + "  unit_id int NOT NULL REFERENCES units(unit_id) ON DELETE CASCADE ,"
                    + "text text,"
                    + "  PRIMARY KEY (unit_id)"
                    + ")";
            stmt.executeUpdate(sql);
            //
            sql = "DROP TABLE IF EXISTS users CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE users ("
                    + "id Int, "
                    + "login varchar(250) NOT NULL, "
                    + "pass varchar(50) NOT NULL, "
                    + "PRIMARY KEY (login))";

            stmt.executeUpdate(sql);

            sql = "insert into users(id, login, pass) values(0,'root', MD5('iLikeEXXO'))";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS user_roles CASCADE";
            stmt.executeUpdate(sql);

            sql = "create table user_roles ( "
                    + "login varchar(250) not null REFERENCES users (login) ON DELETE CASCADE ON UPDATE CASCADE, "
                    + "role varchar(250) not null, "
                    + "primary key (login, role))";
            stmt.executeUpdate(sql);

            sql = "insert into user_roles values ('root', 'portal_admin')";
            stmt.executeUpdate(sql);

            sql = "insert into user_roles values ('root', 'portal_user')";
            stmt.executeUpdate(sql);

            //
            sql = "INSERT INTO units (unit, superior) VALUES ('Компания',NULL)";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO offices VALUES ('По умолчанию','','')";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS qwests CASCADE";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE qwests ("
                    + "quest_id serial,"
                    + "anonim boolean DEFAULT 'f',"
                    + "question text,"
                    + "answers text,"
                    + "start date DEFAULT CURRENT_DATE ,"
                    + "stop date,"
                    + "PRIMARY KEY (quest_id)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS answers CASCADE";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE answers ("
                    + "quest_id int,"
                    + "user_id int,"
                    + "answer text,"
                    + "PRIMARY KEY (quest_id, user_id)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS ver CASCADE";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE ver (ver Int NULL,prog VARCHAR NOT NULL,primary key (prog))";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO ver VALUES(" + BASE.Ver.newVer + ",'EXXO')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO ver VALUES(1,'WELLCOME')";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO menu VALUES('<div style=\"height: auto;\"  class=\"yui3-menu-content\" role=\"presentation\"><ul id=\"topUl\" class=\"first-of-type\" role=\"presentation\"><li class=\"yui3-menuitem\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" tabIndex=\"0\" href=\"?id=2\">Главная</a></li><li class=\"yui3-menuitem item-menu item\" role=\"presentation\"><a aria-haspopup=\"true\" class=\"yui3-menu-label yui3-menuitem-content\" role=\"menuitem\" tabIndex=\"-1\" href=\"#comp\">Портал</a><div aria-hidden=\"true\" id=\"comp\" class=\"yui3-menu yui3-menu-hidden\" role=\"menu\"><div class=\"yui3-menu-content\" role=\"presentation\"><ul class=\"first-of-type\" role=\"presentation\"></ul></div></div></li><li class=\"yui3-menuitem item-menu item\" role=\"presentation\"><a aria-haspopup=\"true\" class=\"yui3-menu-label yui3-menuitem-content\" role=\"menuitem\" tabIndex=\"-1\" href=\"#impl\">Справочник</a><div aria-hidden=\"true\" id=\"impl\" class=\"yui3-menu yui3-menu-hidden\" role=\"menu\"><div class=\"yui3-menu-content\" role=\"presentation\"><ul class=\"first-of-type\" role=\"presentation\"><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"territories.jsp\">Офисы</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"company.jsp\">Структура компании</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"empList.jsp\">Поиск сотрудников</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"empListComp.jsp\">Список по подразделениям</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"projects.jsp\">Проекты и группы</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"empPhoto.jsp\">Фотографии</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"birthday.jsp\">Дни рождения</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"former.jsp\">Бывшие сотрудники</a></li></ul></div></div></li><li class=\"yui3-menuitem item-menu item\" role=\"presentation\"><a aria-haspopup=\"true\" class=\"yui3-menu-label yui3-menuitem-content\" role=\"menuitem\" tabIndex=\"-1\" href=\"#docs\">Документы</a><div aria-hidden=\"true\" id=\"docs\" class=\"yui3-menu yui3-menu-hidden\" role=\"menu\"><div class=\"yui3-menu-content\" role=\"presentation\"><ul class=\"first-of-type\" role=\"presentation\"><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"docClassic.jsp\">Мои документы</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"newDoc.jsp\">Новые документы</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a  class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"newDocK.jsp\">Новое в Компании</a></li><li id=\"yui3-3-1-0-1-13445031453471110\" class=\"yui3-menuitem item-menu item\" role=\"presentation\"><a aria-haspopup=\"true\" class=\"yui3-menu-label yui3-menuitem-content\" role=\"menuitem\" href=\"#li_1344503173251-1\">Документы компании</a><div aria-hidden=\"true\" id=\"li_1344503173251-1\" class=\"yui3-menu yui3-menu-hidden\" role=\"menu\"><div class=\"yui3-menu-content\" role=\"presentation\"><ul class=\"first-of-type\" role=\"presentation\"><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"docClassic.jsp?owner=-100\">Общие документы</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"docClassic.jsp?owner=-101\">Входящие</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"docClassic.jsp?owner=-102\">Исходящие</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"docClassic.jsp?owner=-103\">Внутренние</a></li></ul></div></div></li></ul></div></div></li><li class=\"yui3-menuitem item-menu item\" role=\"presentation\"><a aria-haspopup=\"true\" class=\"yui3-menu-label yui3-menuitem-content\" role=\"menuitem\" tabIndex=\"-1\" href=\"#li_1291481259516-10\">Сообщения</a><div aria-hidden=\"true\" class=\"yui3-menu yui3-menu-hidden\" role=\"menu\"><div class=\"yui3-menu-content\" role=\"presentation\"><ul class=\"first-of-type\" role=\"presentation\"><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"blogInsert.jsp?r=1\">Написать сообщение</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"blogs.jsp\">Отправленные</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"newBlogs.jsp\">Все входящие</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"newBlogsB.jsp\">Деловые входящие</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"blogEditor.jsp\">Черновик</a></li></ul></div></div></li><li class=\"yui3-menuitem item-menu item\" role=\"presentation\"><a aria-haspopup=\"true\" class=\"yui3-menu-label yui3-menuitem-content\" role=\"menuitem\" tabIndex=\"-1\" href=\"#res\">Фотоальбомы</a><div aria-hidden=\"true\" id=\"res\" class=\"yui3-menu yui3-menu-hidden\" role=\"menu\"><div class=\"yui3-menu-content\" role=\"presentation\"><ul class=\"first-of-type\" role=\"presentation\"><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"galClassic.jsp\">Мои фотоальбомы</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"galClassic.jsp?owner=-100\">Общие фотоальбомы</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"newGalK.jsp\">Новое в Компании</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"newGal.jsp\">Новые фотоальбомы</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"newPhotos.jsp\">Новые фотографии</a></li></ul></div></div></li><li class=\"yui3-menuitem item-menu item\" role=\"presentation\"><a aria-haspopup=\"true\" class=\"yui3-menu-label yui3-menuitem-content\" role=\"menuitem\" tabIndex=\"-1\" href=\"#li_1291481543693-10\">Сервисы</a><div aria-hidden=\"true\" class=\"yui3-menu yui3-menu-hidden\" role=\"menu\"><div class=\"yui3-menu-content\" role=\"presentation\"><ul class=\"first-of-type\" role=\"presentation\"><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"services.jsp\">Список сервисов</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"jornal.jsp\">Журнал операций</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"myServices.jsp\">Мои сервисы</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"servControl.jsp\">Контроль операций</a></li></ul></div></div></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" tabIndex=\"-1\" href=\"resources.jsp\">Ресурсы</a></li><li class=\"yui3-menuitem item-menu item\" role=\"presentation\"><a aria-haspopup=\"true\" class=\"yui3-menu-label yui3-menuitem-content\" role=\"menuitem\" tabIndex=\"-1\" href=\"#li_1327225480085-1\">База знаний</a><div aria-hidden=\"true\" id=\"li_1327225480085-1\" class=\"yui3-menu yui3-menu-hidden\" role=\"menu\"><div class=\"yui3-menu-content\" role=\"presentation\"><ul class=\"first-of-type\" role=\"presentation\"><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"findTag.jsp\">Поиск по тегам</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" href=\"tags.jsp\" >Редактирование</a></li></ul></div></div></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" tabIndex=\"-1\" href=\"news.jsp\">Новости компании</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" tabIndex=\"-1\" href=\"desk.jsp\">Доска объявлений</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" tabIndex=\"-1\" href=\"calendar.jsp\">Мой календарь</a></li><li class=\"yui3-menuitem item\" role=\"presentation\"><a class=\"yui3-menuitem-content\" role=\"menuitem\" tabIndex=\"-1\" href=\"votes.jsp\">Голосования</a></li></ul></div>')";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO structure (page_id, user_id, structure, title) VALUES (2,0,'<table id=\"grid\" class=\"grid\"><tbody id=\"atbody\"> <tr id=\"tr\"> <td id=\"menu-td\" class=\"menu-td\" valign=\"top\">"
                    + "<div class=\"menu\" id=\"menu\"></div><div class=\"column\" id=\"undermenu\"></div></td>"
                    + "<td id=\"column-1\" class=\"column\" valign=\"top\"><div class=\"portlet\" id=\"text_1295016772152-0\"></div></td>"
                    + "<td id=\"column-2\" class=\"column\" valign=\"top\"><div class=\"portlet\" id=\"text_1295016772153-0\"></div></td> </tr> </tbody></table>','Главная страница');";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO texts VALUES (0,'text_1295016772152-0','<span><h3>Вы установили программный продукт Intranet EXXO.ru</h3><br><br>Мы подготовили несколько советов, как начать работу в интранет-портале:<br><br><a href=\"http://exxo.ru/intranet-start/\" target=\"_blank\"><font style=\"font-size: 15px;\"><strong>С чего начать</strong></font></a></span><br><br><br><br>С каждой страницы портала можно войти в <strong>Справку</strong> по интересующему разделу. Не забывайте ей пользоваться.<br><br><br><br><br><br><br>"
                    + "','2012-11-27 20:20:00'),(0,'text_1295016772153-0','<br><span>По всем вопросом, связанным с эксплуатацией программного продукта, обращайтесь по электронной почте: <a href=\"mailto:support@exxo.ru\">support@exxo.ru</a><br><br><br>Если вы хотите написать главному разработчику программы: <span> <a href=\"mailto:i@exxo.ru\">i@exxo.ru</a></span></span><br><br><br>Если вам что-то не нравится в программе, или что-то непонятно в инструкциях — не стесняйтесь сообщать нам об этом.<br><br><br><strong>Информацию об обновлениях</strong> можно найти на нашем сайте:<a href=\"http://exxo.ru/clients-main/\" target=\"_blank\"> exxo.ru/clients-main/<br></a><br><br>','2012-11-27 20:40:00')";
            stmt.executeUpdate(sql);

            sql = "DROP INDEX IF EXISTS bloges_idx";
            stmt.executeUpdate(sql);
            sql = "DROP INDEX IF EXISTS files_idx";
            stmt.executeUpdate(sql);
            sql = "DROP INDEX IF EXISTS gallaries_idx";
            stmt.executeUpdate(sql);

            sql = "CREATE INDEX bloges_idx ON bloges USING gist(to_tsvector('russian',name||' '||text))";
            stmt.executeUpdate(sql);
            sql = "CREATE INDEX files_idx ON files USING gist(to_tsvector('russian',name||' '||descr||' '||tags))";
            stmt.executeUpdate(sql);
            sql = "CREATE INDEX gallaries_idx ON gallaries USING gist(to_tsvector('russian',name||' '||descr||' '||tags))";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS css CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE css (css text default '')";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS css1 CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE css1 ("
                    + "page varchar(50) NOT NULL,"
                    + "css text default '',"
                    + "PRIMARY KEY (page)"
                    + ")";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO css1 (page) VALUES ('addInMenu.jsp'),('addNew.jsp'),('addTags.jsp'),('birthday.jsp'),('blog.jsp'),('blogEditor.jsp'),"
                    + "('blogProp.jsp'),('blogs.jsp'),('changeBlogStatus.jsp'),('changePicStatus.jsp'),('company.jsp'),('companyMod.jsp'),"
                    + "('correspondence.jsp'),('desk.jsp'),('css.jsp'),('css1.jsp'),('docClassic.jsp'),('documents.jsp'),('documentsMode.jsp'),"
                    + "('empCard.jsp'),('empDoc.jsp'),('empIns.jsp'),('empList.jsp'),('empListComp.jsp'),('empListTer.jsp'),('empPhoto.jsp'),"
                    + "('empUpd.jsp'),('fileLoader.jsp'),('fileProp.jsp'),('findBlogs.jsp'),('findDocs.jsp'),('findGals.jsp'),('findGalsList.jsp'),"
                    + "('findTag.jsp'),('former.jsp'),('galClassic.jsp'),('gallariesMod.jsp'),('group.jsp'),('groupEdit.jsp'),"
                    + "('groupList.jsp'),('groups.jsp'),('index.jsp'),('jornal.jsp'),('listBlogs.jsp'),('listPage.jsp'),('logoIns.jsp'),('main.jsp'),"
                    + "('menuEditor.jsp'),('movement.jsp'),('my.jsp'),('myEdit.jsp'),('myServices.jsp'),('newBlogs.jsp'),('newBlogsB.jsp'),('newDoc.jsp'),"
                    + "('newDocK.jsp'),('newGal.jsp'),('newGalK.jsp'),('newPhotos.jsp'),('newPhotosList.jsp'),('news.jsp'),('opCard.jsp'),('openTags.jsp'),"
                    + "('openTagsGal.jsp'),('operationCard.jsp'),('photos.jsp'),('pic.jsp'),('processIns.jsp'),('processUpd.jsp'),('projectMode.jsp'),"
                    + "('projects.jsp'),('question.jsp'),('qwestList.jsp'),('resourceMode.jsp'),('resources.jsp'),('servControl.jsp'),('serviceCard.jsp'),"
                    + "('services.jsp'),('settingQwest.jsp'),('styleList.jsp'),('styleP.jsp'),('tags.jsp'),('territories.jsp'),('territoriesMod.jsp'),"
                    + "('unit.jsp'),('votes.jsp'),('blogDopusk.jsp'),('blogDopPhoto.jsp'),('blogDopComp.jsp'),('blogDopCompany.jsp'),('addDopusk.jsp')"
                    + ",('addDopPhoto.jsp'),('addDopCompany.jsp'),('addDopComp.jsp'),('blogInsert.jsp'),('editor.jsp'),"
                    + "('unitEditor.jsp'),('allDayR.xhtml'),('eventsRAdd.xhtml'),('eventR.xhtml'),('allDay.xhtml'),('calendarCompare.xhtml'),"
                    + "('five.xhtml'),('month.xhtml'),('eventsAdd.xhtml'),('event.xhtml'),('addFile.xhtml'),('addPic.xhtml')";
            stmt.executeUpdate(sql);

            sql = "DROP SEQUENCE IF EXISTS events_seq  CASCADE;";
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
                    + "type int DEFAULT 0,"
                    + "outside boolean DEFAULT 'f',"
                    + "important boolean DEFAULT 'f',"
                    + "nophone boolean DEFAULT 'f', "
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
            /*
 * 
 * версия 2.2
DROP SEQUENCE IF EXISTS events_seq  CASCADE;
CREATE SEQUENCE events_seq;

DROP TABLE IF EXISTS events CASCADE

CREATE TABLE events (
id VARCHAR NOT NULL,
user_id INT NOT NULL REFERENCES employee(id) ON DELETE CASCADE ,
day date NOT NULL,
hours int NOT NULL,
minutes int NOT NULL,
long int NOT NULL,
name text DEFAULT '',
descr text DEFAULT '',
type int DEFAULT 0,
PRIMARY KEY (id) );

DROP INDEX IF EXISTS events_day_idx;
DROP INDEX IF EXISTS events_user_idx;

CREATE INDEX events_day_idx ON events (day);
CREATE INDEX events_user_idx ON events (user_id);


DROP SEQUENCE IF EXISTS eventsres_seq  CASCADE;
CREATE SEQUENCE eventsres_seq;

DROP TABLE IF EXISTS eventsres CASCADE

CREATE TABLE eventsres (
id VARCHAR NOT NULL,
id_res int, 
user_id INT NOT NULL REFERENCES employee(id) ON DELETE CASCADE ,
day date NOT NULL,
hours int NOT NULL,
minutes int NOT NULL,
long int NOT NULL,
name text DEFAULT '',
descr text DEFAULT '',
fio text NOT NULL,
PRIMARY KEY (id) );

DROP INDEX IF EXISTS eventsres_day_idx;
DROP INDEX IF EXISTS eventsres_user_idx;
DROP INDEX IF EXISTS eventsres_res_idx;

CREATE INDEX eventsres_day_idx ON eventsres (day);
CREATE INDEX eventsres_user_idx ON eventsres (user_id);
CREATE INDEX eventsres_res_idx ON eventsres (id_res);

             */
            sql = "DROP FUNCTION IF EXISTS catalog_name(integer)";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION catalog_name(sup integer) RETURNS varchar AS $$"
                    + " DECLARE r varchar; "
                    + " BEGIN "
                    + " IF sup = 1 THEN RETURN '/'; "
                    + " ELSE SELECT name INTO r FROM files WHERE id=sup; "
                    + " IF NOT FOUND THEN RETURN ' '; "
                    + " ELSE RETURN r; "
                    + " END IF; "
                    + " END IF; "
                    + " END; "
                    + "$$ LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);
            sql = "CREATE OR REPLACE FUNCTION get_fio(integer) RETURNS varchar AS $$ "
                    + "DECLARE r varchar; "
                    + "BEGIN SELECT fio INTO r FROM employee WHERE id=$1;"
                    + "IF NOT FOUND THEN RETURN 'Компания'; "
                    + "ELSE RETURN r; "
                    + "END IF; "
                    + "END; "
                    + "$$ LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);

            sql = "DROP FUNCTION IF EXISTS gal_name(integer)";
            stmt.executeUpdate(sql);

            sql = "CREATE FUNCTION gal_name(integer) RETURNS varchar AS $$ SELECT name FROM gallaries WHERE id=$1;$$ LANGUAGE SQL;";
            stmt.executeUpdate(sql);

            sql = "DROP FUNCTION IF EXISTS unit_name(integer)";
            stmt.executeUpdate(sql);

            sql = "CREATE FUNCTION unit_name(integer) RETURNS varchar AS $$ SELECT unit FROM units WHERE unit_id=$1;$$ LANGUAGE SQL;";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION inStructure (id integer, point integer) RETURNS boolean AS "
                    + "$body$ "
                    + "DECLARE "
                    + "parent INTEGER; "
                    + "BEGIN "
                    + "IF id=point THEN RETURN TRUE; "
                    + "ELSIF  id=1 THEN RETURN FALSE; "
                    + "ELSE  "
                    + "SELECT superior INTO parent FROM units WHERE unit_id=id; "
                    + "IF FOUND THEN "
                    + "IF id=parent THEN "
                    + "RETURN TRUE; "
                    + "ELSE "
                    + "RETURN  inStructure(parent, point); "
                    + "end IF; "
                    + "ELSE RETURN FALSE; "
                    + "end IF; "
                    + "end IF; "
                    + "END "
                    + "$body$ "
                    + "LANGUAGE 'plpgsql'";

            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION group_boss(gr integer) RETURNS varchar AS $$"
                    + " DECLARE r varchar;"
                    + " BEGIN"
                    + " SELECT fio INTO r FROM wgroup, employee WHERE group_id=gr AND wgroup.id=employee.global_id AND class='boss';"
                    + " IF FOUND THEN RETURN r;"
                    + " ELSE RETURN 'Пока не назначен';"
                    + " END IF;"
                    + " END $$ LANGUAGE 'plpgsql'";

            stmt.executeUpdate(sql);
            /*
    * 
CREATE OR REPLACE FUNCTION BlogsBeanList (o varchar, u varchar, g varchar, l varchar, of varchar) RETURNS varchar[] AS 
          $$
          DECLARE
          list varchar;
          r RECORD;
          clas varchar;
          b varchar(10);
          count Int;
          ro Int;
          res varchar;
          BEGIN
          count:=0;
          res:='0';
          ro:=l::Int+1;
          list:='<table id="listTable">';
          FOR r IN SELECT id, global_id, name, time, bus, dopusk_type, isBlogPermitted (dopusk_type, dopusk, g::Int) AS permitted, blocked, is_blog_opend(global_id, u::Int) AS opend  FROM bloges WHERE   owner=o::int ORDER BY TIME DESC LIMIT ro OFFSET of::Int
          LOOP
          clas:='';
          b:='';
          IF r.bus=1 THEN clas:=' bus';
          END IF;
          IF r.blocked THEN b='blocked';
          END IF;
          IF NOT r.opend THEN clas:=clas||' not_opend';
          END IF;
          IF r.permitted OR o=u THEN
          IF count=ro-1 THEN res:='1';
          ELSE
          list:=list||'<tr class="'||b||'"><td class="type_'||r.dopusk_type||'"></td><td class="tdTime">';
          list:=list||'<span class="time">'||properTime(r.time)||'</span></td><td class="tdName"><a href="blog.jsp?id='||r.id||'" class="blogName'||clas||'">' || r.name ||'</a></td></tr>';
          count:=count+1;
          END IF;
          END IF;
          END LOOP;
          list:=list||'</table>';
          RETURN ARRAY[list,res];
          END;
          $$
          LANGUAGE 'plpgsql'
             */
            sql = "CREATE OR REPLACE FUNCTION BlogsBeanList (o varchar, u varchar, g varchar, l varchar, of varchar) RETURNS varchar[] AS $$ "
                    + "DECLARE "
                    + "list varchar;"
                    + "r RECORD;"
                    + "clas varchar;"
                    + "b varchar(10);"
                    + "count Int;"
                    + "ro Int;"
                    + "res varchar;"
                    + "BEGIN "
                    + "count:=0;"
                    + "res:='0';"
                    + "ro:=l::Int+1;"
                    + "list:='<table id=\"listTable\">';"
                    + "FOR r IN SELECT id, global_id, name, time, bus, dopusk_type, isBlogPermitted (dopusk_type, dopusk, g::Int) AS permitted, blocked, is_blog_opend(global_id, u::Int) AS opend  FROM bloges WHERE   owner=o::int ORDER BY TIME DESC LIMIT ro OFFSET of::Int "
                    + " LOOP "
                    + "clas:='';"
                    + "b:='';"
                    + "IF r.bus=1 THEN clas:=' bus';"
                    + "END IF;"
                    + "IF r.blocked THEN b='blocked';"
                    + "END IF;"
                    + "IF NOT r.opend THEN clas:=clas||' not_opend';"
                    + "END IF;"
                    + "IF r.permitted OR o=u THEN "
                    + "IF count=ro-1 THEN res:='1';"
                    + "ELSE "
                    + "list:=list||'<tr class=\"'||b||'\"><td class=\"type_'||r.dopusk_type||'\"></td><td class=\"tdTime\">'; "
                    + "list:=list||'<span class=\"time\">'||properTime(r.time)||'</span></td><td class=\"tdName\"><a href=\"blog.jsp?id='||r.id||'\" class=\"blogName'||clas||'\">' || r.name ||'</a></td></tr>'; "
                    + "count:=count+1;"
                    + "END IF;"
                    + "END IF;"
                    + "END LOOP;"
                    + "list:=list||'</table>';"
                    + "RETURN ARRAY[list,res];"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);

            /*
      CREATE OR REPLACE FUNCTION properTime (t timestamp) RETURNS varchar AS 
          $$
          DECLARE
          res varchar;
          d varchar;
          dweek varchar;
          y varchar;
          m varchar;
          h varchar;
          min varchar;
          BEGIN
          IF t IS NULL THEN t:=now(); END IF;
          h:=EXTRACT(HOUR FROM t);
          min:=EXTRACT(MINUTE FROM t);
          IF char_length(min)=1 THEN min:='0'||min;
          END IF;
          d:=EXTRACT(DAY FROM t);
          CASE EXTRACT(DOW FROM t)
          WHEN 0 THEN dweek:='Воскресенье';
          WHEN 1 THEN dweek:='Понедельник';
          WHEN 2 THEN dweek:='Вторник';
          WHEN 3 THEN dweek:='Среда';
          WHEN 4 THEN dweek:='Четверг';
          WHEN 5 THEN dweek:='Пятница';
          WHEN 6 THEN dweek:='Суббота';
          END CASE;
          CASE EXTRACT(MONTH FROM t)
          WHEN 1 THEN m:='января';
          WHEN 2 THEN m:='февраля';
          WHEN 3 THEN m:='марта';
          WHEN 4 THEN m:='апреля';
          WHEN 5 THEN m:='мая';
          WHEN 6 THEN m:='июня';
          WHEN 7 THEN m:='июля';
          WHEN 8 THEN m:='августа';
          WHEN 9 THEN m:='сентября';
          WHEN 10 THEN m:='октября';
          WHEN 11 THEN m:='ноября';
          WHEN 12 THEN m:='декабря';
          END CASE;
          y:=EXTRACT(YEAR FROM t);
          IF EXTRACT(DOY FROM t) = EXTRACT(DOY FROM current_timestamp::TIMESTAMP)
          THEN res:='Сегодня'||', <span class="hm">'||h||':'||min||'</span>';
          ELSIF EXTRACT(WEEK FROM t) = EXTRACT(WEEK FROM current_timestamp::TIMESTAMP)
          THEN res:=dweek||', <span class="hm">'||h||':'||min||'</span>';
          ELSIF EXTRACT(YEAR FROM t)=EXTRACT(YEAR FROM current_timestamp::TIMESTAMP)
          THEN res:=d||' '||m||', <span class="hm">'||h||':'||min||'</span>';
          ELSE res:=d||' '||m||' '||y||', <span class="hm">'||h||':'||min||'</span>';
          END IF;
          RETURN '<nobr>'||res||'</nobr>';
          END;
          $$
          LANGUAGE 'plpgsql'
      * 
      * 
             */
            sql = "CREATE OR REPLACE FUNCTION properTime (t timestamp) RETURNS varchar AS $$ "
                    + "DECLARE "
                    + "res varchar;"
                    + "d varchar;"
                    + "dweek varchar;"
                    + "y varchar;"
                    + "m varchar;"
                    + "h varchar;"
                    + "min varchar;"
                    + "BEGIN "
                    + "IF t IS NULL THEN t:=now(); END IF;"
                    + "h:=EXTRACT(HOUR FROM t);"
                    + "min:=EXTRACT(MINUTE FROM t);"
                    + "IF char_length(min)=1 THEN min:='0'||min;"
                    + "END IF;"
                    + "d:=EXTRACT(DAY FROM t);"
                    + "CASE EXTRACT(DOW FROM t) "
                    + "WHEN 0 THEN dweek:='Воскресенье';"
                    + "WHEN 1 THEN dweek:='Понедельник';"
                    + "WHEN 2 THEN dweek:='Вторник';"
                    + "WHEN 3 THEN dweek:='Среда';"
                    + "WHEN 4 THEN dweek:='Четверг';"
                    + "WHEN 5 THEN dweek:='Пятница';"
                    + "WHEN 6 THEN dweek:='Суббота';"
                    + "END CASE;"
                    + "CASE EXTRACT(MONTH FROM t) "
                    + "WHEN 1 THEN m:='января';"
                    + "WHEN 2 THEN m:='февраля';"
                    + "WHEN 3 THEN m:='марта';"
                    + "WHEN 4 THEN m:='апреля';"
                    + "WHEN 5 THEN m:='мая';"
                    + "WHEN 6 THEN m:='июня';"
                    + "WHEN 7 THEN m:='июля';"
                    + "WHEN 8 THEN m:='августа';"
                    + "WHEN 9 THEN m:='сентября';"
                    + "WHEN 10 THEN m:='октября';"
                    + "WHEN 11 THEN m:='ноября';"
                    + "WHEN 12 THEN m:='декабря';"
                    + "END CASE;"
                    + "y:=EXTRACT(YEAR FROM t);"
                    + "IF EXTRACT(DOY FROM t) = EXTRACT(DOY FROM current_timestamp::TIMESTAMP) "
                    + "THEN res:='Сегодня'||', <span class=\"hm\">'||h||':'||min||'</span>';"
                    + "ELSIF EXTRACT(WEEK FROM t) = EXTRACT(WEEK FROM current_timestamp::TIMESTAMP) "
                    + "THEN res:=dweek||', <span class=\"hm\">'||h||':'||min||'</span>';"
                    + "ELSIF EXTRACT(YEAR FROM t)=EXTRACT(YEAR FROM current_timestamp::TIMESTAMP) "
                    + "THEN res:=d||' '||m||', <span class=\"hm\">'||h||':'||min||'</span>';"
                    + "ELSE res:=d||' '||m||' '||y||', <span class=\"hm\">'||h||':'||min||'</span>';"
                    + "END IF;"
                    + "RETURN '<nobr>'||res||'</nobr>';"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql'";

            stmt.executeUpdate(sql);

            /*
 * 
CREATE OR REPLACE FUNCTION BlogsBeanEmp (o varchar) RETURNS varchar AS 
          $$
          DECLARE
          emp varchar;
          r RECORD;
          BEGIN
          SELECT fio, position, unit_name(unit) AS u, unit INTO r FROM employee WHERE id=o::int;

          emp:='<table class="empInfo"><tbody><tr><td class="left"><div id="img"><img src="IMG?id='||o||'" class="exxo-shadow1" ></div></td><td class="right"><div class="maininfo"><strong><a href="empCard.jsp?id='||o||'">'||r.fio||'</a></strong></div><div class="maininfo"><i>'||r.position||'</i></div><div class="maininfo" ><a href="empListComp.jsp?uid='||r.unit||'">'||r.u||'</a></div></td></tr></tbody></table>';

          RETURN emp;
          END;
          $$
          LANGUAGE 'plpgsql'

      CREATE OR REPLACE FUNCTION classicDate (t date) RETURNS varchar AS 
$$
DECLARE
res varchar;
d varchar;
dweek varchar;
y varchar;
m varchar;
BEGIN
d:=EXTRACT(DAY FROM t);
CASE EXTRACT(DOW FROM t)
WHEN 0 THEN dweek:='Воскресенье';
WHEN 1 THEN dweek:='Понедельник';
WHEN 2 THEN dweek:='Вторник';
WHEN 3 THEN dweek:='Среда';
WHEN 4 THEN dweek:='Четверг';
WHEN 5 THEN dweek:='Пятница';
WHEN 6 THEN dweek:='Суббота';
END CASE;
CASE EXTRACT(MONTH FROM t)
WHEN 1 THEN m:='января';
WHEN 2 THEN m:='февраля';
WHEN 3 THEN m:='марта';
WHEN 4 THEN m:='апреля';
WHEN 5 THEN m:='мая';
WHEN 6 THEN m:='июня';
WHEN 7 THEN m:='июля';
WHEN 8 THEN m:='августа';
WHEN 9 THEN m:='сентября';
WHEN 10 THEN m:='октября';
WHEN 11 THEN m:='ноября';
WHEN 12 THEN m:='декабря';
END CASE;
y:=EXTRACT(YEAR FROM t);
res:=d||' '||m||' '||y||', '||dweek;

RETURN res;
END;
$$
LANGUAGE 'plpgsql'
      * 
      * 
             */
            sql = "CREATE OR REPLACE FUNCTION BlogsBeanEmp (o varchar) RETURNS varchar AS $$ "
                    + "DECLARE "
                    + "emp varchar;"
                    + "r RECORD;"
                    + "BEGIN "
                    + "SELECT fio, position, unit_name(unit) AS u, unit INTO r FROM employee WHERE id=o::int;"
                    + "emp:='<table class=\"empInfo\"><tbody><tr><td class=\"left\"><div id=\"img\"><img src=\"IMG?id='||o||'\" class=\"exxo-shadow1\" ></div></td><td class=\"right\"><div class=\"maininfo\"><strong><a href=\"empCard.jsp?id='||o||'\">'||r.fio||'</a></strong></div><div class=\"maininfo\"><i>'||r.position||'</i></div><div class=\"maininfo\" ><a href=\"empListComp.jsp?uid='||r.unit||'\">'||r.u||'</a></div></td></tr></tbody></table>';"
                    + "RETURN emp;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);

            sql = " CREATE OR REPLACE FUNCTION classicDate (t date) RETURNS varchar AS "
                    + "$$ "
                    + "DECLARE "
                    + "res varchar; "
                    + "d varchar; "
                    + "dweek varchar; "
                    + "y varchar; "
                    + "m varchar; "
                    + "BEGIN d:=EXTRACT(DAY FROM t); "
                    + "CASE EXTRACT(DOW FROM t) WHEN 0 THEN dweek:='Воскресенье'; "
                    + "WHEN 1 THEN dweek:='Понедельник'; "
                    + "WHEN 2 THEN dweek:='Вторник'; WHEN 3 THEN dweek:='Среда'; WHEN 4 THEN dweek:='Четверг'; WHEN 5 THEN dweek:='Пятница'; "
                    + "WHEN 6 THEN dweek:='Суббота'; END CASE; CASE EXTRACT(MONTH FROM t) WHEN 1 THEN m:='января'; WHEN 2 THEN m:='февраля'; "
                    + "WHEN 3 THEN m:='марта'; WHEN 4 THEN m:='апреля'; WHEN 5 THEN m:='мая'; WHEN 6 THEN m:='июня'; WHEN 7 THEN m:='июля'; "
                    + "WHEN 8 THEN m:='августа'; WHEN 9 THEN m:='сентября'; WHEN 10 THEN m:='октября'; WHEN 11 THEN m:='ноября'; WHEN 12 THEN m:='декабря'; "
                    + "END CASE; y:=EXTRACT(YEAR FROM t); res:=d||' '||m||' '||y||', '||dweek; RETURN res; END; $$ LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);
            /*
 * 
      * 
CREATE OR REPLACE FUNCTION InsertBloges (n varchar, t varchar, b int, o varchar ) RETURNS void AS 
$$
BEGIN
INSERT INTO blogesTMP (name, text, bus, owner) VALUES (n,t, b, o::int);
EXCEPTION
WHEN unique_violation THEN
UPDATE blogesTMP SET name=n, text=t, bus=b WHERE owner=o::int;
END;
$$
LANGUAGE 'plpgsql'
             */

            sql = "CREATE OR REPLACE FUNCTION InsertBloges (n varchar, t varchar, b int, o varchar ) RETURNS void AS "
                    + "$$ "
                    + "BEGIN "
                    + "INSERT INTO blogesTMP (name, text, bus, owner) VALUES (n,t,b,o::int); "
                    + "EXCEPTION "
                    + "WHEN unique_violation THEN "
                    + "UPDATE blogesTMP SET name=n, text=t, bus=b WHERE owner=o::int; "
                    + "END; "
                    + "$$ "
                    + "LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);
            /*
 * 
     CREATE OR REPLACE FUNCTION blogtmpchange (o varchar, t varchar, d varchar) RETURNS varchar AS
$$
DECLARE
r record;
res varchar;
dop Int[];
dopres Int[];
f varchar;
x Int;
BEGIN
res:='';
dopres:=ARRAY[d::Int];
SELECT dopusk_type, dopusk INTO r FROM blogesTMP WHERE owner=o::Int;
IF NOT FOUND THEN 
INSERT INTO blogesTMP (dopusk_type, dopusk, owner) VALUES (t::Int,dopres,o::Int);
ELSE 
dop:=r.dopusk;
  IF (t::Int<>4 OR r.dopusk_type<>4)
  THEN
  UPDATE blogesTMP SET dopusk_type=t::Int, dopusk=dopres WHERE owner=o::Int;
  ELSE
    IF dop@>dopres THEN dopres:=dop;
    ELSE 
    dopres:=dop||dopres;
    END IF;
  UPDATE blogesTMP SET dopusk_type=t::Int, dopusk=dopres WHERE owner=o::Int;
  END IF;
END IF;
IF t::Int=4 THEN 
FOREACH x IN ARRAY dopres
LOOP
SELECT fio INTO f FROM employee WHERE global_id=x;
IF NOT FOUND THEN NULL;
ELSE
res:=res||'<div class="emps" id="'||x||'">'||f||'</div>';
END IF;
END LOOP;
END IF;
RETURN res;
END;
$$
LANGUAGE 'plpgsql'


CREATE OR REPLACE FUNCTION blogtmpchoose (t varchar, o varchar ) RETURNS void AS 
          
          $$
          BEGIN
          INSERT INTO blogesTMP (dopusk_type, dopusk, owner) VALUES (t::Int, NULL, o::Int);
          EXCEPTION
          WHEN unique_violation THEN
          UPDATE blogesTMP SET dopusk_type=t::Int, dopusk=NULL WHERE owner=o::int;
          END;
          $$
          LANGUAGE 'plpgsql'

             */
            sql = "CREATE OR REPLACE FUNCTION blogtmpchoose (t varchar, o varchar ) RETURNS void AS $$ "
                    + "BEGIN "
                    + "INSERT INTO blogesTMP (dopusk_type, dopusk, owner) VALUES (t::Int, NULL, o::Int);"
                    + "EXCEPTION "
                    + "WHEN unique_violation THEN "
                    + "UPDATE blogesTMP SET dopusk_type=t::Int, dopusk=NULL WHERE owner=o::int;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);

            sql = "     CREATE OR REPLACE FUNCTION blogtmpchange (o varchar, t varchar, d varchar) RETURNS varchar AS "
                    + "$$ "
                    + "DECLARE "
                    + "r record; "
                    + "res varchar; "
                    + "dop Int[]; "
                    + "dopres Int[]; "
                    + "f varchar; "
                    + "x Int; "
                    + "BEGIN "
                    + "res:=''; "
                    + "dopres:=ARRAY[d::Int]; "
                    + "SELECT dopusk_type, dopusk INTO r FROM blogesTMP WHERE owner=o::Int; "
                    + "IF NOT FOUND THEN  "
                    + "INSERT INTO blogesTMP (dopusk_type, dopusk, owner) VALUES (t::Int,dopres,o::Int); "
                    + "ELSE "
                    + "dop:=r.dopusk; "
                    + "  IF (t::Int<>4 OR r.dopusk_type<>4) "
                    + "  THEN "
                    + "  UPDATE blogesTMP SET dopusk_type=t::Int, dopusk=dopres WHERE owner=o::Int; "
                    + "  ELSE "
                    + "   IF dop@>dopres THEN dopres:=dop; "
                    + "    ELSE "
                    + "    dopres:=dop||dopres; "
                    + "    END IF; "
                    + "  UPDATE blogesTMP SET dopusk_type=t::Int, dopusk=dopres WHERE owner=o::Int; "
                    + "  END IF; "
                    + "END IF; "
                    + "IF t::Int=4 THEN  "
                    + "FOREACH x IN ARRAY dopres "
                    + "LOOP "
                    + "SELECT fio INTO f FROM employee WHERE global_id=x; "
                    + "IF NOT FOUND THEN NULL; "
                    + "ELSE "
                    + "res:=res||'<div class=\"emps\" id=\"'||x||'\">'||f||'</div>'; "
                    + "END IF; "
                    + "END LOOP; "
                    + "END IF; "
                    + "RETURN res; "
                    + "END; "
                    + "$$ "
                    + "LANGUAGE 'plpgsql'";
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

            /*
 * 
          CREATE OR REPLACE FUNCTION NewBlogs (o varchar, t varchar, g varchar, l varchar, of varchar) RETURNS varchar[] AS 
          $$
          DECLARE
          list varchar;
          r RECORD;
          qw varchar;
          clas varchar;
          count Int;
          ro Int;
          res varchar;
          BEGIN
          count:=0;
          res:='0';
          ro:=l::Int+1;
          IF t<>'all' THEN qw:=' AND dopusk_type='||t;
          ELSE qw:='';
          END IF;
          list:='<table id="listTable">';
          FOR r IN EXECUTE 'SELECT id, global_id, name, time, bus, dopusk_type, owner, is_blog_opend(global_id, $1) AS opend FROM bloges WHERE NOT blocked AND owner<>$3 AND isBlogPermitted (dopusk_type, dopusk, $2) '||qw||' ORDER BY TIME DESC LIMIT $4 OFFSET $5' USING  o::Int, g::Int, o::Int, ro, of::Int
          LOOP
          clas:='';
          IF r.bus=1 THEN clas:=' bus';
          END IF;
          IF NOT r.opend THEN clas:=clas||' not_opend';
          END IF;
          IF count=ro-1 THEN res:='1';
          ELSE
          list:=list||'<tr><td class="type_'||r.dopusk_type||'"></td><td class="emp"><a href="blogs.jsp?owner='||r.owner||'">'||get_fio(r.owner)||'</a></td><td class="tdName"><a href="blog.jsp?id='||r.id||'" class="blogName'||clas||'">' || r.name ||'</a></td><td class="tdTime">'||'<span class="time">'||properTime(r.time)||'</span></td></tr>';
         
          count:=count+1;
          END IF;
          END LOOP;
          list:=list||'</table>';
          RETURN ARRAY[list,res];
          END;
          $$
          LANGUAGE 'plpgsql'
      
      * 
      * 
             */
            sql = "CREATE OR REPLACE FUNCTION NewBlogs (o varchar, t varchar, g varchar, l varchar, of varchar) RETURNS varchar[] AS $$ "
                    + "DECLARE "
                    + "list varchar;"
                    + "r RECORD;"
                    + "qw varchar;"
                    + "clas varchar;"
                    + "count Int;"
                    + "ro Int;"
                    + "res varchar;"
                    + "BEGIN "
                    + "count:=0;"
                    + "res:='0';"
                    + "ro:=l::Int+1;"
                    + "IF t<>'all' THEN qw:=' AND dopusk_type='||t;"
                    + "ELSE qw:='';"
                    + "END IF;"
                    + "list:='<table id=\"listTable\">';"
                    + "FOR r IN EXECUTE 'SELECT id, global_id, name, time, bus, dopusk_type, owner, is_blog_opend(global_id, $1) AS opend FROM bloges WHERE NOT blocked AND owner<>$3 AND isBlogPermitted (dopusk_type, dopusk, $2) '||qw||' ORDER BY TIME DESC LIMIT $4 OFFSET $5' USING  o::Int, g::Int, o::Int, ro, of::Int "
                    + " LOOP "
                    + "clas:='';"
                    + "IF r.bus=1 THEN clas:=' bus';"
                    + "END IF;"
                    + "IF NOT r.opend THEN clas:=clas||' not_opend';"
                    + "END IF;"
                    + "IF count=ro-1 THEN res:='1';"
                    + "ELSE "
                    + "list:=list||'<tr><td class=\"type_'||r.dopusk_type||'\"></td><td class=\"emp\"><a href=\"blogs.jsp?owner='||r.owner||'\">'||get_fio(r.owner)||'</a></td><td class=\"tdName\"><a href=\"blog.jsp?id='||r.id||'\" class=\"blogName'||clas||'\">' || r.name ||'</a></td><td class=\"tdTime\">'||'<span class=\"time\">'||properTime(r.time)||'</span></td></tr>'; "
                    + "count:=count+1;"
                    + "END IF;"
                    + "END LOOP;"
                    + "list:=list||'</table>';"
                    + "RETURN ARRAY[list,res];"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql'";

            stmt.executeUpdate(sql);

            /*     
     
     
     CREATE OR REPLACE FUNCTION NewBlogsB (o varchar, t varchar, g varchar, l varchar, of varchar) RETURNS varchar[] AS 
          $$
          DECLARE
          list varchar;
          r RECORD;
          qw varchar;
          clas varchar;
          count Int;
          ro Int;
          res varchar;
          BEGIN
          count:=0;
          res:='0';
          ro:=l::Int+1;
          IF t<>'all' THEN qw:=' AND dopusk_type='||t;
          ELSE qw:='';
          END IF;
          list:='<table id="listTable">';
          FOR r IN EXECUTE 'SELECT id, global_id, name, time, bus, dopusk_type, owner, is_blog_opend(global_id, $2) AS opend  FROM bloges WHERE NOT blocked AND owner<>$2 AND bus=1 AND isBlogPermitted (dopusk_type, dopusk, $1) '||qw||' ORDER BY TIME DESC LIMIT $3 OFFSET $4' USING g::Int, o::Int, ro, of::Int
          LOOP
          clas:='';
          IF r.bus=1 THEN clas:=' bus';
          END IF;
          IF NOT r.opend THEN clas:=clas||' not_opend';
          END IF;
          IF count=ro-1 THEN res:='1';
          ELSE
          list:=list||'<tr><td class="type_'||r.dopusk_type||'"></td><td class="emp"><a href="blogs.jsp?owner='||r.owner||'">'||get_fio(r.owner)||'</a></td><td class="tdName"><a href="blog.jsp?id='||r.id||'" class="blogName'||clas||'">' || r.name ||'</a></td><td class="tdTime">'||'<span class="time">'||properTime(r.time)||'</span></td></tr>';
         
          count:=count+1;
          END IF;
          END LOOP;
          list:=list||'</table>';
          RETURN ARRAY[list,res];
          END;
          $$
          LANGUAGE 'plpgsql'
      
      * 
      * 
             */
            sql = "CREATE OR REPLACE FUNCTION NewBlogsB (o varchar, t varchar, g varchar, l varchar, of varchar) RETURNS varchar[] AS $$ "
                    + "DECLARE "
                    + "list varchar;"
                    + "r RECORD;"
                    + "qw varchar;"
                    + "clas varchar;"
                    + "count Int;"
                    + "ro Int;"
                    + "res varchar;"
                    + "BEGIN "
                    + "count:=0;"
                    + "res:='0';"
                    + "ro:=l::Int+1;"
                    + "IF t<>'all' THEN qw:=' AND dopusk_type='||t;"
                    + "ELSE qw:='';"
                    + "END IF;"
                    + "list:='<table id=\"listTable\">';"
                    + "FOR r IN EXECUTE 'SELECT id, global_id, name, time, bus, dopusk_type, owner, is_blog_opend(global_id, $2) AS opend  FROM bloges WHERE NOT blocked AND owner<>$2 AND bus=1 AND isBlogPermitted (dopusk_type, dopusk, $1) '||qw||' ORDER BY TIME DESC LIMIT $3 OFFSET $4' USING g::Int, o::Int, ro, of::Int "
                    + "LOOP "
                    + "clas:='';"
                    + "IF r.bus=1 THEN clas:=' bus';"
                    + "END IF;"
                    + "IF NOT r.opend THEN clas:=clas||' not_opend';"
                    + "END IF;"
                    + "IF count=ro-1 THEN res:='1';"
                    + "ELSE "
                    + "list:=list||'<tr><td class=\"type_'||r.dopusk_type||'\"></td><td class=\"emp\"><a href=\"blogs.jsp?owner='||r.owner||'\">'||get_fio(r.owner)||'</a></td><td class=\"tdName\"><a href=\"blog.jsp?id='||r.id||'\" class=\"blogName'||clas||'\">' || r.name ||'</a></td><td class=\"tdTime\">'||'<span class=\"time\">'||properTime(r.time)||'</span></td></tr>'; "
                    + "count:=count+1;"
                    + "END IF;"
                    + "END LOOP;"
                    + "list:=list||'</table>';"
                    + "RETURN ARRAY[list,res];"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql'";

            stmt.executeUpdate(sql);

            /*
      * 
CREATE OR REPLACE FUNCTION isBlogPermitted (t Int, d Int[], o Int) RETURNS boolean AS
$$
DECLARE 
r boolean;
x Int;
u Int;
BEGIN
IF t=0 THEN RETURN 't';
ELSIF t=1 THEN 
  SELECT inProject(d,o) INTO r;
  RETURN r;
ELSIF t=2 THEN
  SELECT inGroup(d,o) INTO r;
  RETURN r;
ELSIF t=3 THEN 
SELECT unit INTO u FROM employee WHERE global_id=o;
SELECT inStructure(u,d[1]) INTO r;
RETURN r;
ELSIF t=4 THEN 
SELECT oneOf(d,o) INTO r;
RETURN r;
ELSE RETURN 'f';
END IF;
END;
$$
LANGUAGE 'plpgsql';
             */
            sql = "CREATE OR REPLACE FUNCTION isBlogPermitted (t Int, d Int[], o Int) RETURNS boolean AS "
                    + "$$ "
                    + "DECLARE "
                    + "r boolean; "
                    + "x Int; "
                    + "u Int; "
                    + "BEGIN "
                    + "IF t=0 THEN RETURN 't'; "
                    + "ELSIF t=1 THEN "
                    + "SELECT inProject(d,o) INTO r; "
                    + "  RETURN r; "
                    + "ELSIF t=2 THEN "
                    + "  SELECT inGroup(d,o) INTO r; "
                    + "  RETURN r; "
                    + "ELSIF t=3 THEN  "
                    + "SELECT unit INTO u FROM employee WHERE global_id=o; "
                    + "SELECT inStructure(u,d[1]) INTO r; "
                    + "RETURN r; "
                    + "ELSIF t=4 THEN "
                    + "SELECT oneOf(d,o) INTO r; "
                    + "RETURN r; "
                    + "ELSE RETURN 'f'; "
                    + "END IF; "
                    + "END; "
                    + "$$ "
                    + "LANGUAGE 'plpgsql';";

            stmt.executeUpdate(sql);


            /*
CREATE OR REPLACE FUNCTION inProject (d Int[], o Int) RETURNS boolean AS
          $$
          DECLARE 
          r record;
          x Int;
          BEGIN
          FOR r IN SELECT groups.project AS project FROM wgroup, groups WHERE wgroup.id=o AND wgroup.group_id=groups.id
          LOOP
          IF d IS NOT NULL THEN
          FOREACH x in ARRAY d
          LOOP 
          IF r.project=x THEN RETURN 't';
          END IF;
          END LOOP;
          END IF;
          END LOOP;
          RETURN 'f';
          END;
          $$
          LANGUAGE 'plpgsql';

             */
            sql = "CREATE OR REPLACE FUNCTION inProject (d Int[], o Int) RETURNS boolean AS "
                    + "$$ "
                    + "DECLARE "
                    + "r record;"
                    + "x Int;"
                    + "BEGIN "
                    + "FOR r IN SELECT groups.project AS project FROM wgroup, groups WHERE wgroup.id=o AND wgroup.group_id=groups.id "
                    + "LOOP "
                    + "IF d IS NOT NULL THEN "
                    + "FOREACH x in ARRAY d "
                    + "LOOP  "
                    + "IF r.project=x THEN RETURN 't';"
                    + "END IF;"
                    + "END LOOP;"
                    + "END IF;"
                    + "END LOOP;"
                    + "RETURN 'f';"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);
            /* 
 * 

CREATE OR REPLACE FUNCTION inGroup (d Int[], o Int) RETURNS boolean AS
          $$
          DECLARE 
          r record;
          x Int;
          BEGIN
          FOR r IN SELECT group_id FROM wgroup WHERE wgroup.id=o
          LOOP
          IF d IS NOT NULL THEN
          FOREACH x in ARRAY d
          LOOP 
          IF r.group_id=x THEN RETURN 't';
          END IF;
          END LOOP;
          END IF;
          END LOOP;
          RETURN 'f';
          END;
          $$
          LANGUAGE 'plpgsql';
      * 
      * 
             */
            sql = "CREATE OR REPLACE FUNCTION inGroup (d Int[], o Int) RETURNS boolean AS "
                    + " $$ "
                    + "DECLARE "
                    + "r record;"
                    + "x Int;"
                    + "BEGIN "
                    + "FOR r IN SELECT group_id FROM wgroup WHERE wgroup.id=o "
                    + "LOOP "
                    + "IF d IS NOT NULL THEN "
                    + "FOREACH x in ARRAY d "
                    + "LOOP  "
                    + "IF r.group_id=x THEN RETURN 't';"
                    + "END IF;"
                    + "END LOOP;"
                    + "END IF;"
                    + "END LOOP;"
                    + "RETURN 'f';"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql';";
            stmt.executeUpdate(sql);
            /* 
 * 
      * 
CREATE OR REPLACE FUNCTION oneOf (d Int[], o Int) RETURNS boolean AS
$$
DECLARE 
x Int;
BEGIN
IF d IS NOT NULL THEN
FOREACH x in ARRAY d
LOOP 
IF o=x THEN RETURN 't';
END IF;
END LOOP;
END IF;
RETURN 'f';
END;
$$
LANGUAGE 'plpgsql';
      * 
      * 
      * 
      * 
      * 
             */

            sql = "CREATE OR REPLACE FUNCTION oneOf (d Int[], o Int) RETURNS boolean AS "
                    + "$$ "
                    + "DECLARE "
                    + " x Int;"
                    + " BEGIN "
                    + "IF d IS NOT NULL THEN "
                    + "FOREACH x in ARRAY d "
                    + "LOOP  "
                    + "IF o=x THEN RETURN 't'; "
                    + "END IF; "
                    + "END LOOP; "
                    + "END IF; "
                    + "RETURN 'f'; "
                    + "END; "
                    + "$$ "
                    + "LANGUAGE 'plpgsql';";
            stmt.executeUpdate(sql);
            /*     
  CREATE OR REPLACE FUNCTION FindBlogs (o varchar, f varchar, g varchar, l varchar, of varchar) RETURNS varchar[] AS 
          $$
          DECLARE
          list varchar;
          r RECORD;
          clas varchar;
          count Int;
          ro Int;
          res varchar;
          BEGIN
          count:=0;
          res:='0';
          ro:=l::Int+1;
          
          list:='<table id="listTable">';
          
          FOR r IN SELECT id, global_id, name, time, bus, dopusk_type, owner, is_blog_opend(global_id, o::Int) AS opend  FROM bloges WHERE  to_tsvector('russian',name||' '||text) @@ plainto_tsquery(f) AND NOT blocked AND (isBlogPermitted (dopusk_type, dopusk, g::Int) OR owner=o::Int) LIMIT ro OFFSET of::Int
          
          LOOP
          clas:='';
          IF r.bus=1 THEN clas:=' bus';
          END IF;
          IF NOT r.opend THEN clas:=clas||' not_opend';
          END IF;
          IF count=ro-1 THEN res:='1';
          ELSE
          list:=list||'<tr><td class="type_'||r.dopusk_type||'"></td><td class="emp"><a href="blogs.jsp?owner='||r.owner||'">'||get_fio(r.owner)||'</a></td><td class="tdName"><a href="blog.jsp?id='||r.id||'" class="blogName'||clas||'">' || r.name ||'</a></td><td class="tdTime">'||'<span class="time">'||properTime(r.time)||'</span></td></tr>';
         
          count:=count+1;
          END IF;
          END LOOP;
          list:=list||'</table>';
          RETURN ARRAY[list,res];
          END;
          $$
          LANGUAGE 'plpgsql'  
             */
            sql = "  CREATE OR REPLACE FUNCTION FindBlogs (o varchar, f varchar, g varchar, l varchar, of varchar) RETURNS varchar[] AS $$ "
                    + "DECLARE "
                    + "list varchar;"
                    + "r RECORD;"
                    + "clas varchar;"
                    + "count Int;"
                    + "ro Int;"
                    + "res varchar;"
                    + "BEGIN "
                    + "count:=0;"
                    + "res:='0';"
                    + "ro:=l::Int+1;"
                    + "list:='<table id=\"listTable\">';"
                    + "FOR r IN SELECT id, global_id, name, time, bus, dopusk_type, owner, is_blog_opend(global_id, o::Int) AS opend  FROM bloges WHERE  to_tsvector('russian',name||' '||text) @@ plainto_tsquery(f) AND NOT blocked AND (isBlogPermitted (dopusk_type, dopusk, g::Int) OR owner=o::Int) LIMIT ro OFFSET of::Int "
                    + "LOOP "
                    + "clas:='';"
                    + "IF r.bus=1 THEN clas:=' bus';"
                    + "END IF;"
                    + "IF NOT r.opend THEN clas:=clas||' not_opend';"
                    + "END IF;"
                    + "IF count=ro-1 THEN res:='1';"
                    + "ELSE "
                    + "list:=list||'<tr><td class=\"type_'||r.dopusk_type||'\"></td><td class=\"emp\"><a href=\"blogs.jsp?owner='||r.owner||'\">'||get_fio(r.owner)||'</a></td><td class=\"tdName\"><a href=\"blog.jsp?id='||r.id||'\" class=\"blogName'||clas||'\">' || r.name ||'</a></td><td class=\"tdTime\">'||'<span class=\"time\">'||properTime(r.time)||'</span></td></tr>';"
                    + "count:=count+1;"
                    + "END IF;"
                    + "END LOOP;"
                    + "list:=list||'</table>';"
                    + "RETURN ARRAY[list,res];"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql' ";

            stmt.executeUpdate(sql);

            /*
  * 
  CREATE OR REPLACE FUNCTION ListBlogs (l varchar, of varchar) RETURNS varchar[] AS 
          $$
          DECLARE
          list varchar;
          r RECORD;
          clas varchar(3);
          b varchar(10);
          count Int;
          ro Int;
          res varchar;
          BEGIN
          count:=0;
          res:='0';
          ro:=l::Int+1;
          list:='<table id="listTable">';
          FOR r IN SELECT id, global_id, name, time, bus, dopusk_type, owner, blocked FROM bloges ORDER BY TIME DESC LIMIT ro OFFSET of::Int
          LOOP
          clas:='';
          b:='';
          IF r.blocked THEN b:='blocked'; END IF;
          IF r.bus=1 THEN clas:='bus';
          END IF;
          IF count=ro-1 THEN res:='1';
          ELSE
          list:=list||'<tr class="'||b||'"><td class="type_'||r.dopusk_type||'"></td><td class="emp"><a href="blogs.jsp?owner='||r.owner||'">'||get_fio(r.owner)||'</a></td><td class="tdName"><a href="blog.jsp?id='||r.id||'" class="'||clas||'">' || r.name ||'</a></td><td class="tdTime">'||'<span class="time">'||properTime(r.time)||'</span></td></tr>';
          count:=count+1;
          END IF;
          END LOOP;
          list:=list||'</table>';
          RETURN ARRAY[list,res];
          END;
          $$
          LANGUAGE 'plpgsql'
  * 
  * 
  * 
             */
            sql = "CREATE OR REPLACE FUNCTION ListBlogs (l varchar, of varchar) RETURNS varchar[] AS $$ "
                    + "DECLARE "
                    + "list varchar;"
                    + "r RECORD;"
                    + "clas varchar(3);"
                    + "b varchar(10);"
                    + "count Int;"
                    + "ro Int;"
                    + "res varchar;"
                    + "BEGIN "
                    + "count:=0;"
                    + "res:='0';"
                    + "ro:=l::Int+1;"
                    + "list:='<table id=\"listTable\">';"
                    + "FOR r IN SELECT id, global_id, name, time, bus, dopusk_type, owner, blocked FROM bloges ORDER BY TIME DESC LIMIT ro OFFSET of::Int "
                    + "LOOP "
                    + "clas:='';"
                    + "b:='';"
                    + "IF r.blocked THEN b:='blocked'; END IF;"
                    + "IF r.bus=1 THEN clas:='bus';"
                    + "END IF;"
                    + "IF count=ro-1 THEN res:='1';"
                    + "ELSE "
                    + "list:=list||'<tr class=\"'||b||'\"><td class=\"type_'||r.dopusk_type||'\"></td><td class=\"emp\"><a href=\"blogs.jsp?owner='||r.owner||'\">'||get_fio(r.owner)||'</a></td><td class=\"tdName\"><a href=\"blog.jsp?id='||r.id||'\" class=\"'||clas||'\">' || r.name ||'</a></td><td class=\"tdTime\">'||'<span class=\"time\">'||properTime(r.time)||'</span></td></tr>';"
                    + "count:=count+1;"
                    + "END IF;"
                    + "END LOOP;"
                    + "list:=list||'</table>';"
                    + "RETURN ARRAY[list,res];"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);

            /*
    * 
       * 
       * 
       CREATE OR REPLACE FUNCTION refBlog(r Int) RETURNS varchar AS 
$$
DECLARE
rr varchar;
BEGIN
SELECT name INTO rr FROM bloges WHERE id=r;
IF NOT FOUND THEN RETURN '';
ELSE RETURN '<div class="RE">Re: <a href="blog.jsp?id='||r||'">'||rr||'</a></div>';
END IF;
END;
$$
LANGUAGE 'plpgsql'
             */
            sql = "CREATE OR REPLACE FUNCTION refBlog(r Int) RETURNS varchar AS "
                    + "$$ "
                    + "DECLARE "
                    + "rr varchar; "
                    + "BEGIN "
                    + "SELECT name INTO rr FROM bloges WHERE id=r; "
                    + "IF NOT FOUND THEN RETURN ''; "
                    + "ELSE RETURN '<div class=\"RE\">Re: <a href=\"blog.jsp?id='||r||'\">'||rr||'</a></div>'; "
                    + "END IF; "
                    + "END; "
                    + "$$ "
                    + "LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);

            /*
      * 
            * 
      CREATE OR REPLACE FUNCTION refreshBlog(o Int, i Int) RETURNS void AS 
$$
DECLARE
r Int;
s record;
BEGIN
SELECT ref INTO r FROM bloges WHERE owner=o AND id=i;
IF NOT FOUND THEN NULL;
ELSE 
IF r IS NOT NULL THEN
SELECT * INTO s FROM bloges WHERE id=r;
IF NOT FOUND THEN NULL;
ELSE UPDATE bloges SET dopusk_type=s.dopusk_type, dopusk=s.dopusk WHERE id=i;
END IF;
END IF;
END IF;
END;
$$
LANGUAGE 'plpgsql'
             */
            sql = "CREATE OR REPLACE FUNCTION refreshBlog(o Int, i Int) RETURNS void AS "
                    + "$$ "
                    + "DECLARE "
                    + "r Int; "
                    + "s record; "
                    + "BEGIN "
                    + "SELECT ref INTO r FROM bloges WHERE owner=o AND id=i; "
                    + "IF NOT FOUND THEN NULL; "
                    + "ELSE "
                    + "IF r IS NOT NULL THEN "
                    + "SELECT * INTO s FROM bloges WHERE id=r; "
                    + "IF NOT FOUND THEN NULL; "
                    + "ELSE UPDATE bloges SET dopusk_type=s.dopusk_type, dopusk=s.dopusk WHERE id=i; "
                    + "END IF; "
                    + "END IF; "
                    + "END IF; "
                    + "END; "
                    + "$$ "
                    + "LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);

            /*
      * 
      * 
CREATE OR REPLACE FUNCTION BlogsAnswersList (i varchar, u varchar, g varchar) RETURNS varchar AS 
          $$
          DECLARE
          list varchar;
          r RECORD;
          clas varchar(3);
          b varchar(10);
          BEGIN
          list:='<table id="listTable">';
          FOR r IN SELECT id, global_id, name, properTime(time) AS t, bus, dopusk_type, isBlogPermitted (dopusk_type, dopusk, g::Int) AS permitted, owner  FROM bloges WHERE ref=i::Int  AND NOT blocked ORDER BY TIME DESC
          LOOP
          clas:='';
          IF r.bus=1 THEN clas:='bus';
          END IF;
          IF r.permitted OR r.owner=u::Int THEN
          list:=list||'<tr><td class="type_'||r.dopusk_type||'"></td>';
          list:=list||'<tr><td class="empIMG"><img src="IMG?id='||r.owner||'" class="exxo-shadow1" ></td><td class="tdComment"><div class="fio"><a href="empCard.jsp?id='||r.owner||'">'||get_fio(r.owner::Int)||'</a><span class="time">'||r.t||'</span></div><div class="tdName"><a href="blog.jsp?id='||r.id||'" class="'||clas||'">' || r.name ||'</a></div></td></tr>';

          END IF;
          END LOOP;
          list:=list||'</table>';
          RETURN list;
          END;
          $$
          LANGUAGE 'plpgsql'
             */
            sql = "CREATE OR REPLACE FUNCTION BlogsAnswersList (i varchar, u varchar, g varchar) RETURNS varchar AS $$ "
                    + "DECLARE "
                    + "list varchar;"
                    + "r RECORD;"
                    + "clas varchar(3);"
                    + "b varchar(10);"
                    + "BEGIN "
                    + "list:='<table id=\"listTable\">';"
                    + "FOR r IN SELECT id, global_id, name, properTime(time) AS t, bus, dopusk_type, isBlogPermitted (dopusk_type, dopusk, g::Int) AS permitted, owner  FROM bloges WHERE ref=i::Int  AND NOT blocked ORDER BY TIME DESC "
                    + " LOOP "
                    + "clas:='';"
                    + "IF r.bus=1 THEN clas:='bus';"
                    + "END IF;"
                    + "IF r.permitted OR r.owner=u::Int THEN "
                    + "list:=list||'<tr><td class=\"type_'||r.dopusk_type||'\"></td>';"
                    + "list:=list||'<tr><td class=\"empIMG\"><img src=\"IMG?id='||r.owner||'\" class=\"exxo-shadow1\" ></td><td class=\"tdComment\"><div class=\"fio\"><a href=\"empCard.jsp?id='||r.owner||'\">'||get_fio(r.owner::Int)||'</a><span class=\"time\">'||r.t||'</span></div><div class=\"tdName\"><a href=\"blog.jsp?id='||r.id||'\" class=\"'||clas||'\">' || r.name ||'</a></div></td></tr>';"
                    + "END IF;"
                    + "END LOOP;"
                    + "list:=list||'</table>';"
                    + "RETURN list;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);

            /*
      * 
      * 
CREATE OR REPLACE FUNCTION registor (g Int, o Int, s varchar) RETURNS void AS $$
          BEGIN
          INSERT INTO registor (global_id, id, service) VALUES(g,o,s);
          EXCEPTION WHEN unique_violation THEN NULL;
          END;
          $$
          LANGUAGE 'plpgsql'
             */
            sql = "CREATE OR REPLACE FUNCTION registor (g Int, o Int, s varchar) RETURNS void AS $$ "
                    + "BEGIN "
                    + "INSERT INTO registor (global_id, id, service) VALUES(g,o,s); "
                    + "EXCEPTION WHEN unique_violation THEN NULL;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);
            /*
CREATE OR REPLACE FUNCTION respects(g Int, o Int, s varchar) RETURNS void AS $$
          BEGIN
          INSERT INTO respects (global_id, id, service) VALUES(g,o,s);
          EXCEPTION WHEN unique_violation THEN NULL;
          END;
          $$
          LANGUAGE 'plpgsql'
             */
            sql = "CREATE OR REPLACE FUNCTION respects(g Int, o Int, s varchar) RETURNS void AS $$ "
                    + "BEGIN "
                    + "INSERT INTO respects (global_id, id, service) VALUES(g,o,s);"
                    + "EXCEPTION WHEN unique_violation THEN NULL;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql'";

            stmt.executeUpdate(sql);

            /*
  * 
         * 
 CREATE OR REPLACE FUNCTION BlogCommentsList (i varchar) RETURNS varchar AS 
          $$
          DECLARE
          list varchar;
          r RECORD;
          BEGIN
          list:='<table id="listTable">';
          FOR r IN SELECT text, get_fio(owner) AS fio, properTime(time) AS t, owner FROM blogesC WHERE bloges_id=i::Int ORDER BY TIME DESC
          LOOP
          list:=list||'<tr><td class="empIMG"><img src="IMG?id='||r.owner||'" class="exxo-shadow1" ></td><td class="tdComment"><div class="fio"><a href="empCard.jsp?id='||r.owner||'">'||get_fio(r.owner::Int)||'</a><span class="time">'||r.t||'</span></div><div class="tdName">'||r.text||'</div></td></tr>';
          END LOOP;
          list:=list||'</table>';
          RETURN list;
          END;
          $$
          LANGUAGE 'plpgsql'
             */
            sql = "CREATE OR REPLACE FUNCTION BlogCommentsList (i varchar) RETURNS varchar AS $$ "
                    + "DECLARE "
                    + "list varchar;"
                    + "r RECORD;"
                    + "BEGIN "
                    + "list:='<table id=\"listTable\">';"
                    + "FOR r IN SELECT text, get_fio(owner) AS fio, properTime(time) AS t, owner FROM blogesC WHERE bloges_id=i::Int ORDER BY TIME DESC "
                    + "LOOP "
                    + "list:=list||'<tr><td class=\"empIMG\"><img src=\"IMG?id='||r.owner||'\" class=\"exxo-shadow1\" ></td><td class=\"tdComment\"><div class=\"fio\"><a href=\"empCard.jsp?id='||r.owner||'\">'||get_fio(r.owner::Int)||'</a><span class=\"time\">'||r.t||'</span></div><div class=\"tdName\">'||r.text||'</div></td></tr>';"
                    + "END LOOP; "
                    + "list:=list||'</table>';"
                    + "RETURN list;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql'";

            stmt.executeUpdate(sql);
            sql = "CREATE OR REPLACE FUNCTION JornalCSV (i varchar) RETURNS varchar AS $$ "
                    + "DECLARE "
                    + "list varchar; "
                    + "r RECORD; "
                    + "stat varchar; "
                    + "t varchar; "
                    + "BEGIN "
                    + "list:=concat_ws(E'\t','Название сервиса', 'Время заявки','Время последних изменений статуса','номер заявки','Текст' , "
                    + "'Статус' ,'Ответ','Комментарий', 'Исполнитель', 'Тип сервиса')||E'\n'; "
                    + "FOR r IN SELECT timeEnter, timeupd, customer, pid, text, status, why, pjornal.comment, process.name "
                    + "AS name, process.owner, employee.fio AS fio, process.type AS tip FROM pjornal, process, employee "
                    + "WHERE pjornal.customer=i::Int AND pjornal.pid=process.id AND employee.id=process.owner ORDER BY status, timeEnter DESC "
                    + "LOOP "
                    + "IF r.status = 0 THEN stat:='не открыто'; "
                    + "ELSIF r.status = 1 THEN stat:='открыто'; "
                    + "ELSIF r.status = 2 THEN stat:='решение принято'; "
                    + "ELSIF r.status = 3 THEN stat:='отказ'; "
                    + "ELSE stat:='вопрос закрыт'; "
                    + "END IF; "
                    + "IF r.tip = 'simple' THEN t:= 'Открыто-сделано'; "
                    + "ELSIF r.tip = 'desideIsDone' THEN t:= 'Решено-сделано'; "
                    + "ELSIF r.tip = 'DoneIsDone' THEN t:= 'Выполнено-сделано'; "
                    + "ELSE t:= 'Отвечено-сделано'; "
                    + "END IF; "
                    + "list:=list||concat_ws(E'\t',r.name, concat('',to_char(r.timeEnter, 'DD.MM.YYYY HH24:MI:SS')), "
                    + "concat('',to_char(r.timeupd,'DD.MM.YYYY HH24:MI:SS')),concat('',r.pid),concat('\"',replace(r.text,'<br>', '') ,'\"'), "
                    + "stat ,concat('\"',replace(r.why,'<br>', ''),'\"'),concat('\"',replace(r.comment,'<br>', ''),'\"'), r.fio, t)||E'\n'; "
                    + "END LOOP; "
                    + "RETURN list; "
                    + "END; "
                    + "$$ "
                    + "LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION MyServicesCSV (i varchar) RETURNS varchar AS $$ "
                    + "DECLARE "
                    + "list varchar; "
                    + "r RECORD; "
                    + "stat varchar; "
                    + "t varchar; "
                    + "BEGIN "
                    + "list:=concat_ws(E'\t','Название сервиса', 'Время заявки','Время последних изменений статуса', "
                    + "'номер заявки','Текст' , 'Статус' ,'Ответ','Комментарий', 'Отправитель', 'Тип сервиса')||E'\n'; "
                    + "FOR r IN SELECT timeEnter, timeupd, customer, pid, text, status, why, pjornal.comment, process.name "
                    + "AS name, process.owner, employee.fio AS fio, process.type AS tip FROM pjornal, process, employee "
                    + "WHERE process.owner=i::Int AND pjornal.pid=process.id AND employee.id=pjornal.customer ORDER BY status, timeEnter DESC "
                    + "LOOP "
                    + "IF r.status = 0 THEN stat:='не открыто'; "
                    + "ELSIF r.status = 1 THEN stat:='открыто'; "
                    + "ELSIF r.status = 2 THEN stat:='решение принято'; "
                    + "ELSIF r.status = 3 THEN stat:='отказ'; "
                    + "ELSE stat:='вопрос закрыт'; "
                    + "END IF; "
                    + "IF r.tip = 'simple' THEN t:= 'Открыто-сделано'; "
                    + "ELSIF r.tip = 'desideIsDone' THEN t:= 'Решено-сделано'; "
                    + "ELSIF r.tip = 'DoneIsDone' THEN t:= 'Выполнено-сделано'; "
                    + "ELSE t:= 'Отвечено-сделано'; "
                    + "END IF; "
                    + "list:=list||concat_ws(E'\t',r.name, concat('',to_char(r.timeEnter, "
                    + "'DD.MM.YYYY HH24:MI:SS')),concat('',to_char(r.timeupd,'DD.MM.YYYY HH24:MI:SS')), "
                    + "concat('',r.pid),concat('\"',replace(r.text,'<br>', '') ,'\"'), stat ,concat('\"',replace(r.why,'<br>', ''),'\"'), "
                    + "concat('\"',replace(r.comment,'<br>', ''),'\"'), r.fio, t)||E'\n'; "
                    + "END LOOP; "
                    + "RETURN list; "
                    + "END; "
                    + "$$ LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION servControlCSV (i varchar, b boolean) RETURNS varchar AS "
                    + "$$ "
                    + "DECLARE "
                    + "list varchar; "
                    + "r RECORD; "
                    + "stat varchar; "
                    + "t varchar; "
                    + "BEGIN "
                    + "list:=concat_ws(E'\t','Название сервиса', 'Время заявки','Время последних изменений статуса', "
                    + "'номер заявки','Текст' , 'Статус' ,'Ответ','Комментарий', 'Отправитель', 'Тип сервиса', 'Владелец сервиса','Инспектор сервиса')||E'\n'; "
                    + "IF b THEN  "
                    + "FOR r IN SELECT timeEnter, timeupd, (SELECT fio FROM employee WHERE id=process.owner) AS owner, "
                    + "(SELECT fio FROM employee WHERE id=process.supervisor) AS supervisor,pid, text, status, why, pjornal.comment, process.name  "
                    + "AS name, process.owner, employee.fio AS fio, process.type AS tip FROM pjornal, process, employee WHERE pjornal.pid=process.id  "
                    + "AND employee.id=pjornal.customer ORDER BY status, timeEnter DESC "
                    + "LOOP "
                    + "IF r.status = 0 THEN stat:='не открыто'; "
                    + "ELSIF r.status = 1 THEN stat:='открыто'; "
                    + "ELSIF r.status = 2 THEN stat:='решение принято'; "
                    + "ELSIF r.status = 3 THEN stat:='отказ'; "
                    + "ELSE stat:='вопрос закрыт'; "
                    + "END IF; "
                    + "IF r.tip = 'simple' THEN t:= 'Открыто-сделано'; "
                    + "ELSIF r.tip = 'desideIsDone' THEN t:= 'Решено-сделано'; "
                    + "ELSIF r.tip = 'DoneIsDone' THEN t:= 'Выполнено-сделано'; "
                    + "ELSE t:= 'Отвечено-сделано'; "
                    + "END IF; "
                    + "list:=list||concat_ws(E'\t',r.name, concat('',to_char(r.timeEnter, 'DD.MM.YYYY HH24:MI:SS')), "
                    + "concat('',to_char(r.timeupd,'DD.MM.YYYY HH24:MI:SS')),concat('',r.pid),concat('\"',replace(r.text,'<br>', '') ,'\"'), "
                    + " stat ,concat('\"',replace(r.why,'<br>', ''),'\"'),concat('\"',replace(r.comment,'<br>', ''),'\"'), r.fio, t, r.owner, r.supervisor)||E'\n'; "
                    + "END LOOP; "
                    + "ELSE  "
                    + "FOR r IN SELECT timeEnter, timeupd, (SELECT fio FROM employee WHERE id=process.owner) "
                    + "AS owner,(SELECT fio FROM employee WHERE id=process.supervisor) AS supervisor,pid, text, status, why, pjornal.comment, process.name "
                    + "AS name, process.owner, employee.fio AS fio, process.type AS tip FROM pjornal, process, employee WHERE process.supervisor=i::Int "
                    + "AND pjornal.pid=process.id AND employee.id=pjornal.customer ORDER BY status, timeEnter DESC "
                    + "LOOP "
                    + "IF r.status = 0 THEN stat:='не открыто'; "
                    + "ELSIF r.status = 1 THEN stat:='открыто'; "
                    + "ELSIF r.status = 2 THEN stat:='решение принято'; "
                    + "ELSIF r.status = 3 THEN stat:='отказ'; "
                    + "ELSE stat:='вопрос закрыт'; "
                    + "END IF; "
                    + "IF r.tip = 'simple' THEN t:= 'Открыто-сделано'; "
                    + "ELSIF r.tip = 'desideIsDone' THEN t:= 'Решено-сделано'; "
                    + "ELSIF r.tip = 'DoneIsDone' THEN t:= 'Выполнено-сделано'; "
                    + "ELSE t:= 'Отвечено-сделано'; "
                    + "END IF; "
                    + "list:=list||concat_ws(E'\t',r.name, concat('',to_char(r.timeEnter, 'DD.MM.YYYY HH24:MI:SS')), "
                    + "concat('',to_char(r.timeupd,'DD.MM.YYYY HH24:MI:SS')),concat('',r.pid),concat('\"',replace(r.text,'<br>', '') ,'\"'), "
                    + "stat ,concat('\"',replace(r.why,'<br>', ''),'\"'),concat('\"',replace(r.comment,'<br>', ''),'\"'), r.fio, t, r.owner, r.supervisor)||E'\n'; "
                    + "END LOOP; "
                    + "END IF; "
                    + "RETURN list; "
                    + "END; "
                    + "$$ "
                    + "LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION insert_tag(gid integer, id integer) RETURNS void AS $$"
                    + " BEGIN "
                    + " INSERT INTO global_tags VALUES (gid , id);"
                    + " EXCEPTION "
                    + " WHEN unique_violation THEN NULL; "
                    + "END; "
                    + "$$ LANGUAGE 'plpgsql'";

            stmt.executeUpdate(sql);
            sql = "CREATE OR REPLACE FUNCTION find_as_tags(gid integer, id integer) RETURNS void AS $$ "
                    + "  DECLARE "
                    + "  r RECORD; "
                    + "  BEGIN "
                    + "  DELETE FROM found_tags WHERE owner=id; "
                    + "  FOR r IN SELECT tag_id FROM global_tags WHERE global_id=gid "
                    + "  LOOP "
                    + "  INSERT INTO found_tags VALUES (id , r.tag_id, '1'); "
                    + "  END LOOP; "
                    + "  END; "
                    + "  $$ LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);
            /*
      * 
        CREATE OR REPLACE FUNCTION find_as_tags(gid integer, id integer) RETURNS void AS $$
          DECLARE
          r RECORD;
          BEGIN 
          DELETE FROM found_tags WHERE owner=id;
          FOR r IN SELECT tag_id FROM global_tags WHERE global_id=gid
          LOOP
          INSERT INTO found_tags VALUES (id , r.tag_id, '1');
          END LOOP;
          END;
          $$ LANGUAGE 'plpgsql';
             */
 /*
       * 
         * 
         DROP SEQUENCE IF EXISTS files_ver_seq  CASCADE
          CREATE SEQUENCE files_ver_seq
          
          CREATE TABLE files_vers(
          id int NOT NULL,
          ver_id int NOT NULL DEFAULT NEXTVAL('files_ver_seq'),
          created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
          bin bytea,
          size int,
          ico bytea DEFAULT null,
          sizeico Int DEFAULT 0,
          PRIMARY KEY (id,ver_id))
         * 
         * 
             */
            sql = "DROP SEQUENCE IF EXISTS files_ver_seq  CASCADE";
            stmt.executeUpdate(sql);
            sql = "CREATE SEQUENCE files_ver_seq";
            stmt.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS files_vers CASCADE";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE files_vers("
                    + "  id int NOT NULL REFERENCES files(id) ON DELETE CASCADE,"
                    + "  ver_id int NOT NULL,"
                    + "  created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "  size int,"
                    + "  ico bytea DEFAULT null,"
                    + "  sizeico Int DEFAULT 0, "
                    + "  store int NOT NULL DEFAULT 0,"
                    + "  status INT NOT NULL DEFAULT 0, "
                    + "  PRIMARY KEY (id,ver_id), "
                    + "  UNIQUE(ver_id))";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION drop_vers(i Int, t boolean, max Int) RETURNS SETOF files_vers AS $$ "
                    + "DECLARE "
                    + "r int; "
                    + "v int; "
                    + "rec RECORD; "
                    + "BEGIN  "
                    + "IF max<1 THEN max:=1; END IF; "
                    + "IF NOT t THEN max:=1; END IF; "
                    + "FOR rec IN SELECT ver_id  FROM files_vers WHERE files_vers.id=i ORDER BY ver_id DESC LIMIT max "
                    + "LOOP "
                    + "v:=rec.ver_id; "
                    + "END LOOP; "
                    + "RETURN QUERY SELECT * FROM files_vers WHERE files_vers.id=i AND ver_id < v; "
                    + "RETURN; "
                    + "END; "
                    + "$$ LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS file_ver_reg CASCADE";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE file_ver_reg ( "
                    + "id int not null REFERENCES files_vers(ver_id) ON DELETE CASCADE, "
                    + "user_id int not null REFERENCES employee(id) ON DELETE CASCADE, "
                    + "visited timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                    + "UNIQUE (id, user_id))";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION file_ver_reg (g Int, o Int) RETURNS void AS $$ "
                    + "BEGIN "
                    + "INSERT INTO file_ver_reg VALUES(g,o); "
                    + "EXCEPTION WHEN unique_violation THEN NULL; "
                    + "END; "
                    + "$$ "
                    + "LANGUAGE 'plpgsql';";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS file_store CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE file_store( "
                    + "id int not null, "
                    + "bin bytea, "
                    + "PRIMARY KEY (id));";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS lost_vers CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE lost_vers ( id int not null,  store int not null);";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION refDoc(r Int, v Int) RETURNS varchar AS "
                    + "$$ "
                    + "DECLARE "
                    + "rr varchar; "
                    + "BEGIN "
                    + "SELECT name INTO rr FROM files WHERE id=r; "
                    + "IF NOT FOUND THEN RETURN ''; "
                    + "ELSE RETURN '<div class=\"DOCRe\">Doc: <a href=\"fileLoader.jsp?id='||r||'&v='||v||'\">'||rr||'</a></div>'; "
                    + "END IF; "
                    + "END; "
                    + "$$ LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS visas CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE visas ( "
                    + "ver_id int not null REFERENCES files_vers(ver_id) ON DELETE CASCADE, "
                    + "owner int not null, "
                    + "answer int null, "
                    + "data timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                    + "comment int null, "
                    + "UNIQUE (ver_id, owner) )";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION add_visa (id Int, vid Int, o Int, c Int) RETURNS void AS $$ "
                    + "BEGIN "
                    + "INSERT INTO visas VALUES(vid, o, c);"
                    + "EXCEPTION WHEN unique_violation THEN NULL;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql';";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS sign CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE sign ("
                    + " ver_id int not null REFERENCES files_vers(ver_id) ON DELETE CASCADE, "
                    + "owner int not null,"
                    + "answer int null,"
                    + "data timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "comment int null,"
                    + "UNIQUE (ver_id, owner));";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION add_sign (i Int, vid Int, o Int, c Int) RETURNS void AS $$ "
                    + " BEGIN "
                    + "INSERT INTO sign VALUES(vid, o, c);"
                    + "EXCEPTION WHEN unique_violation THEN NULL;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql'";

            stmt.executeUpdate(sql);
            sql = "CREATE OR REPLACE FUNCTION isBoss(emp Int, boss Int) RETURNS boolean AS $$ "
                    + "DECLARE "
                    + "u Int;"
                    + "b Int;"
                    + "res boolean;"
                    + "BEGIN "
                    + "res:=FALSE; "
                    + "u:=(SELECT unit FROM employee WHERE id=emp);"
                    + "LOOP "
                    + "b:=(SELECT id FROM employee WHERE head=1 AND unit=u);"
                    + "IF b=boss THEN  "
                    + "res=TRUE;"
                    + "EXIT;"
                    + "END IF;"
                    + "IF u=1 THEN EXIT; END IF;"
                    + "u:=(SELECT superior FROM units WHERE unit_id=u);"
                    + "END LOOP;"
                    + "RETURN res;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql';";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION isSignable(doc Int, type Int, dopusk Int[], emp Int) RETURNS boolean AS $$ "
                    + "DECLARE "
                    + "res boolean; "
                    + "u Int;"
                    + "BEGIN "
                    + "res:=false;"
                    + "IF (dopusk IS NULL OR array_length(dopusk,1)=0)  THEN RETURN FALSE; END IF;"
                    + "IF TYPE<>4 THEN RETURN FALSE; END IF;"
                    + "IF emp<0 THEN RETURN FALSE; END IF;"
                    + "FOREACH u IN ARRAY dopusk "
                    + "LOOP "
                    + "IF NOT isBoss(emp,(SELECT id FROM employee WHERE global_id=u)) AND NOT isGroupBoss(get_global(emp), u) "
                    + "AND NOT isProjectBoss(get_global(emp), u) "
                    + "THEN RETURN FALSE;"
                    + "END IF; "
                    + "res:=TRUE; "
                    + "END LOOP;"
                    + "RETURN res;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql';";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION isGroupBoss(emp Int, boss Int) RETURNS boolean AS $$ "
                    + "DECLARE "
                    + "r record;"
                    + "gr record;"
                    + "BEGIN "
                    + "FOR gr IN SELECT group_id FROM wgroup WHERE id=emp "
                    + "LOOP "
                    + "FOR r IN SELECT class = 'boss' AS isBoss FROM wgroup WHERE id=boss AND group_id=gr.group_id "
                    + "LOOP "
                    + "IF r.isBoss THEN RETURN TRUE; END IF;"
                    + "END LOOP; END LOOP;"
                    + "RETURN FALSE;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql';";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION get_global(integer) RETURNS Integer AS $$ SELECT global_id FROM employee WHERE id=$1;$$ LANGUAGE SQL;";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION isProjectBoss(emp Int, b Int) RETURNS boolean AS $$ "
                    + "DECLARE "
                    + "r record;"
                    + "BEGIN "
                    + "FOR r IN SELECT projects.boss = b AS isBoss FROM wgroup, projects, groups WHERE wgroup.id = emp AND wgroup.group_id = groups.id AND groups.project = projects.id "
                    + "LOOP "
                    + "IF r.isBoss THEN RETURN TRUE; END IF;"
                    + "END LOOP; "
                    + "RETURN FALSE;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql';";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION isSigned(c Int, v Int) RETURNS boolean AS $$ "
                    + "DECLARE "
                    + "d Int[];"
                    + "u Int;"
                    + "a Int;"
                    + "BEGIN "
                    + "d:= (SELECT dopusk FROM files WHERE id=c);"
                    + "FOREACH u IN ARRAY d "
                    + "LOOP "
                    + "a:= (SELECT answer FROM sign WHERE ver_id=v AND owner=(SELECT id FROM employee WHERE global_id=u));"
                    + "IF a IS NULL THEN RETURN FALSE;"
                    + "ELSIF a=2 THEN RETURN FALSE;"
                    + "END IF;"
                    + "END LOOP;"
                    + "RETURN TRUE;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql';";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION make_step_6 (i Int, v Int, c Int) RETURNS void AS $$ "
                    + "BEGIN "
                    + "IF isSigned(i, v) THEN UPDATE files SET step = 6 WHERE copy = c; END IF;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql';";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION isSignedBy(v Int, o Int) RETURNS boolean AS $$ "
                    + "DECLARE "
                    + "a Int;"
                    + "BEGIN "
                    + "a:= (SELECT answer FROM sign WHERE ver_id=v AND owner=o);"
                    + "IF a IS NULL THEN RETURN FALSE;"
                    + "END IF;"
                    + "RETURN TRUE;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql';";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION isHead(Integer) RETURNS boolean AS $$ SELECT head=1 FROM employee WHERE id=$1; $$ LANGUAGE SQL;";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION is_doc_opend(v Int, u Int) RETURNS boolean AS $$ "
                    + "DECLARE "
                    + "a timestamp;"
                    + "BEGIN "
                    + "a:=(SELECT visited FROM file_ver_reg WHERE id=v AND user_id=u);"
                    + "IF a IS NULL THEN RETURN FALSE;"
                    + "END IF;"
                    + "RETURN TRUE;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql';";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS blog_structure CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE blog_structure ("
                    + "id int NOT NULL REFERENCES bloges(id) ON DELETE CASCADE,"
                    + "user_id int NOT NULL REFERENCES employee(id) ON DELETE CASCADE,"
                    + "superior int NOT NULL DEFAULT 0,"
                    + "PRIMARY KEY (id, user_id));";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS blog_cat CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE blog_cat ("
                    + "id serial,"
                    + "user_id int NOT NULL REFERENCES employee(id) ON DELETE CASCADE,"
                    + "superior int NOT NULL DEFAULT 0,"
                    + "name varchar(255) NOT NULL,"
                    + "PRIMARY KEY (id, user_id));";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION blog_superior(i Int, o Int) RETURNS Int AS $$ "
                    + "DECLARE "
                    + "a Int;"
                    + "BEGIN "
                    + "a:=(SELECT superior FROM blog_structure WHERE id=i AND user_id=o);"
                    + "IF a IS NULL THEN RETURN 0;"
                    + "END IF;"
                    + "RETURN a;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql';";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION blog_move (i varchar, o varchar, s varchar ) RETURNS void AS $$ "
                    + "BEGIN "
                    + "INSERT INTO blog_structure (id, user_id, superior) VALUES (i::Int, o::Int, s::Int);"
                    + "EXCEPTION "
                    + "WHEN unique_violation THEN "
                    + "UPDATE blog_structure SET superior = s::Int WHERE id=i::Int AND user_id=o::Int; "
                    + "END; "
                    + "$$ LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS blog_invis CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE blog_invis ("
                    + "id int not null REFERENCES bloges(id) ON DELETE CASCADE,"
                    + "user_id int not null REFERENCES employee(id) ON DELETE CASCADE,"
                    + "unique (id, user_id))";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS blog_marked CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE blog_marked ("
                    + "id int not null REFERENCES bloges(id) ON DELETE CASCADE,"
                    + "user_id int not null REFERENCES employee(id) ON DELETE CASCADE,"
                    + "unique (id, user_id))";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION is_blog_marked(i Int, o Int) RETURNS boolean AS $$ "
                    + "DECLARE "
                    + "a Int;"
                    + "BEGIN "
                    + "a:=(SELECT id FROM blog_marked WHERE id=i AND user_id=o);"
                    + "IF a IS NULL THEN RETURN 'f';"
                    + "END IF;"
                    + "RETURN 't';"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql';";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION is_blog_invis(i Int, o Int) RETURNS boolean AS $$ "
                    + "DECLARE "
                    + "a Int;"
                    + "BEGIN "
                    + "a:=(SELECT id FROM blog_invis WHERE id=i AND user_id=o);"
                    + "IF a IS NULL THEN RETURN 'f';"
                    + "END IF;"
                    + "RETURN 't';"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql';";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION blog_re (o Integer, d Integer) RETURNS void AS $$ "
                    + "DECLARE "
                    + "r record;"
                    + "dop Int[];"
                    + "dopres Int[];"
                    + "BEGIN "
                    + "dopres:=ARRAY[d::Int];"
                    + "SELECT dopusk INTO r FROM blogesTMP WHERE owner=o::Int;"
                    + "dop:=r.dopusk;"
                    + "IF dop@>dopres THEN dopres:=dop;"
                    + "ELSE  "
                    + "dopres:=dop||dopres;"
                    + "END IF;"
                    + "UPDATE blogesTMP SET dopusk=dopres WHERE owner=o::Int;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION is_blog_opend(g Int, u Int) RETURNS boolean AS $$ "
                    + "DECLARE "
                    + "a Int;"
                    + "BEGIN "
                    + "a:=(SELECT id FROM registor WHERE global_id=g AND id=u);"
                    + "IF a IS NULL THEN RETURN FALSE;"
                    + "END IF;"
                    + "RETURN TRUE;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql';";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS picsC CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE picsC (id serial, "
                    + "pics_id int NOT NULL REFERENCES gallaries(id) ON DELETE CASCADE, "
                    + "text text, "
                    + "owner int NOT NULL REFERENCES employee(id) ON DELETE CASCADE, "
                    + "time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                    + "PRIMARY KEY (id))";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION PicCommentsList (i varchar) RETURNS varchar AS $$ "
                    + "DECLARE "
                    + "list varchar;"
                    + "r RECORD;"
                    + "BEGIN "
                    + "list:='<table id=\"listTable\">'; "
                    + "FOR r IN SELECT text, get_fio(owner) AS fio, properTime(time) AS t, owner FROM picsC WHERE pics_id=i::Int ORDER BY TIME DESC "
                    + "LOOP "
                    + "list:=list||'<tr><td class=\"empIMG\"><img src=\"IMG?id='||r.owner||'\" class=\"exxo-shadow1\" ></td><td class=\"tdComment\"><div class=\"fio\"><a href=\"empCard.jsp?id='||r.owner||'\">'||get_fio(r.owner::Int)||'</a><span class=\"time\">'||r.t||'</span></div><div class=\"tdName\">'||r.text||'</div></td></tr>'; "
                    + "END LOOP; "
                    + "list:=list||'</table>';"
                    + "IF list='<table id=\"listTable\"></table>' THEN list:='<table id=\"listTable\"><tr><td id=\"nocomment\">Комментариев нет. Вы можете стать  первым!</td></tr></table>'; "
                    + "END IF; "
                    + "RETURN list; "
                    + "END; "
                    + "$$ LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION gal_cat (o Int) RETURNS Int AS $$ "
                    + "DECLARE "
                    + "res Int; "
                    + "BEGIN "
                    + "res=(SELECT count(*) FROM gallaries WHERE owner=o AND file=0); "
                    + "RETURN res; "
                    + "END; "
                    + "$$ LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION gal_pic (o Int) RETURNS Int AS $$ "
                    + "DECLARE "
                    + "res Int;"
                    + "BEGIN "
                    + "res=(SELECT count(*) FROM gallaries WHERE owner=o AND file=1); "
                    + "RETURN res;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql'";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS personal CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE personal ("
                    + "owner int NOT NULL REFERENCES employee(id) ON DELETE CASCADE, "
                    + "structure text,"
                    + "UNIQUE (owner))";
            stmt.executeUpdate(sql);

            sql = "CREATE OR REPLACE FUNCTION personal (i Varchar, t Varchar) RETURNS void AS $$ "
                    + "BEGIN "
                    + "INSERT INTO personal VALUES(i::Int, t);"
                    + "EXCEPTION WHEN unique_violation THEN  "
                    + "UPDATE personal SET structure=t WHERE owner=i::Int;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql';";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS ips CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE ips (id int, "
                    + "t timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                    + "ip varchar);";
            stmt.executeUpdate(sql);

            sql = "CREATE INDEX ips_id_idx ON ips (id);";
            stmt.executeUpdate(sql);

            sql = "CREATE INDEX ips_t_idx ON ips (t);";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS docPoket CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE docPoket (id int not null REFERENCES employee(id) ON DELETE CASCADE, "
                    + "doc int not null REFERENCES files(id) ON DELETE CASCADE, "
                    + "ser varchar(10) not NULL DEFAULT 'blog', "
                    + "PRIMARY KEY (id, doc, ser));";
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
                    + "FOR r IN SELECT doc FROM docPoket WHERE id=o AND ser='blog' "
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

            sql = "DROP TABLE IF EXISTS roles CASCADE";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE roles ( "
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
            sql = "INSERT INTO roles VALUES ('boss','допущен почти ко всем функциям портала. Может назначать роли сотрудникам. Не может осуществлять операции с документами в разделах документов компании. Лишен надзорных функций в фотоальбомах, документообороте, сообщениях (но может добавить их, присвоив себе соответствующие роли). Выполняет контрольные функции в сервисе «Стандартные бизнес-процессы»','t'),('master','допущен к редактированию страниц портала, меню, таблиц стилей, добавлению и удалению страниц, изменению логотипа и слогана, редактированию страниц подразделений. Осуществляет модерацию сервиса «Доска объявлений»','t'),('staff','допущен ко всем операциям с корпоративным справочником, кроме изменения ролей сотрудников и редактирования проектов и рабочих групп','t'),('photo_editor','допущен к редактированию фотоальбомов компании. Может блокировать фотографии других пользователей, выполняя надзорные функции','t'),('resources_editor','допущен к редактированию сервиса «Переговорные». Может изменять записи других сотрудников в журналах использования переговорных','t'),('news_editor','допущен к добавлению, редактированию и удалению новостей компании','t'),('business_process_editor','добавляет, удаляет и редактирует бизнес-процессы','t'),('business_process_inspector','выполняет контроль исполнения операций в сервисе «Стандартные бизнес-процессы»','t'),('tags_editor','формирует структуру тегов Базы знаний','t'),('tags_expert','не используется','t'),('unit_editor','редактирует страницу подразделения, сотрудником которого он является','t'),('projects_editor','формирует и редактирует списки проектов и рабочих групп','t'),('files_security','осуществляет контрольные функции в сервисе «Документооборот». Может блокировать документы других сотрудников. Поскольку имеет доступ ко всем документам всех сотрудников, следует использовать данную роль осторожно','t'),('general_files_editor','осуществляет операции с документами в разделе «общие компании»','t'),('in_files_editor','осуществляет операции с документами в разделе «входящие компании»','t'),('out_files_editor',' осуществляет операции с документами в разделе «исходящие компании»','t'),('inner_files_editor','осуществляет операции с документами в разделе «внутренние компании»','t'),('security','осуществляет контрольные функции в сервисах «Документооборот», «Фотоальбомы», «Сообщения». Может блокировать документы, сообщения, фотографии других сотрудников. Поскольку имеет доступ ко всем документам и сообщениям всех сотрудников, следует использовать данную роль осторожно','t'),('blog_security','осуществляет контрольные функции в сервисе «Сообщения». Может блокировать сообщения других сотрудников. Поскольку имеет доступ ко всем сообщениям всех сотрудников, следует использовать данную роль осторожно','t'),('roles_editor','редактирует список ролей','t'),('roles_staff','назначает роли сотрудникам','t'),('vote_editor','добавляет, редактирует опросы в сервисе «Опросы и голосования». Имеет доступ к результатам опросов','t')";
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
            con.commit();

            sql = "DROP SEQUENCE IF EXISTS rating_seq CASCADE;";
            stmt.executeUpdate(sql);

            sql = "CREATE SEQUENCE rating_seq;";
            stmt.executeUpdate(sql);
            con.commit();

            sql = "DROP TABLE IF EXISTS rating CASCADE;";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE rating ("
                    + "id int NOT NULL DEFAULT NEXTVAL('rating_seq'),"
                    + "name varchar NOT NULL,"
                    + "description text,"
                    + "type varchar NOT NULL,"
                    + "UNIQUE(id))";
            stmt.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS employeeVoice CASCADE;";
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
            con.commit();

            stmt.close();
            con.close();
        }

        res.sendRedirect("index.xhtml");
    }

    private void startSQL() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://" + BASE.Ver.getDBHost() + "/" + BASE.Ver.getDBName();
        Properties properties = BASE.Ver.getDBProp();
        con = DriverManager.getConnection(url, properties);
        stmt = con.createStatement();
    }

    public boolean isDemo() throws ClassNotFoundException {
        boolean r = false;
        try {
            startSQL();
            String sql = "SELECT key='iLikeEXXO' FROM register WHERE reg='EXXO' LIMIT 1";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                r = rs.getBoolean(1);
            }
            rs.close();
            stmt.close();
            con.close();
            return r;
        } catch (SQLException ex) {
            return true;
        }
    }
}
