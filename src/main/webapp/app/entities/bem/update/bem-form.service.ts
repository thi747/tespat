import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBem, NewBem } from '../bem.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBem for edit and NewBemFormGroupInput for create.
 */
type BemFormGroupInput = IBem | PartialWithRequiredKeyOf<NewBem>;

type BemFormDefaults = Pick<NewBem, 'id'>;

type BemFormGroupContent = {
  id: FormControl<IBem['id'] | NewBem['id']>;
  patrimonio: FormControl<IBem['patrimonio']>;
  nome: FormControl<IBem['nome']>;
  descricao: FormControl<IBem['descricao']>;
  numeroDeSerie: FormControl<IBem['numeroDeSerie']>;
  dataAquisicao: FormControl<IBem['dataAquisicao']>;
  valorCompra: FormControl<IBem['valorCompra']>;
  valorAtual: FormControl<IBem['valorAtual']>;
  estado: FormControl<IBem['estado']>;
  status: FormControl<IBem['status']>;
  observacoes: FormControl<IBem['observacoes']>;
  categoria: FormControl<IBem['categoria']>;
  fornecedor: FormControl<IBem['fornecedor']>;
};

export type BemFormGroup = FormGroup<BemFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BemFormService {
  createBemFormGroup(bem: BemFormGroupInput = { id: null }): BemFormGroup {
    const bemRawValue = {
      ...this.getFormDefaults(),
      ...bem,
    };
    return new FormGroup<BemFormGroupContent>({
      id: new FormControl(
        { value: bemRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      patrimonio: new FormControl(bemRawValue.patrimonio, {
        validators: [Validators.required],
      }),
      nome: new FormControl(bemRawValue.nome, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(bemRawValue.descricao),
      numeroDeSerie: new FormControl(bemRawValue.numeroDeSerie),
      dataAquisicao: new FormControl(bemRawValue.dataAquisicao),
      valorCompra: new FormControl(bemRawValue.valorCompra),
      valorAtual: new FormControl(bemRawValue.valorAtual),
      estado: new FormControl(bemRawValue.estado),
      status: new FormControl(bemRawValue.status),
      observacoes: new FormControl(bemRawValue.observacoes),
      categoria: new FormControl(bemRawValue.categoria),
      fornecedor: new FormControl(bemRawValue.fornecedor),
    });
  }

  getBem(form: BemFormGroup): IBem | NewBem {
    return form.getRawValue() as IBem | NewBem;
  }

  resetForm(form: BemFormGroup, bem: BemFormGroupInput): void {
    const bemRawValue = { ...this.getFormDefaults(), ...bem };
    form.reset(
      {
        ...bemRawValue,
        id: { value: bemRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): BemFormDefaults {
    return {
      id: null,
    };
  }
}
