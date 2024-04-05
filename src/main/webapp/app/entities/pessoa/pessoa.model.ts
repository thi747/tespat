export interface IPessoa {
  usuario: string;
  nome?: string | null;
  cpf?: string | null;
  email?: string | null;
  ativo?: boolean | null;
  endereco?: string | null;
  cidade?: string | null;
  estado?: string | null;
}

export type NewPessoa = Omit<IPessoa, 'usuario'> & { usuario: null };
