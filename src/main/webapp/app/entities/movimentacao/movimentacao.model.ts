import dayjs from 'dayjs/esm';
import { IBem } from 'app/entities/bem/bem.model';
import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { TipoMovimentacao } from 'app/entities/enumerations/tipo-movimentacao.model';

export interface IMovimentacao {
  id: number;
  descricao?: string | null;
  data?: dayjs.Dayjs | null;
  tipo?: keyof typeof TipoMovimentacao | null;
  bem?: IBem | null;
  pessoa?: IPessoa | null;
}

export type NewMovimentacao = Omit<IMovimentacao, 'id'> & { id: null };
