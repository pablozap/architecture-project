import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAmenazas } from '../amenazas.model';
import { AmenazasService } from '../service/amenazas.service';
import { AmenazasFormGroup, AmenazasFormService } from './amenazas-form.service';

@Component({
  standalone: true,
  selector: 'jhi-amenazas-update',
  templateUrl: './amenazas-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AmenazasUpdateComponent implements OnInit {
  isSaving = false;
  amenazas: IAmenazas | null = null;

  protected amenazasService = inject(AmenazasService);
  protected amenazasFormService = inject(AmenazasFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AmenazasFormGroup = this.amenazasFormService.createAmenazasFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ amenazas }) => {
      this.amenazas = amenazas;
      if (amenazas) {
        this.updateForm(amenazas);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const amenazas = this.amenazasFormService.getAmenazas(this.editForm);
    if (amenazas.id !== null) {
      this.subscribeToSaveResponse(this.amenazasService.update(amenazas));
    } else {
      this.subscribeToSaveResponse(this.amenazasService.create(amenazas));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAmenazas>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(amenazas: IAmenazas): void {
    this.amenazas = amenazas;
    this.amenazasFormService.resetForm(this.editForm, amenazas);
  }
}
