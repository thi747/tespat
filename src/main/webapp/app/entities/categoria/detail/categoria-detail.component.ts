import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICategoria } from '../categoria.model';

@Component({
  standalone: true,
  selector: 'jhi-categoria-detail',
  templateUrl: './categoria-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CategoriaDetailComponent {
  @Input() categoria: ICategoria | null = null;

  previousState(): void {
    window.history.back();
  }
}
