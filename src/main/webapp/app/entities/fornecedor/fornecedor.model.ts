export interface IFornecedor {
  nome: string;
  descricao?: string | null;
  cpfOuCnpj?: string | null;
  email?: string | null;
  telefone?: string | null;
  endereco?: string | null;
  cidade?: string | null;
  estado?: string | null;
}

export type NewFornecedor = Omit<IFornecedor, 'nome'> & { nome: null };
