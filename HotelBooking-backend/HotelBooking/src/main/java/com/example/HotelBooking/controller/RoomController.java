package com.example.HotelBooking.controller;

import com.example.HotelBooking.dtos.Response;
import com.example.HotelBooking.dtos.RoomDTO;
import com.example.HotelBooking.enums.RoomType;
import com.example.HotelBooking.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addRoom(@RequestParam Integer roomNumber,
                                            @RequestParam RoomType type,
                                            @RequestParam BigDecimal pricePerNight,
                                            @RequestParam Integer capacity,
                                            @RequestParam String description,
                                            @RequestParam MultipartFile imagefile)   {
        RoomDTO roomDTO = RoomDTO.builder()
                .roomNumber(roomNumber)
                .type(type)
                .pricepernight(pricePerNight)
                .capacity(capacity)
                .description(description)
                .build();

        return ResponseEntity.ok(roomService.addRoom(roomDTO, imagefile));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateRoom(@RequestParam (value="roomNumber", required = false) Integer roomNumber,
                                            @RequestParam (value="type", required = false) RoomType type,
                                            @RequestParam (value="pricePerNight", required = false) BigDecimal pricePerNight,
                                            @RequestParam (value="capacity", required = false) Integer capacity,
                                            @RequestParam (value="description", required = false) String description,
                                            @RequestParam (value="imageFile", required = false) MultipartFile imagefile,
                                               @RequestParam (value="id", required = true) Long id)   {

        RoomDTO roomDTO = RoomDTO.builder()
                .id(id)
                .roomNumber(roomNumber)
                .type(type)
                .pricepernight(pricePerNight)
                .capacity(capacity)
                .description(description)
                .build();

        return ResponseEntity.ok(roomService.updateRoom(roomDTO, imagefile));
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteRoom(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.deleteRoom(id));
    }

    @GetMapping("/available")
    public ResponseEntity<Response> getAvailableRooms(@RequestParam LocalDate checkInDate, @RequestParam LocalDate checkOutDate, @RequestParam(required = false) RoomType roomType) {

        return ResponseEntity.ok(roomService.getAvailableRooms(checkInDate, checkOutDate, roomType));
    }

    @GetMapping("/types")
    public ResponseEntity<List<RoomType>> getAllRoomTypes(){
        return ResponseEntity.ok(roomService.getAllRoomTypes());
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchRooms(@RequestParam String input) {
        return ResponseEntity.ok(roomService.searchRooms(input));
    }




}
