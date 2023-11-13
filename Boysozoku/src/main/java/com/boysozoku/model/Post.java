package com.boysozoku.model;

import java.sql.Date;

public class Post {
    BuildBean build;

    Date postDate;

    String User;

    public Post(BuildBean build, Date postDate, String user) {
        this.build = build;
        this.postDate = postDate;
        User = user;
    }

    public BuildBean getBuild() {
        return build;
    }

    public void setBuild(BuildBean build) {
        this.build = build;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }
}
