import dayjs from 'dayjs/esm';

import { IMovimentacao, NewMovimentacao } from './movimentacao.model';

export const sampleWithRequiredData: IMovimentacao = {
  id: 25099,
  tipo: 'ENTRADA',
};

export const sampleWithPartialData: IMovimentacao = {
  id: 896,
  descricao: 'treble whether ha',
  tipo: 'SAIDA',
};

export const sampleWithFullData: IMovimentacao = {
  id: 11335,
  data: dayjs('2024-05-10'),
  descricao: 'into dangerous amongst',
  tipo: 'SAIDA',
};

export const sampleWithNewData: NewMovimentacao = {
  tipo: 'ENTRADA',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
