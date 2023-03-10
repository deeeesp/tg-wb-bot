package ru.stazaev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stazaev.entity.AppUser;

import java.util.Optional;

public interface AppUserDAO extends JpaRepository<AppUser,Long> {
    Optional<AppUser> findAppUserByTelegramUserId(long id);
}
