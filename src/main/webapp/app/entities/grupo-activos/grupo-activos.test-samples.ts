import { IGrupoActivos, NewGrupoActivos } from './grupo-activos.model';

export const sampleWithRequiredData: IGrupoActivos = {
  id: 30712,
  nombre: 'between with',
  disponibilidad: 'LOW',
  integridad: 'LOW',
  confidencialidad: 'LOW',
  criticidad: 'HIGH',
};

export const sampleWithPartialData: IGrupoActivos = {
  id: 26686,
  nombre: 'restfully ew',
  disponibilidad: 'MEDIUM',
  integridad: 'HIGH',
  confidencialidad: 'LOW',
  criticidad: 'LOW',
};

export const sampleWithFullData: IGrupoActivos = {
  id: 16846,
  nombre: 'though midst besides',
  disponibilidad: 'MEDIUM',
  integridad: 'LOW',
  confidencialidad: 'LOW',
  criticidad: 'HIGH',
};

export const sampleWithNewData: NewGrupoActivos = {
  nombre: 'ashamed decisive limited',
  disponibilidad: 'HIGH',
  integridad: 'MEDIUM',
  confidencialidad: 'LOW',
  criticidad: 'MEDIUM',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
