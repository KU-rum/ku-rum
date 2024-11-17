package ku_rum.backend.domain.friend.dto.response;

import ku_rum.backend.domain.friend.domain.Friend;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendListResponse {
    private Long id;

    private String name;

    @Builder
    private FriendListResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static FriendListResponse of(Friend friend) {
        return FriendListResponse.builder()
                .id(friend.getToUser().getId())
                .name(friend.getToUser().getNickname())
                .build();
    }
}
