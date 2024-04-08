export interface ILocal {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  sala?: string | null;
}

export type NewLocal = Omit<ILocal, 'id'> & { id: null };
