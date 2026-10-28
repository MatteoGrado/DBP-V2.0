package de.grado.backofficeservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class BackOfficeService
{
    private final WebClient webClient;

    @Cacheable(value = "customer")
    public void getCustomers()
    {
    }
}
