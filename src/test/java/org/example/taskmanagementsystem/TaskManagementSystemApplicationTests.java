package org.example.taskmanagementsystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = { "spring.liquibase.enabled=false" })
@ActiveProfiles(value = "dev")
class TaskManagementSystemApplicationTests {

	@Test
	void contextLoads() {
	}

}
