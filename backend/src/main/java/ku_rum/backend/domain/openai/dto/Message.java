package ku_rum.backend.domain.openai.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String role;
    private String content;

}
