package de.grado.customerservice.service;

import de.grado.customerservice.dto.CreateCustomerRequest;
import de.grado.customerservice.model.Account;
import de.grado.customerservice.model.Card;
import de.grado.customerservice.model.CardType;
import de.grado.customerservice.model.Customer;
import de.grado.customerservice.repositorys.CustomerRepository;
import io.sentry.Sentry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.List;
import java.util.Optional;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService
{
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final Pattern FIRST_NAME_PATTERN = fieldPattern(
            "first\\s*name|firstname|given\\s*name|vorname");
    private static final Pattern LAST_NAME_PATTERN = fieldPattern(
            "last\\s*name|lastname|surname|family\\s*name|nachname");
    private static final Pattern DATE_OF_BIRTH_PATTERN = fieldPattern(
            "date\\s*of\\s*birth|dateofbirth|dob|geburtsdatum");

    private final OcrService ocrService;
    private final CustomerRepository customerRepository;

    public List<Customer> getCustomerList()
    {
        log.info("Getting customer list");
        return customerRepository.findAll();
    }

    public Optional<Customer> findCustomer(BigInteger id)
    {
        log.info("Getting customer by id: {}", id);
        return customerRepository.findById(id);
    }

    @Transactional
    public void createCustomer(CreateCustomerRequest request)
    {
        Customer customer = new Customer();

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAddress(request.getAddress());
        customer.setZipCode(request.getZipCode());
        customer.setCity(request.getCity());
        customer.setDateOfBirth(request.getDateOfBirth());

        customerRepository.save(customer);
        log.info("Created customer with id {}", customer.getId());
    }

    @Transactional
    public Optional<Customer> authenticateCustomer(MultipartFile file) throws IOException, InterruptedException
    {
        log.info("Authenticating customer with file {}", file.getOriginalFilename());
        String text = ocrService.extractText(file);

        String firstName = extractField(text, FIRST_NAME_PATTERN);
        String lastName = extractField(text, LAST_NAME_PATTERN);
        String dateOfBirth = extractField(text, DATE_OF_BIRTH_PATTERN);

        if (firstName == null || lastName == null || dateOfBirth == null)
        {
            log.warn("Could not extract all identity fields from uploaded document");
            return Optional.empty();
        }

        String normalizedFirstName = normalizeText(firstName);
        String normalizedLastName = normalizeText(lastName);
        String normalizedDateOfBirth = normalizeDate(dateOfBirth);

        Optional<Customer> authenticatedCustomer = customerRepository.findAll().stream().filter(customer ->
                normalizedFirstName.equals(normalizeText(customer.getFirstName()))
                        && normalizedLastName.equals(normalizeText(customer.getLastName()))
                        && normalizedDateOfBirth.equals(normalizeDate(customer.getDateOfBirth())))
                .findFirst();

        if (authenticatedCustomer.isEmpty())
        {
            log.info("Customer authentication failed");
            return Optional.empty();
        }

        Customer customer = authenticatedCustomer.get();
        Account account;
        if (customer.getAccounts().isEmpty())
        {
            account = new Account();
            account.setAccountNumber(generateNumber(10));
            customer.addAccount(account);
        }
        else
        {
            account = customer.getAccounts().iterator().next();
        }

        if (account.getCards().isEmpty())
        {
            Card card = new Card();
            card.setCardNumber(generateNumber(16));
            card.setCardType(CardType.DEBIT);
            card.setExpirationDate(LocalDate.now().plusYears(5));
            account.addCard(card);

            customerRepository.save(customer);
            log.info("Created card for authenticated customer {}", customer.getId());
        }

        log.info("Customer authentication succeeded for customer {}", customer.getId());
        return Optional.of(customer);
    }

    private static String generateNumber(int length)
    {
        StringBuilder number = new StringBuilder(length);
        for (int index = 0; index < length; index++)
        {
            number.append(SECURE_RANDOM.nextInt(10));
        }
        return number.toString();
    }

    private static Pattern fieldPattern(String fieldNames)
    {
        return Pattern.compile("(?im)^[ \\t]*(?:" + fieldNames
                + ")[ \\t]*(?:[:=])[ \\t]*(.+?)\\s*$|^[ \\t]*(?:"
                + fieldNames + ")[ \\t]*\\r?\\n[ \\t]*(.+?)\\s*$", Pattern.MULTILINE);
    }

    private static String extractField(String text, Pattern pattern)
    {
        Matcher matcher = pattern.matcher(text);
        if (!matcher.find())
        {
            return null;
        }

        String value = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
        return value == null || value.isBlank() ? null : value.trim();
    }

    private static String normalizeText(String value)
    {
        if (value == null)
        {
            return "";
        }

        String withoutAccents = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return withoutAccents.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]", "");
    }

    private static String normalizeDate(String value)
    {
        if (value == null)
        {
            return "";
        }

        String cleaned = value.trim();
        DateTimeFormatter[] formats = {
                DateTimeFormatter.ofPattern("dd.MM.uuuu"),
                DateTimeFormatter.ofPattern("dd/MM/uuuu"),
                DateTimeFormatter.ofPattern("dd-MM-uuuu"),
                DateTimeFormatter.ISO_LOCAL_DATE
        };
        DateTimeParseException lastParseException = null;

        for (DateTimeFormatter format : formats)
        {
            try
            {
                return LocalDate.parse(cleaned, format).toString();
            }
            catch (DateTimeParseException e)
            {
                lastParseException = e;
            }
        }

        Sentry.captureException(lastParseException);
        throw new IllegalArgumentException("Invalid date of birth: " + value, lastParseException);
    }
}
