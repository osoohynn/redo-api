package com.lsk.redoapi.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류가 발생했습니다."),
    INVALID_INPUT_VALUE(400, "잘못된 입력값입니다."),
    METHOD_NOT_ALLOWED(405, "허용되지 않은 메서드입니다."),

    // Auth
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD(401, "잘못된 비밀번호입니다."),
    DUPLICATE_USERNAME(409, "이미 존재하는 사용자 이름입니다."),

    // 리뷰
    REVIEW_NOT_FOUND(404, "찾을 수 없는 리뷰입니다.");

    private final int status;
    private final String message;
}
