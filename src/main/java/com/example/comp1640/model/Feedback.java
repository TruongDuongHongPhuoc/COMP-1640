package com.example.comp1640.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document("FeedbackItem")
public class Feedback {
    @Id
    private String id;
    private String content;
    private String userId;
    private String contributionId;
    public Feedback(String id, String content, String userId, String contributionId) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.contributionId = contributionId;
    }
}
