package io.github.zhyshko.dao;

import io.github.zhyshko.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreDao  extends JpaRepository<Store, UUID> {

}
