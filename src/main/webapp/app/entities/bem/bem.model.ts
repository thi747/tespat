import dayjs from 'dayjs/esm';
import { ICategoria } from 'app/entities/categoria/categoria.model';
import { IFornecedor } from 'app/entities/fornecedor/fornecedor.model';
import { TipoConservacao } from 'app/entities/enumerations/tipo-conservacao.model';
import { TipoStatus } from 'app/entities/enumerations/tipo-status.model';

export interface IBem {
  id: number;
  patrimonio?: number | null;
  nome?: string | null;
  descricao?: string | null;
  numeroDeSerie?: string | null;
  dataAquisicao?: dayjs.Dayjs | null;
  valorCompra?: number | null;
  valorAtual?: number | null;
  estado?: keyof typeof TipoConservacao | null;
  status?: keyof typeof TipoStatus | null;
  observacoes?: string | null;
  categoria?: Pick<ICategoria, 'id' | 'nome'> | null;
  fornecedor?: Pick<IFornecedor, 'id' | 'nome'> | null;
}

export type NewBem = Omit<IBem, 'id'> & { id: null };
