import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICategoria, NewCategoria } from '../categoria.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { nome: unknown }> = Partial<Omit<T, 'nome'>> & { nome: T['nome'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICategoria for edit and NewCategoriaFormGroupInput for create.
 */
type CategoriaFormGroupInput = ICategoria | PartialWithRequiredKeyOf<NewCategoria>;

type CategoriaFormDefaults = Pick<NewCategoria, 'nome'>;

type CategoriaFormGroupContent = {
  nome: FormControl<ICategoria['nome'] | NewCategoria['nome']>;
};

export type CategoriaFormGroup = FormGroup<CategoriaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CategoriaFormService {
  createCategoriaFormGroup(categoria: CategoriaFormGroupInput = { nome: null }): CategoriaFormGroup {
    const categoriaRawValue = {
      ...this.getFormDefaults(),
      ...categoria,
    };
    return new FormGroup<CategoriaFormGroupContent>({
      nome: new FormControl(
        { value: categoriaRawValue.nome, disabled: categoriaRawValue.nome !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
    });
  }

  getCategoria(form: CategoriaFormGroup): NewCategoria {
    return form.getRawValue() as NewCategoria;
  }

  resetForm(form: CategoriaFormGroup, categoria: CategoriaFormGroupInput): void {
    const categoriaRawValue = { ...this.getFormDefaults(), ...categoria };
    form.reset(
      {
        ...categoriaRawValue,
        nome: { value: categoriaRawValue.nome, disabled: categoriaRawValue.nome !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CategoriaFormDefaults {
    return {
      nome: null,
    };
  }
}
