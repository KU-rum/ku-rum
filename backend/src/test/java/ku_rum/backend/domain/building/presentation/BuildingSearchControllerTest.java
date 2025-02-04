package ku_rum.backend.domain.building.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import ku_rum.backend.config.RestDocsTestSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class BuildingSearchControllerTest extends RestDocsTestSupport {
  @Autowired
  private ObjectMapper objectMapper;


}