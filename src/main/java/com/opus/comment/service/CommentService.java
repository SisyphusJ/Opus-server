package com.opus.comment.service;

import com.opus.comment.domain.CommentResponseDTO;
import com.opus.utils.SecurityUtil;
import com.opus.comment.domain.CommentDTO;
import com.opus.comment.domain.CommentVO;
import com.opus.comment.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentMapper commentMapper;

    @Transactional
    public void addComment(CommentDTO commentDTO) {

        CommentVO comment = CommentVO.builder()
                .pinId(commentDTO.getPinId())
                .memberId(SecurityUtil.getCurrentUserId())
                .topLevelCommentId(commentDTO.getTopLevelCommentId())
                .level(commentDTO.getLevel())
                .content(commentDTO.getContent())
                .parentNickname(commentDTO.getParentNickname())
                .build();

        commentMapper.insertComment(comment);
    }

    // 게시글에 달린 댓글 리스트
    @Transactional(readOnly = true)
    public List<CommentResponseDTO> getCommentListByPinId(int pinId) {
         List<CommentVO> comments = commentMapper.selectCommentsByPinId(pinId);
         return comments.stream()
                 .map(CommentResponseDTO::of)
                 .collect(Collectors.toList());
    }

    // 내가 쓴 댓글 리스트
    @Transactional(readOnly = true)
    public List<CommentResponseDTO> getMyCommentList() {
        List<CommentVO> comments = commentMapper.selectCommentsByMemberId(SecurityUtil.getCurrentUserId());
        return comments.stream()
                .map(CommentResponseDTO::of)
                .collect(Collectors.toList());
    }

    // 댓글 수정
    public void editComment(CommentDTO commentDTO) {
        CommentVO comment = CommentVO.builder()
                .commentId(commentDTO.getCommentId())
                .pinId(commentDTO.getPinId())
                .memberId(SecurityUtil.getCurrentUserId())
                .topLevelCommentId(commentDTO.getTopLevelCommentId())
                .parentNickname(commentDTO.getParentNickname())
                .level(commentDTO.getLevel())
                .content(commentDTO.getContent())
                .build();

        commentMapper.updateComment(comment);
    }

    // 댓글 삭제
    public void removeComment(int commentId) {
        CommentVO comment = CommentVO.builder()
                .commentId(commentId)
                .memberId(SecurityUtil.getCurrentUserId())
                .build();
        commentMapper.deleteComment(comment);
    }

}
