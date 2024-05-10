import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPessoa } from '../pessoa.model';

@Component({
  standalone: true,
  selector: 'jhi-pessoa-detail',
  templateUrl: './pessoa-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PessoaDetailComponent {
  pessoa = input<IPessoa | null>(null);

  previousState(): void {
    window.history.back();
  }
}
