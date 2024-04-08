import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IBem } from '../bem.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../bem.test-samples';

import { BemService, RestBem } from './bem.service';

const requireRestSample: RestBem = {
  ...sampleWithRequiredData,
  dataAquisicao: sampleWithRequiredData.dataAquisicao?.format(DATE_FORMAT),
};

describe('Bem Service', () => {
  let service: BemService;
  let httpMock: HttpTestingController;
  let expectedResult: IBem | IBem[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BemService);
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

    it('should create a Bem', () => {
      const bem = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(bem).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Bem', () => {
      const bem = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(bem).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Bem', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Bem', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Bem', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addBemToCollectionIfMissing', () => {
      it('should add a Bem to an empty array', () => {
        const bem: IBem = sampleWithRequiredData;
        expectedResult = service.addBemToCollectionIfMissing([], bem);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bem);
      });

      it('should not add a Bem to an array that contains it', () => {
        const bem: IBem = sampleWithRequiredData;
        const bemCollection: IBem[] = [
          {
            ...bem,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBemToCollectionIfMissing(bemCollection, bem);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Bem to an array that doesn't contain it", () => {
        const bem: IBem = sampleWithRequiredData;
        const bemCollection: IBem[] = [sampleWithPartialData];
        expectedResult = service.addBemToCollectionIfMissing(bemCollection, bem);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bem);
      });

      it('should add only unique Bem to an array', () => {
        const bemArray: IBem[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const bemCollection: IBem[] = [sampleWithRequiredData];
        expectedResult = service.addBemToCollectionIfMissing(bemCollection, ...bemArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bem: IBem = sampleWithRequiredData;
        const bem2: IBem = sampleWithPartialData;
        expectedResult = service.addBemToCollectionIfMissing([], bem, bem2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bem);
        expect(expectedResult).toContain(bem2);
      });

      it('should accept null and undefined values', () => {
        const bem: IBem = sampleWithRequiredData;
        expectedResult = service.addBemToCollectionIfMissing([], null, bem, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bem);
      });

      it('should return initial array if no Bem is added', () => {
        const bemCollection: IBem[] = [sampleWithRequiredData];
        expectedResult = service.addBemToCollectionIfMissing(bemCollection, undefined, null);
        expect(expectedResult).toEqual(bemCollection);
      });
    });

    describe('compareBem', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBem(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareBem(entity1, entity2);
        const compareResult2 = service.compareBem(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareBem(entity1, entity2);
        const compareResult2 = service.compareBem(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareBem(entity1, entity2);
        const compareResult2 = service.compareBem(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
