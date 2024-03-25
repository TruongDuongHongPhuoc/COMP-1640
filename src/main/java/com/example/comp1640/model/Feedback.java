package com.example.comp1640.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@Document("FeedbackItem")
public class Feedback {
    @Id
    private String id;
    private String content;
    private String userId;
    private String contributionId;
    private LocalDateTime localDateTime;

    public Feedback(String id, String content, String userId, String contributionId) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.contributionId = contributionId;
        ZoneId hoChiMinhZoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        localDateTime = LocalDateTime.now(hoChiMinhZoneId);

    }
}
