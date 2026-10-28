package de.grado.customerservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;
    private String lastName;
    private String dateOfBirth;

    private String email;
    private String phoneNumber;

    private String address;
    private String zipCode;
    private String city;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Account> accounts = new HashSet<>();

    public void addAccount(Account account)
    {
        accounts.add(account);
        account.setCustomer(this);
    }

    public void removeAccount(Account account)
    {
        accounts.remove(account);
        account.setCustomer(null);
    }
}
