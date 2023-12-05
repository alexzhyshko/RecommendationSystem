package io.github.zhyshko.dao.user;

import io.github.zhyshko.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findByExternalIdAndStoreId(UUID externalId, UUID storeId);

}
