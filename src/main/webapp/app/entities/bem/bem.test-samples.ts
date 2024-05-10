import dayjs from 'dayjs/esm';

import { IBem, NewBem } from './bem.model';

export const sampleWithRequiredData: IBem = {
  id: 26837,
  patrimonio: 4602,
  nome: 'judgementally',
};

export const sampleWithPartialData: IBem = {
  id: 26078,
  patrimonio: 23123,
  nome: 'profitable devoted certainly',
  descricao: 'recorder accentuate deep',
  dataAquisicao: dayjs('2024-05-10'),
  valorCompra: 16461.29,
  valorAtual: 22907.43,
  observacoes: 'outside',
  imagem: '../fake-data/blob/hipster.png',
  imagemContentType: 'unknown',
};

export const sampleWithFullData: IBem = {
  id: 16626,
  patrimonio: 6712,
  nome: 'front furiously chasm',
  descricao: 'circa hen bustling',
  numeroDeSerie: 'debunk gutter generally',
  dataAquisicao: dayjs('2024-05-09'),
  valorCompra: 15554.88,
  valorAtual: 728.86,
  estado: 'INSERVIVEL',
  status: 'MANUTENCAO',
  observacoes: 'round',
  imagem: '../fake-data/blob/hipster.png',
  imagemContentType: 'unknown',
};

export const sampleWithNewData: NewBem = {
  patrimonio: 28266,
  nome: 'forenenst eager shovel',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
