import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../controles.test-samples';

import { ControlesFormService } from './controles-form.service';

describe('Controles Form Service', () => {
  let service: ControlesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ControlesFormService);
  });

  describe('Service methods', () => {
    describe('createControlesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createControlesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            descripcion: expect.any(Object),
            riesgos: expect.any(Object),
          }),
        );
      });

      it('passing IControles should create a new form with FormGroup', () => {
        const formGroup = service.createControlesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            descripcion: expect.any(Object),
            riesgos: expect.any(Object),
          }),
        );
      });
    });

    describe('getControles', () => {
      it('should return NewControles for default Controles initial value', () => {
        const formGroup = service.createControlesFormGroup(sampleWithNewData);

        const controles = service.getControles(formGroup) as any;

        expect(controles).toMatchObject(sampleWithNewData);
      });

      it('should return NewControles for empty Controles initial value', () => {
        const formGroup = service.createControlesFormGroup();

        const controles = service.getControles(formGroup) as any;

        expect(controles).toMatchObject({});
      });

      it('should return IControles', () => {
        const formGroup = service.createControlesFormGroup(sampleWithRequiredData);

        const controles = service.getControles(formGroup) as any;

        expect(controles).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IControles should not enable id FormControl', () => {
        const formGroup = service.createControlesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewControles should disable id FormControl', () => {
        const formGroup = service.createControlesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
