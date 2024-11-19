import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IControles, NewControles } from '../controles.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IControles for edit and NewControlesFormGroupInput for create.
 */
type ControlesFormGroupInput = IControles | PartialWithRequiredKeyOf<NewControles>;

type ControlesFormDefaults = Pick<NewControles, 'id' | 'riesgos'>;

type ControlesFormGroupContent = {
  id: FormControl<IControles['id'] | NewControles['id']>;
  nombre: FormControl<IControles['nombre']>;
  descripcion: FormControl<IControles['descripcion']>;
  riesgos: FormControl<IControles['riesgos']>;
};

export type ControlesFormGroup = FormGroup<ControlesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ControlesFormService {
  createControlesFormGroup(controles: ControlesFormGroupInput = { id: null }): ControlesFormGroup {
    const controlesRawValue = {
      ...this.getFormDefaults(),
      ...controles,
    };
    return new FormGroup<ControlesFormGroupContent>({
      id: new FormControl(
        { value: controlesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombre: new FormControl(controlesRawValue.nombre, {
        validators: [Validators.required],
      }),
      descripcion: new FormControl(controlesRawValue.descripcion, {
        validators: [Validators.required],
      }),
      riesgos: new FormControl(controlesRawValue.riesgos ?? []),
    });
  }

  getControles(form: ControlesFormGroup): IControles | NewControles {
    return form.getRawValue() as IControles | NewControles;
  }

  resetForm(form: ControlesFormGroup, controles: ControlesFormGroupInput): void {
    const controlesRawValue = { ...this.getFormDefaults(), ...controles };
    form.reset(
      {
        ...controlesRawValue,
        id: { value: controlesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ControlesFormDefaults {
    return {
      id: null,
      riesgos: [],
    };
  }
}
