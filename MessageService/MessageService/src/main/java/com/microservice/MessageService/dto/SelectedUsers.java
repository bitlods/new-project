package com.microservice.MessageService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectedUsers {
    private Long postId;
    private List<Long> checkedUser;
}
