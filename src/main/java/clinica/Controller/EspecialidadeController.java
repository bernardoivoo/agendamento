package clinica.Controller;

import clinica.Model.entidades.Especialidade;
import clinica.Service.EspecialidadeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/especialidade")
public class EspecialidadeController {

    private final EspecialidadeService service;

    public EspecialidadeController(EspecialidadeService service) {
        this.service = service;
    }

    @PostMapping
    public Especialidade salvar(@RequestBody Especialidade especialidade) {
        return service.salvar(especialidade);
    }
}

