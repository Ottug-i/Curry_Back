package com.ottugi.curry.domain.rank;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankRepository extends CrudRepository<Rank, String> {
    Rank findByName(String name);
    List<Rank> findAllByOrderByScoreDesc();
}
