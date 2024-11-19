import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IAmenazas, NewAmenazas } from '../amenazas.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAmenazas for edit and NewAmenazasFormGroupInput for create.
 */
type AmenazasFormGroupInput = IAmenazas | PartialWithRequiredKeyOf<NewAmenazas>;

type AmenazasFormDefaults = Pick<NewAmenazas, 'id'>;

type AmenazasFormGroupContent = {
  id: FormControl<IAmenazas['id'] | NewAmenazas['id']>;
  nombre: FormControl<IAmenazas['nombre']>;
};

export type AmenazasFormGroup = FormGroup<AmenazasFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AmenazasFormService {
  createAmenazasFormGroup(amenazas: AmenazasFormGroupInput = { id: null }): AmenazasFormGroup {
    const amenazasRawValue = {
      ...this.getFormDefaults(),
      ...amenazas,
    };
    return new FormGroup<AmenazasFormGroupContent>({
      id: new FormControl(
        { value: amenazasRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombre: new FormControl(amenazasRawValue.nombre, {
        validators: [Validators.required],
      }),
    });
  }

  getAmenazas(form: AmenazasFormGroup): IAmenazas | NewAmenazas {
    return form.getRawValue() as IAmenazas | NewAmenazas;
  }

  resetForm(form: AmenazasFormGroup, amenazas: AmenazasFormGroupInput): void {
    const amenazasRawValue = { ...this.getFormDefaults(), ...amenazas };
    form.reset(
      {
        ...amenazasRawValue,
        id: { value: amenazasRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AmenazasFormDefaults {
    return {
      id: null,
    };
  }
}
