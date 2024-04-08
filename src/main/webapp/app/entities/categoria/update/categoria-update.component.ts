import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICategoria } from '../categoria.model';
import { CategoriaService } from '../service/categoria.service';
import { CategoriaFormService, CategoriaFormGroup } from './categoria-form.service';

@Component({
  standalone: true,
  selector: 'jhi-categoria-update',
  templateUrl: './categoria-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CategoriaUpdateComponent implements OnInit {
  isSaving = false;
  categoria: ICategoria | null = null;

  protected categoriaService = inject(CategoriaService);
  protected categoriaFormService = inject(CategoriaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CategoriaFormGroup = this.categoriaFormService.createCategoriaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoria }) => {
      this.categoria = categoria;
      if (categoria) {
        this.updateForm(categoria);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categoria = this.categoriaFormService.getCategoria(this.editForm);
    this.subscribeToSaveResponse(this.categoriaService.create(categoria));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoria>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(categoria: ICategoria): void {
    this.categoria = categoria;
    this.categoriaFormService.resetForm(this.editForm, categoria);
  }
}
