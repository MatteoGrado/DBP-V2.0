package de.grado.backofficeservice.service;

import de.grado.backofficeservice.dto.CustomerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BackOfficeService
{
    private final WebClient webClient;

    @Cacheable(value = "customer")
    public List<CustomerDTO> getCustomers()
    {
        log.info("Getting Customer list");
        List<CustomerDTO> customers = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/customerList")
                        .build())
                .retrieve()
                .bodyToFlux(CustomerDTO.class)
                .collectList()
                .block();

        if (!CollectionUtils.isEmpty(customers)) {
            return customers;
        } else  {
            log.info("No customers found");
            throw new RuntimeException("No customers found");
        }
    }
}
