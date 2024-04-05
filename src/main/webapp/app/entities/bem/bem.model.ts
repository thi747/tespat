import dayjs from 'dayjs/esm';
import { ICategoria } from 'app/entities/categoria/categoria.model';
import { IFornecedor } from 'app/entities/fornecedor/fornecedor.model';
import { ILocal } from 'app/entities/local/local.model';
import { TipoConservacao } from 'app/entities/enumerations/tipo-conservacao.model';
import { TipoStatus } from 'app/entities/enumerations/tipo-status.model';

export interface IBem {
  patrimonio: number;
  nome?: string | null;
  descricao?: string | null;
  observacoes?: string | null;
  numeroDeSerie?: string | null;
  dataAquisicao?: dayjs.Dayjs | null;
  valorCompra?: number | null;
  valorAtual?: number | null;
  estado?: keyof typeof TipoConservacao | null;
  status?: keyof typeof TipoStatus | null;
  categoria?: ICategoria | null;
  fornecedor?: IFornecedor | null;
  local?: ILocal | null;
}

export type NewBem = Omit<IBem, 'patrimonio'> & { patrimonio: null };
