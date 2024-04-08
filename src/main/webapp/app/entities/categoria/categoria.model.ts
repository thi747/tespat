export interface ICategoria {
  nome: string;
}

export type NewCategoria = Omit<ICategoria, 'nome'> & { nome: null };
