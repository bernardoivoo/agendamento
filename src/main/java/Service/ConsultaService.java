package Service;

import Model.entidades.Consulta;
import Model.entidades.Medico;
import Model.entidades.Paciente;
import Model.enums.StatusConsulta;
import Repository.ConsultaRepository;
import Repository.MedicoRepository;
import Repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public List<Consulta> listarTodas() {
        return consultaRepository.findAll();
    }

    public Consulta buscarPorId(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
    }

    public Consulta agendarConsulta(Long pacienteId, Long medicoId, LocalDateTime dataHora, String observacoes) {

        if (dataHora.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Não é possível agendar uma consulta no passado");
        }

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));

        boolean ocupado = consultaRepository.existsByMedicoAndDataHora(medico, dataHora);

        if (ocupado) {
            throw new RuntimeException("Esse horário já está ocupado para este médico");
        }

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDataHora(dataHora);
        consulta.setStatus(StatusConsulta.AGENDADA);
        consulta.setObservacoes(observacoes);

        return consultaRepository.save(consulta);
    }

    public Consulta confirmarConsulta(Long id) {
        Consulta consulta = buscarPorId(id);

        consulta.setStatus(StatusConsulta.CONFIRMADA);

        return consultaRepository.save(consulta);
    }

    public Consulta cancelarConsulta(Long id) {
        Consulta consulta = buscarPorId(id);

        consulta.setStatus(StatusConsulta.CANCELADA);

        return consultaRepository.save(consulta);
    }

    public Consulta marcarComoRealizada(Long id) {
        Consulta consulta = buscarPorId(id);

        consulta.setStatus(StatusConsulta.REALIZADA);

        return consultaRepository.save(consulta);
    }

    public Consulta marcarComoNaoCompareceu(Long id) {
        Consulta consulta = buscarPorId(id);

        consulta.setStatus(StatusConsulta.NAO_COMPARECEU);

        return consultaRepository.save(consulta);
    }

    public void deletar(Long id) {
        consultaRepository.deleteById(id);
    }
}
