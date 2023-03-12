package ru.stazaev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.stazaev.entity.AppUser;
import ru.stazaev.entity.enums.UserState;

import java.util.Optional;

public interface AppUserDAO extends JpaRepository<AppUser,Long> {
    Optional<AppUser> findAppUserByTelegramUserId(long id);

    @Modifying
    @Transactional
    @Query("update AppUser a set a.city =:city, a.state =:state where a.telegramUserId =:id")
    void updateCityByTelegramUserId(@Param("id") long id, @Param("city") String city, @Param("state") UserState status);
}
