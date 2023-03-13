package ru.stazaev.entity.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {
    private Long telegramUserId;
    private String city;
    private int code;
}
