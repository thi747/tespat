import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IMovimentacao } from '../movimentacao.model';

@Component({
  standalone: true,
  selector: 'jhi-movimentacao-detail',
  templateUrl: './movimentacao-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class MovimentacaoDetailComponent {
  movimentacao = input<IMovimentacao | null>(null);

  previousState(): void {
    window.history.back();
  }
}
