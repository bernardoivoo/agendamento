package Controller;

import Model.entidades.Consulta;
import Service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping
    public List<Consulta> listarTodas() {
        return consultaService.listarTodas();
    }

    @GetMapping("/{id}")
    public Consulta buscarPorId(@PathVariable Long id) {
        return consultaService.buscarPorId(id);
    }

    // -------- AGENDAR CONSULTA ----------
    @PostMapping("/agendar")
    public Consulta agendarConsulta(
            @RequestParam Long pacienteId,
            @RequestParam Long medicoId,
            @RequestParam String dataHora,
            @RequestParam(required = false) String observacoes
    ) {
        LocalDateTime dataHoraConvertida = LocalDateTime.parse(dataHora);
        return consultaService.agendarConsulta(pacienteId, medicoId, dataHoraConvertida, observacoes);
    }

    // -------- CONFIRMAR ----------
    @PutMapping("/{id}/confirmar")
    public Consulta confirmarConsulta(@PathVariable Long id) {
        return consultaService.confirmarConsulta(id);
    }

    // -------- CANCELAR ----------
    @PutMapping("/{id}/cancelar")
    public Consulta cancelarConsulta(@PathVariable Long id) {
        return consultaService.cancelarConsulta(id);
    }

    // -------- REALIZADA ----------
    @PutMapping("/{id}/realizada")
    public Consulta marcarRealizada(@PathVariable Long id) {
        return consultaService.marcarComoRealizada(id);
    }

    // -------- NÃO COMPARECEU ----------
    @PutMapping("/{id}/nao-compareceu")
    public Consulta marcarNaoCompareceu(@PathVariable Long id) {
        return consultaService.marcarComoNaoCompareceu(id);
    }

    // -------- DELETAR ----------
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        consultaService.deletar(id);
    }
}
