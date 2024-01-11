package com.example.appdocbao;

import java.io.Serializable;

public class DBmysql implements Serializable {
    private int Id;
    private String Name , Link;

    public DBmysql(int id, String name, String link) {
        Id = id;
        Name = name;
        Link = link;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }
}
