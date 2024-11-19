import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IRiesgo, NewRiesgo } from '../riesgo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRiesgo for edit and NewRiesgoFormGroupInput for create.
 */
type RiesgoFormGroupInput = IRiesgo | PartialWithRequiredKeyOf<NewRiesgo>;

type RiesgoFormDefaults = Pick<NewRiesgo, 'id' | 'documentado' | 'evidencia' | 'activos' | 'controles'>;

type RiesgoFormGroupContent = {
  id: FormControl<IRiesgo['id'] | NewRiesgo['id']>;
  proceso: FormControl<IRiesgo['proceso']>;
  tipoRiesgo: FormControl<IRiesgo['tipoRiesgo']>;
  descripcion: FormControl<IRiesgo['descripcion']>;
  clasificacion: FormControl<IRiesgo['clasificacion']>;
  frecuencia: FormControl<IRiesgo['frecuencia']>;
  afectacionEconomica: FormControl<IRiesgo['afectacionEconomica']>;
  impactoReputacional: FormControl<IRiesgo['impactoReputacional']>;
  probabilidadInherente: FormControl<IRiesgo['probabilidadInherente']>;
  impactoInherente: FormControl<IRiesgo['impactoInherente']>;
  zonaRiesgo: FormControl<IRiesgo['zonaRiesgo']>;
  afectacion: FormControl<IRiesgo['afectacion']>;
  tipoControl: FormControl<IRiesgo['tipoControl']>;
  implementacion: FormControl<IRiesgo['implementacion']>;
  calificacionControl: FormControl<IRiesgo['calificacionControl']>;
  documentado: FormControl<IRiesgo['documentado']>;
  frecuenciaControl: FormControl<IRiesgo['frecuenciaControl']>;
  evidencia: FormControl<IRiesgo['evidencia']>;
  probabilidad: FormControl<IRiesgo['probabilidad']>;
  impacto: FormControl<IRiesgo['impacto']>;
  probabilidadResidualFinal: FormControl<IRiesgo['probabilidadResidualFinal']>;
  impactoResidualFinal: FormControl<IRiesgo['impactoResidualFinal']>;
  zonaDeRiesgoFinal: FormControl<IRiesgo['zonaDeRiesgoFinal']>;
  riesgoResidual: FormControl<IRiesgo['riesgoResidual']>;
  tratamiento: FormControl<IRiesgo['tratamiento']>;
  planAccion: FormControl<IRiesgo['planAccion']>;
  responsable: FormControl<IRiesgo['responsable']>;
  fechaImplementacion: FormControl<IRiesgo['fechaImplementacion']>;
  seguimientoControlExistente: FormControl<IRiesgo['seguimientoControlExistente']>;
  estado: FormControl<IRiesgo['estado']>;
  observaciones: FormControl<IRiesgo['observaciones']>;
  fechaMonitoreo: FormControl<IRiesgo['fechaMonitoreo']>;
  activos: FormControl<IRiesgo['activos']>;
  controles: FormControl<IRiesgo['controles']>;
  amenaza: FormControl<IRiesgo['amenaza']>;
  vulnerabilidad: FormControl<IRiesgo['vulnerabilidad']>;
};

export type RiesgoFormGroup = FormGroup<RiesgoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RiesgoFormService {
  createRiesgoFormGroup(riesgo: RiesgoFormGroupInput = { id: null }): RiesgoFormGroup {
    const riesgoRawValue = {
      ...this.getFormDefaults(),
      ...riesgo,
    };
    return new FormGroup<RiesgoFormGroupContent>({
      id: new FormControl(
        { value: riesgoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      proceso: new FormControl(riesgoRawValue.proceso),
      tipoRiesgo: new FormControl(riesgoRawValue.tipoRiesgo),
      descripcion: new FormControl(riesgoRawValue.descripcion),
      clasificacion: new FormControl(riesgoRawValue.clasificacion),
      frecuencia: new FormControl(riesgoRawValue.frecuencia, {
        validators: [Validators.required],
      }),
      afectacionEconomica: new FormControl(riesgoRawValue.afectacionEconomica, {
        validators: [Validators.required],
      }),
      impactoReputacional: new FormControl(riesgoRawValue.impactoReputacional),
      probabilidadInherente: new FormControl(riesgoRawValue.probabilidadInherente),
      impactoInherente: new FormControl(riesgoRawValue.impactoInherente),
      zonaRiesgo: new FormControl(riesgoRawValue.zonaRiesgo),
      afectacion: new FormControl(riesgoRawValue.afectacion, {
        validators: [Validators.required],
      }),
      tipoControl: new FormControl(riesgoRawValue.tipoControl, {
        validators: [Validators.required],
      }),
      implementacion: new FormControl(riesgoRawValue.implementacion, {
        validators: [Validators.required],
      }),
      calificacionControl: new FormControl(riesgoRawValue.calificacionControl, {
        validators: [Validators.max(100)],
      }),
      documentado: new FormControl(riesgoRawValue.documentado),
      frecuenciaControl: new FormControl(riesgoRawValue.frecuenciaControl, {
        validators: [Validators.required],
      }),
      evidencia: new FormControl(riesgoRawValue.evidencia),
      probabilidad: new FormControl(riesgoRawValue.probabilidad),
      impacto: new FormControl(riesgoRawValue.impacto),
      probabilidadResidualFinal: new FormControl(riesgoRawValue.probabilidadResidualFinal),
      impactoResidualFinal: new FormControl(riesgoRawValue.impactoResidualFinal),
      zonaDeRiesgoFinal: new FormControl(riesgoRawValue.zonaDeRiesgoFinal),
      riesgoResidual: new FormControl(riesgoRawValue.riesgoResidual),
      tratamiento: new FormControl(riesgoRawValue.tratamiento, {
        validators: [Validators.required],
      }),
      planAccion: new FormControl(riesgoRawValue.planAccion),
      responsable: new FormControl(riesgoRawValue.responsable),
      fechaImplementacion: new FormControl(riesgoRawValue.fechaImplementacion),
      seguimientoControlExistente: new FormControl(riesgoRawValue.seguimientoControlExistente),
      estado: new FormControl(riesgoRawValue.estado),
      observaciones: new FormControl(riesgoRawValue.observaciones),
      fechaMonitoreo: new FormControl(riesgoRawValue.fechaMonitoreo),
      activos: new FormControl(riesgoRawValue.activos ?? []),
      controles: new FormControl(riesgoRawValue.controles ?? []),
      amenaza: new FormControl(riesgoRawValue.amenaza, {
        validators: [Validators.required],
      }),
      vulnerabilidad: new FormControl(riesgoRawValue.vulnerabilidad, {
        validators: [Validators.required],
      }),
    });
  }

  getRiesgo(form: RiesgoFormGroup): IRiesgo | NewRiesgo {
    return form.getRawValue() as IRiesgo | NewRiesgo;
  }

  resetForm(form: RiesgoFormGroup, riesgo: RiesgoFormGroupInput): void {
    const riesgoRawValue = { ...this.getFormDefaults(), ...riesgo };
    form.reset(
      {
        ...riesgoRawValue,
        id: { value: riesgoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RiesgoFormDefaults {
    return {
      id: null,
      documentado: false,
      evidencia: false,
      activos: [],
      controles: [],
    };
  }
}
