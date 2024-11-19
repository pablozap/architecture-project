import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRiesgo } from 'app/entities/riesgo/riesgo.model';
import { RiesgoService } from 'app/entities/riesgo/service/riesgo.service';
import { Disponibilidad } from 'app/entities/enumerations/disponibilidad.model';
import { Integridad } from 'app/entities/enumerations/integridad.model';
import { Confidencialidad } from 'app/entities/enumerations/confidencialidad.model';
import { Criticidad } from 'app/entities/enumerations/criticidad.model';
import { GrupoActivosService } from '../service/grupo-activos.service';
import { IGrupoActivos } from '../grupo-activos.model';
import { GrupoActivosFormGroup, GrupoActivosFormService } from './grupo-activos-form.service';

@Component({
  standalone: true,
  selector: 'jhi-grupo-activos-update',
  templateUrl: './grupo-activos-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class GrupoActivosUpdateComponent implements OnInit {
  isSaving = false;
  grupoActivos: IGrupoActivos | null = null;
  disponibilidadValues = Object.keys(Disponibilidad);
  integridadValues = Object.keys(Integridad);
  confidencialidadValues = Object.keys(Confidencialidad);
  criticidadValues = Object.keys(Criticidad);

  riesgosSharedCollection: IRiesgo[] = [];

  protected grupoActivosService = inject(GrupoActivosService);
  protected grupoActivosFormService = inject(GrupoActivosFormService);
  protected riesgoService = inject(RiesgoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: GrupoActivosFormGroup = this.grupoActivosFormService.createGrupoActivosFormGroup();

  compareRiesgo = (o1: IRiesgo | null, o2: IRiesgo | null): boolean => this.riesgoService.compareRiesgo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ grupoActivos }) => {
      this.grupoActivos = grupoActivos;
      if (grupoActivos) {
        this.updateForm(grupoActivos);
      }

      this.loadRelationshipsOptions();
    });
    this.editForm.get('disponibilidad')?.valueChanges.subscribe((): void => this.grupoActivosService.setCriticidad(this.editForm));
    this.editForm.get('integridad')?.valueChanges.subscribe((): void => this.grupoActivosService.setCriticidad(this.editForm));
    this.editForm.get('confidencialidad')?.valueChanges.subscribe((): void => this.grupoActivosService.setCriticidad(this.editForm));
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const grupoActivos = this.grupoActivosFormService.getGrupoActivos(this.editForm);
    if (grupoActivos.id !== null) {
      this.subscribeToSaveResponse(this.grupoActivosService.update(grupoActivos));
    } else {
      this.subscribeToSaveResponse(this.grupoActivosService.create(grupoActivos));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrupoActivos>>): void {
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

  protected updateForm(grupoActivos: IGrupoActivos): void {
    this.grupoActivos = grupoActivos;
    this.grupoActivosFormService.resetForm(this.editForm, grupoActivos);

    this.riesgosSharedCollection = this.riesgoService.addRiesgoToCollectionIfMissing<IRiesgo>(
      this.riesgosSharedCollection,
      ...(grupoActivos.riesgos ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.riesgoService
      .query()
      .pipe(map((res: HttpResponse<IRiesgo[]>) => res.body ?? []))
      .pipe(
        map((riesgos: IRiesgo[]) =>
          this.riesgoService.addRiesgoToCollectionIfMissing<IRiesgo>(riesgos, ...(this.grupoActivos?.riesgos ?? [])),
        ),
      )
      .subscribe((riesgos: IRiesgo[]) => (this.riesgosSharedCollection = riesgos));
  }
}
