package ku_rum.backend.domain.friend.dto.response;

import ku_rum.backend.domain.friend.domain.Friend;
import ku_rum.backend.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendFIndResponse {

    private Long id;
    private String nickname;

    @Builder
    private FriendFIndResponse(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public static FriendFIndResponse from(User user) {
        return FriendFIndResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build();
    }
}
