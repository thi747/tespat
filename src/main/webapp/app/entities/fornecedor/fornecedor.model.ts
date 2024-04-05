export interface IFornecedor {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  cpfOuCnpj?: string | null;
  email?: string | null;
  telefone?: string | null;
  endereco?: string | null;
  cidade?: string | null;
  estado?: string | null;
}

export type NewFornecedor = Omit<IFornecedor, 'id'> & { id: null };
