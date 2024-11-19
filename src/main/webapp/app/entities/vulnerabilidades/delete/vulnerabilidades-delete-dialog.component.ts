import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IVulnerabilidades } from '../vulnerabilidades.model';
import { VulnerabilidadesService } from '../service/vulnerabilidades.service';

@Component({
  standalone: true,
  templateUrl: './vulnerabilidades-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class VulnerabilidadesDeleteDialogComponent {
  vulnerabilidades?: IVulnerabilidades;

  protected vulnerabilidadesService = inject(VulnerabilidadesService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vulnerabilidadesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
