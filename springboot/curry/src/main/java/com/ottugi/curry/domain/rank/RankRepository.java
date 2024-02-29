package com.ottugi.curry.domain.rank;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository extends CrudRepository<Rank, String> {
    Rank findByName(String name);

    List<Rank> findAllByOrderByScoreDesc();

    List<Rank> findTopNByOrderByScoreDesc(int N);
}
