package com.codestates.srdemoref.review.mapper;

import com.codestates.srdemoref.review.dto.ReviewPatchDto;
import com.codestates.srdemoref.review.dto.ReviewPostDto;
import com.codestates.srdemoref.review.dto.ReviewResponseDto;
import com.codestates.srdemoref.review.dto.ReviewResponseDto.ReviewResponseDtoBuilder;
import com.codestates.srdemoref.review.entity.ReviewEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-11T16:02:09+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.15 (Azul Systems, Inc.)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public ReviewEntity reviewPostDtoToReview(ReviewPostDto reviewPostDto) {
        if ( reviewPostDto == null ) {
            return null;
        }

        ReviewEntity reviewEntity = new ReviewEntity();

        reviewEntity.setTitle( reviewPostDto.getTitle() );
        reviewEntity.setContent( reviewPostDto.getContent() );
        reviewEntity.setImage( reviewPostDto.getImage() );
        reviewEntity.setOrder( reviewPostDto.getOrder() );
        reviewEntity.setMember( reviewPostDto.getMember() );

        return reviewEntity;
    }

    @Override
    public ReviewEntity reviewPatchDtoToReview(ReviewPatchDto reviewPatchDto) {
        if ( reviewPatchDto == null ) {
            return null;
        }

        ReviewEntity reviewEntity = new ReviewEntity();

        reviewEntity.setReviewId( reviewPatchDto.getReviewId() );
        reviewEntity.setTitle( reviewPatchDto.getTitle() );
        reviewEntity.setContent( reviewPatchDto.getContent() );
        reviewEntity.setImage( reviewPatchDto.getImage() );
        reviewEntity.setOrder( reviewPatchDto.getOrder() );
        reviewEntity.setMember( reviewPatchDto.getMember() );

        return reviewEntity;
    }

    @Override
    public List<ReviewResponseDto> reviewToReviewResponseDtos(List<ReviewEntity> review) {
        if ( review == null ) {
            return null;
        }

        List<ReviewResponseDto> list = new ArrayList<ReviewResponseDto>( review.size() );
        for ( ReviewEntity reviewEntity : review ) {
            list.add( reviewEntityToReviewResponseDto( reviewEntity ) );
        }

        return list;
    }

    protected ReviewResponseDto reviewEntityToReviewResponseDto(ReviewEntity reviewEntity) {
        if ( reviewEntity == null ) {
            return null;
        }

        ReviewResponseDtoBuilder reviewResponseDto = ReviewResponseDto.builder();

        reviewResponseDto.title( reviewEntity.getTitle() );
        reviewResponseDto.content( reviewEntity.getContent() );
        reviewResponseDto.image( reviewEntity.getImage() );

        return reviewResponseDto.build();
    }
}
