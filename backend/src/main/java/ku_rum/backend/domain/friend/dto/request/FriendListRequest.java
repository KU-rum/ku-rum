package ku_rum.backend.domain.friend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendListRequest {

    private Long userId;

    public static FriendListRequest from(Long userId) {
        return new FriendListRequest(userId);
    }
}
