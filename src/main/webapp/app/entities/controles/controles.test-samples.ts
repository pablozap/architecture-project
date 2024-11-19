import { IControles, NewControles } from './controles.model';

export const sampleWithRequiredData: IControles = {
  id: 11633,
  nombre: 'provided intensely within',
  descripcion: 'sneaky cautiously',
};

export const sampleWithPartialData: IControles = {
  id: 23022,
  nombre: 'jiggle',
  descripcion: 'who sadly',
};

export const sampleWithFullData: IControles = {
  id: 16553,
  nombre: 'amid granular',
  descripcion: 'eek partially recovery',
};

export const sampleWithNewData: NewControles = {
  nombre: 'community',
  descripcion: 'provided',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
