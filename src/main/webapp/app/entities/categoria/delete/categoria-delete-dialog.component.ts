import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICategoria } from '../categoria.model';
import { CategoriaService } from '../service/categoria.service';

@Component({
  standalone: true,
  templateUrl: './categoria-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CategoriaDeleteDialogComponent {
  categoria?: ICategoria;

  protected categoriaService = inject(CategoriaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.categoriaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
