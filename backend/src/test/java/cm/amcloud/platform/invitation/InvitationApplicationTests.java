package cm.amcloud.platform.invitation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource; 

/**
 * Integration test for the Spring Boot Invitation application context loading.
 * This test verifies that the application's Spring context loads successfully
 * with all its configurations, without requiring a running PostgreSQL instance
 * or a Spring Cloud Config Server during test execution.
 * All configuration properties are provided via @TestPropertySource
 * to simulate a complete deployment environment using an in-memory H2 database.
 */
@SpringBootTest // Loads the full Spring application context.
                // Essential for integration tests that require Spring beans to be started.
@TestPropertySource(properties = {
    // --- Database configuration for tests (using H2 in-memory) ---
    // The JDBC URL for an in-memory H2 database named 'testdb'.
    // DB_CLOSE_DELAY=-1 keeps the database alive as long as the JVM is running,
    // which is useful when multiple tests share the same database instance.
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",

    // --- JPA and Hibernate configuration properties ---
    // These properties are often required for JPA/Hibernate setup, even with an in-memory database.
    // The PostgreSQL dialect is specified to align with production configuration, even if H2 is used for tests.
    "spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect",
    "spring.sql.init.mode=always",
    "spring.jpa.hibernate.ddl-auto=update",
    "spring.jpa.open-in-view=false", // Explicitly disable open-in-view to suppress warning

    // --- Server Port Configuration ---
    "server.port=8084", // Dummy server port for the test context

    // --- Spring Cloud Config Server URL ---
    // If your application connects to a Spring Cloud Config Server.
    "spring.cloud.config.uri=http://localhost:8888", // Dummy Config Server URL
    "spring.cloud.config.enabled=false" // Disable Spring Cloud Config Client for this test context
})
class InvitationApplicationTests {

    /**
     * This test method simply verifies that the Spring application context
     * loads successfully without any errors. It serves as a basic sanity check
     * for the application's overall configuration and bean wiring within a test environment.
     * No specific assertions are needed here, as the test passes if the context loads without exceptions.
     */
    @Test
    void contextLoads() {
        // The test passes if the application context loads without throwing exceptions.
        // This confirms that all required properties are resolved and beans can be created.
    }

}
