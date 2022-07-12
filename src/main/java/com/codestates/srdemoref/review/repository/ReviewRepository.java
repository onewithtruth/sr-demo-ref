package com.codestates.srdemoref.review.repository;

import com.codestates.srdemoref.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
}
