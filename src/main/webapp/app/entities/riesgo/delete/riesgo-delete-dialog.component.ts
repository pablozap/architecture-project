import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IRiesgo } from '../riesgo.model';
import { RiesgoService } from '../service/riesgo.service';

@Component({
  standalone: true,
  templateUrl: './riesgo-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class RiesgoDeleteDialogComponent {
  riesgo?: IRiesgo;

  protected riesgoService = inject(RiesgoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.riesgoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
