import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFornecedor } from '../fornecedor.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../fornecedor.test-samples';

import { FornecedorService } from './fornecedor.service';

const requireRestSample: IFornecedor = {
  ...sampleWithRequiredData,
};

describe('Fornecedor Service', () => {
  let service: FornecedorService;
  let httpMock: HttpTestingController;
  let expectedResult: IFornecedor | IFornecedor[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FornecedorService);
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

    it('should create a Fornecedor', () => {
      const fornecedor = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fornecedor).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Fornecedor', () => {
      const fornecedor = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fornecedor).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Fornecedor', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Fornecedor', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Fornecedor', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFornecedorToCollectionIfMissing', () => {
      it('should add a Fornecedor to an empty array', () => {
        const fornecedor: IFornecedor = sampleWithRequiredData;
        expectedResult = service.addFornecedorToCollectionIfMissing([], fornecedor);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fornecedor);
      });

      it('should not add a Fornecedor to an array that contains it', () => {
        const fornecedor: IFornecedor = sampleWithRequiredData;
        const fornecedorCollection: IFornecedor[] = [
          {
            ...fornecedor,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFornecedorToCollectionIfMissing(fornecedorCollection, fornecedor);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Fornecedor to an array that doesn't contain it", () => {
        const fornecedor: IFornecedor = sampleWithRequiredData;
        const fornecedorCollection: IFornecedor[] = [sampleWithPartialData];
        expectedResult = service.addFornecedorToCollectionIfMissing(fornecedorCollection, fornecedor);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fornecedor);
      });

      it('should add only unique Fornecedor to an array', () => {
        const fornecedorArray: IFornecedor[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fornecedorCollection: IFornecedor[] = [sampleWithRequiredData];
        expectedResult = service.addFornecedorToCollectionIfMissing(fornecedorCollection, ...fornecedorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fornecedor: IFornecedor = sampleWithRequiredData;
        const fornecedor2: IFornecedor = sampleWithPartialData;
        expectedResult = service.addFornecedorToCollectionIfMissing([], fornecedor, fornecedor2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fornecedor);
        expect(expectedResult).toContain(fornecedor2);
      });

      it('should accept null and undefined values', () => {
        const fornecedor: IFornecedor = sampleWithRequiredData;
        expectedResult = service.addFornecedorToCollectionIfMissing([], null, fornecedor, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fornecedor);
      });

      it('should return initial array if no Fornecedor is added', () => {
        const fornecedorCollection: IFornecedor[] = [sampleWithRequiredData];
        expectedResult = service.addFornecedorToCollectionIfMissing(fornecedorCollection, undefined, null);
        expect(expectedResult).toEqual(fornecedorCollection);
      });
    });

    describe('compareFornecedor', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFornecedor(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFornecedor(entity1, entity2);
        const compareResult2 = service.compareFornecedor(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFornecedor(entity1, entity2);
        const compareResult2 = service.compareFornecedor(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFornecedor(entity1, entity2);
        const compareResult2 = service.compareFornecedor(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
