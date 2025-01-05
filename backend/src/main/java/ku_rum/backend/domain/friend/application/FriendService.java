package ku_rum.backend.domain.friend.application;

import ku_rum.backend.domain.friend.domain.Friend;
import ku_rum.backend.domain.friend.domain.repository.FriendRepository;
import ku_rum.backend.domain.friend.dto.request.FriendFindRequest;
import ku_rum.backend.domain.friend.dto.request.FriendListRequest;
import ku_rum.backend.domain.friend.dto.response.FriendFindResponse;
import ku_rum.backend.domain.friend.dto.response.FriendListResponse;
import ku_rum.backend.domain.user.domain.User;
import ku_rum.backend.domain.user.domain.repository.UserRepository;
import ku_rum.backend.global.exception.friend.NoFriendsException;
import ku_rum.backend.global.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ku_rum.backend.global.response.status.BaseExceptionResponseStatus.NO_FRIENDS_FOUND;
import static ku_rum.backend.global.response.status.BaseExceptionResponseStatus.USER_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public List<FriendListResponse> getMyLists(final FriendListRequest friendListRequest) {
        User user = userRepository.findUserById(friendListRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        List<Friend> friends = friendRepository.findAllByFromUser(user);

        if (friends.isEmpty()){
            throw new NoFriendsException(NO_FRIENDS_FOUND);
        }

        return friends.stream()
                .map(FriendListResponse::from)
                .toList();
    }

    public FriendFindResponse findByNameInLists(final FriendFindRequest friendFindRequest) {
        User fromUser = userRepository.findUserById(friendFindRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        User toUser = userRepository.findUserByNickname(friendFindRequest.getNickname())
                        .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        if (!friendRepository.existsByFromUserAndToUser(fromUser, toUser))
            throw new NoFriendsException(NO_FRIENDS_FOUND);

        return FriendFindResponse.from(toUser);
    }

}
