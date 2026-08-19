package com.fiap.ec.backend_consultas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.fiap.ec.backend_consultas.model.Consulta;
import com.fiap.ec.backend_consultas.model.Especialidade;
import com.fiap.ec.backend_consultas.model.Medico;
import com.fiap.ec.backend_consultas.model.Paciente;
import com.fiap.ec.backend_consultas.repository.ConsultaRepository;
import com.fiap.ec.backend_consultas.repository.EspecialidadeRepository;
import com.fiap.ec.backend_consultas.repository.MedicoRepository;
import com.fiap.ec.backend_consultas.repository.PacienteRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final EspecialidadeRepository especialidadeRepository;

    public DataLoader(ConsultaRepository consultaRepository,
                      MedicoRepository medicoRepository,
                      PacienteRepository pacienteRepository,
                      EspecialidadeRepository especialidadeRepository) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.especialidadeRepository = especialidadeRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Seed especialidades
        if (especialidadeRepository.count() == 0) {
            especialidadeRepository.saveAll(List.of(
                    new Especialidade("Cardiologia", "Coração e sistema circulatório"),
                    new Especialidade("Ortopedia", "Ossos e articulações"),
                    new Especialidade("Pediatria", "Crianças e adolescentes")
            ));
            System.out.println("DataLoader: 3 especialidades criadas.");
        }

        // Seed pacientes
        if (pacienteRepository.count() == 0) {
            pacienteRepository.saveAll(List.of(
                    new Paciente("João Silva", "12345678900", "joao@email.com",
                            "11999999999", LocalDate.of(1995, 5, 10), true),
                    new Paciente("João Santos", "12345678910", "joao2@email.com",
                            "11999999999", LocalDate.of(1995, 5, 10), true),
                    new Paciente("Maria Oliveira", "98765432100", "maria@email.com",
                            "11988888888", LocalDate.of(1990, 3, 20), true)
            ));
            System.out.println("DataLoader: 3 pacientes criados.");
        }

        // Seed médicos
        if (medicoRepository.count() == 0) {
            List<Especialidade> especialidades = especialidadeRepository.findAll();
            Especialidade cardio = especialidades.get(0);
            Especialidade ortop = especialidades.size() > 1 ? especialidades.get(1) : cardio;
            Especialidade pedia = especialidades.size() > 2 ? especialidades.get(2) : cardio;

            medicoRepository.saveAll(List.of(
                    new Medico("Dr. Carlos Andrade", "CRM12345", cardio, true),
                    new Medico("Dra. Ana Lima", "CRM67890", ortop, true),
                    new Medico("Dr. Pedro Costa", "CRM11111", pedia, true)
            ));
            System.out.println("DataLoader: 3 médicos criados.");
        }

        // Seed consultas
        if (consultaRepository.count() == 0) {
            List<Medico> medicos = medicoRepository.findAll();
            List<Paciente> pacientes = pacienteRepository.findAll();

            if (medicos.isEmpty() || pacientes.isEmpty()) {
                System.out.println("DataLoader: sem médicos ou pacientes, pulando consultas.");
                return;
            }

            Medico m1 = medicos.get(0);
            Medico m2 = medicos.size() > 1 ? medicos.get(1) : m1;
            Paciente p1 = pacientes.get(0);
            Paciente p2 = pacientes.size() > 1 ? pacientes.get(1) : p1;

            consultaRepository.saveAll(List.of(
                    new Consulta(m1, p1, LocalDateTime.of(2026, 5, 20, 9, 0),
                            "agendada", 250.00, "Consulta de rotina"),
                    new Consulta(m2, p2, LocalDateTime.of(2026, 5, 21, 14, 30),
                            "confirmada", 350.00, "Retorno pós-exame"),
                    new Consulta(m1, p2, LocalDateTime.of(2026, 5, 15, 10, 0),
                            "realizada", 200.00, null),
                    new Consulta(m2, p1, LocalDateTime.of(2026, 5, 18, 11, 0),
                            "cancelada", 300.00, "Paciente desmarcou")
            ));
            System.out.println("DataLoader: 4 consultas criadas.");
        }
    }
}
