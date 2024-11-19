import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IVulnerabilidades } from '../vulnerabilidades.model';

@Component({
  standalone: true,
  selector: 'jhi-vulnerabilidades-detail',
  templateUrl: './vulnerabilidades-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class VulnerabilidadesDetailComponent {
  vulnerabilidades = input<IVulnerabilidades | null>(null);

  previousState(): void {
    window.history.back();
  }
}
