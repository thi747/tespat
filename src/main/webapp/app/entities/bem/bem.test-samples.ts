import dayjs from 'dayjs/esm';

import { IBem, NewBem } from './bem.model';

export const sampleWithRequiredData: IBem = {
  id: 22027,
  patrimonio: 26837,
  nome: 'formal',
};

export const sampleWithPartialData: IBem = {
  id: 2208,
  patrimonio: 9375,
  nome: 'true unknown adorable',
  descricao: 'wildly astride',
  numeroDeSerie: 'which',
  valorCompra: 5800.12,
  valorAtual: 8895.34,
  estado: 'REGULAR',
};

export const sampleWithFullData: IBem = {
  id: 22908,
  patrimonio: 5016,
  nome: 'psst phew',
  descricao: 'geez ouch or',
  numeroDeSerie: 'gosh right',
  dataAquisicao: dayjs('2024-04-08'),
  valorCompra: 29345.91,
  valorAtual: 6440.28,
  estado: 'REGULAR',
  status: 'ESTOQUE',
  observacoes: 'blah',
};

export const sampleWithNewData: NewBem = {
  patrimonio: 6224,
  nome: 'mechanically',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
