import { Component, inject, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICategoria } from 'app/entities/categoria/categoria.model';
import { CategoriaService } from 'app/entities/categoria/service/categoria.service';
import { IFornecedor } from 'app/entities/fornecedor/fornecedor.model';
import { FornecedorService } from 'app/entities/fornecedor/service/fornecedor.service';
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
  fornecedoresSharedCollection: IFornecedor[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected bemService = inject(BemService);
  protected bemFormService = inject(BemFormService);
  protected categoriaService = inject(CategoriaService);
  protected fornecedorService = inject(FornecedorService);
  protected elementRef = inject(ElementRef);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: BemFormGroup = this.bemFormService.createBemFormGroup();

  compareCategoria = (o1: ICategoria | null, o2: ICategoria | null): boolean => this.categoriaService.compareCategoria(o1, o2);

  compareFornecedor = (o1: IFornecedor | null, o2: IFornecedor | null): boolean => this.fornecedorService.compareFornecedor(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bem }) => {
      this.bem = bem;
      if (bem) {
        this.updateForm(bem);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('tesPatApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
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
    this.fornecedoresSharedCollection = this.fornecedorService.addFornecedorToCollectionIfMissing<IFornecedor>(
      this.fornecedoresSharedCollection,
      bem.fornecedor,
    );
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
        map((fornecedores: IFornecedor[]) =>
          this.fornecedorService.addFornecedorToCollectionIfMissing<IFornecedor>(fornecedores, this.bem?.fornecedor),
        ),
      )
      .subscribe((fornecedores: IFornecedor[]) => (this.fornecedoresSharedCollection = fornecedores));
  }
}
