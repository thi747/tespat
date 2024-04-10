import { ILocal, NewLocal } from './local.model';

export const sampleWithRequiredData: ILocal = {
  id: 16825,
  nome: 'bark although enormously',
};

export const sampleWithPartialData: ILocal = {
  id: 14272,
  nome: 'satisfied finished',
  sala: 'jolly eek',
};

export const sampleWithFullData: ILocal = {
  id: 32354,
  nome: 'solemnly cuddly deliberately',
  descricao: 'anenst feminise pension',
  sala: 'hm',
};

export const sampleWithNewData: NewLocal = {
  nome: 'lambast ah aha',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
