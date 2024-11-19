import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IActivoInformacion } from '../activo-informacion.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../activo-informacion.test-samples';

import { ActivoInformacionService, RestActivoInformacion } from './activo-informacion.service';

const requireRestSample: RestActivoInformacion = {
  ...sampleWithRequiredData,
  fecha: sampleWithRequiredData.fecha?.format(DATE_FORMAT),
  fechaIngreso: sampleWithRequiredData.fechaIngreso?.format(DATE_FORMAT),
  fechaRetiro: sampleWithRequiredData.fechaRetiro?.format(DATE_FORMAT),
};

describe('ActivoInformacion Service', () => {
  let service: ActivoInformacionService;
  let httpMock: HttpTestingController;
  let expectedResult: IActivoInformacion | IActivoInformacion[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ActivoInformacionService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ActivoInformacion', () => {
      const activoInformacion = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(activoInformacion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ActivoInformacion', () => {
      const activoInformacion = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(activoInformacion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ActivoInformacion', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ActivoInformacion', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ActivoInformacion', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addActivoInformacionToCollectionIfMissing', () => {
      it('should add a ActivoInformacion to an empty array', () => {
        const activoInformacion: IActivoInformacion = sampleWithRequiredData;
        expectedResult = service.addActivoInformacionToCollectionIfMissing([], activoInformacion);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(activoInformacion);
      });

      it('should not add a ActivoInformacion to an array that contains it', () => {
        const activoInformacion: IActivoInformacion = sampleWithRequiredData;
        const activoInformacionCollection: IActivoInformacion[] = [
          {
            ...activoInformacion,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addActivoInformacionToCollectionIfMissing(activoInformacionCollection, activoInformacion);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ActivoInformacion to an array that doesn't contain it", () => {
        const activoInformacion: IActivoInformacion = sampleWithRequiredData;
        const activoInformacionCollection: IActivoInformacion[] = [sampleWithPartialData];
        expectedResult = service.addActivoInformacionToCollectionIfMissing(activoInformacionCollection, activoInformacion);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(activoInformacion);
      });

      it('should add only unique ActivoInformacion to an array', () => {
        const activoInformacionArray: IActivoInformacion[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const activoInformacionCollection: IActivoInformacion[] = [sampleWithRequiredData];
        expectedResult = service.addActivoInformacionToCollectionIfMissing(activoInformacionCollection, ...activoInformacionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const activoInformacion: IActivoInformacion = sampleWithRequiredData;
        const activoInformacion2: IActivoInformacion = sampleWithPartialData;
        expectedResult = service.addActivoInformacionToCollectionIfMissing([], activoInformacion, activoInformacion2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(activoInformacion);
        expect(expectedResult).toContain(activoInformacion2);
      });

      it('should accept null and undefined values', () => {
        const activoInformacion: IActivoInformacion = sampleWithRequiredData;
        expectedResult = service.addActivoInformacionToCollectionIfMissing([], null, activoInformacion, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(activoInformacion);
      });

      it('should return initial array if no ActivoInformacion is added', () => {
        const activoInformacionCollection: IActivoInformacion[] = [sampleWithRequiredData];
        expectedResult = service.addActivoInformacionToCollectionIfMissing(activoInformacionCollection, undefined, null);
        expect(expectedResult).toEqual(activoInformacionCollection);
      });
    });

    describe('compareActivoInformacion', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareActivoInformacion(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareActivoInformacion(entity1, entity2);
        const compareResult2 = service.compareActivoInformacion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareActivoInformacion(entity1, entity2);
        const compareResult2 = service.compareActivoInformacion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareActivoInformacion(entity1, entity2);
        const compareResult2 = service.compareActivoInformacion(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
