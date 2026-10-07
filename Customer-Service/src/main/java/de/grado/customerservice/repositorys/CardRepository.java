package de.grado.customerservice.repositorys;

import de.grado.customerservice.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface CardRepository extends JpaRepository<Card, BigInteger>
{
}
