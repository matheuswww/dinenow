package com.github.matheuswwwp.dinenow.repository.admin;

import com.github.matheuswwwp.dinenow.model.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    @Query("SELECT a FROM Admin a WHERE a.email = :email")
    Optional<Admin> findByAdminEmail(@Param("email") String email);
}