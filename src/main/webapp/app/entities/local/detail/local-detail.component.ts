import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ILocal } from '../local.model';

@Component({
  standalone: true,
  selector: 'jhi-local-detail',
  templateUrl: './local-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class LocalDetailComponent {
  local = input<ILocal | null>(null);

  previousState(): void {
    window.history.back();
  }
}
