import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFornecedor, NewFornecedor } from '../fornecedor.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { nome: unknown }> = Partial<Omit<T, 'nome'>> & { nome: T['nome'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFornecedor for edit and NewFornecedorFormGroupInput for create.
 */
type FornecedorFormGroupInput = IFornecedor | PartialWithRequiredKeyOf<NewFornecedor>;

type FornecedorFormDefaults = Pick<NewFornecedor, 'nome'>;

type FornecedorFormGroupContent = {
  nome: FormControl<IFornecedor['nome'] | NewFornecedor['nome']>;
  descricao: FormControl<IFornecedor['descricao']>;
  cpfOuCnpj: FormControl<IFornecedor['cpfOuCnpj']>;
  email: FormControl<IFornecedor['email']>;
  telefone: FormControl<IFornecedor['telefone']>;
  endereco: FormControl<IFornecedor['endereco']>;
  cidade: FormControl<IFornecedor['cidade']>;
  estado: FormControl<IFornecedor['estado']>;
};

export type FornecedorFormGroup = FormGroup<FornecedorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FornecedorFormService {
  createFornecedorFormGroup(fornecedor: FornecedorFormGroupInput = { nome: null }): FornecedorFormGroup {
    const fornecedorRawValue = {
      ...this.getFormDefaults(),
      ...fornecedor,
    };
    return new FormGroup<FornecedorFormGroupContent>({
      nome: new FormControl(
        { value: fornecedorRawValue.nome, disabled: fornecedorRawValue.nome !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      descricao: new FormControl(fornecedorRawValue.descricao),
      cpfOuCnpj: new FormControl(fornecedorRawValue.cpfOuCnpj, {
        validators: [Validators.required, Validators.minLength(11)],
      }),
      email: new FormControl(fornecedorRawValue.email),
      telefone: new FormControl(fornecedorRawValue.telefone),
      endereco: new FormControl(fornecedorRawValue.endereco),
      cidade: new FormControl(fornecedorRawValue.cidade),
      estado: new FormControl(fornecedorRawValue.estado, {
        validators: [Validators.minLength(2), Validators.maxLength(2)],
      }),
    });
  }

  getFornecedor(form: FornecedorFormGroup): IFornecedor | NewFornecedor {
    return form.getRawValue() as IFornecedor | NewFornecedor;
  }

  resetForm(form: FornecedorFormGroup, fornecedor: FornecedorFormGroupInput): void {
    const fornecedorRawValue = { ...this.getFormDefaults(), ...fornecedor };
    form.reset(
      {
        ...fornecedorRawValue,
        nome: { value: fornecedorRawValue.nome, disabled: fornecedorRawValue.nome !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FornecedorFormDefaults {
    return {
      nome: null,
    };
  }
}
