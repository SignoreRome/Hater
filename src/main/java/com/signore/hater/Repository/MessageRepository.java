package com.signore.hater.Repository;

import com.signore.hater.Entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findByTag(String tag, Pageable pageable);
    List<Message> findByTag(String tag);

    Page<Message> findAll(Pageable pageable);

}
