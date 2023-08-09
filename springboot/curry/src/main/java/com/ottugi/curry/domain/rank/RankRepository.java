package com.ottugi.curry.domain.rank;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RankRepository extends CrudRepository<Rank, String> {
    Rank findByName(String name);
    List<Rank> findTop10ByOrderByScoreDesc();
}
