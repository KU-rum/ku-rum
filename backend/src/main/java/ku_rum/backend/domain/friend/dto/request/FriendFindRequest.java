package ku_rum.backend.domain.friend.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendFindRequest {

    private Long userId;
    private String nickname;

    @Builder
    private FriendFindRequest(Long userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
    }

    public static FriendFindRequest of(Long userId, String nickname) {
        return FriendFindRequest.builder().
                userId(userId).
                nickname(nickname).
                build();
    }
}
