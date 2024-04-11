import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IBem } from 'app/entities/bem/bem.model';
import { BemService } from 'app/entities/bem/service/bem.service';
import { ILocal } from 'app/entities/local/local.model';
import { LocalService } from 'app/entities/local/service/local.service';
import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { TipoMovimentacao } from 'app/entities/enumerations/tipo-movimentacao.model';
import { MovimentacaoService } from '../service/movimentacao.service';
import { IMovimentacao } from '../movimentacao.model';
import { MovimentacaoFormService, MovimentacaoFormGroup } from './movimentacao-form.service';

@Component({
  standalone: true,
  selector: 'jhi-movimentacao-update',
  templateUrl: './movimentacao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MovimentacaoUpdateComponent implements OnInit {
  isSaving = false;
  movimentacao: IMovimentacao | null = null;
  tipoMovimentacaoValues = Object.keys(TipoMovimentacao);

  bensSharedCollection: IBem[] = [];
  locaisSharedCollection: ILocal[] = [];
  pessoasSharedCollection: IPessoa[] = [];

  protected movimentacaoService = inject(MovimentacaoService);
  protected movimentacaoFormService = inject(MovimentacaoFormService);
  protected bemService = inject(BemService);
  protected localService = inject(LocalService);
  protected pessoaService = inject(PessoaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MovimentacaoFormGroup = this.movimentacaoFormService.createMovimentacaoFormGroup();

  compareBem = (o1: IBem | null, o2: IBem | null): boolean => this.bemService.compareBem(o1, o2);

  compareLocal = (o1: ILocal | null, o2: ILocal | null): boolean => this.localService.compareLocal(o1, o2);

  comparePessoa = (o1: IPessoa | null, o2: IPessoa | null): boolean => this.pessoaService.comparePessoa(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ movimentacao }) => {
      this.movimentacao = movimentacao;
      if (movimentacao) {
        this.updateForm(movimentacao);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const movimentacao = this.movimentacaoFormService.getMovimentacao(this.editForm);
    if (movimentacao.id !== null) {
      this.subscribeToSaveResponse(this.movimentacaoService.update(movimentacao));
    } else {
      this.subscribeToSaveResponse(this.movimentacaoService.create(movimentacao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMovimentacao>>): void {
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

  protected updateForm(movimentacao: IMovimentacao): void {
    this.movimentacao = movimentacao;
    this.movimentacaoFormService.resetForm(this.editForm, movimentacao);

    this.bensSharedCollection = this.bemService.addBemToCollectionIfMissing<IBem>(this.bensSharedCollection, movimentacao.bem);
    this.locaisSharedCollection = this.localService.addLocalToCollectionIfMissing<ILocal>(this.locaisSharedCollection, movimentacao.local);
    this.pessoasSharedCollection = this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(
      this.pessoasSharedCollection,
      movimentacao.pessoa,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.bemService
      .query()
      .pipe(map((res: HttpResponse<IBem[]>) => res.body ?? []))
      .pipe(map((bens: IBem[]) => this.bemService.addBemToCollectionIfMissing<IBem>(bens, this.movimentacao?.bem)))
      .subscribe((bens: IBem[]) => (this.bensSharedCollection = bens));

    this.localService
      .query()
      .pipe(map((res: HttpResponse<ILocal[]>) => res.body ?? []))
      .pipe(map((locais: ILocal[]) => this.localService.addLocalToCollectionIfMissing<ILocal>(locais, this.movimentacao?.local)))
      .subscribe((locais: ILocal[]) => (this.locaisSharedCollection = locais));

    this.pessoaService
      .query()
      .pipe(map((res: HttpResponse<IPessoa[]>) => res.body ?? []))
      .pipe(map((pessoas: IPessoa[]) => this.pessoaService.addPessoaToCollectionIfMissing<IPessoa>(pessoas, this.movimentacao?.pessoa)))
      .subscribe((pessoas: IPessoa[]) => (this.pessoasSharedCollection = pessoas));
  }
}
