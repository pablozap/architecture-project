import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IVulnerabilidades, NewVulnerabilidades } from '../vulnerabilidades.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVulnerabilidades for edit and NewVulnerabilidadesFormGroupInput for create.
 */
type VulnerabilidadesFormGroupInput = IVulnerabilidades | PartialWithRequiredKeyOf<NewVulnerabilidades>;

type VulnerabilidadesFormDefaults = Pick<NewVulnerabilidades, 'id'>;

type VulnerabilidadesFormGroupContent = {
  id: FormControl<IVulnerabilidades['id'] | NewVulnerabilidades['id']>;
  nombre: FormControl<IVulnerabilidades['nombre']>;
  descripcion: FormControl<IVulnerabilidades['descripcion']>;
};

export type VulnerabilidadesFormGroup = FormGroup<VulnerabilidadesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VulnerabilidadesFormService {
  createVulnerabilidadesFormGroup(vulnerabilidades: VulnerabilidadesFormGroupInput = { id: null }): VulnerabilidadesFormGroup {
    const vulnerabilidadesRawValue = {
      ...this.getFormDefaults(),
      ...vulnerabilidades,
    };
    return new FormGroup<VulnerabilidadesFormGroupContent>({
      id: new FormControl(
        { value: vulnerabilidadesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombre: new FormControl(vulnerabilidadesRawValue.nombre, {
        validators: [Validators.required],
      }),
      descripcion: new FormControl(vulnerabilidadesRawValue.descripcion, {
        validators: [Validators.required],
      }),
    });
  }

  getVulnerabilidades(form: VulnerabilidadesFormGroup): IVulnerabilidades | NewVulnerabilidades {
    return form.getRawValue() as IVulnerabilidades | NewVulnerabilidades;
  }

  resetForm(form: VulnerabilidadesFormGroup, vulnerabilidades: VulnerabilidadesFormGroupInput): void {
    const vulnerabilidadesRawValue = { ...this.getFormDefaults(), ...vulnerabilidades };
    form.reset(
      {
        ...vulnerabilidadesRawValue,
        id: { value: vulnerabilidadesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): VulnerabilidadesFormDefaults {
    return {
      id: null,
    };
  }
}
