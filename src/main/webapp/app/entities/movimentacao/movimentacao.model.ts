import dayjs from 'dayjs/esm';
import { IBem } from 'app/entities/bem/bem.model';
import { ILocal } from 'app/entities/local/local.model';
import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { TipoMovimentacao } from 'app/entities/enumerations/tipo-movimentacao.model';

export interface IMovimentacao {
  id: number;
  data?: dayjs.Dayjs | null;
  descricao?: string | null;
  tipo?: keyof typeof TipoMovimentacao | null;
  bem?: Pick<IBem, 'id' | 'patrimonio'> | null;
  local?: Pick<ILocal, 'id' | 'nome'> | null;
  pessoa?: Pick<IPessoa, 'id' | 'usuario'> | null;
}

export type NewMovimentacao = Omit<IMovimentacao, 'id'> & { id: null };
