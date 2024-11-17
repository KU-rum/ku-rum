package ku_rum.backend.domain.friend.domain.repository;

import ku_rum.backend.domain.friend.domain.Friend;
import ku_rum.backend.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findAllByFromUser(User user);

    boolean existsByFromUserAndToUser(User fromUser, User toUser);
}