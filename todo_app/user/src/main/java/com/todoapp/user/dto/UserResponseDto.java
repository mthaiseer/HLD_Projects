package com.todoapp.user.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class UserResponseDto {

    Long id;
    private String name;
    private String email;
}
