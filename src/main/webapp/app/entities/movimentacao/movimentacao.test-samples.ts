import dayjs from 'dayjs/esm';

import { IMovimentacao, NewMovimentacao } from './movimentacao.model';

export const sampleWithRequiredData: IMovimentacao = {
  id: 25099,
  tipo: 'ENTRADA',
};

export const sampleWithPartialData: IMovimentacao = {
  id: 896,
  data: dayjs('2024-04-04'),
  tipo: 'SAIDA',
};

export const sampleWithFullData: IMovimentacao = {
  id: 30038,
  descricao: 'rough famously',
  data: dayjs('2024-04-05'),
  tipo: 'ENTRADA',
};

export const sampleWithNewData: NewMovimentacao = {
  tipo: 'SAIDA',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
