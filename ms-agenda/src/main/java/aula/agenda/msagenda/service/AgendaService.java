package aula.agenda.msagenda.service;

import aula.agenda.msagenda.dto.AgendaDTO;
import aula.agenda.msagenda.dto.FuncionarioDTO;
import aula.agenda.msagenda.dto.SalaDTO;
import aula.agenda.msagenda.model.Agenda;
import aula.agenda.msagenda.repository.AgendaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AgendaService {

    private final AgendaRepository repository;
    private final RestTemplate restTemplate;

    public AgendaService(AgendaRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    private void validar(Agenda agenda) {
        if (agenda.getDataHoraReserva().getMinute() != 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horario de agendamento invalido, reserva deve ser feita soente com horario cheio. Ex: 10:00-11:00");
        }
        if (repository.existsBySalaAndDataHoraReserva(agenda.getSala(), agenda.getDataHoraReserva())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sala indisponivel, sala ja reservada nesse horario.");
        }
        if (repository.existsByResponsavelAndDataHoraReserva(agenda.getResponsavel(), agenda.getDataHoraReserva())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Funcionario ja possui sala reservada nesse horario.");
        }
    }

    public void inserirAgenda(AgendaDTO agendaDTO) {
        Agenda agenda = agendaDTO.converteParaAgenda();
        validar(agenda);
        repository.save(agenda);
    }

    public List<AgendaDTO> listarAgendas() {
        List<Agenda> agendas = repository.findAll();
        List<AgendaDTO> agendasDTO = AgendaDTO.converteListaAgenda(agendas);
        for (AgendaDTO agenda: agendasDTO) {
            agenda.setResponsavel(buscarNomeFuncionario(agenda.getIdResponsavel()));
            agenda.setSala(buscarNomeSala(agenda.getIdSala()));
        }
        return agendasDTO;
    }

    private String buscarNomeFuncionario(Long id) {
        ResponseEntity<FuncionarioDTO> entity = restTemplate.getForEntity("http://localhost:8080/funcionario/" + id, FuncionarioDTO.class);
        FuncionarioDTO funcionarioDTO = entity.getBody();
        return funcionarioDTO.getNome();
    }

    private String buscarNomeSala(Long id) {
        ResponseEntity<SalaDTO> entity = restTemplate.getForEntity("http://localhost:8080/sala/" + id, SalaDTO.class);
        SalaDTO salaDTO = entity.getBody();
        return salaDTO.getNome();
    }
}
