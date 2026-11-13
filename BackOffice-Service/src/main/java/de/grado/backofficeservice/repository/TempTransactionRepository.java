package de.grado.backofficeservice.repository;

import de.grado.backofficeservice.model.TempTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TempTransactionRepository extends JpaRepository<TempTransaction, UUID>
{
    List<TempTransaction> findByFromIban(String iban);

    List<TempTransaction> findByToIban(String iban);

    List<TempTransaction> findByFromIbanAndToIban(String fromIban, String toIban);
}
