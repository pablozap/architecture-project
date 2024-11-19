import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../riesgo.test-samples';

import { RiesgoFormService } from './riesgo-form.service';

describe('Riesgo Form Service', () => {
  let service: RiesgoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RiesgoFormService);
  });

  describe('Service methods', () => {
    describe('createRiesgoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRiesgoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            proceso: expect.any(Object),
            tipoRiesgo: expect.any(Object),
            descripcion: expect.any(Object),
            clasificacion: expect.any(Object),
            frecuencia: expect.any(Object),
            afectacionEconomica: expect.any(Object),
            impactoReputacional: expect.any(Object),
            probabilidadInherente: expect.any(Object),
            impactoInherente: expect.any(Object),
            zonaRiesgo: expect.any(Object),
            afectacion: expect.any(Object),
            tipoControl: expect.any(Object),
            implementacion: expect.any(Object),
            calificacionControl: expect.any(Object),
            documentado: expect.any(Object),
            frecuenciaControl: expect.any(Object),
            evidencia: expect.any(Object),
            probabilidad: expect.any(Object),
            impacto: expect.any(Object),
            probabilidadResidualFinal: expect.any(Object),
            impactoResidualFinal: expect.any(Object),
            zonaDeRiesgoFinal: expect.any(Object),
            riesgoResidual: expect.any(Object),
            tratamiento: expect.any(Object),
            planAccion: expect.any(Object),
            responsable: expect.any(Object),
            fechaImplementacion: expect.any(Object),
            seguimientoControlExistente: expect.any(Object),
            estado: expect.any(Object),
            observaciones: expect.any(Object),
            fechaMonitoreo: expect.any(Object),
            activos: expect.any(Object),
            controles: expect.any(Object),
            amenaza: expect.any(Object),
            vulnerabilidad: expect.any(Object),
          }),
        );
      });

      it('passing IRiesgo should create a new form with FormGroup', () => {
        const formGroup = service.createRiesgoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            proceso: expect.any(Object),
            tipoRiesgo: expect.any(Object),
            descripcion: expect.any(Object),
            clasificacion: expect.any(Object),
            frecuencia: expect.any(Object),
            afectacionEconomica: expect.any(Object),
            impactoReputacional: expect.any(Object),
            probabilidadInherente: expect.any(Object),
            impactoInherente: expect.any(Object),
            zonaRiesgo: expect.any(Object),
            afectacion: expect.any(Object),
            tipoControl: expect.any(Object),
            implementacion: expect.any(Object),
            calificacionControl: expect.any(Object),
            documentado: expect.any(Object),
            frecuenciaControl: expect.any(Object),
            evidencia: expect.any(Object),
            probabilidad: expect.any(Object),
            impacto: expect.any(Object),
            probabilidadResidualFinal: expect.any(Object),
            impactoResidualFinal: expect.any(Object),
            zonaDeRiesgoFinal: expect.any(Object),
            riesgoResidual: expect.any(Object),
            tratamiento: expect.any(Object),
            planAccion: expect.any(Object),
            responsable: expect.any(Object),
            fechaImplementacion: expect.any(Object),
            seguimientoControlExistente: expect.any(Object),
            estado: expect.any(Object),
            observaciones: expect.any(Object),
            fechaMonitoreo: expect.any(Object),
            activos: expect.any(Object),
            controles: expect.any(Object),
            amenaza: expect.any(Object),
            vulnerabilidad: expect.any(Object),
          }),
        );
      });
    });

    describe('getRiesgo', () => {
      it('should return NewRiesgo for default Riesgo initial value', () => {
        const formGroup = service.createRiesgoFormGroup(sampleWithNewData);

        const riesgo = service.getRiesgo(formGroup) as any;

        expect(riesgo).toMatchObject(sampleWithNewData);
      });

      it('should return NewRiesgo for empty Riesgo initial value', () => {
        const formGroup = service.createRiesgoFormGroup();

        const riesgo = service.getRiesgo(formGroup) as any;

        expect(riesgo).toMatchObject({});
      });

      it('should return IRiesgo', () => {
        const formGroup = service.createRiesgoFormGroup(sampleWithRequiredData);

        const riesgo = service.getRiesgo(formGroup) as any;

        expect(riesgo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRiesgo should not enable id FormControl', () => {
        const formGroup = service.createRiesgoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRiesgo should disable id FormControl', () => {
        const formGroup = service.createRiesgoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
