package project.sso.sso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.sso.sso.entity.Term;

import java.util.List;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

    // Assume latest term id is the current term id.
    @Query("SELECT MAX(t.id) FROM Term t")
    Long getCurrentTermId();

    @Query("SELECT DISTINCT t.id FROM Term t")
    List<Long> getAllTermId();

}
