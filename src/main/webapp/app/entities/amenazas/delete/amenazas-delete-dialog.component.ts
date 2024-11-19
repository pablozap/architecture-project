import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAmenazas } from '../amenazas.model';
import { AmenazasService } from '../service/amenazas.service';

@Component({
  standalone: true,
  templateUrl: './amenazas-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AmenazasDeleteDialogComponent {
  amenazas?: IAmenazas;

  protected amenazasService = inject(AmenazasService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.amenazasService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
