import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IRiesgo } from '../riesgo.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../riesgo.test-samples';

import { RestRiesgo, RiesgoService } from './riesgo.service';

const requireRestSample: RestRiesgo = {
  ...sampleWithRequiredData,
  fechaImplementacion: sampleWithRequiredData.fechaImplementacion?.format(DATE_FORMAT),
  fechaMonitoreo: sampleWithRequiredData.fechaMonitoreo?.format(DATE_FORMAT),
};

describe('Riesgo Service', () => {
  let service: RiesgoService;
  let httpMock: HttpTestingController;
  let expectedResult: IRiesgo | IRiesgo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(RiesgoService);
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

    it('should create a Riesgo', () => {
      const riesgo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(riesgo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Riesgo', () => {
      const riesgo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(riesgo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Riesgo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Riesgo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Riesgo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRiesgoToCollectionIfMissing', () => {
      it('should add a Riesgo to an empty array', () => {
        const riesgo: IRiesgo = sampleWithRequiredData;
        expectedResult = service.addRiesgoToCollectionIfMissing([], riesgo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(riesgo);
      });

      it('should not add a Riesgo to an array that contains it', () => {
        const riesgo: IRiesgo = sampleWithRequiredData;
        const riesgoCollection: IRiesgo[] = [
          {
            ...riesgo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRiesgoToCollectionIfMissing(riesgoCollection, riesgo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Riesgo to an array that doesn't contain it", () => {
        const riesgo: IRiesgo = sampleWithRequiredData;
        const riesgoCollection: IRiesgo[] = [sampleWithPartialData];
        expectedResult = service.addRiesgoToCollectionIfMissing(riesgoCollection, riesgo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(riesgo);
      });

      it('should add only unique Riesgo to an array', () => {
        const riesgoArray: IRiesgo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const riesgoCollection: IRiesgo[] = [sampleWithRequiredData];
        expectedResult = service.addRiesgoToCollectionIfMissing(riesgoCollection, ...riesgoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const riesgo: IRiesgo = sampleWithRequiredData;
        const riesgo2: IRiesgo = sampleWithPartialData;
        expectedResult = service.addRiesgoToCollectionIfMissing([], riesgo, riesgo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(riesgo);
        expect(expectedResult).toContain(riesgo2);
      });

      it('should accept null and undefined values', () => {
        const riesgo: IRiesgo = sampleWithRequiredData;
        expectedResult = service.addRiesgoToCollectionIfMissing([], null, riesgo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(riesgo);
      });

      it('should return initial array if no Riesgo is added', () => {
        const riesgoCollection: IRiesgo[] = [sampleWithRequiredData];
        expectedResult = service.addRiesgoToCollectionIfMissing(riesgoCollection, undefined, null);
        expect(expectedResult).toEqual(riesgoCollection);
      });
    });

    describe('compareRiesgo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRiesgo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRiesgo(entity1, entity2);
        const compareResult2 = service.compareRiesgo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRiesgo(entity1, entity2);
        const compareResult2 = service.compareRiesgo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRiesgo(entity1, entity2);
        const compareResult2 = service.compareRiesgo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
