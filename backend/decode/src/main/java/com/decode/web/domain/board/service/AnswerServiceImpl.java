package com.decode.web.domain.board.service;

import com.decode.web.domain.board.dto.AnswerCountResponseDto;
import com.decode.web.domain.board.dto.AnswerSomethingDto;
import com.decode.web.domain.board.dto.BoardProfileDto;
import com.decode.web.domain.board.dto.BoardProfileResponseDto;
import com.decode.web.domain.board.dto.CreateAnswerDto;
import com.decode.web.domain.board.dto.QuestionDocument;
import com.decode.web.domain.board.dto.RecommendDto;
import com.decode.web.domain.board.dto.ResponseAnswerDto;
import com.decode.web.domain.board.dto.ResponseCommentDto;
import com.decode.web.domain.board.dto.UpdateAnswerDto;
import com.decode.web.domain.board.mapper.AnswerMapper;
import com.decode.web.domain.board.repository.AnswerJpaRepository;
import com.decode.web.domain.board.repository.AnswerRepository;
import com.decode.web.domain.board.repository.QuestionELKRepository;
import com.decode.web.domain.board.repository.QuestionRepository;
import com.decode.web.domain.board.repository.RecommendRepository;
import com.decode.web.domain.mail.dto.MailDto;
import com.decode.web.domain.mail.service.MailService;
import com.decode.web.domain.user.dto.ResponseUserProfileDto;
import com.decode.web.domain.user.enums.Point;
import com.decode.web.domain.user.mapper.ResponseUserProfileMapper;
import com.decode.web.domain.user.repository.UserProfileRepository;
import com.decode.web.domain.user.service.PointService;
import com.decode.web.entity.AnswerEntity;
import com.decode.web.entity.QuestionEntity;
import com.decode.web.entity.RecommendEntity;
import com.decode.web.entity.UserProfileEntity;
import com.decode.web.exception.InvalidWriterException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerServiceImpl implements AnswerService {

    private final UserProfileRepository userProfileRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final CommentService commentService;
    private final ResponseUserProfileMapper responseUserProfileMapper;
    private final RecommendRepository recommendRepository;
    private final AnswerJpaRepository answerJpaRepository;
    private final QuestionELKRepository questionELKRepository;
    private final PointService pointService;
    private final MailService mailService;

    @Override
    public Long save(CreateAnswerDto createAnswerDto) {
        UserProfileEntity userProfile = userProfileRepository.findById(
                createAnswerDto.getUserId()).orElseThrow(
                () -> new EntityNotFoundException(
                        "User not found with id: " + createAnswerDto.getUserId()));
        QuestionEntity question = questionRepository.findById(
                createAnswerDto.getQuestionId()).orElseThrow(
                () -> new EntityNotFoundException(
                        "Question not found with id: " + createAnswerDto.getQuestionId()));
        // dto -> entity
        AnswerEntity answer = answerMapper.toEntity(createAnswerDto);
        answer.setAnswerWriter(userProfile);
        answer.setQuestion(question);

        // save 후 id value 반환
        return answerRepository.save(answer).getId();
    }

    @Override
    public AnswerEntity update(UpdateAnswerDto updateAnswerDto, AnswerEntity answerEntity) {

        // dto -> entity
//        AnswerEntity answer = answerRepository.findById(updateAnswerDto.getId()).orElseThrow(
//                () -> new EntityNotFoundException(
//                        "Answer not found with id: " + updateAnswerDto.getId()));
        // 기존의 answer content 내용 수정
        log.debug("Before Update Answer Entity : {}", answerEntity);
        answerEntity.setContent(updateAnswerDto.getContent());
        log.debug("After Update Answer Entity : {}", answerEntity);
        // 저장해서 db에 update 하기
        return answerRepository.save(answerEntity);
    }

    @Override
    public void delete(AnswerEntity answer) {
        // 삭제하기
        answerRepository.delete(answer);
    }

    @Override
    public List<ResponseAnswerDto> getResponseAnswerDtoList(QuestionEntity questionEntity) {
        List<AnswerEntity> answerList = answerRepository.findAllByQuestion(questionEntity);

        return answerList.stream().map(this::convertToResponseAnswerDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseAnswerDto convertToResponseAnswerDto(AnswerEntity answerEntity) {

        ResponseAnswerDto responseAnswerDto = new ResponseAnswerDto();
        responseAnswerDto.setAnswerId(answerEntity.getId());
        responseAnswerDto.setContent(answerEntity.getContent());
        responseAnswerDto.setAdopted(answerEntity.isAdopted());
        responseAnswerDto.setCreatedTime(answerEntity.getCreatedTime());
        responseAnswerDto.setUpdatedTime(answerEntity.getUpdatedTime());
        // CommentEntity를 가져와서 ResponseCommentDto로 변환
        List<ResponseCommentDto> responseCommentList = commentService.getResponseAnswerDtoList(
                answerEntity);
        responseAnswerDto.setCommentList(responseCommentList);

        UserProfileEntity answerWriterEntity = answerEntity.getAnswerWriter();
        ResponseUserProfileDto userProfileDto = responseUserProfileMapper.toDto(answerWriterEntity);
        responseAnswerDto.setAnswerWriter(userProfileDto);

        return responseAnswerDto;
    }

    @Override
    public Long recommend(RecommendDto recommendDto) {
        // redis cache hit 조사
        // ...
        // 후 expire 5분 설정 후 끝나면 DB 저장
        // 우선 단순 API 구현

        //dto -> entity
        AnswerEntity answer = answerRepository.findById(recommendDto.getAnswerId()).orElseThrow(
                () -> new EntityNotFoundException(
                        "Answer not found with id: " + recommendDto.getAnswerId()));
        UserProfileEntity userProfile = userProfileRepository.findById(recommendDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with id: " + recommendDto.getUserId()));

        RecommendEntity recommend = RecommendEntity.builder().answer(answer)
                .userProfile(userProfile).build();
        recommendRepository.save(recommend);
        return recommend.getId();
    }

    @Override
    public Long unRecommend(Long userId, Long answerId) {
        UserProfileEntity userProfileEntity = userProfileRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with id: " + userId));
        AnswerEntity answerEntity = answerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Answer not found with id: " + answerId));
        RecommendEntity recommendEntity = recommendRepository.findByAnswerAndUserProfile(
                answerEntity, userProfileEntity);
        if (recommendEntity == null) {
            throw new BadCredentialsException("Not exist such as recommend");
        }
        recommendRepository.delete(recommendEntity);
        return recommendEntity.getId();
    }

    @Override
    public BoardProfileResponseDto findAllByUserId(Long userId) {
        List<BoardProfileDto> questions = answerJpaRepository.findAllByUserId(userId)
                .stream()
                .map(this::convertAnswerToBoardProfileDto)
                .distinct()
                .collect(Collectors.toList());
        return BoardProfileResponseDto.builder()
                .list(questions)
                .size(questions.size())
                .build();
    }

    public BoardProfileDto convertAnswerToBoardProfileDto(AnswerEntity answer) {
        Long questionId = answer.getQuestion().getId();
        QuestionDocument questionDocument = questionELKRepository.findById(questionId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Question not found with id: " + questionId));
//        return new BoardProfileDto(questionDocument.getTitle(), questionDocument.getId());
        return BoardProfileDto.builder()
                .title(questionDocument.getTitle())
                .content(questionDocument.getContent())
                .questionId(questionDocument.getId())
                .build();
    }

    @Override
    @Transactional
    public void doAdopt(Long userId, Long answerId) {
        AnswerEntity answer = answerJpaRepository.findOneByAnswerId(answerId);
        Long questionId = answer.getQuestion().getId();
        QuestionDocument questionDocument = questionELKRepository.findById(questionId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Question not found with id: " + questionId));
        if (!questionDocument.getWriterId().equals(userId)) {
            throw new InvalidWriterException("글 작성자가 아닙니다.");
        }
        answer.doAdopt();
        pointService.updateUserPointAndExp(answer.getAnswerWriter().getId(), Point.ADOPT);
    }

    @Override
    public AnswerCountResponseDto getAnswerCountByUserId(Long userId) {
        Long selectedCnt = answerJpaRepository.getAnswerCountByUserId(userId);
        return AnswerCountResponseDto.builder()
                .selectedCnt(selectedCnt)
                .build();
    }

    @Override
    public void sendEmailToSubscriber(Long answerId) {
        answerRepository.findById(answerId)
                .get()
                .getQuestion()
                .getMetoos()
                .stream()
                .map(e -> MailDto.builder()
                        .message("나도 궁금해요! 답변이 등록되었습니다.")
                        .to(e.getUserProfile().getUserInfoEntity().getEmail())
                        .title("답변이 등록되었습니다.")
                        .build())
                .collect(Collectors.toList())
                .forEach(mailService::sendMail);
    }

    @Override
    public List<AnswerSomethingDto> getRecommendAnswersByUserId(Long userId)
            throws BadRequestException {
        Optional<UserProfileEntity> userProfile = userProfileRepository.findById(userId);
        if (userProfile.isEmpty()) {
            throw new BadRequestException("유저 아이디를 찾을 수 없습니다.");
        }
        UserProfileEntity userProfileEntity = userProfile.get();
        return userProfileEntity.getRecommends()
                .stream()
                .map(e -> AnswerSomethingDto.builder()
                        .id(e.getAnswer().getId())
                        .content(e.getAnswer().getContent())
                        .recommendCnt(e.getAnswer().getRecommends().size())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getAdoptAnswersByUserId(Long questionId)
            throws BadRequestException {
        List<AnswerEntity> adpotedList = answerRepository.findByIsAdoptedTrueAndQuestionId(questionId);
        return adpotedList.stream()
                .map(AnswerEntity::getId)
                .collect(Collectors.toList());
    }
}
