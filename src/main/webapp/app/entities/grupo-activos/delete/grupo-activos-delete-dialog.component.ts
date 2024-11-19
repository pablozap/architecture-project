import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IGrupoActivos } from '../grupo-activos.model';
import { GrupoActivosService } from '../service/grupo-activos.service';

@Component({
  standalone: true,
  templateUrl: './grupo-activos-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class GrupoActivosDeleteDialogComponent {
  grupoActivos?: IGrupoActivos;

  protected grupoActivosService = inject(GrupoActivosService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.grupoActivosService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
