import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IControles } from '../controles.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../controles.test-samples';

import { ControlesService } from './controles.service';

const requireRestSample: IControles = {
  ...sampleWithRequiredData,
};

describe('Controles Service', () => {
  let service: ControlesService;
  let httpMock: HttpTestingController;
  let expectedResult: IControles | IControles[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ControlesService);
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

    it('should create a Controles', () => {
      const controles = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(controles).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Controles', () => {
      const controles = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(controles).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Controles', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Controles', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Controles', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addControlesToCollectionIfMissing', () => {
      it('should add a Controles to an empty array', () => {
        const controles: IControles = sampleWithRequiredData;
        expectedResult = service.addControlesToCollectionIfMissing([], controles);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(controles);
      });

      it('should not add a Controles to an array that contains it', () => {
        const controles: IControles = sampleWithRequiredData;
        const controlesCollection: IControles[] = [
          {
            ...controles,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addControlesToCollectionIfMissing(controlesCollection, controles);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Controles to an array that doesn't contain it", () => {
        const controles: IControles = sampleWithRequiredData;
        const controlesCollection: IControles[] = [sampleWithPartialData];
        expectedResult = service.addControlesToCollectionIfMissing(controlesCollection, controles);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(controles);
      });

      it('should add only unique Controles to an array', () => {
        const controlesArray: IControles[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const controlesCollection: IControles[] = [sampleWithRequiredData];
        expectedResult = service.addControlesToCollectionIfMissing(controlesCollection, ...controlesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const controles: IControles = sampleWithRequiredData;
        const controles2: IControles = sampleWithPartialData;
        expectedResult = service.addControlesToCollectionIfMissing([], controles, controles2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(controles);
        expect(expectedResult).toContain(controles2);
      });

      it('should accept null and undefined values', () => {
        const controles: IControles = sampleWithRequiredData;
        expectedResult = service.addControlesToCollectionIfMissing([], null, controles, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(controles);
      });

      it('should return initial array if no Controles is added', () => {
        const controlesCollection: IControles[] = [sampleWithRequiredData];
        expectedResult = service.addControlesToCollectionIfMissing(controlesCollection, undefined, null);
        expect(expectedResult).toEqual(controlesCollection);
      });
    });

    describe('compareControles', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareControles(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareControles(entity1, entity2);
        const compareResult2 = service.compareControles(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareControles(entity1, entity2);
        const compareResult2 = service.compareControles(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareControles(entity1, entity2);
        const compareResult2 = service.compareControles(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
