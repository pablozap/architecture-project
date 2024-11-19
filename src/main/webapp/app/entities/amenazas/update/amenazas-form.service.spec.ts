import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../amenazas.test-samples';

import { AmenazasFormService } from './amenazas-form.service';

describe('Amenazas Form Service', () => {
  let service: AmenazasFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AmenazasFormService);
  });

  describe('Service methods', () => {
    describe('createAmenazasFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAmenazasFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
          }),
        );
      });

      it('passing IAmenazas should create a new form with FormGroup', () => {
        const formGroup = service.createAmenazasFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
          }),
        );
      });
    });

    describe('getAmenazas', () => {
      it('should return NewAmenazas for default Amenazas initial value', () => {
        const formGroup = service.createAmenazasFormGroup(sampleWithNewData);

        const amenazas = service.getAmenazas(formGroup) as any;

        expect(amenazas).toMatchObject(sampleWithNewData);
      });

      it('should return NewAmenazas for empty Amenazas initial value', () => {
        const formGroup = service.createAmenazasFormGroup();

        const amenazas = service.getAmenazas(formGroup) as any;

        expect(amenazas).toMatchObject({});
      });

      it('should return IAmenazas', () => {
        const formGroup = service.createAmenazasFormGroup(sampleWithRequiredData);

        const amenazas = service.getAmenazas(formGroup) as any;

        expect(amenazas).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAmenazas should not enable id FormControl', () => {
        const formGroup = service.createAmenazasFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAmenazas should disable id FormControl', () => {
        const formGroup = service.createAmenazasFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
