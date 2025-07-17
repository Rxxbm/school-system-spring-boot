package com.rubem.service;

import com.rubem.dto.AulaDTO;
import com.rubem.dto.AulaResponseDTO;
import com.rubem.dto.ProfessorResponseDTO;
import com.rubem.model.Aula;
import com.rubem.model.Gasto;
import com.rubem.model.Professor;
import com.rubem.model.Turma;
import com.rubem.repository.AulaRepository;
import com.rubem.repository.GastoRepository;
import com.rubem.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AulaService {

    private final TurmaRepository turmaRepository;

    private final AulaRepository aulaRepository;
    private final GastoRepository gastoRepository;
    private final BigDecimal VALOR_HORA_AULA = new BigDecimal("50.00"); // Valor fixo por hora

    public AulaService(AulaRepository aulaRepository, GastoRepository gastoRepository, TurmaRepository turmaRepository) {
        this.aulaRepository = aulaRepository;
        this.gastoRepository = gastoRepository;
        this.turmaRepository = turmaRepository;
    }

    // CREATE
    public Aula criarAula(AulaDTO aula) {
        Turma turma = turmaRepository.findById(aula.getTurmaId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));


        validarAula(aula);
        Aula res = new Aula();

        res.setData(aula.getData());
        res.setTurma(turma);
        res.setHoraFim(aula.getHoraFim());
        res.setHoraInicio(aula.getHoraInicio());
        res.setRealizada(false);

        return aulaRepository.save(res);
    }

    // READ
    public List<AulaResponseDTO> listarTodasAulas() {
        return aulaRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public AulaResponseDTO buscarAulaPorId(Long id) {
        Aula aula = aulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula não encontrada"));

        return convertToResponseDTO(aula);
    }

    // UPDATE
    @Transactional
    public Aula atualizarAula(Long id, AulaDTO aulaAtualizada) {
        Aula aulaExistente = aulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula não encontrada"));

        // Só permite alterar a data se a aula não foi realizada
        if (aulaExistente.isRealizada()) {
            throw new RuntimeException("Não é possível alterar uma aula já realizada");
        }

        // Atualiza apenas os campos permitidos
        aulaExistente.setData(aulaAtualizada.getData());
        aulaExistente.setHoraInicio(aulaAtualizada.getHoraInicio());
        aulaExistente.setHoraFim(aulaAtualizada.getHoraFim());

        validarAula(aulaAtualizada);
        return aulaRepository.save(aulaExistente);
    }

    // DELETE
    public void deletarAula(Long id) {
        Aula aula = aulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula não encontrada"));

        if (aula.isRealizada()) {
            throw new RuntimeException("Não é possível excluir uma aula já realizada");
        }

        aulaRepository.deleteById(id);
    }

    // Método para marcar aula como realizada e calcular gastos
    @Transactional
    public void marcarComoRealizada(Long idAula) {
        Aula aula = aulaRepository.findById(idAula)
                .orElseThrow(() -> new RuntimeException("Aula não encontrada"));

        if (aula.isRealizada()) {
            throw new RuntimeException("Aula já foi marcada como realizada");
        }

        aula.setRealizada(true);
        aulaRepository.save(aula);

        // Calcular e registrar o gasto
        calcularERegistrarGasto(aula);
    }

    // Método para postergar aula
    @Transactional
    public Aula postergarAula(Long idAula, LocalDate novaData) {
        Aula aula = aulaRepository.findById(idAula)
                .orElseThrow(() -> new RuntimeException("Aula não encontrada"));

        if (aula.isRealizada()) {
            throw new RuntimeException("Não é possível postergar uma aula já realizada");
        }

        if (novaData.isBefore(LocalDate.now())) {
            throw new RuntimeException("A nova data deve ser futura ou presente");
        }

        aula.setData(novaData);
        return aulaRepository.save(aula);
    }

    // Validações
    private void validarAula(AulaDTO aula) {
        if (aula.getHoraFim().isBefore(aula.getHoraInicio())) {
            throw new RuntimeException("Hora de término deve ser após hora de início");
        }

        if (aula.getData().isBefore(LocalDate.now())) {
            throw new RuntimeException("Data da aula deve ser presente ou futura");
        }
    }

    // Cálculo de gastos
    private void calcularERegistrarGasto(Aula aula) {
        Duration duracao = Duration.between(aula.getHoraInicio(), aula.getHoraFim());
        long horas = duracao.toHours();

        // Valor fixo da aula + valor do professor
        BigDecimal valorAula = VALOR_HORA_AULA.add(aula.getTurma().getProfessor().getValorHora());
        BigDecimal valorTotal = valorAula.multiply(new BigDecimal(horas));

        Gasto gasto = new Gasto();
        gasto.setDescricao("Aula da turma " + aula.getTurma().getId() +
                " - Prof. " + aula.getTurma().getProfessor().getNome());
        gasto.setValor(valorTotal);
        gasto.setData(LocalDate.now());

        gastoRepository.save(gasto);
    }

    AulaResponseDTO convertToResponseDTO(Aula aula){
        AulaResponseDTO dto = new AulaResponseDTO(aula);
        return dto;
    }
}