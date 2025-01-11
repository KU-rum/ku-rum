package ku_rum.backend.domain.friend.dto.response;

import ku_rum.backend.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendFindResponse {

    private Long id;
    private String nickname;

    @Builder
    private FriendFindResponse(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public static FriendFindResponse from(User user) {
        return FriendFindResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build();
    }
}
