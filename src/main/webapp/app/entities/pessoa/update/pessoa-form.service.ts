import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPessoa, NewPessoa } from '../pessoa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { usuario: unknown }> = Partial<Omit<T, 'usuario'>> & { usuario: T['usuario'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPessoa for edit and NewPessoaFormGroupInput for create.
 */
type PessoaFormGroupInput = IPessoa | PartialWithRequiredKeyOf<NewPessoa>;

type PessoaFormDefaults = Pick<NewPessoa, 'usuario' | 'ativo'>;

type PessoaFormGroupContent = {
  usuario: FormControl<IPessoa['usuario'] | NewPessoa['usuario']>;
  nome: FormControl<IPessoa['nome']>;
  cpf: FormControl<IPessoa['cpf']>;
  email: FormControl<IPessoa['email']>;
  ativo: FormControl<IPessoa['ativo']>;
  endereco: FormControl<IPessoa['endereco']>;
  cidade: FormControl<IPessoa['cidade']>;
  estado: FormControl<IPessoa['estado']>;
};

export type PessoaFormGroup = FormGroup<PessoaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PessoaFormService {
  createPessoaFormGroup(pessoa: PessoaFormGroupInput = { usuario: null }): PessoaFormGroup {
    const pessoaRawValue = {
      ...this.getFormDefaults(),
      ...pessoa,
    };
    return new FormGroup<PessoaFormGroupContent>({
      usuario: new FormControl(
        { value: pessoaRawValue.usuario, disabled: pessoaRawValue.usuario !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(pessoaRawValue.nome, {
        validators: [Validators.required],
      }),
      cpf: new FormControl(pessoaRawValue.cpf, {
        validators: [Validators.required, Validators.minLength(11)],
      }),
      email: new FormControl(pessoaRawValue.email),
      ativo: new FormControl(pessoaRawValue.ativo, {
        validators: [Validators.required],
      }),
      endereco: new FormControl(pessoaRawValue.endereco),
      cidade: new FormControl(pessoaRawValue.cidade),
      estado: new FormControl(pessoaRawValue.estado, {
        validators: [Validators.minLength(2), Validators.maxLength(2)],
      }),
    });
  }

  getPessoa(form: PessoaFormGroup): IPessoa | NewPessoa {
    return form.getRawValue() as IPessoa | NewPessoa;
  }

  resetForm(form: PessoaFormGroup, pessoa: PessoaFormGroupInput): void {
    const pessoaRawValue = { ...this.getFormDefaults(), ...pessoa };
    form.reset(
      {
        ...pessoaRawValue,
        usuario: { value: pessoaRawValue.usuario, disabled: pessoaRawValue.usuario !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PessoaFormDefaults {
    return {
      usuario: null,
      ativo: false,
    };
  }
}
