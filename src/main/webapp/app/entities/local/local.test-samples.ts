import { ILocal, NewLocal } from './local.model';

export const sampleWithRequiredData: ILocal = {
  nome: 'd5915ad6-914d-4564-a15e-2d4dac7dfe2f',
};

export const sampleWithPartialData: ILocal = {
  nome: '16b0b61f-c580-4f4e-9c18-adbb6a9e3208',
  descricao: 'until',
};

export const sampleWithFullData: ILocal = {
  nome: '2e60216a-fb49-46bd-be2c-704d1afcf8e0',
  descricao: 'plump dazzling',
  sala: 'soap ugh duh',
};

export const sampleWithNewData: NewLocal = {
  nome: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
