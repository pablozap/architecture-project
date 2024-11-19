import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IGrupoActivos } from 'app/entities/grupo-activos/grupo-activos.model';
import { GrupoActivosService } from 'app/entities/grupo-activos/service/grupo-activos.service';
import { Proceso } from 'app/entities/enumerations/proceso.model';
import { TipoActivo } from 'app/entities/enumerations/tipo-activo.model';
import { ClasificacionInformacion1712 } from 'app/entities/enumerations/clasificacion-informacion-1712.model';
import { Formato } from 'app/entities/enumerations/formato.model';
import { ActivoInformacionService } from '../service/activo-informacion.service';
import { IActivoInformacion } from '../activo-informacion.model';
import { ActivoInformacionFormGroup, ActivoInformacionFormService } from './activo-informacion-form.service';

@Component({
  standalone: true,
  selector: 'jhi-activo-informacion-update',
  templateUrl: './activo-informacion-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ActivoInformacionUpdateComponent implements OnInit {
  isSaving = false;
  activoInformacion: IActivoInformacion | null = null;
  procesoValues = Object.keys(Proceso);
  tipoActivoValues = Object.keys(TipoActivo);
  clasificacionInformacion1712Values = Object.keys(ClasificacionInformacion1712);
  formatoValues = Object.keys(Formato);

  grupoActivosSharedCollection: IGrupoActivos[] = [];

  protected activoInformacionService = inject(ActivoInformacionService);
  protected activoInformacionFormService = inject(ActivoInformacionFormService);
  protected grupoActivosService = inject(GrupoActivosService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ActivoInformacionFormGroup = this.activoInformacionFormService.createActivoInformacionFormGroup();

  compareGrupoActivos = (o1: IGrupoActivos | null, o2: IGrupoActivos | null): boolean =>
    this.grupoActivosService.compareGrupoActivos(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activoInformacion }) => {
      this.activoInformacion = activoInformacion;
      if (activoInformacion) {
        this.updateForm(activoInformacion);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const activoInformacion = this.activoInformacionFormService.getActivoInformacion(this.editForm);
    if (activoInformacion.id !== null) {
      this.subscribeToSaveResponse(this.activoInformacionService.update(activoInformacion));
    } else {
      this.subscribeToSaveResponse(this.activoInformacionService.create(activoInformacion));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivoInformacion>>): void {
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

  protected updateForm(activoInformacion: IActivoInformacion): void {
    this.activoInformacion = activoInformacion;
    this.activoInformacionFormService.resetForm(this.editForm, activoInformacion);

    this.grupoActivosSharedCollection = this.grupoActivosService.addGrupoActivosToCollectionIfMissing<IGrupoActivos>(
      this.grupoActivosSharedCollection,
      activoInformacion.grupoActivo,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.grupoActivosService
      .query()
      .pipe(map((res: HttpResponse<IGrupoActivos[]>) => res.body ?? []))
      .pipe(
        map((grupoActivos: IGrupoActivos[]) =>
          this.grupoActivosService.addGrupoActivosToCollectionIfMissing<IGrupoActivos>(grupoActivos, this.activoInformacion?.grupoActivo),
        ),
      )
      .subscribe((grupoActivos: IGrupoActivos[]) => (this.grupoActivosSharedCollection = grupoActivos));
  }
}
