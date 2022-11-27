package aula.agenda.msagenda.repository;

import aula.agenda.msagenda.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    boolean existsBySalaAndDataHoraReserva(Long idSala, LocalDateTime dataHoraReserva);
    boolean existsByResponsavelAndDataHoraReserva(Long idFuncionario, LocalDateTime dataHoraReserva);
}
