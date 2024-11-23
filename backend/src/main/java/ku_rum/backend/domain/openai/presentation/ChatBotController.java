package ku_rum.backend.domain.openai.presentation;

import jakarta.annotation.PostConstruct;
import ku_rum.backend.domain.openai.dto.GPTRequest;
import ku_rum.backend.domain.openai.dto.GPTResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/api/v1/chatbot")
public class ChatBotController {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Autowired
    private RestTemplate template;

    @GetMapping("/chat")
    public String chat(@RequestParam(name = "prompt") String prompt) {
        try {
            GPTRequest request = new GPTRequest(model, prompt);
            GPTResponse response = template.postForObject(apiURL, request, GPTResponse.class);

            String result = response.getChoices().get(0).getMessage().getContent();
            log.info("result: {}", result);
            return result;
        } catch (Exception e) {
            log.error("API 호출 중 오류 발생: {}", e.getMessage(), e);
            return "오류 발생: " + e.getMessage();
        }
    }

    @PostConstruct
    public void validateUrl() {
        System.out.println("Loaded API URL: " + apiURL);
    }
}
