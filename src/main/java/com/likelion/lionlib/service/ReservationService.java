package com.likelion.lionlib.service;

import com.likelion.lionlib.domain.Book;
import com.likelion.lionlib.domain.Member;
import com.likelion.lionlib.domain.Reservation;
import com.likelion.lionlib.dto.ReservationRequest;
import com.likelion.lionlib.dto.ReservationResponse;
import com.likelion.lionlib.dto.ReservationCountResponse;
import com.likelion.lionlib.repository.BookRepository;
import com.likelion.lionlib.repository.MemberRepository;
import com.likelion.lionlib.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final GlobalService globalService;

    @Transactional
    public ReservationResponse addReservation(Long memberId, ReservationRequest reservationRequest) {
        Long bookId = reservationRequest.getBookId();
        Member member = globalService.findMemberById(memberId);  // 이제 memberId도 요청에서 가져옴

        if (bookId == null) {
            throw new IllegalArgumentException("Book ID must not be null");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + bookId));

        Reservation reservation = Reservation.builder()
                .book(book)
                .member(member)
                .build();

        reservationRepository.save(reservation);
        return new ReservationResponse(member.getId(), book.getId());
    }


    @Transactional(readOnly = true)
    public ReservationResponse getReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        return new ReservationResponse(reservation.getMember().getId(), reservation.getBook().getId());
    }

    @Transactional
    public void cancelReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> getReservationsByMemberId(Long memberId) {
        List<Reservation> reservations = reservationRepository.findByMemberId(memberId);
        return reservations.stream()
                .map(reservation -> new ReservationResponse(
                        reservation.getMember().getId(),
                        reservation.getBook().getId()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReservationCountResponse getReservationCountByBookId(Long bookId) {
        Long count = reservationRepository.countByBookId(bookId);
        return new ReservationCountResponse(count);
    }
}