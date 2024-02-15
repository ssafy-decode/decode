package com.decode.web.domain.user.service;

import com.decode.web.domain.common.redis.RedisService;
import com.decode.web.domain.tag.repository.UserTagRepository;
import com.decode.web.domain.user.dto.FindPasswordDto;
import com.decode.web.domain.user.dto.RankResponseDto;
import com.decode.web.domain.user.dto.RequestUserTagDto;
import com.decode.web.domain.user.dto.ResponseUserProfileDto;
import com.decode.web.domain.user.dto.UserRankInfoDto;
import com.decode.web.domain.user.enums.Tier;
import com.decode.web.domain.user.mapper.ResponseUserProfileMapper;
import com.decode.web.domain.user.repository.UserInfoRepository;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.entity.AnswerEntity;
import com.decode.web.entity.UserInfoEntity;
import com.decode.web.entity.UserProfileEntity;
import com.decode.web.entity.UserTagEntity;
import com.decode.web.exception.UserException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserInfoRepository userInfoRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserTagRepository userTagRepository;
    private final RedisService redisService;
    private final ResponseUserProfileMapper responseUserProfileMapper;

    @Override
    public UserInfoEntity getUserById(Long id) {
        UserInfoEntity user = userInfoRepository.getReferenceById(id);
        user.getUserProfileEntity().setTier(Tier.expToTier(user.getUserProfileEntity().getExp()));
        return user;
    }

    @Override
    public UserProfileEntity getUserProfileById(Long id) {
        Optional<UserProfileEntity> profile = userProfileRepository.findById(id);
        log.info("id: {}", id);
        if (profile.isPresent()) {

            UserProfileEntity userProfile = profile.get();
            userProfile.setTier(Tier.expToTier(profile.get().getExp()));
            return userProfile;
        }
        return null;
    }

    @Override
    public List<UserInfoEntity> getAllUser() {

        return userInfoRepository.findAll();
    }

    @Override
    public boolean emailDupCheck(String email) {
        return userInfoRepository.findByEmail(email).isEmpty();
    }

    @Override
    public boolean nickDupCheck(String nickname) {
        return userProfileRepository.findByNickname(nickname).isEmpty();
    }

    @Override
    public boolean pwCheck(String password) {
        // 영어/숫자/특수문자 조합으로 8자리 이상
        return password.length() >= 8
                && password.matches(".*[a-zA-Z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[~!@#$%^&*()_+|<>?:{}].*");
    }

    @Override
    @Transactional
    public Long createUser(UserInfoEntity user, String nickname) {

        UserProfileEntity profile = UserProfileEntity.builder()
                .nickname(nickname)
                .exp(0)
                .tier(Tier.BRONZE.getTier())
                .profileImg("default")
                .point(5000)
                .coin(0)
                .build();

        profile.setUserInfoEntity(user);
        userProfileRepository.save(profile);
        if (user.getId() == null) {
            throw new UserException("회원가입 실패");
        }
        return user.getId();
    }

    @Override
    public UserInfoEntity getUserByEmail(String email) throws UsernameNotFoundException {
        UserInfoEntity user = userInfoRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return user;
    }

    @Override
    @Transactional
    public void updateUserInfo(Long id, String password) {
        UserInfoEntity user = userInfoRepository.getReferenceById(id);
        user.updateInfo(password);

    }


    @Override
    @Transactional
    public void updateUserProfile(Long id, UserProfileEntity profile) {
        UserProfileEntity user = userProfileRepository.getReferenceById(id);
        user.updateProfile(profile);
    }

    @Override
    public boolean pwConfirm(Long id, String password) {
        UserInfoEntity user = userInfoRepository.getReferenceById(id);
        return user.getPassword().equals(password);
    }

    @Override
    public String findEmail(String name, String phoneNumber, String birth) {
        UserInfoEntity user = userInfoRepository.findByNameAndPhoneNumberAndBirth(name, phoneNumber,
                        birth)
                .orElseThrow(() -> new UsernameNotFoundException("아이디 찾기 실패"));
        return user.getEmail();
    }

    @Override
    public boolean findUserByEmailAndNameAndPhoneNumberAndBirth(FindPasswordDto findPasswordDto) {
        return userInfoRepository.findByEmailAndNameAndPhoneNumberAndBirth(
                findPasswordDto.getEmail(),
                findPasswordDto.getName(),
                findPasswordDto.getPhoneNumber(),
                findPasswordDto.getBirth()
        ).isPresent();
    }


    @Override
    @Transactional
    public void addUserTag(RequestUserTagDto requestUserTagDto) {
        Long userId = requestUserTagDto.getUserId();
        List<Long> tagIds = requestUserTagDto.getTagIdList();
        for (Long tagId : tagIds) {
            userTagRepository.save(UserTagEntity.builder()
                    .userProfile(userProfileRepository.getReferenceById(userId)).tagId(tagId)
                    .build());
        }


    }

    @Override
    @Transactional
    public void updateUserTag(RequestUserTagDto requestUserTagDto) {
        Long userId = requestUserTagDto.getUserId();
        List<Long> tagIds = requestUserTagDto.getTagIdList();
        List<UserTagEntity> userTagEntities = userTagRepository.findAllByUserProfile(
                userProfileRepository.getReferenceById(userId));
        userTagRepository.deleteAll(userTagEntities);
        for (Long tagId : tagIds) {
            userTagRepository.save(UserTagEntity.builder()
                    .userProfile(userProfileRepository.getReferenceById(userId)).tagId(tagId)
                    .build());
        }

    }

    @Override
    @Transactional
    public void setAttendance(String email) {
        LocalDate date = LocalDate.now();
        String key = "ATD:" + email;
        String value = date.toString();
        redisService.setValueForSet(key, value);
    }

    @Override
    public Set<String> getAttendance(Long id) {
        String key = "ATD:" + userInfoRepository.getReferenceById(id).getEmail();
        return redisService.getValuesForSet(key);
    }

