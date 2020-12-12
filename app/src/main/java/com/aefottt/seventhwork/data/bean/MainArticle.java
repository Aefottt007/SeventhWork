package com.aefottt.seventhwork.data.bean;

public class MainArticle {
    String userName;
    String title;
    String content;
    String kind;
    String time;
    String url;

    public MainArticle(String userName, String title, String content, String kind, String time, String url) {
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.kind = kind;
        this.time = time;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getKind() {
        return kind;
    }

    public String getTime() {
        return time;
    }

}
