import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '88286c9d-ad4c-4c29-b0dc-ddd143c34aa3',
};

export const sampleWithPartialData: IAuthority = {
  name: '518a4732-9b61-4e8c-bc0c-f7a9211c9409',
};

export const sampleWithFullData: IAuthority = {
  name: '5a617b05-5e34-4007-a646-caa689879ed3',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
