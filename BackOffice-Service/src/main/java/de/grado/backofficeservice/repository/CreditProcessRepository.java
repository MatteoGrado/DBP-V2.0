package de.grado.backofficeservice.repository;

import de.grado.backofficeservice.model.CreditProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CreditProcessRepository extends JpaRepository<CreditProcess, UUID>
{
}
