import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ILocal } from '../local.model';
import { LocalService } from '../service/local.service';

@Component({
  standalone: true,
  templateUrl: './local-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class LocalDeleteDialogComponent {
  local?: ILocal;

  protected localService = inject(LocalService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.localService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
