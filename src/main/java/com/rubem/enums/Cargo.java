package com.rubem.enums;

public enum Cargo {
    ALUNO,
    PROFESSOR,
    GERENTE,
    ATENDENTE;

    public String toSpringRole() {
        return "ROLE_" + this.name();
    }

    public String toRoleName() {
        return this.name();
    }
}
