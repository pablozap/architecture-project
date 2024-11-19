import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IGrupoActivos } from 'app/entities/grupo-activos/grupo-activos.model';
import { GrupoActivosService } from 'app/entities/grupo-activos/service/grupo-activos.service';
import { IControles } from 'app/entities/controles/controles.model';
import { ControlesService } from 'app/entities/controles/service/controles.service';
import { IAmenazas } from 'app/entities/amenazas/amenazas.model';
import { AmenazasService } from 'app/entities/amenazas/service/amenazas.service';
import { IVulnerabilidades } from 'app/entities/vulnerabilidades/vulnerabilidades.model';
import { VulnerabilidadesService } from 'app/entities/vulnerabilidades/service/vulnerabilidades.service';
import { TipoRiesgo } from 'app/entities/enumerations/tipo-riesgo.model';
import { Clasificacion } from 'app/entities/enumerations/clasificacion.model';
import { AfectacionEconomica } from 'app/entities/enumerations/afectacion-economica.model';
import { AfectacionReputacional } from 'app/entities/enumerations/afectacion-reputacional.model';
import { ProbabilidadInherente } from 'app/entities/enumerations/probabilidad-inherente.model';
import { ImpactoInherente } from 'app/entities/enumerations/impacto-inherente.model';
import { ZonaRiesgo } from 'app/entities/enumerations/zona-riesgo.model';
import { Afectacion } from 'app/entities/enumerations/afectacion.model';
import { TipoControl } from 'app/entities/enumerations/tipo-control.model';
import { Implementacion } from 'app/entities/enumerations/implementacion.model';
import { FrecuenciaControl } from 'app/entities/enumerations/frecuencia-control.model';
import { ProbabilidadResidualFinal } from 'app/entities/enumerations/probabilidad-residual-final.model';
import { ImpactoResidualFinal } from 'app/entities/enumerations/impacto-residual-final.model';
import { ZonaRiesgoFinalControl } from 'app/entities/enumerations/zona-riesgo-final-control.model';
import { TratamientoRiesgo } from 'app/entities/enumerations/tratamiento-riesgo.model';
import { RiesgoService } from '../service/riesgo.service';
import { IRiesgo } from '../riesgo.model';
import { RiesgoFormGroup, RiesgoFormService } from './riesgo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-riesgo-update',
  templateUrl: './riesgo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RiesgoUpdateComponent implements OnInit {
  isSaving = false;
  riesgo: IRiesgo | null = null;
  tipoRiesgoValues = Object.keys(TipoRiesgo);
  clasificacionValues = Object.keys(Clasificacion);
  afectacionEconomicaValues = Object.keys(AfectacionEconomica);
  afectacionReputacionalValues = Object.keys(AfectacionReputacional);
  probabilidadInherenteValues = Object.keys(ProbabilidadInherente);
  impactoInherenteValues = Object.keys(ImpactoInherente);
  zonaRiesgoValues = Object.keys(ZonaRiesgo);
  afectacionValues = Object.keys(Afectacion);
  tipoControlValues = Object.keys(TipoControl);
  implementacionValues = Object.keys(Implementacion);
  frecuenciaControlValues = Object.keys(FrecuenciaControl);
  probabilidadResidualFinalValues = Object.keys(ProbabilidadResidualFinal);
  impactoResidualFinalValues = Object.keys(ImpactoResidualFinal);
  zonaRiesgoFinalControlValues = Object.keys(ZonaRiesgoFinalControl);
  tratamientoRiesgoValues = Object.keys(TratamientoRiesgo);

  grupoActivosSharedCollection: IGrupoActivos[] = [];
  controlesSharedCollection: IControles[] = [];
  amenazasSharedCollection: IAmenazas[] = [];
  vulnerabilidadesSharedCollection: IVulnerabilidades[] = [];

  protected riesgoService = inject(RiesgoService);
  protected riesgoFormService = inject(RiesgoFormService);
  protected grupoActivosService = inject(GrupoActivosService);
  protected controlesService = inject(ControlesService);
  protected amenazasService = inject(AmenazasService);
  protected vulnerabilidadesService = inject(VulnerabilidadesService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RiesgoFormGroup = this.riesgoFormService.createRiesgoFormGroup();

  compareGrupoActivos = (o1: IGrupoActivos | null, o2: IGrupoActivos | null): boolean =>
    this.grupoActivosService.compareGrupoActivos(o1, o2);

  compareControles = (o1: IControles | null, o2: IControles | null): boolean => this.controlesService.compareControles(o1, o2);

  compareAmenazas = (o1: IAmenazas | null, o2: IAmenazas | null): boolean => this.amenazasService.compareAmenazas(o1, o2);

  compareVulnerabilidades = (o1: IVulnerabilidades | null, o2: IVulnerabilidades | null): boolean =>
    this.vulnerabilidadesService.compareVulnerabilidades(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ riesgo }) => {
      this.riesgo = riesgo;
      if (riesgo) {
        this.updateForm(riesgo);
      }

      this.loadRelationshipsOptions();
    });
    this.editForm.get('tipoRiesgo')?.valueChanges.subscribe((): void => this.riesgoService.updateDescription(this.editForm));
    this.editForm.get('activos')?.valueChanges.subscribe((): void => this.riesgoService.updateDescription(this.editForm));
    this.editForm.get('amenaza')?.valueChanges.subscribe((): void => this.riesgoService.updateDescription(this.editForm));
    this.editForm.get('vulnerabilidad')?.valueChanges.subscribe((): void => this.riesgoService.updateDescription(this.editForm));
    this.editForm.get('frecuencia')?.valueChanges.subscribe((): void => this.riesgoService.updateZonaRiesgo(this.editForm));
    this.editForm.get('afectacionEconomica')?.valueChanges.subscribe((): void => this.riesgoService.updateZonaRiesgo(this.editForm));
    this.editForm.get('probabilidadInherente')?.valueChanges.subscribe((): void => this.riesgoService.updateZonaRiesgo(this.editForm));
    this.editForm.get('afectacion')?.valueChanges.subscribe((): void => this.riesgoService.updateZonaRiesgo(this.editForm));
    this.editForm.get('tipoControl')?.valueChanges.subscribe((): void => this.riesgoService.updateZonaRiesgo(this.editForm));
    this.editForm.get('implementacion')?.valueChanges.subscribe((): void => this.riesgoService.updateZonaRiesgo(this.editForm));
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const riesgo = this.riesgoFormService.getRiesgo(this.editForm);
    if (riesgo.id !== null) {
      this.subscribeToSaveResponse(this.riesgoService.update(riesgo));
    } else {
      this.subscribeToSaveResponse(this.riesgoService.create(riesgo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRiesgo>>): void {
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

  protected updateForm(riesgo: IRiesgo): void {
    this.riesgo = riesgo;
    this.riesgoFormService.resetForm(this.editForm, riesgo);

    this.grupoActivosSharedCollection = this.grupoActivosService.addGrupoActivosToCollectionIfMissing<IGrupoActivos>(
      this.grupoActivosSharedCollection,
      ...(riesgo.activos ?? []),
    );
    this.controlesSharedCollection = this.controlesService.addControlesToCollectionIfMissing<IControles>(
      this.controlesSharedCollection,
      ...(riesgo.controles ?? []),
    );
    this.amenazasSharedCollection = this.amenazasService.addAmenazasToCollectionIfMissing<IAmenazas>(
      this.amenazasSharedCollection,
      riesgo.amenaza,
    );
    this.vulnerabilidadesSharedCollection = this.vulnerabilidadesService.addVulnerabilidadesToCollectionIfMissing<IVulnerabilidades>(
      this.vulnerabilidadesSharedCollection,
      riesgo.vulnerabilidad,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.grupoActivosService
      .query()
      .pipe(map((res: HttpResponse<IGrupoActivos[]>) => res.body ?? []))
      .pipe(
        map((grupoActivos: IGrupoActivos[]) =>
          this.grupoActivosService.addGrupoActivosToCollectionIfMissing<IGrupoActivos>(grupoActivos, ...(this.riesgo?.activos ?? [])),
        ),
      )
      .subscribe((grupoActivos: IGrupoActivos[]) => (this.grupoActivosSharedCollection = grupoActivos));

    this.controlesService
      .query()
      .pipe(map((res: HttpResponse<IControles[]>) => res.body ?? []))
      .pipe(
        map((controles: IControles[]) =>
          this.controlesService.addControlesToCollectionIfMissing<IControles>(controles, ...(this.riesgo?.controles ?? [])),
        ),
      )
      .subscribe((controles: IControles[]) => (this.controlesSharedCollection = controles));

    this.amenazasService
      .query()
      .pipe(map((res: HttpResponse<IAmenazas[]>) => res.body ?? []))
      .pipe(
        map((amenazas: IAmenazas[]) => this.amenazasService.addAmenazasToCollectionIfMissing<IAmenazas>(amenazas, this.riesgo?.amenaza)),
      )
      .subscribe((amenazas: IAmenazas[]) => (this.amenazasSharedCollection = amenazas));

    this.vulnerabilidadesService
      .query()
      .pipe(map((res: HttpResponse<IVulnerabilidades[]>) => res.body ?? []))
      .pipe(
        map((vulnerabilidades: IVulnerabilidades[]) =>
          this.vulnerabilidadesService.addVulnerabilidadesToCollectionIfMissing<IVulnerabilidades>(
            vulnerabilidades,
            this.riesgo?.vulnerabilidad,
          ),
        ),
      )
      .subscribe((vulnerabilidades: IVulnerabilidades[]) => (this.vulnerabilidadesSharedCollection = vulnerabilidades));
  }
}
