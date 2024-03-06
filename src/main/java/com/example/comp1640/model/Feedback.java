package com.example.comp1640.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;


@Document("FeedbackItem")
public class Feedback {
    @Id
    private String id;
    private String content;
    private String userId;
    public Feedback(String id, String content, String userId) {
        this.id = id;
        this.content = content;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
