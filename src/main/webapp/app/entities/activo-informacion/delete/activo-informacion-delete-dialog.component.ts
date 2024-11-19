import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IActivoInformacion } from '../activo-informacion.model';
import { ActivoInformacionService } from '../service/activo-informacion.service';

@Component({
  standalone: true,
  templateUrl: './activo-informacion-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ActivoInformacionDeleteDialogComponent {
  activoInformacion?: IActivoInformacion;

  protected activoInformacionService = inject(ActivoInformacionService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.activoInformacionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
