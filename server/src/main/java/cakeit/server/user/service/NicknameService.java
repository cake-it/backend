package cakeit.server.user.service;

public interface NicknameService {

    /**
     * 랜덤 닉네임 생성하는 메서드
     * - 닉네임 자동 생성 후, 중복이면 재시도
     */
    public String addRandomNickname();

    /**
     * 닉네임이 중복인지 확인하는 메서드
     *
     */
    public boolean isDuplicatedNickname(String nickname);

}
