import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPessoa } from '../pessoa.model';
import { PessoaService } from '../service/pessoa.service';

@Component({
  standalone: true,
  templateUrl: './pessoa-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PessoaDeleteDialogComponent {
  pessoa?: IPessoa;

  protected pessoaService = inject(PessoaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pessoaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
