import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFornecedor } from '../fornecedor.model';
import { FornecedorService } from '../service/fornecedor.service';
import { FornecedorFormService, FornecedorFormGroup } from './fornecedor-form.service';

@Component({
  standalone: true,
  selector: 'jhi-fornecedor-update',
  templateUrl: './fornecedor-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FornecedorUpdateComponent implements OnInit {
  isSaving = false;
  fornecedor: IFornecedor | null = null;

  protected fornecedorService = inject(FornecedorService);
  protected fornecedorFormService = inject(FornecedorFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FornecedorFormGroup = this.fornecedorFormService.createFornecedorFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fornecedor }) => {
      this.fornecedor = fornecedor;
      if (fornecedor) {
        this.updateForm(fornecedor);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fornecedor = this.fornecedorFormService.getFornecedor(this.editForm);
    if (fornecedor.id !== null) {
      this.subscribeToSaveResponse(this.fornecedorService.update(fornecedor));
    } else {
      this.subscribeToSaveResponse(this.fornecedorService.create(fornecedor));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFornecedor>>): void {
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

  protected updateForm(fornecedor: IFornecedor): void {
    this.fornecedor = fornecedor;
    this.fornecedorFormService.resetForm(this.editForm, fornecedor);
  }
}
