import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../vulnerabilidades.test-samples';

import { VulnerabilidadesFormService } from './vulnerabilidades-form.service';

describe('Vulnerabilidades Form Service', () => {
  let service: VulnerabilidadesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VulnerabilidadesFormService);
  });

  describe('Service methods', () => {
    describe('createVulnerabilidadesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createVulnerabilidadesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            descripcion: expect.any(Object),
          }),
        );
      });

      it('passing IVulnerabilidades should create a new form with FormGroup', () => {
        const formGroup = service.createVulnerabilidadesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            descripcion: expect.any(Object),
          }),
        );
      });
    });

    describe('getVulnerabilidades', () => {
      it('should return NewVulnerabilidades for default Vulnerabilidades initial value', () => {
        const formGroup = service.createVulnerabilidadesFormGroup(sampleWithNewData);

        const vulnerabilidades = service.getVulnerabilidades(formGroup) as any;

        expect(vulnerabilidades).toMatchObject(sampleWithNewData);
      });

      it('should return NewVulnerabilidades for empty Vulnerabilidades initial value', () => {
        const formGroup = service.createVulnerabilidadesFormGroup();

        const vulnerabilidades = service.getVulnerabilidades(formGroup) as any;

        expect(vulnerabilidades).toMatchObject({});
      });

      it('should return IVulnerabilidades', () => {
        const formGroup = service.createVulnerabilidadesFormGroup(sampleWithRequiredData);

        const vulnerabilidades = service.getVulnerabilidades(formGroup) as any;

        expect(vulnerabilidades).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IVulnerabilidades should not enable id FormControl', () => {
        const formGroup = service.createVulnerabilidadesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewVulnerabilidades should disable id FormControl', () => {
        const formGroup = service.createVulnerabilidadesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
