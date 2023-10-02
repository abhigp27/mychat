package com.saffron.mychat.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("chats")
public class Chat {

    @Id
    @Column("chat_id")
    private Long chatId;

    @Column("user_id")
    private Long userId; // Consider using a User object for a proper relationship

    @Column("business_id")
    private Long businessId; // Consider using a Business object for a proper relationship

    @Column("other_user_id")
    private Long otherUserId; // Consider using a User object for a proper relationship

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(Long otherUserId) {
        this.otherUserId = otherUserId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

