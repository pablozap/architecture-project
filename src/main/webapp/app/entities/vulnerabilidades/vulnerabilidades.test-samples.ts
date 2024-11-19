import { IVulnerabilidades, NewVulnerabilidades } from './vulnerabilidades.model';

export const sampleWithRequiredData: IVulnerabilidades = {
  id: 16877,
  nombre: 'heavy depot though',
  descripcion: 'er along',
};

export const sampleWithPartialData: IVulnerabilidades = {
  id: 13183,
  nombre: 'agile usefully secondary',
  descripcion: 'stable',
};

export const sampleWithFullData: IVulnerabilidades = {
  id: 14578,
  nombre: 'um',
  descripcion: 'ouch sesame',
};

export const sampleWithNewData: NewVulnerabilidades = {
  nombre: 'gadzooks geez willfully',
  descripcion: 'bleakly emergent',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
