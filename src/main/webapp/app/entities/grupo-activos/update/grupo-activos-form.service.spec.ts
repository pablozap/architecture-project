import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../grupo-activos.test-samples';

import { GrupoActivosFormService } from './grupo-activos-form.service';

describe('GrupoActivos Form Service', () => {
  let service: GrupoActivosFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GrupoActivosFormService);
  });

  describe('Service methods', () => {
    describe('createGrupoActivosFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGrupoActivosFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            disponibilidad: expect.any(Object),
            integridad: expect.any(Object),
            confidencialidad: expect.any(Object),
            criticidad: expect.any(Object),
            riesgos: expect.any(Object),
          }),
        );
      });

      it('passing IGrupoActivos should create a new form with FormGroup', () => {
        const formGroup = service.createGrupoActivosFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            disponibilidad: expect.any(Object),
            integridad: expect.any(Object),
            confidencialidad: expect.any(Object),
            criticidad: expect.any(Object),
            riesgos: expect.any(Object),
          }),
        );
      });
    });

    describe('getGrupoActivos', () => {
      it('should return NewGrupoActivos for default GrupoActivos initial value', () => {
        const formGroup = service.createGrupoActivosFormGroup(sampleWithNewData);

        const grupoActivos = service.getGrupoActivos(formGroup) as any;

        expect(grupoActivos).toMatchObject(sampleWithNewData);
      });

      it('should return NewGrupoActivos for empty GrupoActivos initial value', () => {
        const formGroup = service.createGrupoActivosFormGroup();

        const grupoActivos = service.getGrupoActivos(formGroup) as any;

        expect(grupoActivos).toMatchObject({});
      });

      it('should return IGrupoActivos', () => {
        const formGroup = service.createGrupoActivosFormGroup(sampleWithRequiredData);

        const grupoActivos = service.getGrupoActivos(formGroup) as any;

        expect(grupoActivos).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGrupoActivos should not enable id FormControl', () => {
        const formGroup = service.createGrupoActivosFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGrupoActivos should disable id FormControl', () => {
        const formGroup = service.createGrupoActivosFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
