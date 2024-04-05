import { ICategoria, NewCategoria } from './categoria.model';

export const sampleWithRequiredData: ICategoria = {
  id: 4070,
  nome: 'whereas ouch',
};

export const sampleWithPartialData: ICategoria = {
  id: 29549,
  nome: 'onto aha leading',
};

export const sampleWithFullData: ICategoria = {
  id: 20827,
  nome: 'inside',
};

export const sampleWithNewData: NewCategoria = {
  nome: 'enterprise sandy grounded',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
