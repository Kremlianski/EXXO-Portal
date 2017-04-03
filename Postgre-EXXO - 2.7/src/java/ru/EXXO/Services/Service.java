package ru.EXXO.Services;

import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import ru.EXXO.Status;

public class Service implements java.io.Serializable, ru.EXXO.changeable {

    private int id;
    private String name;
    private int owner;
    private String description;
    private int supervisor;
    private String template;
    private String type;
    private int mintodo;
    private int mintodeside;
    private int mintoopen;
    private boolean stoped;
    private Status s;

    public Service(int id, String name, int owner, String description, int supervisor, String template, String type, int mintodo, int mintodeside, int mintoopen, boolean stoped, Status s) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.supervisor = supervisor;
        this.template = template;
        this.type = type;
        this.mintodo = mintodo;
        this.mintodeside = mintodeside;
        this.mintoopen = mintoopen;
        this.stoped = stoped;
        this.s = s;
    }

    public Status getStatus() {
        return s;
    }

    public void setStatus(Status s) {
        this.s = s;
    }

    public void applyChanges(HttpSession ses) throws SQLException {
        if (s == Status.NEW) {
            new ServiceDAO().addService(this);
            s = Status.CLEAN;
        } else if (s == Status.MODIFIED) {
            new ServiceDAO().modifyService(this);
            s = Status.CLEAN;
        }
    }

    public void remove(HttpSession ses) throws SQLException {
        if (s != Status.NEW) {
            new ServiceDAO().removeService(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(int supervisor) {
        this.supervisor = supervisor;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMintodo() {
        return mintodo;
    }

    public void setMintodo(int mintodo) {
        this.mintodo = mintodo;
    }

    public int getMintodeside() {
        return mintodeside;
    }

    public void setMintodeside(int mintodeside) {
        this.mintodeside = mintodeside;
    }

    public int getMintoopen() {
        return mintoopen;
    }

    public void setMintoopen(int mintoopen) {
        this.mintoopen = mintoopen;
    }

    public boolean isStoped() {
        return stoped;
    }

    public void setStoped(boolean stoped) {
        this.stoped = stoped;
    }

}
