import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IVulnerabilidades } from '../vulnerabilidades.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../vulnerabilidades.test-samples';

import { VulnerabilidadesService } from './vulnerabilidades.service';

const requireRestSample: IVulnerabilidades = {
  ...sampleWithRequiredData,
};

describe('Vulnerabilidades Service', () => {
  let service: VulnerabilidadesService;
  let httpMock: HttpTestingController;
  let expectedResult: IVulnerabilidades | IVulnerabilidades[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(VulnerabilidadesService);
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

    it('should create a Vulnerabilidades', () => {
      const vulnerabilidades = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(vulnerabilidades).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Vulnerabilidades', () => {
      const vulnerabilidades = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(vulnerabilidades).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Vulnerabilidades', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Vulnerabilidades', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Vulnerabilidades', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addVulnerabilidadesToCollectionIfMissing', () => {
      it('should add a Vulnerabilidades to an empty array', () => {
        const vulnerabilidades: IVulnerabilidades = sampleWithRequiredData;
        expectedResult = service.addVulnerabilidadesToCollectionIfMissing([], vulnerabilidades);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(vulnerabilidades);
      });

      it('should not add a Vulnerabilidades to an array that contains it', () => {
        const vulnerabilidades: IVulnerabilidades = sampleWithRequiredData;
        const vulnerabilidadesCollection: IVulnerabilidades[] = [
          {
            ...vulnerabilidades,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addVulnerabilidadesToCollectionIfMissing(vulnerabilidadesCollection, vulnerabilidades);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Vulnerabilidades to an array that doesn't contain it", () => {
        const vulnerabilidades: IVulnerabilidades = sampleWithRequiredData;
        const vulnerabilidadesCollection: IVulnerabilidades[] = [sampleWithPartialData];
        expectedResult = service.addVulnerabilidadesToCollectionIfMissing(vulnerabilidadesCollection, vulnerabilidades);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(vulnerabilidades);
      });

      it('should add only unique Vulnerabilidades to an array', () => {
        const vulnerabilidadesArray: IVulnerabilidades[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const vulnerabilidadesCollection: IVulnerabilidades[] = [sampleWithRequiredData];
        expectedResult = service.addVulnerabilidadesToCollectionIfMissing(vulnerabilidadesCollection, ...vulnerabilidadesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const vulnerabilidades: IVulnerabilidades = sampleWithRequiredData;
        const vulnerabilidades2: IVulnerabilidades = sampleWithPartialData;
        expectedResult = service.addVulnerabilidadesToCollectionIfMissing([], vulnerabilidades, vulnerabilidades2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(vulnerabilidades);
        expect(expectedResult).toContain(vulnerabilidades2);
      });

      it('should accept null and undefined values', () => {
        const vulnerabilidades: IVulnerabilidades = sampleWithRequiredData;
        expectedResult = service.addVulnerabilidadesToCollectionIfMissing([], null, vulnerabilidades, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(vulnerabilidades);
      });

      it('should return initial array if no Vulnerabilidades is added', () => {
        const vulnerabilidadesCollection: IVulnerabilidades[] = [sampleWithRequiredData];
        expectedResult = service.addVulnerabilidadesToCollectionIfMissing(vulnerabilidadesCollection, undefined, null);
        expect(expectedResult).toEqual(vulnerabilidadesCollection);
      });
    });

    describe('compareVulnerabilidades', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareVulnerabilidades(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareVulnerabilidades(entity1, entity2);
        const compareResult2 = service.compareVulnerabilidades(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareVulnerabilidades(entity1, entity2);
        const compareResult2 = service.compareVulnerabilidades(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareVulnerabilidades(entity1, entity2);
        const compareResult2 = service.compareVulnerabilidades(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
