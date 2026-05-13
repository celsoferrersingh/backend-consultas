package com.fiap.ec.backend_consultas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "medicos")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String crm;

    @ManyToOne
    @JoinColumn(name = "especialidade_id")
    private Especialidade especialidade;

    private Boolean ativo;

    public Medico() {
    }

    public Medico(String nome, String crm, Especialidade especialidade, Boolean ativo) {
        this.nome = nome;
        this.crm = crm;
        this.especialidade = especialidade;
        this.ativo = ativo;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCrm() { return crm; }
    public Especialidade getEspecialidade() { return especialidade; }
    public Boolean getAtivo() { return ativo; }

    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCrm(String crm) { this.crm = crm; }
    public void setEspecialidade(Especialidade especialidade) { this.especialidade = especialidade; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}
