import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILocal, NewLocal } from '../local.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { nome: unknown }> = Partial<Omit<T, 'nome'>> & { nome: T['nome'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILocal for edit and NewLocalFormGroupInput for create.
 */
type LocalFormGroupInput = ILocal | PartialWithRequiredKeyOf<NewLocal>;

type LocalFormDefaults = Pick<NewLocal, 'nome'>;

type LocalFormGroupContent = {
  nome: FormControl<ILocal['nome'] | NewLocal['nome']>;
  descricao: FormControl<ILocal['descricao']>;
  sala: FormControl<ILocal['sala']>;
};

export type LocalFormGroup = FormGroup<LocalFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LocalFormService {
  createLocalFormGroup(local: LocalFormGroupInput = { nome: null }): LocalFormGroup {
    const localRawValue = {
      ...this.getFormDefaults(),
      ...local,
    };
    return new FormGroup<LocalFormGroupContent>({
      nome: new FormControl(
        { value: localRawValue.nome, disabled: localRawValue.nome !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      descricao: new FormControl(localRawValue.descricao),
      sala: new FormControl(localRawValue.sala),
    });
  }

  getLocal(form: LocalFormGroup): ILocal | NewLocal {
    return form.getRawValue() as ILocal | NewLocal;
  }

  resetForm(form: LocalFormGroup, local: LocalFormGroupInput): void {
    const localRawValue = { ...this.getFormDefaults(), ...local };
    form.reset(
      {
        ...localRawValue,
        nome: { value: localRawValue.nome, disabled: localRawValue.nome !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): LocalFormDefaults {
    return {
      nome: null,
    };
  }
}