//    @Override
//    @Transactional
//    public void setExp(Long id, int exp) {
//        LocalDate date = LocalDate.now();
//        String email = userInfoRepository.getReferenceById(id).getEmail();
//        String key = "EXP:" + email;
//        String hashKey = date.toString();
//        redisService.incrementValueForHash(key, hashKey, exp);
//        // 지금까지 경험치 총합에 += exp 해야함
//        UserProfileEntity profile = userProfileRepository.findById(id)
//                .orElseThrow(() -> new UserException("유저 정보가 없습니다."));
//        profile.setExp(profile.getExp() + exp);
//    }

    @Override
    public Map<String, Integer> getExp(Long id) {
        String email = userInfoRepository.getReferenceById(id).getEmail();
        String key = "EXP:" + email;
        return redisService.getValuesForHash(key);
    }

    @Override
    public List<UserProfileEntity> getRank() {
        List<UserProfileEntity> rank = userProfileRepository.findAll();
        rank.sort((o1, o2) -> o2.getExp() - o1.getExp());
        return rank;
    }

    @Override
    public ResponseUserProfileDto getUserProfileDtoById(Long userId) {
        UserProfileEntity userProfileEntity = userProfileRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(
                        "user not found with id: " + userId));
        userProfileEntity.setTier(Tier.expToTier(userProfileEntity.getExp()));
        ResponseUserProfileDto userProfileDto = responseUserProfileMapper.toDto(userProfileEntity);
        List<UserTagEntity> userTagEntities = userProfileEntity.getUserTags();
        List<Long> userTagIdList = userTagEntities.stream()
                .map(UserTagEntity::getTagId)
                .toList();
        userProfileDto.setUserTagList(userTagIdList);

        return userProfileDto;
    }

    @Override
    public List<RankResponseDto> getRankV2() {
        List<UserProfileEntity> userProfiles = userProfileRepository.findAll(); // 사용자 프로필 엔티티들을 가져옴

        // UserProfileEntity 리스트를 RankResponseDto 리스트로 매핑
        List<RankResponseDto> rankResponseDtos = userProfiles.stream()
                .map(this::mapToRankResponseDto)
                .collect(Collectors.toList());

        return rankResponseDtos;
    }

    @Override
    public UserRankInfoDto getRankByUserId(Long userId) {
        // Get user profile by userId
        UserProfileEntity userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "User with userId " + userId + " not found."));

        // Calculate user rank based on exp (dummy calculation for now)
        int myRank = calculateRank(userProfile.getExp());

        // Get total user count (dummy value for now)
        int totalUserCount = userProfileRepository.findAll().size();

        return UserRankInfoDto.builder()
                .userId(userProfile.getId())
                .nickname(userProfile.getNickname())
                .tier(Tier.expToTier(userProfile.getExp()))
                .profileImg(userProfile.getProfileImg())
                .exp(userProfile.getExp())
                .myRank(myRank)
                .totalUserCount(totalUserCount)
                .answerCount(userProfile.getAnswers().size())
                .adoptCount((int) userProfile.getAnswers()
                        .stream()
                        .filter(AnswerEntity::isAdopted)
                        .count())
                .followerCount(userProfile.getFollowings().size())
                .followCount(userProfile.getFollowers().size())
                .build();
    }

    private int calculateRank(int exp) {
        // 더 높은 랭크를 가진 사용자는 더 높은 경험치를 가지고 있다고 가정합니다.
        List<UserProfileEntity> usersSortedByExp = userProfileRepository.findAllByOrderByExpDesc();

        // 현재 사용자의 랭크를 찾습니다.
        int myRank = 1;
        for (UserProfileEntity user : usersSortedByExp) {
            if (user.getExp() > exp) {
                myRank++; // 경험치가 높은 사용자를 만날 때마다 랭크를 증가시킵니다.
            } else {
                break; // 현재 사용자의 경험치보다 작은 경험치를 가진 사용자를 만나면 루프를 종료합니다.
            }
        }

        return myRank;
    }

    // UserProfileEntity를 RankResponseDto로 매핑하는 메소드
    private RankResponseDto mapToRankResponseDto(UserProfileEntity userProfile) {
        return RankResponseDto.builder()
                .userId(userProfile.getId())
                .nickname(userProfile.getNickname())
                .tier(Tier.expToTier(userProfile.getExp()))
                .profileImg(userProfile.getProfileImg())
                .exp(userProfile.getExp())
                .answerCount(userProfile.getAnswers().size()) // 답변 수 계산
                .adoptCount((int) userProfile.getAnswers().stream().filter(AnswerEntity::isAdopted)
                        .count()) // 채택된 답변 수 계산
                .followerCount(userProfile.getFollowings().size()) // 팔로워 수 계산
                .tagIds(userProfile.getUserTags().stream().map(UserTagEntity::getTagId)
                        .collect(Collectors.toList())) // 태그 ID 리스트 가져오기
                .build();
    }
}
