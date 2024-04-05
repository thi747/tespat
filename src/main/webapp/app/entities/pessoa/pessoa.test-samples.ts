import { IPessoa, NewPessoa } from './pessoa.model';

export const sampleWithRequiredData: IPessoa = {
  usuario: '97364310-ea01-4d34-8105-a2cd701c79a5',
  nome: 'abaft',
  cpf: 'hobnob thoughtfully',
  ativo: true,
};

export const sampleWithPartialData: IPessoa = {
  usuario: '6cd7ee45-daf5-4573-9874-21d73afd0969',
  nome: 'for',
  cpf: 'whether entrepreneur',
  email: 'Amir.Schultz@gmail.com',
  ativo: true,
};

export const sampleWithFullData: IPessoa = {
  usuario: 'd5babf45-7c5d-4522-b33e-ddff4d99a556',
  nome: 'equate meanwhile hometown',
  cpf: 'capXXXXXXXX',
  email: 'Carlee20@gmail.com',
  ativo: false,
  endereco: 'yahoo hate find',
  cidade: 'like drat',
  estado: 'sk',
};

export const sampleWithNewData: NewPessoa = {
  nome: 'abnormally a blissfully',
  cpf: 'yuck preheat',
  ativo: false,
  usuario: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
