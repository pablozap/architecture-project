import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IGrupoActivos } from '../grupo-activos.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../grupo-activos.test-samples';

import { GrupoActivosService } from './grupo-activos.service';

const requireRestSample: IGrupoActivos = {
  ...sampleWithRequiredData,
};

describe('GrupoActivos Service', () => {
  let service: GrupoActivosService;
  let httpMock: HttpTestingController;
  let expectedResult: IGrupoActivos | IGrupoActivos[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(GrupoActivosService);
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

    it('should create a GrupoActivos', () => {
      const grupoActivos = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(grupoActivos).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GrupoActivos', () => {
      const grupoActivos = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(grupoActivos).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GrupoActivos', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GrupoActivos', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GrupoActivos', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGrupoActivosToCollectionIfMissing', () => {
      it('should add a GrupoActivos to an empty array', () => {
        const grupoActivos: IGrupoActivos = sampleWithRequiredData;
        expectedResult = service.addGrupoActivosToCollectionIfMissing([], grupoActivos);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(grupoActivos);
      });

      it('should not add a GrupoActivos to an array that contains it', () => {
        const grupoActivos: IGrupoActivos = sampleWithRequiredData;
        const grupoActivosCollection: IGrupoActivos[] = [
          {
            ...grupoActivos,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGrupoActivosToCollectionIfMissing(grupoActivosCollection, grupoActivos);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GrupoActivos to an array that doesn't contain it", () => {
        const grupoActivos: IGrupoActivos = sampleWithRequiredData;
        const grupoActivosCollection: IGrupoActivos[] = [sampleWithPartialData];
        expectedResult = service.addGrupoActivosToCollectionIfMissing(grupoActivosCollection, grupoActivos);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(grupoActivos);
      });

      it('should add only unique GrupoActivos to an array', () => {
        const grupoActivosArray: IGrupoActivos[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const grupoActivosCollection: IGrupoActivos[] = [sampleWithRequiredData];
        expectedResult = service.addGrupoActivosToCollectionIfMissing(grupoActivosCollection, ...grupoActivosArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const grupoActivos: IGrupoActivos = sampleWithRequiredData;
        const grupoActivos2: IGrupoActivos = sampleWithPartialData;
        expectedResult = service.addGrupoActivosToCollectionIfMissing([], grupoActivos, grupoActivos2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(grupoActivos);
        expect(expectedResult).toContain(grupoActivos2);
      });

      it('should accept null and undefined values', () => {
        const grupoActivos: IGrupoActivos = sampleWithRequiredData;
        expectedResult = service.addGrupoActivosToCollectionIfMissing([], null, grupoActivos, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(grupoActivos);
      });

      it('should return initial array if no GrupoActivos is added', () => {
        const grupoActivosCollection: IGrupoActivos[] = [sampleWithRequiredData];
        expectedResult = service.addGrupoActivosToCollectionIfMissing(grupoActivosCollection, undefined, null);
        expect(expectedResult).toEqual(grupoActivosCollection);
      });
    });

    describe('compareGrupoActivos', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGrupoActivos(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareGrupoActivos(entity1, entity2);
        const compareResult2 = service.compareGrupoActivos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareGrupoActivos(entity1, entity2);
        const compareResult2 = service.compareGrupoActivos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareGrupoActivos(entity1, entity2);
        const compareResult2 = service.compareGrupoActivos(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
