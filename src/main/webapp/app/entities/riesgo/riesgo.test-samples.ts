import dayjs from 'dayjs/esm';

import { IRiesgo, NewRiesgo } from './riesgo.model';

export const sampleWithRequiredData: IRiesgo = {
  id: 30179,
  frecuencia: 5601,
  afectacionEconomica: 'LOW',
  afectacion: 'PROBABILITY',
  tipoControl: 'DETECTIVE',
  implementacion: 'MANUAL',
  frecuenciaControl: 'RANDOM',
  tratamiento: 'REDUCE',
};

export const sampleWithPartialData: IRiesgo = {
  id: 5735,
  proceso: 'hence',
  tipoRiesgo: 'INTEGRITY',
  descripcion: 'fort famously until',
  clasificacion: 'RELATIONS',
  frecuencia: 10505,
  afectacionEconomica: 'LOW',
  impactoInherente: 'NA',
  zonaRiesgo: 'LOWEST',
  afectacion: 'IMPACT',
  tipoControl: 'CORRECTIVE',
  implementacion: 'MANUAL',
  calificacionControl: 34,
  documentado: true,
  frecuenciaControl: 'CONTINUOUS',
  evidencia: false,
  impacto: 5055,
  probabilidadResidualFinal: 'LOW',
  impactoResidualFinal: 'LOWEST',
  zonaDeRiesgoFinal: 'LOWEST',
  riesgoResidual: 'huzzah',
  tratamiento: 'TRANSFER',
  responsable: 'whereas',
  fechaImplementacion: dayjs('2024-11-09'),
  estado: 'below',
  observaciones: 'as urgently',
};

export const sampleWithFullData: IRiesgo = {
  id: 24188,
  proceso: 'overheard quarrelsome accessorise',
  tipoRiesgo: 'CONFIDENTIALITY',
  descripcion: 'meaningfully',
  clasificacion: 'EXTERN',
  frecuencia: 16357,
  afectacionEconomica: 'HIGH',
  impactoReputacional: 'MEDIUM',
  probabilidadInherente: 'HIGH',
  impactoInherente: 'NA',
  zonaRiesgo: 'HIGH',
  afectacion: 'IMPACT',
  tipoControl: 'DETECTIVE',
  implementacion: 'AUTOMATIC',
  calificacionControl: 24,
  documentado: true,
  frecuenciaControl: 'CONTINUOUS',
  evidencia: true,
  probabilidad: 8678,
  impacto: 18608,
  probabilidadResidualFinal: 'MEDIUM',
  impactoResidualFinal: 'MEDIUM',
  zonaDeRiesgoFinal: 'LOW',
  riesgoResidual: 'surprisingly',
  tratamiento: 'TRANSFER',
  planAccion: 'blindly however',
  responsable: 'tousle',
  fechaImplementacion: dayjs('2024-11-09'),
  seguimientoControlExistente: 'nor flimsy',
  estado: 'jaggedly unabashedly',
  observaciones: 'mockingly and',
  fechaMonitoreo: dayjs('2024-11-10'),
};

export const sampleWithNewData: NewRiesgo = {
  frecuencia: 12176,
  afectacionEconomica: 'MEDIUM',
  afectacion: 'IMPACT',
  tipoControl: 'CORRECTIVE',
  implementacion: 'AUTOMATIC',
  frecuenciaControl: 'RANDOM',
  tratamiento: 'ACCEPT',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
