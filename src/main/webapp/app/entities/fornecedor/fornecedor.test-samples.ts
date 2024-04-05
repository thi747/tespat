import { IFornecedor, NewFornecedor } from './fornecedor.model';

export const sampleWithRequiredData: IFornecedor = {
  id: 31813,
  nome: 'petal loop gently',
  cpfOuCnpj: 'betweenXXXX',
};

export const sampleWithPartialData: IFornecedor = {
  id: 27550,
  nome: 'breed',
  descricao: 'naive scattering',
  cpfOuCnpj: 'excepting jovial',
  endereco: 'corny er compute',
  cidade: 'alight ha euthanize',
};

export const sampleWithFullData: IFornecedor = {
  id: 8316,
  nome: 'tiptoe memorialise blah',
  descricao: 'descriptive through silence',
  cpfOuCnpj: 'hen voluntarily coolly',
  email: 'Gardner_Spinka@yahoo.com',
  telefone: 'energetically formal',
  endereco: 'putrefy commonly',
  cidade: 'why factor',
  estado: 'to',
};

export const sampleWithNewData: NewFornecedor = {
  nome: 'around unlined',
  cpfOuCnpj: 'video circular',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
