package ku_rum.backend.domain.friend.presentation;

import jakarta.validation.Valid;
import ku_rum.backend.domain.friend.application.FriendService;
import ku_rum.backend.domain.friend.dto.request.FriendFindRequest;
import ku_rum.backend.domain.friend.dto.request.FriendListRequest;
import ku_rum.backend.domain.friend.dto.response.FriendFIndResponse;
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

    @GetMapping
    public BaseResponse<List<FriendListResponse>> getMyLists(@RequestBody @Valid final FriendListRequest friendListRequest) {
        return BaseResponse.ok(friendService.getMyLists(friendListRequest));
    }

    @GetMapping("/find")
    public BaseResponse<FriendFIndResponse> findByNameInLists(@RequestBody @Valid final FriendFindRequest friendFindRequest) {
        return BaseResponse.ok(friendService.findByNameInLists(friendFindRequest));
    }


}
