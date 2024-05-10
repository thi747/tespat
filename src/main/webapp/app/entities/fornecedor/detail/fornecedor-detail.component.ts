import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IFornecedor } from '../fornecedor.model';

@Component({
  standalone: true,
  selector: 'jhi-fornecedor-detail',
  templateUrl: './fornecedor-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class FornecedorDetailComponent {
  fornecedor = input<IFornecedor | null>(null);

  previousState(): void {
    window.history.back();
  }
}
