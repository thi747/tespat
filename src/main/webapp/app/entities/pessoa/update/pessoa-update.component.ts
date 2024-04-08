import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPessoa } from '../pessoa.model';
import { PessoaService } from '../service/pessoa.service';
import { PessoaFormService, PessoaFormGroup } from './pessoa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-pessoa-update',
  templateUrl: './pessoa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PessoaUpdateComponent implements OnInit {
  isSaving = false;
  pessoa: IPessoa | null = null;

  protected pessoaService = inject(PessoaService);
  protected pessoaFormService = inject(PessoaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PessoaFormGroup = this.pessoaFormService.createPessoaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pessoa }) => {
      this.pessoa = pessoa;
      if (pessoa) {
        this.updateForm(pessoa);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pessoa = this.pessoaFormService.getPessoa(this.editForm);
    if (pessoa.id !== null) {
      this.subscribeToSaveResponse(this.pessoaService.update(pessoa));
    } else {
      this.subscribeToSaveResponse(this.pessoaService.create(pessoa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPessoa>>): void {
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

  protected updateForm(pessoa: IPessoa): void {
    this.pessoa = pessoa;
    this.pessoaFormService.resetForm(this.editForm, pessoa);
  }
}
