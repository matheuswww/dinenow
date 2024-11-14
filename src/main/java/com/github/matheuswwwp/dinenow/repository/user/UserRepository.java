package com.github.matheuswwwp.dinenow.repository.user;

import com.github.matheuswwwp.dinenow.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findByUserId(@Param("id") UUID id);
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByUserEmail(@Param("email") String email);
}
