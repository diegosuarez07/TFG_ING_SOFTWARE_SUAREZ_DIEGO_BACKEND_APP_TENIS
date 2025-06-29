package com.siglo21.tfg.repository;

import com.siglo21.tfg.entity.TournamentRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TournamentRegistrationRepository extends JpaRepository<TournamentRegistration, Long> {

    List<TournamentRegistration> findByUserId(Long userId);

    @Query("SELECT tr FROM TournamentRegistration tr " +
            "LEFT JOIN FETCH tr.user " +
            "LEFT JOIN FETCH tr.tournament " +
            "WHERE tr.userId = :userId")
    List<TournamentRegistration> findByUserIdWithDetails(@Param("userId") Long userId);

    List<TournamentRegistration> findByTournamentId(Long tournamentId);

    @Query("SELECT tr FROM TournamentRegistration tr " +
            "LEFT JOIN FETCH tr.user " +
            "LEFT JOIN FETCH tr.tournament " +
            "WHERE tr.tournamentId = :tournamentId")
    List<TournamentRegistration> findByTournamentIdWithDetails(@Param("tournamentId") Long tournamentId);


    @Query("SELECT tr FROM TournamentRegistration tr " +
            "LEFT JOIN FETCH tr.user " +
            "LEFT JOIN FETCH tr.tournament " +
            "WHERE tr.registrationId = :registrationId")
    Optional<TournamentRegistration> findByIdWithDetails(@Param("registrationId") Long registrationId);


    List<TournamentRegistration> findByTournamentIdAndUserId(Long tournamentId, Long userId);
    boolean existsByTournamentIdAndUserId(Long tournamentId, Long userId);
    long countByTournamentId(Long tournamentId);


}
