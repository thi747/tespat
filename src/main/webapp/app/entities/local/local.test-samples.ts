import { ILocal, NewLocal } from './local.model';

export const sampleWithRequiredData: ILocal = {
  id: 5368,
  nome: 'cheerfully',
};

export const sampleWithPartialData: ILocal = {
  id: 26397,
  nome: 'enormously',
  descricao: 'seemingly till mushy',
  sala: 'recognition humiliate',
};

export const sampleWithFullData: ILocal = {
  id: 3908,
  nome: 'lest than',
  descricao: 'big',
  sala: 'miserably gosh',
};

export const sampleWithNewData: NewLocal = {
  nome: 'ensconce ultimately',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
