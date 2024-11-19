import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IGrupoActivos, NewGrupoActivos } from '../grupo-activos.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGrupoActivos for edit and NewGrupoActivosFormGroupInput for create.
 */
type GrupoActivosFormGroupInput = IGrupoActivos | PartialWithRequiredKeyOf<NewGrupoActivos>;

type GrupoActivosFormDefaults = Pick<NewGrupoActivos, 'id' | 'riesgos'>;

type GrupoActivosFormGroupContent = {
  id: FormControl<IGrupoActivos['id'] | NewGrupoActivos['id']>;
  nombre: FormControl<IGrupoActivos['nombre']>;
  disponibilidad: FormControl<IGrupoActivos['disponibilidad']>;
  integridad: FormControl<IGrupoActivos['integridad']>;
  confidencialidad: FormControl<IGrupoActivos['confidencialidad']>;
  criticidad: FormControl<IGrupoActivos['criticidad']>;
  riesgos: FormControl<IGrupoActivos['riesgos']>;
};

export type GrupoActivosFormGroup = FormGroup<GrupoActivosFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GrupoActivosFormService {
  createGrupoActivosFormGroup(grupoActivos: GrupoActivosFormGroupInput = { id: null }): GrupoActivosFormGroup {
    const grupoActivosRawValue = {
      ...this.getFormDefaults(),
      ...grupoActivos,
    };
    return new FormGroup<GrupoActivosFormGroupContent>({
      id: new FormControl(
        { value: grupoActivosRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombre: new FormControl(grupoActivosRawValue.nombre, {
        validators: [Validators.required],
      }),
      disponibilidad: new FormControl(grupoActivosRawValue.disponibilidad, {
        validators: [Validators.required],
      }),
      integridad: new FormControl(grupoActivosRawValue.integridad, {
        validators: [Validators.required],
      }),
      confidencialidad: new FormControl(grupoActivosRawValue.confidencialidad, {
        validators: [Validators.required],
      }),
      criticidad: new FormControl(grupoActivosRawValue.criticidad, {
        validators: [Validators.required],
      }),
      riesgos: new FormControl(grupoActivosRawValue.riesgos ?? []),
    });
  }

  getGrupoActivos(form: GrupoActivosFormGroup): IGrupoActivos | NewGrupoActivos {
    return form.getRawValue() as IGrupoActivos | NewGrupoActivos;
  }

  resetForm(form: GrupoActivosFormGroup, grupoActivos: GrupoActivosFormGroupInput): void {
    const grupoActivosRawValue = { ...this.getFormDefaults(), ...grupoActivos };
    form.reset(
      {
        ...grupoActivosRawValue,
        id: { value: grupoActivosRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): GrupoActivosFormDefaults {
    return {
      id: null,
      riesgos: [],
    };
  }
}
