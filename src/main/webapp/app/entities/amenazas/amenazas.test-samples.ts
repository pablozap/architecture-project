import { IAmenazas, NewAmenazas } from './amenazas.model';

export const sampleWithRequiredData: IAmenazas = {
  id: 211,
  nombre: 'embalm earnest indeed',
};

export const sampleWithPartialData: IAmenazas = {
  id: 5116,
  nombre: 'fluctuate obediently',
};

export const sampleWithFullData: IAmenazas = {
  id: 75,
  nombre: 'monstrous lay towards',
};

export const sampleWithNewData: NewAmenazas = {
  nombre: 'furthermore fortunately repentant',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
