package com.ottugi.curry.domain.lately;

import com.ottugi.curry.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LatelyRepository extends JpaRepository<Lately, Long> {
    Lately findTop1ByUserIdOrderByIdDesc(User user);
}
