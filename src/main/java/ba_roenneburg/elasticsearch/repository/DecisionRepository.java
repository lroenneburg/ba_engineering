package ba_roenneburg.elasticsearch.repository;

import ba_roenneburg.model.Decision;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface DecisionRepository extends ElasticsearchRepository<Decision, String> {

    @Override
    Optional<Decision> findById(String s);
}
