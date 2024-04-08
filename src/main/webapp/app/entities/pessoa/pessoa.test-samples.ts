import { IPessoa, NewPessoa } from './pessoa.model';

export const sampleWithRequiredData: IPessoa = {
  id: 3771,
  usuario: 'necessary budget keen',
  nome: 'equally',
  cpf: 'whereas till',
  ativo: false,
};

export const sampleWithPartialData: IPessoa = {
  id: 23750,
  usuario: 'hopelessly who spend',
  nome: 'corner',
  cpf: 'gee anaesthetise',
  email: 'Margaretta27@hotmail.com',
  ativo: true,
  cidade: 'beautifully',
};

export const sampleWithFullData: IPessoa = {
  id: 3165,
  usuario: 'in-joke outline hobnob',
  nome: 'woot thirsty rare',
  cpf: 'epauliere yet award',
  email: 'Coby.Hessel@gmail.com',
  ativo: true,
  endereco: 'whether entrepreneur',
  cidade: 'tightly',
  estado: 'lo',
};

export const sampleWithNewData: NewPessoa = {
  usuario: 'unimpressively',
  nome: 'scholarly equate',
  cpf: 'ironXXXXXXX',
  ativo: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
