package ku_rum.backend.domain.friend.presentation;

import jakarta.validation.Valid;
import ku_rum.backend.domain.friend.application.FriendService;
import ku_rum.backend.domain.friend.dto.request.FriendFindRequest;
import ku_rum.backend.domain.friend.dto.request.FriendListRequest;
import ku_rum.backend.domain.friend.dto.response.FriendFindResponse;
import ku_rum.backend.domain.friend.dto.response.FriendListResponse;
import ku_rum.backend.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
@Validated
public class FriendController {

    private final FriendService friendService;

    /***
     * 내 모든 친구 조회 API
     * @param friendListRequest userId 자신의 유저 아이디
     * @return 모든 조회된 친구 목록
     */
    @GetMapping
    public BaseResponse<List<FriendListResponse>> getMyLists(@RequestBody @Valid final FriendListRequest friendListRequest) {
        return BaseResponse.ok(friendService.getMyLists(friendListRequest));
    }

    /** 닉네임으로 친구 조회 API
     * @param friendFindRequest userId 자신의 유저 아이디, nickname 친구 닉네임
     * @return 반환된 친구 목록
     */
    @GetMapping("/find")
    public BaseResponse<FriendFindResponse> findByNameInLists(@RequestBody @Valid final FriendFindRequest friendFindRequest) {
        return BaseResponse.ok(friendService.findByNameInLists(friendFindRequest));
    }


}
