package de.grado.userservice;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
@Import(TestcontainersConfiguration.class)
@Testcontainers
public class LoginIntegrationTest
{
    @Container
    static final KeycloakContainer keycloak = new KeycloakContainer("quay.io/keycloak/keycloak:latest")
            .withRealmImportFile("DBP-V2.0-test-realm.json");

    @DynamicPropertySource
    static void keycloakProperties(DynamicPropertyRegistry registry)
    {
        registry.add("keycloak.server-url", keycloak::getAuthServerUrl);
        registry.add("keycloak.realm", () -> "DBP-V2.0-test");
        registry.add("keycloak.client-id", () -> "user-service");
        registry.add("keycloak.client-secret", () -> "ran-test-secret-12345678910");
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
                () -> keycloak.getAuthServerUrl() + "/realms/DBP-V2.0-test");
        registry.add("spring.cloud.consul.enabled", () -> "false");
    }

    @BeforeAll
    static void prepareTestUser()
    {
        UserRepresentation user = keycloak.getKeycloakAdminClient()
                .realm("DBP-V2.0-test")
                .users()
                .search("matteo.grado")
                .stream()
                .findFirst()
                .orElseThrow();

        user.setEmailVerified(true);
        user.setRequiredActions(List.of());
        user.setEnabled(true);
        keycloak.getKeycloakAdminClient()
                .realm("DBP-V2.0-test")
                .users()
                .get(user.getId())
                .update(user);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldLoginAndReturnAccessToken()
    {

        Map<String, String> loginRequest = Map.of(
                "username", "matteo.grado",
                "password", "123"
        );

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "/api/v1/user/login",
                loginRequest,
                Map.class
        );

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
                .isNotNull();

        assertThat(response.getBody())
                .containsKey("access_token");

        assertThat(response.getBody().get("access_token"))
                .isNotNull();
    }
}
