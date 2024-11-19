import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IAmenazas } from '../amenazas.model';

@Component({
  standalone: true,
  selector: 'jhi-amenazas-detail',
  templateUrl: './amenazas-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AmenazasDetailComponent {
  amenazas = input<IAmenazas | null>(null);

  previousState(): void {
    window.history.back();
  }
}
