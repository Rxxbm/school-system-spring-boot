package com.rubem.service;

import com.rubem.dto.ProfessorGastoDTO;
import com.rubem.dto.RelatorioFinanceiroDTO;
import com.rubem.model.Aula;
import com.rubem.model.Gasto;
import com.rubem.model.Professor;
import com.rubem.model.Turma;
import com.rubem.repository.AulaRepository;
import com.rubem.repository.GastoRepository;
import com.rubem.repository.ProfessorRepository;
import com.rubem.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    private final TurmaRepository turmaRepository;
    private final AulaRepository aulaRepository;
    private final GastoRepository gastoRepository;
    private final ProfessorRepository professorRepository;

    private final BigDecimal VALOR_HORA_AULA = new BigDecimal("50.00");

    public RelatorioService(TurmaRepository turmaRepository,
                            AulaRepository aulaRepository,
                            GastoRepository gastoRepository,
                            ProfessorRepository professorRepository
                            ) {
        this.turmaRepository = turmaRepository;
        this.aulaRepository = aulaRepository;
        this.gastoRepository = gastoRepository;
        this.professorRepository = professorRepository;
    }

    public List<RelatorioFinanceiroDTO> gerarRelatorioMensal(int ano) {
        List<RelatorioFinanceiroDTO> relatorios = new ArrayList<>();

        for (int mes = 1; mes <= 12; mes++) {
            YearMonth yearMonth = YearMonth.of(ano, mes);
            RelatorioFinanceiroDTO relatorio = new RelatorioFinanceiroDTO();
            relatorio.setPeriodo(yearMonth);

            // Valor arrecadado (turmas iniciadas no mês/ano)
            int finalMes = mes;
            BigDecimal arrecadado = turmaRepository.findAll().stream()
                    .filter(t -> t.getDataInicio().getMonthValue() == finalMes &&
                            t.getDataInicio().getYear() == ano)
                    .map(Turma::getValorArrecadado)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            relatorio.setValorArrecadado(arrecadado);

            // Gasto realizado (aulas realizadas no mês/ano)
            BigDecimal gastoRealizado = calcularGastoRealizado(mes, ano);
            relatorio.setGastoRealizado(gastoRealizado);

            // Gasto previsto (aulas agendadas para o mês/ano)
            BigDecimal gastoPrevisto = calcularGastoPrevisto(mes, ano);
            relatorio.setGastoPrevisto(gastoPrevisto);

            // Saldo
            relatorio.setSaldo(arrecadado.subtract(gastoRealizado));

            relatorio.setGastosPorProfessor(calcularGastosPorProfessor(mes, ano));

            relatorios.add(relatorio);
        }

        return relatorios;
    }

    private List<ProfessorGastoDTO> calcularGastosPorProfessor(int mes, int ano) {
        // Obter todos os professores que ministraram aulas no período
        List<Professor> professores = professorRepository.findAll().stream()
                .filter(p -> !p.getTurmas().isEmpty())
                .collect(Collectors.toList());

        return professores.stream()
                .map(professor -> {
                    ProfessorGastoDTO dto = new ProfessorGastoDTO();
                    dto.setProfessorId(professor.getId());
                    dto.setProfessorNome(professor.getNome());

                    // Calcular gastos realizados
                    BigDecimal realizado = aulaRepository
                            .findByTurmaProfessorIdAndRealizadaTrueAndDataMonthAndDataYear(
                                    professor.getId(), mes, ano)
                            .stream()
                            .map(Aula::getCustoTotal)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    dto.setGastoRealizado(realizado);

                    // Calcular gastos previstos
                    BigDecimal previsto = aulaRepository
                            .findByTurmaProfessorIdAndRealizadaFalseAndDataMonthAndDataYear(
                                    professor.getId(), mes, ano)
                            .stream()
                            .map(Aula::getCustoTotal)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    dto.setGastoPrevisto(previsto);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public RelatorioFinanceiroDTO gerarRelatorioAnual(int ano) {
        RelatorioFinanceiroDTO relatorio = new RelatorioFinanceiroDTO();
        relatorio.setPeriodo(YearMonth.of(ano, 1)); // Usamos janeiro como representação do ano

        // Valor arrecadado no ano
        BigDecimal arrecadado = turmaRepository.findAll().stream()
                .filter(t -> t.getDataInicio().getYear() == ano)
                .map(Turma::getValorArrecadado)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        relatorio.setValorArrecadado(arrecadado);

        // Gasto realizado no ano
        BigDecimal gastoRealizado = BigDecimal.ZERO;
        for (int mes = 1; mes <= 12; mes++) {
            gastoRealizado = gastoRealizado.add(calcularGastoRealizado(mes, ano));
        }
        relatorio.setGastoRealizado(gastoRealizado);

        // Gasto previsto para o restante do ano
        BigDecimal gastoPrevisto = BigDecimal.ZERO;
        int mesAtual = LocalDate.now().getMonthValue();
        for (int mes = mesAtual; mes <= 12; mes++) {
            gastoPrevisto = gastoPrevisto.add(calcularGastoPrevisto(mes, ano));
        }
        relatorio.setGastoPrevisto(gastoPrevisto);

        // Saldo
        relatorio.setSaldo(arrecadado.subtract(gastoRealizado));

        return relatorio;
    }

    private BigDecimal calcularGastoRealizado(int mes, int ano) {
        // Gasto das aulas realizadas (valor fixo + valor do professor)
        BigDecimal gastoAulas = aulaRepository.findByRealizadaTrueAndDataMonthAndDataYear(mes, ano).stream()
                .map(aula -> {
                    Duration duracao = Duration.between(aula.getHoraInicio(), aula.getHoraFim());
                    BigDecimal valorHoraTotal = VALOR_HORA_AULA.add(aula.getTurma().getProfessor().getValorHora());
                    return valorHoraTotal.multiply(new BigDecimal(duracao.toHours()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Outros gastos registrados (pagamentos aos professores, etc.)
        BigDecimal outrosGastos = gastoRepository.findByDataMonthAndDataYear(mes, ano).stream()
                .filter(g -> !g.getDescricao().contains("Aula da turma")) // Exclui gastos de aulas já contabilizados
                .map(Gasto::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return gastoAulas.add(outrosGastos);
    }

    private BigDecimal calcularGastoPrevisto(int mes, int ano) {
        return aulaRepository.findByRealizadaFalseAndDataMonthAndDataYear(mes, ano).stream()
                .map(aula -> {
                    Duration duracao = Duration.between(aula.getHoraInicio(), aula.getHoraFim());
                    BigDecimal valorHoraTotal = VALOR_HORA_AULA.add(aula.getTurma().getProfessor().getValorHora());
                    return valorHoraTotal.multiply(new BigDecimal(duracao.toHours()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Gasto> consultarGastosPorProfessor(Long professorId, Integer mes, Integer ano) {
        List<Aula> aulas1 = aulaRepository.findByTurmaProfessorId(1L);
        System.out.println("Aulas encontradas: " + aulas1.size());
        aulas1.forEach(a -> System.out.println(a.getData() + " | " + a.getHoraInicio() + " - " + a.getHoraFim()));


        // Busca todas as aulas do professor, filtrando por mês/ano se fornecidos
        List<Aula> aulas = aulaRepository.findByTurmaProfessorId(professorId).stream()
                .filter(a -> mes == null || a.getData().getMonthValue() == mes)
                .filter(a -> ano == null || a.getData().getYear() == ano)
                .collect(Collectors.toList());

        System.out.println("Filtro - mês: " + mes + ", ano: " + ano);
        aulas1.forEach(a -> {
            System.out.println("Aula: " + a.getData());
            System.out.println("Mês da aula: " + a.getData().getMonthValue());
            System.out.println("Ano da aula: " + a.getData().getYear());
        });

        // Converte para gastos
        return aulas.stream()
                .map(aula -> {
                    Gasto gasto = new Gasto();
                    gasto.setDescricao("Aula em " + aula.getData() + " - Turma " + aula.getTurma().getLingua() + " ID " + aula.getTurma().getId());
                    gasto.setValor(aula.getCustoTotal());
                    gasto.setData(aula.getData());
                    return gasto;
                })
                .collect(Collectors.toList());
    }
}