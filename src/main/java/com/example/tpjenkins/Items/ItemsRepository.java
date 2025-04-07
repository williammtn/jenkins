package com.example.tpjenkins.Items;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The interface Items repository.
 */
@Repository
public interface ItemsRepository extends JpaRepository<ItemsModel, Long> {}