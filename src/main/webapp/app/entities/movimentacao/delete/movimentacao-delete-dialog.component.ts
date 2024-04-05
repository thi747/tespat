import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMovimentacao } from '../movimentacao.model';
import { MovimentacaoService } from '../service/movimentacao.service';

@Component({
  standalone: true,
  templateUrl: './movimentacao-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MovimentacaoDeleteDialogComponent {
  movimentacao?: IMovimentacao;

  protected movimentacaoService = inject(MovimentacaoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.movimentacaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
