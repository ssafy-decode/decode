package com.decode.web.domain.user.enums;

public enum Point {
    LOGIN("로그인", 10),
    QUESTION("질문 등록", 30),
    ANSWER("답변 등록", 70),
    ADOPT("답변 채택", 100);

    private static final int EXP_RATIO = 3;

    private final String type;
    private final int point;

    Point(String type, int point) {
        this.type = type;
        this.point = point;
    }

    public int point() {
        return this.point;
    }

    public int exp() {
        return this.point * EXP_RATIO;
    }
}
