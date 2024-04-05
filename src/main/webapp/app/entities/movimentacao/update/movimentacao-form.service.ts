import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMovimentacao, NewMovimentacao } from '../movimentacao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMovimentacao for edit and NewMovimentacaoFormGroupInput for create.
 */
type MovimentacaoFormGroupInput = IMovimentacao | PartialWithRequiredKeyOf<NewMovimentacao>;

type MovimentacaoFormDefaults = Pick<NewMovimentacao, 'id'>;

type MovimentacaoFormGroupContent = {
  id: FormControl<IMovimentacao['id'] | NewMovimentacao['id']>;
  descricao: FormControl<IMovimentacao['descricao']>;
  data: FormControl<IMovimentacao['data']>;
  tipo: FormControl<IMovimentacao['tipo']>;
  bem: FormControl<IMovimentacao['bem']>;
  pessoa: FormControl<IMovimentacao['pessoa']>;
};

export type MovimentacaoFormGroup = FormGroup<MovimentacaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MovimentacaoFormService {
  createMovimentacaoFormGroup(movimentacao: MovimentacaoFormGroupInput = { id: null }): MovimentacaoFormGroup {
    const movimentacaoRawValue = {
      ...this.getFormDefaults(),
      ...movimentacao,
    };
    return new FormGroup<MovimentacaoFormGroupContent>({
      id: new FormControl(
        { value: movimentacaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      descricao: new FormControl(movimentacaoRawValue.descricao),
      data: new FormControl(movimentacaoRawValue.data),
      tipo: new FormControl(movimentacaoRawValue.tipo, {
        validators: [Validators.required],
      }),
      bem: new FormControl(movimentacaoRawValue.bem),
      pessoa: new FormControl(movimentacaoRawValue.pessoa),
    });
  }

  getMovimentacao(form: MovimentacaoFormGroup): IMovimentacao | NewMovimentacao {
    return form.getRawValue() as IMovimentacao | NewMovimentacao;
  }

  resetForm(form: MovimentacaoFormGroup, movimentacao: MovimentacaoFormGroupInput): void {
    const movimentacaoRawValue = { ...this.getFormDefaults(), ...movimentacao };
    form.reset(
      {
        ...movimentacaoRawValue,
        id: { value: movimentacaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MovimentacaoFormDefaults {
    return {
      id: null,
    };
  }
}
