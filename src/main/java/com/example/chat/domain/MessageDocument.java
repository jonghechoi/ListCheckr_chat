package com.example.chat.domain;

import com.example.chat.domain.dto.MessageRequestDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Document(collection = "chat-collection")
@NoArgsConstructor
public class MessageDocument {
    @Id
    private String boardId;
    private Set<String> members;
    private String createTime;
    private List<MessageContent> messageContentList = new ArrayList<>();

    public MessageDocument toEntity(MessageRequestDto dto) {
        MessageDocument messageDocument = new MessageDocument();
        messageDocument.setBoardId(dto.getBoardId());
        messageDocument.setMembers(dto.getSender());
        messageDocument.setCreateTime(dto.getTime());
        messageDocument.setMessageContentList(dto.getMessage());

        return messageDocument;
    }

    private void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    private void setMembers(String member) {
        this.members.add(member);
    }

    private void setCreateTime(String time) {
        this.createTime = time;
    }

    private void setMessageContentList(MessageContent messageContent) {
        this.messageContentList.add(messageContent);
    }
}
