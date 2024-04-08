import { ICategoria, NewCategoria } from './categoria.model';

export const sampleWithRequiredData: ICategoria = {
  nome: '14ceea3b-5810-472e-91b6-83b27592fc14',
};

export const sampleWithPartialData: ICategoria = {
  nome: 'cd23a254-db0d-4cf1-a6ed-e655cefb3362',
};

export const sampleWithFullData: ICategoria = {
  nome: 'a3666059-82ac-462a-98d6-b927d80452ab',
};

export const sampleWithNewData: NewCategoria = {
  nome: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
