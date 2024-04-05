import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBem, NewBem } from '../bem.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { patrimonio: unknown }> = Partial<Omit<T, 'patrimonio'>> & { patrimonio: T['patrimonio'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBem for edit and NewBemFormGroupInput for create.
 */
type BemFormGroupInput = IBem | PartialWithRequiredKeyOf<NewBem>;

type BemFormDefaults = Pick<NewBem, 'patrimonio'>;

type BemFormGroupContent = {
  patrimonio: FormControl<IBem['patrimonio'] | NewBem['patrimonio']>;
  nome: FormControl<IBem['nome']>;
  descricao: FormControl<IBem['descricao']>;
  observacoes: FormControl<IBem['observacoes']>;
  numeroDeSerie: FormControl<IBem['numeroDeSerie']>;
  dataAquisicao: FormControl<IBem['dataAquisicao']>;
  valorCompra: FormControl<IBem['valorCompra']>;
  valorAtual: FormControl<IBem['valorAtual']>;
  estado: FormControl<IBem['estado']>;
  status: FormControl<IBem['status']>;
  categoria: FormControl<IBem['categoria']>;
  fornecedor: FormControl<IBem['fornecedor']>;
  local: FormControl<IBem['local']>;
};

export type BemFormGroup = FormGroup<BemFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BemFormService {
  createBemFormGroup(bem: BemFormGroupInput = { patrimonio: null }): BemFormGroup {
    const bemRawValue = {
      ...this.getFormDefaults(),
      ...bem,
    };
    return new FormGroup<BemFormGroupContent>({
      patrimonio: new FormControl(
        { value: bemRawValue.patrimonio, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(bemRawValue.nome, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(bemRawValue.descricao),
      observacoes: new FormControl(bemRawValue.observacoes),
      numeroDeSerie: new FormControl(bemRawValue.numeroDeSerie),
      dataAquisicao: new FormControl(bemRawValue.dataAquisicao),
      valorCompra: new FormControl(bemRawValue.valorCompra),
      valorAtual: new FormControl(bemRawValue.valorAtual),
      estado: new FormControl(bemRawValue.estado),
      status: new FormControl(bemRawValue.status),
      categoria: new FormControl(bemRawValue.categoria),
      fornecedor: new FormControl(bemRawValue.fornecedor),
      local: new FormControl(bemRawValue.local),
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
        patrimonio: { value: bemRawValue.patrimonio, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): BemFormDefaults {
    return {
      patrimonio: null,
    };
  }
}
