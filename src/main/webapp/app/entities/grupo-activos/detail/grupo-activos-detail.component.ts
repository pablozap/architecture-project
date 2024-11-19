import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IGrupoActivos } from '../grupo-activos.model';

@Component({
  standalone: true,
  selector: 'jhi-grupo-activos-detail',
  templateUrl: './grupo-activos-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class GrupoActivosDetailComponent {
  grupoActivos = input<IGrupoActivos | null>(null);

  previousState(): void {
    window.history.back();
  }
}
