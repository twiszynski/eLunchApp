package pl.twisz.eLunchApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.twisz.eLunchApp.model.OperationEvidence;

@Repository
public interface OperationEvidenceRepo extends JpaRepository<OperationEvidence, Long> {
}
