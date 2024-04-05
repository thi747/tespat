export interface ILocal {
  nome: string;
  descricao?: string | null;
  sala?: string | null;
}

export type NewLocal = Omit<ILocal, 'nome'> & { nome: null };
