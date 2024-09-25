package com.likelion.lionlib.repository;

import com.likelion.lionlib.domain.Reservation;
import com.likelion.lionlib.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByMember(Member member);
    List<Reservation> findByMemberId(Long member);
    Long countByBookId(Long bookId);
}
