import dayjs from 'dayjs/esm';

import { IBem, NewBem } from './bem.model';

export const sampleWithRequiredData: IBem = {
  patrimonio: 26735,
  nome: 'supposing disappointment publicity',
};

export const sampleWithPartialData: IBem = {
  patrimonio: 21684,
  nome: 'whenever whose opposite',
  numeroDeSerie: 'which listen',
};

export const sampleWithFullData: IBem = {
  patrimonio: 22908,
  nome: 'outside',
  descricao: 'phew whereas',
  observacoes: 'ouch',
  numeroDeSerie: 'insignificant',
  dataAquisicao: dayjs('2024-04-07'),
  valorCompra: 23670.57,
  valorAtual: 3696.13,
  estado: 'NOVO',
  status: 'MANUTENCAO',
};

export const sampleWithNewData: NewBem = {
  nome: 'unused as',
  patrimonio: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
