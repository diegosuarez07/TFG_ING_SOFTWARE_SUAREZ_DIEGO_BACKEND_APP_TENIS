package com.siglo21.tfg.repository;

import com.siglo21.tfg.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    List<Tournament> findByRegistrationEndDateAfterAndTournamentEndDateAfterOrderByTournamentStartDateAsc(
            java.time.LocalDate date1, java.time.LocalDate date2);

}
