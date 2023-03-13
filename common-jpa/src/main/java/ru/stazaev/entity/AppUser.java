package ru.stazaev.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.stazaev.entity.enums.UserState;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long telegramUserId;
    private String name;
    private String city;
    private int code;
    @Enumerated(EnumType.STRING)
    private UserState state;
}
