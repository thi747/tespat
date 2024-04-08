import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICategoria } from 'app/entities/categoria/categoria.model';
import { CategoriaService } from 'app/entities/categoria/service/categoria.service';
import { IFornecedor } from 'app/entities/fornecedor/fornecedor.model';
import { FornecedorService } from 'app/entities/fornecedor/service/fornecedor.service';
import { ILocal } from 'app/entities/local/local.model';
import { LocalService } from 'app/entities/local/service/local.service';
import { TipoConservacao } from 'app/entities/enumerations/tipo-conservacao.model';
import { TipoStatus } from 'app/entities/enumerations/tipo-status.model';
import { BemService } from '../service/bem.service';
import { IBem } from '../bem.model';
import { BemFormService, BemFormGroup } from './bem-form.service';

@Component({
  standalone: true,
  selector: 'jhi-bem-update',
  templateUrl: './bem-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class BemUpdateComponent implements OnInit {
  isSaving = false;
  bem: IBem | null = null;
  tipoConservacaoValues = Object.keys(TipoConservacao);
  tipoStatusValues = Object.keys(TipoStatus);

  categoriasSharedCollection: ICategoria[] = [];
  fornecedorsSharedCollection: IFornecedor[] = [];
  localsSharedCollection: ILocal[] = [];

  protected bemService = inject(BemService);
  protected bemFormService = inject(BemFormService);
  protected categoriaService = inject(CategoriaService);
  protected fornecedorService = inject(FornecedorService);
  protected localService = inject(LocalService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: BemFormGroup = this.bemFormService.createBemFormGroup();

  compareCategoria = (o1: ICategoria | null, o2: ICategoria | null): boolean => this.categoriaService.compareCategoria(o1, o2);

  compareFornecedor = (o1: IFornecedor | null, o2: IFornecedor | null): boolean => this.fornecedorService.compareFornecedor(o1, o2);

  compareLocal = (o1: ILocal | null, o2: ILocal | null): boolean => this.localService.compareLocal(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bem }) => {
      this.bem = bem;
      if (bem) {
        this.updateForm(bem);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bem = this.bemFormService.getBem(this.editForm);
    if (bem.id !== null) {
      this.subscribeToSaveResponse(this.bemService.update(bem));
    } else {
      this.subscribeToSaveResponse(this.bemService.create(bem));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBem>>): void {
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

  protected updateForm(bem: IBem): void {
    this.bem = bem;
    this.bemFormService.resetForm(this.editForm, bem);

    this.categoriasSharedCollection = this.categoriaService.addCategoriaToCollectionIfMissing<ICategoria>(
      this.categoriasSharedCollection,
      bem.categoria,
    );
    this.fornecedorsSharedCollection = this.fornecedorService.addFornecedorToCollectionIfMissing<IFornecedor>(
      this.fornecedorsSharedCollection,
      bem.fornecedor,
    );
    this.localsSharedCollection = this.localService.addLocalToCollectionIfMissing<ILocal>(this.localsSharedCollection, bem.local);
  }

  protected loadRelationshipsOptions(): void {
    this.categoriaService
      .query()
      .pipe(map((res: HttpResponse<ICategoria[]>) => res.body ?? []))
      .pipe(
        map((categorias: ICategoria[]) =>
          this.categoriaService.addCategoriaToCollectionIfMissing<ICategoria>(categorias, this.bem?.categoria),
        ),
      )
      .subscribe((categorias: ICategoria[]) => (this.categoriasSharedCollection = categorias));

    this.fornecedorService
      .query()
      .pipe(map((res: HttpResponse<IFornecedor[]>) => res.body ?? []))
      .pipe(
        map((fornecedors: IFornecedor[]) =>
          this.fornecedorService.addFornecedorToCollectionIfMissing<IFornecedor>(fornecedors, this.bem?.fornecedor),
        ),
      )
      .subscribe((fornecedors: IFornecedor[]) => (this.fornecedorsSharedCollection = fornecedors));

    this.localService
      .query()
      .pipe(map((res: HttpResponse<ILocal[]>) => res.body ?? []))
      .pipe(map((locals: ILocal[]) => this.localService.addLocalToCollectionIfMissing<ILocal>(locals, this.bem?.local)))
      .subscribe((locals: ILocal[]) => (this.localsSharedCollection = locals));
  }
}
