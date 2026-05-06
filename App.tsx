/**
 * App.tsx - Aplicativo de Consultas Médicas
 * Versão 3: Componentização
 *
 * Evolução:
 * Aula 1 (31/03) → MVP Simples
 * Aula 2 (07/04) → Integração TypeScript
 * Aula 3 (14/04) → Componentização ← VOCÊ ESTÁ AQUI
 */

import React, { useState } from "react";
import { View, Text, StyleSheet, ScrollView } from "react-native";
import { StatusBar } from "expo-status-bar";

// Importando a modelagem TypeScript
import { Especialidade } from "./src/types/especialidade";
import { Paciente } from "./src/types/paciente";
import { Medico } from "./src/interfaces/medico";
import { Consulta } from "./src/interfaces/consulta";

// Importando o componente reutilizável
import { ConsultaCard } from "./src/components";

export default function App() {

  // ─── Especialidades ────────────────────────────────────────────────────────
  const cardiologia: Especialidade = {
    id: 1,
    nome: "Cardiologia",
    descricao: "Cuidados com o coração",
  };
  const ortopedia: Especialidade = {
    id: 2,
    nome: "Ortopedia",
    descricao: "Tratamento de ossos e articulações",
  };
  const pediatria: Especialidade = {
    id: 3,
    nome: "Pediatria",
    descricao: "Cuidados com crianças e adolescentes",
  };

  // ─── Médicos ───────────────────────────────────────────────────────────────
  const medico1: Medico = {
    id: 1,
    nome: "Dr. Roberto Silva",
    crm: "CRM12345",
    especialidade: cardiologia,
    ativo: true,
  };
  const medico2: Medico = {
    id: 2,
    nome: "Dra. Ana Paula Costa",
    crm: "CRM54321",
    especialidade: ortopedia,
    ativo: true,
  };
  const medico3: Medico = {
    id: 3,
    nome: "Dr. João Mendes",
    crm: "CRM98765",
    especialidade: pediatria,
    ativo: true,
  };

  // ─── Pacientes ─────────────────────────────────────────────────────────────
  const paciente1: Paciente = {
    id: 1,
    nome: "Carlos Andrade",
    cpf: "123.456.789-00",
    email: "carlos@email.com",
    telefone: "(11) 98765-4321",
  };
  const paciente2: Paciente = {
    id: 2,
    nome: "Maria Silva",
    cpf: "987.654.321-00",
    email: "maria@email.com",
    telefone: "(11) 91234-5678",
  };
  const paciente3: Paciente = {
    id: 3,
    nome: "Pedro Santos",
    cpf: "456.789.123-00",
    email: "pedro@email.com",
  };

  // ─── Lista de Consultas ────────────────────────────────────────────────────
  // Estado agora é um ARRAY de consultas, não mais uma única consulta
  const [consultas, setConsultas] = useState<Consulta[]>([
    {
      id: 1,
      medico: medico1,
      paciente: paciente1,
      data: new Date(2026, 2, 10),
      valor: 350,
      status: "agendada",
      observacoes: "Consulta de rotina",
    },
    {
      id: 2,
      medico: medico2,
      paciente: paciente2,
      data: new Date(2026, 2, 15),
      valor: 280,
      status: "agendada",
      observacoes: "Dor no joelho esquerdo",
    },
    {
      id: 3,
      medico: medico3,
      paciente: paciente3,
      data: new Date(2026, 2, 20),
      valor: 200,
      status: "agendada",
    },
  ]);

  /**
   * Atualiza o status de uma consulta específica pelo id.
   * O componente filho não altera o estado diretamente —
   * apenas "comunica" ao pai (App) que uma ação foi solicitada.
   */
  function confirmarConsulta(id: number) {
    setConsultas(consultas.map((c) =>
      c.id === id ? { ...c, status: "confirmada" } : c
    ));
  }

  function cancelarConsulta(id: number) {
    setConsultas(consultas.map((c) =>
      c.id === id ? { ...c, status: "cancelada" } : c
    ));
  }

  return (
    <View style={styles.container}>
      <StatusBar style="light" />

      <ScrollView contentContainerStyle={styles.scrollContent}>
        {/* Cabeçalho */}
        <View style={styles.header}>
          <Text style={styles.titulo}>Sistema de Consultas</Text>
          <Text style={styles.subtitulo}>
            {consultas.length} consulta{consultas.length !== 1 ? "s" : ""} cadastrada{consultas.length !== 1 ? "s" : ""}
          </Text>
        </View>

        {/*
          Renderização dinâmica: .map() percorre TODAS as consultas
          e renderiza um ConsultaCard para cada uma.
          A key={} é obrigatória para o React identificar cada item.
        */}
        {consultas.map((consulta) => (
          <ConsultaCard
            key={consulta.id}
            consulta={consulta}
            onConfirmar={() => confirmarConsulta(consulta.id)}
            onCancelar={() => cancelarConsulta(consulta.id)}
          />
        ))}

      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#79059C",
  },
  scrollContent: {
    padding: 20,
    paddingTop: 60,
    paddingBottom: 40,
  },
  header: {
    alignItems: "center",
    marginBottom: 24,
  },
  titulo: {
    fontSize: 28,
    fontWeight: "bold",
    color: "#fff",
    marginBottom: 8,
  },
  subtitulo: {
    fontSize: 18,
    color: "#fff",
    opacity: 0.9,
  },
});