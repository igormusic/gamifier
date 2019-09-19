package com.transactrules.repository;
import com.transactrules.domain.Behaviour;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Behaviour entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BehaviourRepository extends JpaRepository<Behaviour, Long> {

}
