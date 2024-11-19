import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IRiesgo } from '../riesgo.model';

@Component({
  standalone: true,
  selector: 'jhi-riesgo-detail',
  templateUrl: './riesgo-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class RiesgoDetailComponent {
  riesgo = input<IRiesgo | null>(null);

  previousState(): void {
    window.history.back();
  }
}
