import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../activo-informacion.test-samples';

import { ActivoInformacionFormService } from './activo-informacion-form.service';

describe('ActivoInformacion Form Service', () => {
  let service: ActivoInformacionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ActivoInformacionFormService);
  });

  describe('Service methods', () => {
    describe('createActivoInformacionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createActivoInformacionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            proceso: expect.any(Object),
            nombre: expect.any(Object),
            descripcion: expect.any(Object),
            tipoActivo: expect.any(Object),
            ley1581: expect.any(Object),
            clasificacionInformacion1712: expect.any(Object),
            ley1266: expect.any(Object),
            formato: expect.any(Object),
            propietario: expect.any(Object),
            usuario: expect.any(Object),
            custodio: expect.any(Object),
            usuarioFinal: expect.any(Object),
            fecha: expect.any(Object),
            estadoActivo: expect.any(Object),
            fechaIngreso: expect.any(Object),
            fechaRetiro: expect.any(Object),
            grupoActivo: expect.any(Object),
          }),
        );
      });

      it('passing IActivoInformacion should create a new form with FormGroup', () => {
        const formGroup = service.createActivoInformacionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            proceso: expect.any(Object),
            nombre: expect.any(Object),
            descripcion: expect.any(Object),
            tipoActivo: expect.any(Object),
            ley1581: expect.any(Object),
            clasificacionInformacion1712: expect.any(Object),
            ley1266: expect.any(Object),
            formato: expect.any(Object),
            propietario: expect.any(Object),
            usuario: expect.any(Object),
            custodio: expect.any(Object),
            usuarioFinal: expect.any(Object),
            fecha: expect.any(Object),
            estadoActivo: expect.any(Object),
            fechaIngreso: expect.any(Object),
            fechaRetiro: expect.any(Object),
            grupoActivo: expect.any(Object),
          }),
        );
      });
    });

    describe('getActivoInformacion', () => {
      it('should return NewActivoInformacion for default ActivoInformacion initial value', () => {
        const formGroup = service.createActivoInformacionFormGroup(sampleWithNewData);

        const activoInformacion = service.getActivoInformacion(formGroup) as any;

        expect(activoInformacion).toMatchObject(sampleWithNewData);
      });

      it('should return NewActivoInformacion for empty ActivoInformacion initial value', () => {
        const formGroup = service.createActivoInformacionFormGroup();

        const activoInformacion = service.getActivoInformacion(formGroup) as any;

        expect(activoInformacion).toMatchObject({});
      });

      it('should return IActivoInformacion', () => {
        const formGroup = service.createActivoInformacionFormGroup(sampleWithRequiredData);

        const activoInformacion = service.getActivoInformacion(formGroup) as any;

        expect(activoInformacion).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IActivoInformacion should not enable id FormControl', () => {
        const formGroup = service.createActivoInformacionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewActivoInformacion should disable id FormControl', () => {
        const formGroup = service.createActivoInformacionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
