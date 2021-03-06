package ba_roenneburg.elasticsearch.repository;

import ba_roenneburg.model.Decision;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface DecisionRepository extends ElasticsearchRepository<Decision, String> {

    @Override
    Optional<Decision> findById(String id);

    Optional<Decision> findByEcli(String ecli);

    @Override
    long count();

    @Override
    boolean existsById(String id);

    @Override
    void deleteById(String id);

    @Override
    void deleteAll();

    ArrayList<Decision> findByCourtType(String courtType);
}
