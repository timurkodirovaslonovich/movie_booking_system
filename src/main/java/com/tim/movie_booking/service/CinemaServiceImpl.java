package com.tim.movie_booking.service;

import com.tim.movie_booking.dto.CinemaRequestDto;
import com.tim.movie_booking.dto.CinemaResponseDto;
import com.tim.movie_booking.dto.HallResponseDto;
import com.tim.movie_booking.entity.Cinema;
import com.tim.movie_booking.entity.Hall;
import com.tim.movie_booking.exception.ResourceNotFoundException;
import com.tim.movie_booking.repository.CinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CinemaServiceImpl implements CinemaService {

    private final CinemaRepository cinemaRepository;



    @Override
    public CinemaResponseDto getCinemaById(UUID uuid) {
        Cinema foundCinema = cinemaRepository.findById(uuid).orElseThrow(() ->
                new ResourceNotFoundException("Cinema not found with id: " + uuid));
        return toDto(foundCinema);

    }

    @Override
    public List<CinemaResponseDto> getCinemas() {
       return cinemaRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();

    }

    @Override
    public CinemaResponseDto createCinema(CinemaRequestDto request) {
        if (cinemaRepository.existsByName(request.getName())) {
            throw new ResourceNotFoundException("cinema already exists with name: " + request.getName());
        } else {
            Cinema cinema = new Cinema(request.getName(), request.getAddress());
            cinemaRepository.save(cinema);
            return toDto(cinema);
        }
    }

    @Override
    public CinemaResponseDto updateCinema(CinemaRequestDto request, UUID uuid) {

            Cinema currentCinema = cinemaRepository.findById(uuid).orElseThrow(() ->
                new ResourceNotFoundException("Cinema not found with id: " + uuid));


            if (request.getName().isEmpty() & request.getAddress().isEmpty()) {
                    throw new ResourceNotFoundException("request body can't be empty");
                }

            currentCinema.setName(request.getName());
            currentCinema.setAddress(request.getAddress());


            return toDto(currentCinema);
        }


    @Override
    public void deleteCinema(UUID uuid) {

        if (cinemaRepository.existsById(uuid)) {
            cinemaRepository.deleteById(uuid);
        } else {
            throw new ResourceNotFoundException("User not found with id: " + uuid);
        }

    }

    //mappers
    public CinemaResponseDto toDto(Cinema cinema) {
        CinemaResponseDto dto = new CinemaResponseDto();
        dto.setUuid(cinema.getId());
        dto.setName(cinema.getName());
        dto.setAddress(cinema.getAddress());

        // ✅ Map each hall from the cinema's actual hall list
        List<HallResponseDto> hallDtos = cinema.getHalls()
                .stream()
                .map(this::toHallDto)   // convert each Hall entity to HallResponseDto
                .toList();

        dto.setHalls(hallDtos);
        return dto;
    }

    // ✅ Separate hall mapper — reads from Hall entity, not from an empty DTO
    private HallResponseDto toHallDto(Hall hall) {
        HallResponseDto dto = new HallResponseDto();
        dto.setId(hall.getId());
        dto.setHallNumber(hall.getHallNumber());  // ✅ from hall entity
        dto.setCapacity(hall.getCapacity());       // ✅ from hall entity
        dto.setCinemaId(hall.getCinema().getId()); // ✅ from hall entity
        dto.setCinemaName(hall.getCinema().getName());
        return dto;
    }
}

