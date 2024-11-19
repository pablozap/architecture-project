import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAmenazas } from '../amenazas.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../amenazas.test-samples';

import { AmenazasService } from './amenazas.service';

const requireRestSample: IAmenazas = {
  ...sampleWithRequiredData,
};

describe('Amenazas Service', () => {
  let service: AmenazasService;
  let httpMock: HttpTestingController;
  let expectedResult: IAmenazas | IAmenazas[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AmenazasService);
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

    it('should create a Amenazas', () => {
      const amenazas = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(amenazas).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Amenazas', () => {
      const amenazas = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(amenazas).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Amenazas', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Amenazas', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Amenazas', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAmenazasToCollectionIfMissing', () => {
      it('should add a Amenazas to an empty array', () => {
        const amenazas: IAmenazas = sampleWithRequiredData;
        expectedResult = service.addAmenazasToCollectionIfMissing([], amenazas);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(amenazas);
      });

      it('should not add a Amenazas to an array that contains it', () => {
        const amenazas: IAmenazas = sampleWithRequiredData;
        const amenazasCollection: IAmenazas[] = [
          {
            ...amenazas,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAmenazasToCollectionIfMissing(amenazasCollection, amenazas);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Amenazas to an array that doesn't contain it", () => {
        const amenazas: IAmenazas = sampleWithRequiredData;
        const amenazasCollection: IAmenazas[] = [sampleWithPartialData];
        expectedResult = service.addAmenazasToCollectionIfMissing(amenazasCollection, amenazas);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(amenazas);
      });

      it('should add only unique Amenazas to an array', () => {
        const amenazasArray: IAmenazas[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const amenazasCollection: IAmenazas[] = [sampleWithRequiredData];
        expectedResult = service.addAmenazasToCollectionIfMissing(amenazasCollection, ...amenazasArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const amenazas: IAmenazas = sampleWithRequiredData;
        const amenazas2: IAmenazas = sampleWithPartialData;
        expectedResult = service.addAmenazasToCollectionIfMissing([], amenazas, amenazas2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(amenazas);
        expect(expectedResult).toContain(amenazas2);
      });

      it('should accept null and undefined values', () => {
        const amenazas: IAmenazas = sampleWithRequiredData;
        expectedResult = service.addAmenazasToCollectionIfMissing([], null, amenazas, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(amenazas);
      });

      it('should return initial array if no Amenazas is added', () => {
        const amenazasCollection: IAmenazas[] = [sampleWithRequiredData];
        expectedResult = service.addAmenazasToCollectionIfMissing(amenazasCollection, undefined, null);
        expect(expectedResult).toEqual(amenazasCollection);
      });
    });

    describe('compareAmenazas', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAmenazas(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAmenazas(entity1, entity2);
        const compareResult2 = service.compareAmenazas(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAmenazas(entity1, entity2);
        const compareResult2 = service.compareAmenazas(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAmenazas(entity1, entity2);
        const compareResult2 = service.compareAmenazas(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
