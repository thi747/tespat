import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IBem } from '../bem.model';
import { BemService } from '../service/bem.service';

@Component({
  standalone: true,
  templateUrl: './bem-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class BemDeleteDialogComponent {
  bem?: IBem;

  protected bemService = inject(BemService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bemService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
