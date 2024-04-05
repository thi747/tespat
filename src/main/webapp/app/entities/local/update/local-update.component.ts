import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ILocal } from '../local.model';
import { LocalService } from '../service/local.service';
import { LocalFormService, LocalFormGroup } from './local-form.service';

@Component({
  standalone: true,
  selector: 'jhi-local-update',
  templateUrl: './local-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class LocalUpdateComponent implements OnInit {
  isSaving = false;
  local: ILocal | null = null;

  protected localService = inject(LocalService);
  protected localFormService = inject(LocalFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: LocalFormGroup = this.localFormService.createLocalFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ local }) => {
      this.local = local;
      if (local) {
        this.updateForm(local);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const local = this.localFormService.getLocal(this.editForm);
    if (local.nome !== null) {
      this.subscribeToSaveResponse(this.localService.update(local));
    } else {
      this.subscribeToSaveResponse(this.localService.create(local));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocal>>): void {
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

  protected updateForm(local: ILocal): void {
    this.local = local;
    this.localFormService.resetForm(this.editForm, local);
  }
}
