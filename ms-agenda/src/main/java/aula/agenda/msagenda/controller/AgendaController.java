package aula.agenda.msagenda.controller;

import aula.agenda.msagenda.dto.AgendaDTO;
import aula.agenda.msagenda.model.Agenda;
import aula.agenda.msagenda.service.AgendaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    private final AgendaService service;

    public AgendaController(AgendaService service) {
        this.service = service;
    }

    @PostMapping("/")
    public void inserirAgenda(@RequestBody AgendaDTO agenda) {
        service.inserirAgenda(agenda);
    }

    @GetMapping("/")
    public List<AgendaDTO> listarAgendas() {
        return service.listarAgendas();
    }
}
