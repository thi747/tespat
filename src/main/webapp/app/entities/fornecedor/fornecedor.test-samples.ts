import { IFornecedor, NewFornecedor } from './fornecedor.model';

export const sampleWithRequiredData: IFornecedor = {
  nome: '4ea2a0ed-2b71-4ea4-9e00-d635bb1678b9',
  cpfOuCnpj: 'finallyXXXX',
};

export const sampleWithPartialData: IFornecedor = {
  nome: '785b52af-1ec6-4df6-b93a-5d273c42b3f0',
  cpfOuCnpj: 'hen voluntarily coolly',
  email: 'Gardner_Spinka@yahoo.com',
  telefone: 'energetically formal',
  endereco: 'putrefy commonly',
};

export const sampleWithFullData: IFornecedor = {
  nome: '6c91394f-8131-46f5-8469-7b57d612cbc1',
  descricao: 'huge video circular',
  cpfOuCnpj: 'gah recklessly willfully',
  email: 'Dallas67@gmail.com',
  telefone: 'surmise mmm and',
  endereco: 'oh if playfully',
  cidade: 'although finally address',
  estado: 'un',
};

export const sampleWithNewData: NewFornecedor = {
  cpfOuCnpj: 'an follow successfully',
  nome: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
