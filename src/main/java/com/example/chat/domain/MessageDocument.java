package com.example.chat.domain;

import com.example.chat.domain.dto.MessageRequestDto;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Document(collection = "chat-collection")
@NoArgsConstructor
public class MessageDocument {
    @Id
    private String boardId;
    private Set<String> members = new HashSet<>();
    private String createTime;
    private List<MessageContent> messageContentList = new ArrayList<>();

    public MessageDocument(String boardId, String sender, String createTime) {
        this.setBoardId(boardId);
        this.setMembers(sender);
        this.setCreateTime(createTime);
    }

    public MessageDocument(MessageRequestDto dto, List<MessageContent> messageContentList) {
        this.setBoardId(dto.getBoardId());
        this.setMembers(dto.getMessageContent().getSender());
        this.setCreateTime(LocalDateTime.now().toString());
        this.setMessageContentList(messageContentList);
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public void setMembers(String member) {
        this.members.add(member);
    }

    public void setCreateTime(String time) {
        this.createTime = time;
    }

    public void setMessageContentList(List<MessageContent> messageContents) {
        this.messageContentList.addAll(messageContents);
    }

    public List<MessageContent> getMessageContentList() {
        return this.messageContentList;
    }
}