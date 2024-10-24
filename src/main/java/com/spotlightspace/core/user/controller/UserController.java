package com.spotlightspace.core.user.controller;

import com.spotlightspace.common.annotation.AuthUser;
import com.spotlightspace.core.user.dto.request.UpdateUserRequestDto;
import com.spotlightspace.core.user.dto.response.GetUserResponseDto;
import com.spotlightspace.core.user.service.UserService;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    /**
     * 회원 수정을 진행합니다
     *
     * @param userId               수정할 user의 id를 입력받고
     * @param authUser             로그인한 user의 id를 입력받습니다
     * @param updateUserRequestDto 수정할 정보인, 비밀번호, 닉네임, 생일, 전화번호를 바꿀 수 있습니다
     * @param file                 이미지는 필수가 아닙니다
     * @return 200ok를 반환합니다
     * @throws IOException 이미지 업로드를 위한 exception입니다.
     */
    @PatchMapping("/user/{userId}")
    public ResponseEntity<Void> updateUser(
            @PathVariable Long userId,
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestPart UpdateUserRequestDto updateUserRequestDto,
            @RequestPart(required = false) MultipartFile file) throws IOException {
        userService.updateUser(userId, authUser, updateUserRequestDto, file);
        return ResponseEntity.ok().build();
    }

    /**
     * 사용자 본인을 조회하는 로직입니다
     *
     * @param userId   유저아이디를 입력받습니다
     * @param authUser 현재 로그인중인 유저를 확인합니다.
     * @return 사용자의 정보를 반환받습니다
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<GetUserResponseDto> getUser(
            @PathVariable Long userId,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        return ResponseEntity.ok().body(userService.getUser(userId, authUser.getUserId()));
    }

    /**
     * 유저를 삭제합니다
     * @param userId 유저아이디를 입력받습니다
     * @param authUser 현재 로그인중인 유저 정보를 받아옵니다
     * @return 회원이 삭제됩니다
     */
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long userId,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        userService.deleteUser(userId, authUser.getUserId());
        return ResponseEntity.ok().build();
    }
}
