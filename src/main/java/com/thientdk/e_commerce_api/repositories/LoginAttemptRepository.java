package com.thientdk.e_commerce_api.repositories;

import com.thientdk.e_commerce_api.entities.LoginAttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttemptEntity, String> {
}
