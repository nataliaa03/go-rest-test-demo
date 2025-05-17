package org.example;

public class Post {
    public int id;
    public int user_id;
    public String title;
    public String body;

    public Post() {
    }

    public Post(int id, int user_id, String title, String body) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.body = body;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
