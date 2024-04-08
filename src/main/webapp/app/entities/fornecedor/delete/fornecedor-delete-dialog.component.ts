import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFornecedor } from '../fornecedor.model';
import { FornecedorService } from '../service/fornecedor.service';

@Component({
  standalone: true,
  templateUrl: './fornecedor-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FornecedorDeleteDialogComponent {
  fornecedor?: IFornecedor;

  protected fornecedorService = inject(FornecedorService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.fornecedorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
