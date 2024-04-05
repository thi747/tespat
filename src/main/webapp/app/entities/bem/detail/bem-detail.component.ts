import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IBem } from '../bem.model';

@Component({
  standalone: true,
  selector: 'jhi-bem-detail',
  templateUrl: './bem-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class BemDetailComponent {
  @Input() bem: IBem | null = null;

  previousState(): void {
    window.history.back();
  }
}
