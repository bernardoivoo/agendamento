package clinica.Controller;

import clinica.Model.entidades.Consulta;
import clinica.Service.ConsultaService;
import clinica.dto.AgendarConsultaDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping
    public List<Consulta> listarTodas() {
        return consultaService.listarTodas();
    }

    @GetMapping("/{id}")
    public Consulta buscarPorId(@PathVariable Long id) {
        return consultaService.buscarPorId(id);
    }

    @PostMapping
    public Consulta agendarConsulta(@RequestBody AgendarConsultaDTO dto) {
        return consultaService.agendarConsulta(
                dto.getPacienteId(),
                dto.getMedicoId(),
                dto.getDataHora(),
                dto.getObservacoes()
        );
    }

    @PutMapping("/{id}/confirmar")
    public Consulta confirmarConsulta(@PathVariable Long id) {
        return consultaService.confirmarConsulta(id);
    }

    @PutMapping("/{id}/cancelar")
    public Consulta cancelarConsulta(@PathVariable Long id) {
        return consultaService.cancelarConsulta(id);
    }

    @PutMapping("/{id}/realizada")
    public Consulta marcarRealizada(@PathVariable Long id) {
        return consultaService.marcarComoRealizada(id);
    }

    @PutMapping("/{id}/nao-compareceu")
    public Consulta marcarNaoCompareceu(@PathVariable Long id) {
        return consultaService.marcarComoNaoCompareceu(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        consultaService.deletar(id);
    }
}
