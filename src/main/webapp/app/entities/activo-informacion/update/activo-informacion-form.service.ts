import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IActivoInformacion, NewActivoInformacion } from '../activo-informacion.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IActivoInformacion for edit and NewActivoInformacionFormGroupInput for create.
 */
type ActivoInformacionFormGroupInput = IActivoInformacion | PartialWithRequiredKeyOf<NewActivoInformacion>;

type ActivoInformacionFormDefaults = Pick<NewActivoInformacion, 'id' | 'ley1581' | 'ley1266'>;

type ActivoInformacionFormGroupContent = {
  id: FormControl<IActivoInformacion['id'] | NewActivoInformacion['id']>;
  proceso: FormControl<IActivoInformacion['proceso']>;
  nombre: FormControl<IActivoInformacion['nombre']>;
  descripcion: FormControl<IActivoInformacion['descripcion']>;
  tipoActivo: FormControl<IActivoInformacion['tipoActivo']>;
  ley1581: FormControl<IActivoInformacion['ley1581']>;
  clasificacionInformacion1712: FormControl<IActivoInformacion['clasificacionInformacion1712']>;
  ley1266: FormControl<IActivoInformacion['ley1266']>;
  formato: FormControl<IActivoInformacion['formato']>;
  propietario: FormControl<IActivoInformacion['propietario']>;
  usuario: FormControl<IActivoInformacion['usuario']>;
  custodio: FormControl<IActivoInformacion['custodio']>;
  usuarioFinal: FormControl<IActivoInformacion['usuarioFinal']>;
  fecha: FormControl<IActivoInformacion['fecha']>;
  estadoActivo: FormControl<IActivoInformacion['estadoActivo']>;
  fechaIngreso: FormControl<IActivoInformacion['fechaIngreso']>;
  fechaRetiro: FormControl<IActivoInformacion['fechaRetiro']>;
  grupoActivo: FormControl<IActivoInformacion['grupoActivo']>;
};

export type ActivoInformacionFormGroup = FormGroup<ActivoInformacionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ActivoInformacionFormService {
  createActivoInformacionFormGroup(activoInformacion: ActivoInformacionFormGroupInput = { id: null }): ActivoInformacionFormGroup {
    const activoInformacionRawValue = {
      ...this.getFormDefaults(),
      ...activoInformacion,
    };
    return new FormGroup<ActivoInformacionFormGroupContent>({
      id: new FormControl(
        { value: activoInformacionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      proceso: new FormControl(activoInformacionRawValue.proceso),
      nombre: new FormControl(activoInformacionRawValue.nombre, {
        validators: [Validators.required],
      }),
      descripcion: new FormControl(activoInformacionRawValue.descripcion, {
        validators: [Validators.required],
      }),
      tipoActivo: new FormControl(activoInformacionRawValue.tipoActivo, {
        validators: [Validators.required],
      }),
      ley1581: new FormControl(activoInformacionRawValue.ley1581, {
        validators: [Validators.required],
      }),
      clasificacionInformacion1712: new FormControl(activoInformacionRawValue.clasificacionInformacion1712, {
        validators: [Validators.required],
      }),
      ley1266: new FormControl(activoInformacionRawValue.ley1266, {
        validators: [Validators.required],
      }),
      formato: new FormControl(activoInformacionRawValue.formato),
      propietario: new FormControl(activoInformacionRawValue.propietario),
      usuario: new FormControl(activoInformacionRawValue.usuario),
      custodio: new FormControl(activoInformacionRawValue.custodio),
      usuarioFinal: new FormControl(activoInformacionRawValue.usuarioFinal),
      fecha: new FormControl(activoInformacionRawValue.fecha),
      estadoActivo: new FormControl(activoInformacionRawValue.estadoActivo),
      fechaIngreso: new FormControl(activoInformacionRawValue.fechaIngreso),
      fechaRetiro: new FormControl(activoInformacionRawValue.fechaRetiro),
      grupoActivo: new FormControl(activoInformacionRawValue.grupoActivo),
    });
  }

  getActivoInformacion(form: ActivoInformacionFormGroup): IActivoInformacion | NewActivoInformacion {
    return form.getRawValue() as IActivoInformacion | NewActivoInformacion;
  }

  resetForm(form: ActivoInformacionFormGroup, activoInformacion: ActivoInformacionFormGroupInput): void {
    const activoInformacionRawValue = { ...this.getFormDefaults(), ...activoInformacion };
    form.reset(
      {
        ...activoInformacionRawValue,
        id: { value: activoInformacionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ActivoInformacionFormDefaults {
    return {
      id: null,
      ley1581: false,
      ley1266: false,
    };
  }
}
