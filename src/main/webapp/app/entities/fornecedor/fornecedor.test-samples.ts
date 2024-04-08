import { IFornecedor, NewFornecedor } from './fornecedor.model';

export const sampleWithRequiredData: IFornecedor = {
  id: 31813,
  nome: 'petal loop gently',
  cpfOuCnpj: 'betweenXXXX',
};

export const sampleWithPartialData: IFornecedor = {
  id: 27550,
  nome: 'breed',
  cpfOuCnpj: 'naive scattering',
  email: 'Matilda.Walter@yahoo.com',
  endereco: 'ornate',
  cidade: 'peel if',
};

export const sampleWithFullData: IFornecedor = {
  id: 30006,
  nome: 'rescind alight',
  cpfOuCnpj: 'buzzingXXXX',
  email: 'Jennings.Rau@yahoo.com',
  descricao: 'finally',
  telefone: 'memorialise blah positively',
  endereco: 'through',
  cidade: 'whether',
  estado: 'hm',
};

export const sampleWithNewData: NewFornecedor = {
  nome: 'instead pirouette incidentally',
  cpfOuCnpj: 'pace muffle sheep',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
