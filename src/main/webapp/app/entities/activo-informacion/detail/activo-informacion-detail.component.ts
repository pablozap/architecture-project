import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IActivoInformacion } from '../activo-informacion.model';

@Component({
  standalone: true,
  selector: 'jhi-activo-informacion-detail',
  templateUrl: './activo-informacion-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ActivoInformacionDetailComponent {
  activoInformacion = input<IActivoInformacion | null>(null);

  previousState(): void {
    window.history.back();
  }
}
