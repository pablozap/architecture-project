import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IControles } from '../controles.model';

@Component({
  standalone: true,
  selector: 'jhi-controles-detail',
  templateUrl: './controles-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ControlesDetailComponent {
  controles = input<IControles | null>(null);

  previousState(): void {
    window.history.back();
  }
}
