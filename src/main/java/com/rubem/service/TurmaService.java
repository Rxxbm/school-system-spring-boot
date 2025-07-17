package com.rubem.service;

import com.rubem.dto.*;
import com.rubem.model.Aluno;
import com.rubem.model.Matricula;
import com.rubem.model.Professor;
import com.rubem.model.Turma;
import com.rubem.repository.AlunoRepository;
import com.rubem.repository.MatriculaRepository;
import com.rubem.repository.ProfessorRepository;
import com.rubem.repository.TurmaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurmaService {


    private final TurmaRepository turmaRepository;
    private final ProfessorRepository professorRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    public TurmaService(TurmaRepository turmaRepository, ProfessorRepository professorRepository) {
        this.turmaRepository = turmaRepository;
        this.professorRepository = professorRepository;
    }


    @Transactional
    public TurmaResponseDTO createTurma(TurmaDTO turmaDTO) {
        Professor professor = professorRepository.findById(turmaDTO.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        Turma turma = new Turma();
        turma.setDataInicio(turmaDTO.getDataInicio());
        turma.setDataTermino(turmaDTO.getDataTermino());
        turma.setLingua(turmaDTO.getLingua());
        turma.setNivel(turmaDTO.getNivel());
        turma.setPreco(turmaDTO.getPreco());
        turma.setValorArrecadado(turmaDTO.getValorArrecadado());
        turma.setProfessor(professor);

        Turma savedTurma = turmaRepository.save(turma);
        return convertToResponseDTO(savedTurma);
    }

    public List<TurmaResponseDTO> getAllTurmas() {
        return turmaRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public TurmaResponseDTO getTurmaById(Long id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
        return convertToResponseDTO(turma);
    }

    @Transactional
    public TurmaResponseDTO updateTurma(Long id, TurmaDTO turmaDTO) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        Professor professor = professorRepository.findById(turmaDTO.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        turma.setDataInicio(turmaDTO.getDataInicio());
        turma.setDataTermino(turmaDTO.getDataTermino());
        turma.setLingua(turmaDTO.getLingua());
        turma.setNivel(turmaDTO.getNivel());
        turma.setPreco(turmaDTO.getPreco());
        turma.setProfessor(professor);

        Turma updatedTurma = turmaRepository.save(turma);
        return convertToResponseDTO(updatedTurma);
    }

    @Transactional
    public void deleteTurma(Long id) {
        if (!turmaRepository.existsById(id)) {
            throw new RuntimeException("Turma não encontrada");
        }
        turmaRepository.deleteById(id);
    }

    private TurmaResponseDTO convertToResponseDTO(Turma turma) {
        TurmaResponseDTO dto = new TurmaResponseDTO();
        dto.setId(turma.getId());
        dto.setDataInicio(turma.getDataInicio());
        dto.setDataTermino(turma.getDataTermino());
        dto.setLingua(turma.getLingua());
        dto.setNivel(turma.getNivel());
        dto.setPreco(turma.getPreco());
        dto.setProfessorId(turma.getProfessor().getId());
        dto.setProfessorNome(turma.getProfessor().getNome());
        dto.setValorArrecadado(turma.getValorArrecadado());
        return dto;
    }

    @Transactional
    public void registrarNota(MatriculaDTO dto) {
        // Verifica se a turma já foi finalizada
        Turma turma = turmaRepository.findById(dto.getTurma())
                .orElseThrow(() -> new EntityNotFoundException("Turma não encontrada"));

        if (turma.getDataTermino().isAfter(LocalDate.now())) {
            throw new IllegalStateException("A turma ainda não foi finalizada");
        }

        // Busca a matrícula do aluno na turma
        Matricula matricula = matriculaRepository.findByAlunoIdAndTurmaId(dto.getAluno(), dto.getTurma())
                .orElseThrow(() -> new EntityNotFoundException("Aluno não está matriculado nesta turma"));

        // Atualiza a nota e observações
        matricula.setNotaFinal(dto.getNotaFinal());

        matriculaRepository.save(matricula);
    }

    @Transactional
    public void matricularAluno(Long turmaId, Long alunoId) {
        Turma turma = turmaRepository.findById(turmaId)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // Verificar se o aluno já está matriculado nesta turma
        boolean jaMatriculado = turma.getMatriculas().stream()
                .anyMatch(m -> m.getAluno().getId().equals(alunoId));

        if (jaMatriculado) {
            throw new RuntimeException("Aluno já matriculado nesta turma");
        }

        Matricula matricula = new Matricula(aluno, turma, null);

        matriculaRepository.save(matricula);
    }

    @Transactional
    public void removerAlunoDaTurma(Long turmaId, Long alunoId) {
        Turma turma = turmaRepository.findById(turmaId)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        // Encontra a matrícula específica deste aluno nesta turma
        Matricula matricula = turma.getMatriculas().stream()
                .filter(m -> m.getAluno().getId().equals(alunoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Aluno não está matriculado nesta turma"));

        matriculaRepository.delete(matricula);

        // Alternativa: se você quiser manter o histórico, pode marcar como inativa em vez de deletar
        // matricula.setAtiva(false);
        // matriculaRepository.save(matricula);
    }

    public List<AlunoNotaDTO> listarAlunosDaTurma(Long turmaId) {
        return turmaRepository.findAlunosComNotasByTurmaId(turmaId);
    }

    @Transactional
    public void atribuirNotaFinal(Long professorId, MatriculaDTO dto) {
        // Verifica se o professor é responsável pela turma
        Turma turma = turmaRepository.findById(dto.getTurma())
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        if (!turma.getProfessor().getId().equals(professorId)) {
            throw new SecurityException("Apenas o professor responsável pode atribuir notas");
        }

        if(!turma.getDataTermino().isBefore(LocalDate.now())){
           // throw new RuntimeException("A turma ainda não foi finalizada");
        }

        // Encontra a matrícula específica
        Matricula matricula = matriculaRepository
                .findByAlunoIdAndTurmaId(dto.getAluno(), dto.getTurma())
                .orElseThrow(() -> new RuntimeException("Aluno não está matriculado nesta turma"));

        // Atualiza a nota
        matricula.setNotaFinal(dto.getNotaFinal());
        matriculaRepository.save(matricula);
    }

    @Transactional
    public void registrarPagamento(Long turmaId, BigDecimal valor) {
        Turma turma = turmaRepository.findById(turmaId)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor do pagamento deve ser positivo");
        }

        BigDecimal novoValorArrecadado = turma.getValorArrecadado().add(valor);
        if (novoValorArrecadado.compareTo(turma.getPreco()) > 0) {
            throw new RuntimeException("Valor excede o total da turma");
        }

        turma.setValorArrecadado(novoValorArrecadado);
        turmaRepository.save(turma);
    }
}