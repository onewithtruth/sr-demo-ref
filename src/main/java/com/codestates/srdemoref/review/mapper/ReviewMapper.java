package com.codestates.srdemoref.review.mapper;


import com.codestates.srdemoref.review.dto.ReviewPatchDto;
import com.codestates.srdemoref.review.dto.ReviewPostDto;
import com.codestates.srdemoref.review.dto.ReviewResponseDto;
import com.codestates.srdemoref.review.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {
    ReviewEntity reviewPostDtoToReview(ReviewPostDto reviewPostDto);
    ReviewEntity reviewPatchDtoToReview(ReviewPatchDto reviewPatchDto);
    List<ReviewResponseDto> reviewToReviewResponseDtos(List<ReviewEntity> review);
}
