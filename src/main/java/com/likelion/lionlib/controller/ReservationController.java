package com.likelion.lionlib.controller;

import com.likelion.lionlib.dto.CustomUserDetails;
import com.likelion.lionlib.dto.ReservationRequest;
import com.likelion.lionlib.dto.ReservationResponse;
import com.likelion.lionlib.dto.ReservationCountResponse;
import com.likelion.lionlib.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReservationController {
    private final ReservationService reservationService;

    // 도서 예약 등록
    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(Authentication authentication, @RequestBody ReservationRequest reservationRequest) {
        Long memberId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        log.info("Request POST a reservation for book: {}", reservationRequest.getBookId());
        ReservationResponse response = reservationService.addReservation(memberId, reservationRequest);
        log.info("Response POST a reservation: {}", response);
        return ResponseEntity.ok(response);
    }

    //예약 정보 조회
    @GetMapping("/reservations/{reservationId}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable("reservationId") Long reservationId) {
        log.info("Request GET a reservation with ID: {}", reservationId);
        ReservationResponse response = reservationService.getReservation(reservationId);
        log.info("Response GET a reservation: {}", response);
        return ResponseEntity.ok(response);
    }

    //예약 취소
    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable("reservationId") Long reservationId) {
        log.info("Request DELETE reservation with ID: {}", reservationId);
        reservationService.cancelReservation(reservationId);
        log.info("Response DELETE reservation with ID: {}", reservationId);
        return ResponseEntity.noContent().build();
    }

    //사용자 예약 목록 조회
    @GetMapping("/members/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservationsByMemberId(Authentication authentication) {
        Long memberId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        log.info("Request GET reservations for member with ID: {}", memberId);
        List<ReservationResponse> response = reservationService.getReservationsByMemberId(memberId);
        log.info("Response GET reservations for member: {}", response);
        return ResponseEntity.ok(response);
    }

    // 도서 예약 수 현황 조회
    @GetMapping("/books/{bookId}/reservations")
    public ResponseEntity<ReservationCountResponse> getReservationCountByBookId(@PathVariable("bookId") Long bookId) {
        log.info("Request GET reservation count for book with ID: {}", bookId);
        ReservationCountResponse response = reservationService.getReservationCountByBookId(bookId);
        log.info("Response GET reservation count for book: {}", response);
        return ResponseEntity.ok(response);
    }

}

