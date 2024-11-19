import dayjs from 'dayjs/esm';

import { IActivoInformacion, NewActivoInformacion } from './activo-informacion.model';

export const sampleWithRequiredData: IActivoInformacion = {
  id: 1738,
  nombre: 'about profitable',
  descripcion: 'qua thunderbolt aware',
  tipoActivo: 'SI',
  ley1581: false,
  clasificacionInformacion1712: 'PUBLIC',
  ley1266: false,
};

export const sampleWithPartialData: IActivoInformacion = {
  id: 19347,
  proceso: 'EVALUATION',
  nombre: 'hm',
  descripcion: 'silently',
  tipoActivo: 'STI',
  ley1581: false,
  clasificacionInformacion1712: 'PUBLIC',
  ley1266: false,
  formato: 'PRESENTATION',
  propietario: 'display',
  usuario: 'lively',
  custodio: 'hence',
  usuarioFinal: 'powerfully above absent',
  fecha: dayjs('2024-11-09'),
  fechaIngreso: dayjs('2024-11-09'),
};

export const sampleWithFullData: IActivoInformacion = {
  id: 6784,
  proceso: 'IMPROVEMENT',
  nombre: 'aw',
  descripcion: 'whereas',
  tipoActivo: 'AUX',
  ley1581: false,
  clasificacionInformacion1712: 'RESTRICTED',
  ley1266: true,
  formato: 'TEXT',
  propietario: 'step-mother square',
  usuario: 'whereas king',
  custodio: 'actually apud',
  usuarioFinal: 'distorted unlike',
  fecha: dayjs('2024-11-09'),
  estadoActivo: 'stable baggy',
  fechaIngreso: dayjs('2024-11-09'),
  fechaRetiro: dayjs('2024-11-09'),
};

export const sampleWithNewData: NewActivoInformacion = {
  nombre: 'pharmacopoeia lest',
  descripcion: 'soup',
  tipoActivo: 'STI',
  ley1581: false,
  clasificacionInformacion1712: 'CLASSIFIED',
  ley1266: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
